package co.uk.xdesigntest.service;

import co.uk.xdesigntest.entity.Munro;
import co.uk.xdesigntest.enums.CategoryEnum;
import co.uk.xdesigntest.enums.SortTypeEnum;
import co.uk.xdesigntest.exception.ValidationException;
import co.uk.xdesigntest.service.base.IMunroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Samuel Catalano
 * @since October, 2020
 */

@Service
@Slf4j
public class MunroService implements IMunroService {

    private static List<Munro> munros;

    /*
     This it's just to simulate a cached list instead of load it on every single request
     In an ideal context, the best solution would be using a Redis or a true cache implementation like Google CacheBuilder for example
     */
    static {
        try {
            var csvInMemory = new CSVInMemory();
            munros = csvInMemory.readAndConvertMunrosFromCSVFile();
        } catch (final FileNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Find Munro by runningNo.
     * @param runningNo the running number
     * @return {@link Munro} by runningNo
     */
    @Override
    public Optional<Munro> findByRunningNumber(int runningNo) {
        return munros.stream().filter(m -> m.getRunningNo() == runningNo).filter(munro -> (!munro.getPost1997().equals(""))).findFirst();
    }

    /**
     * Returns list of {@link Munro} by specific range of height.
     * @param minHeight the minimum height acceptable : optional
     * @param maxHeight the maximum height acceptable : optional
     * @param category the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return list of {@link Munro} by specific range of height.
     */
    @Override
    public List<Munro> getMunros(Double minHeight, Double maxHeight, String category, String orderHeightBy, String orderNameBy, Integer limit) throws ValidationException {
        if (invalidLimit(limit))
            throw new ValidationException("Invalid value for limit: " + limit);

        if (invalidHeight(minHeight, maxHeight))
            throw new ValidationException("Heights cannot be less than zero");

        if (invalidMaxHeightLessThanMinHeight(minHeight, maxHeight))
            throw new ValidationException("Maximum height could not be less than minimum height");

        return this.applyCriteria(munros, minHeight, maxHeight, category, orderHeightBy, orderNameBy, limit);
    }

    /**
     * Apply criteria to {@link List} of {@link Munro}.
     * @param result the result after the applied criteria
     * @param orderHeightBy the order by height
     * @param orderNameBy the order by name
     * @return criteria to {@link List} of {@link Munro}.
     */
    private List<Munro> applyCriteria(List<Munro> result, Double minHeight, Double maxHeight, String category, String orderHeightBy, String orderNameBy, Integer limit) {
        if (orderHeightBy != null && orderHeightBy.toUpperCase().equals(SortTypeEnum.ASC.getName()))
            result = this.applySortedHeightAsc(result);

        if (orderHeightBy != null && orderHeightBy.toUpperCase().equals(SortTypeEnum.DESC.getName()))
            result = this.applySortedHeightDesc(result);

        if (orderNameBy != null && orderNameBy.toUpperCase().equals(SortTypeEnum.ASC.getName()))
            result = this.applySortedNameAsc(result);

        if (orderNameBy != null && orderNameBy.toUpperCase().equals(SortTypeEnum.DESC.getName()))
            result = this.applySortedNameDesc(result);

        result = this.applyCategory(result, category);
        result = this.applyHeights(result, minHeight, maxHeight);
        result = this.applyLimit(result, limit);

        return result;
    }

    /**
     * Applies the heigh criteria at the Munros list if defined by the user.
     * @param list the list of the Munros
     * @param minHeight the minimum height criteria
     * @param maxHeight the maximum height criteria
     * @return the heigh criteria at the Munros list if defined by the user
     */
    private List<Munro> applyHeights(List<Munro> list, final Double minHeight, final Double maxHeight) {
        if (minHeight != null)
            list = list.stream().filter(munro -> munro.getHeightInMetre() >= minHeight).collect(Collectors.toList());

        if (maxHeight != null)
            list = list.stream().filter(munro -> munro.getHeightInMetre() < maxHeight).collect(Collectors.toList());

        return list;
    }

    /**
     * Applies the limit criteria at the Munros list if defined by the user.
     * @param list the list of the Munros
     * @param limit the limit criteria
     * @return the limit criteria at the Munros list if defined by the user
     */
    private List<Munro> applyLimit(List<Munro> list, final Integer limit) {
        if (limit != null)
            list = list.stream().limit(limit).collect(Collectors.toList());

        return list;
    }

    /**
     * Applies the category criteria at the Munros list if defined by the user.
     * @param list the list of the Munros
     * @param category the category criteria
     * @return the category criteria at the Munros list if defined by the user
     */
    private List<Munro> applyCategory(final List<Munro> list, String category) {
        if (category != null) {
            return list.stream().filter(munro -> munro.getPost1997().equals(CategoryEnum.get(category).getName())).collect(Collectors.toList());
        } else {
            return list.stream().filter(munro -> !munro.getPost1997().equals("")).collect(Collectors.toList());
        }
    }

    /**
     * Check invalid criteria that maxHeight could not be less than minHeight.
     * @param minHeight the minimum height value
     * @param maxHeight the maximum height value
     * @return <code>true</code> or <code>false</code>
     */
    private boolean invalidMaxHeightLessThanMinHeight(final Double minHeight, final Double maxHeight) {
        return (minHeight != null && maxHeight != null && maxHeight < minHeight);
    }

    /**
     * Check invalid values to the heights.
     * @param minHeight the minimum height value
     * @param maxHeight the maximum height value
     * @return <code>true</code> or <code>false</code>
     */
    private boolean invalidHeight(final Double minHeight, final Double maxHeight) {
        return (minHeight != null && minHeight < 0) || (maxHeight != null && maxHeight < 0);
    }

    /**
     * Check invalid value to the limit.
     * @param limit the limit value
     * @return <code>true</code> or <code>false</code>
     */
    private boolean invalidLimit(final Integer limit) {
        return (limit != null && limit <= 0);
    }

    /**
     * Applies a sorted result ordered by height asc.
     * @param result the sorted result
     * @return sorted result ordered by height asc
     */
    private List<Munro> applySortedHeightAsc(final List<Munro> result) {
        return result.stream().sorted(Comparator.comparing(Munro::getHeightInMetre)).collect(Collectors.toList());
    }

    /**
     * Applies a sorted result ordered by height desc.
     * @param result the sorted result
     * @return sorted result ordered by height desc
     */
    private List<Munro> applySortedHeightDesc(final List<Munro> result) {
        return result.stream().sorted(Comparator.comparing(Munro::getHeightInMetre).reversed()).collect(Collectors.toList());
    }

    /**
     * Applies a sorted result ordered by orderNameBy asc.
     * @param result the sorted result
     * @return sorted result ordered by height asc
     */
    private List<Munro> applySortedNameAsc(final List<Munro> result) {
        return result.stream().sorted(Comparator.comparing(Munro::getName)).collect(Collectors.toList());
    }

    /**
     * Applies a sorted result ordered by orderNameBy desc.
     * @param result the sorted result
     * @return sorted result ordered by height desc
     */
    private List<Munro> applySortedNameDesc(final List<Munro> result) {
        return result.stream().sorted(Comparator.comparing(Munro::getName).reversed()).collect(Collectors.toList());
    }
}