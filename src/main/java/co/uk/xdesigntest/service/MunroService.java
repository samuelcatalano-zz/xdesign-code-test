package co.uk.xdesigntest.service;

import co.uk.xdesigntest.dao.MunroDao;
import co.uk.xdesigntest.entity.Munro;
import co.uk.xdesigntest.enums.CategoryEnum;
import co.uk.xdesigntest.enums.SortTypeEnum;
import co.uk.xdesigntest.exception.ValidationException;
import co.uk.xdesigntest.service.base.IMunroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Samuel Catalano
 * @since October, 2020
 */

@Service
@Slf4j
public class MunroService implements IMunroService {

    @Autowired
    private MunroDao dao;

    /**
     * Find Munro by runningNo.
     * @param runningNo the running number
     * @return {@link Munro} by runningNo
     */
    @Override
    public Optional<Munro> findByRunningNumber(int runningNo) {
        var munros = this.dao.listAll();
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
    public Stream<Munro> getMunros(Double minHeight, Double maxHeight, String category, String orderHeightBy, String orderNameBy, Integer limit) throws ValidationException {
        if (invalidLimit(limit))
            throw new ValidationException("Invalid value for limit: " + limit);

        if (invalidHeight(minHeight, maxHeight))
            throw new ValidationException("Heights cannot be less than zero");

        if (invalidMaxHeightLessThanMinHeight(minHeight, maxHeight))
            throw new ValidationException("Maximum height could not be less than minimum height");

        final Stream<Munro> munros = this.dao.listAll().stream();
        return this.applyCriteria(munros, minHeight, maxHeight, category, orderHeightBy, orderNameBy, limit);
    }

    /**
     * Apply criteria to {@link List} of {@link Munro}.
     * @param result the result after the applied criteria
     * @param orderHeightBy the order by height
     * @param orderNameBy the order by name
     * @return criteria to {@link List} of {@link Munro}.
     */
    private Stream<Munro> applyCriteria(Stream<Munro> result, Double minHeight, Double maxHeight, String category, String orderHeightBy, String orderNameBy, Integer limit) {
        result = this.applyCategory(result, category);
        result = this.applyHeights(result, minHeight, maxHeight);
        result = this.orderByHeight(result, orderHeightBy);
        result = this.orderByName(result, orderNameBy);
        result = this.applyLimit(result, limit);

        return result;
    }

    /**
     * Order list by height.
     * @param result list ordered by height
     * @param orderHeightBy <code>ASC</code> or <code>DESC</code>
     * @return list ordered by height
     */
    private Stream<Munro> orderByHeight(Stream<Munro> result, String orderHeightBy) {
        if ((orderHeightBy != null) && (orderHeightBy.toUpperCase().equals(SortTypeEnum.ASC.getName())))
            result = this.applySortedHeightAsc(result);

        if ((orderHeightBy != null) && (orderHeightBy.toUpperCase().equals(SortTypeEnum.DESC.getName())))
            result = this.applySortedHeightDesc(result);

        return result;
    }

    /**
     * Order list by name.
     * @param result list ordered by height
     * @param orderNameBy <code>ASC</code> or <code>DESC</code>
     * @return list ordered by name
     */
    private Stream<Munro> orderByName(Stream<Munro> result, final String orderNameBy) {
        if ((orderNameBy != null) && (orderNameBy.toUpperCase().equals(SortTypeEnum.ASC.getName())))
            result = this.applySortedNameAsc(result);

        if ((orderNameBy != null) && (orderNameBy.toUpperCase().equals(SortTypeEnum.DESC.getName())))
            result = this.applySortedNameDesc(result);

        return result;
    }

    /**
     * Applies the heigh criteria at the Munros list if defined by the user.
     * @param list the list of the Munros
     * @param minHeight the minimum height criteria
     * @param maxHeight the maximum height criteria
     * @return the heigh criteria at the Munros list if defined by the user
     */
    private Stream<Munro> applyHeights(Stream<Munro> list, final Double minHeight, final Double maxHeight) {
        if (minHeight != null)
            list = list.parallel().filter(munro -> munro.getHeightInMetre() >= minHeight);

        if (maxHeight != null)
            list = list.parallel().filter(munro -> munro.getHeightInMetre() < maxHeight);

        return list;
    }

    /**
     * Applies the limit criteria at the Munros list if defined by the user.
     * @param list the list of the Munros
     * @param limit the limit criteria
     * @return the limit criteria at the Munros list if defined by the user
     */
    private Stream<Munro> applyLimit(Stream<Munro> list, final Integer limit) {
        if (limit != null)
            list = list.parallel().limit(limit);

        return list;
    }

    /**
     * Applies the category criteria at the Munros list if defined by the user.
     * @param list the list of the Munros
     * @param category the category criteria
     * @return the category criteria at the Munros list if defined by the user
     */
    private Stream<Munro> applyCategory(Stream<Munro> list, String category) {
        if (category != null)
            list = list.parallel().filter(munro -> munro.getPost1997().equals(CategoryEnum.get(category).getName()));
        else
            list = list.parallel().filter(munro -> !munro.getPost1997().equals(""));

        return list;
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
    private Stream<Munro> applySortedHeightAsc(final Stream<Munro> result) {
        return result.parallel().sorted(Comparator.comparing(Munro::getHeightInMetre));
    }

    /**
     * Applies a sorted result ordered by height desc.
     * @param result the sorted result
     * @return sorted result ordered by height desc
     */
    private Stream<Munro> applySortedHeightDesc(final Stream<Munro> result) {
        return result.parallel().sorted(Comparator.comparing(Munro::getHeightInMetre).reversed());
    }

    /**
     * Applies a sorted result ordered by orderNameBy asc.
     * @param result the sorted result
     * @return sorted result ordered by height asc
     */
    private Stream<Munro> applySortedNameAsc(final Stream<Munro> result) {
        return result.parallel().sorted(Comparator.comparing(Munro::getName));
    }

    /**
     * Applies a sorted result ordered by orderNameBy desc.
     * @param result the sorted result
     * @return sorted result ordered by height desc
     */
    private Stream<Munro> applySortedNameDesc(final Stream<Munro> result) {
        return result.parallel().sorted(Comparator.comparing(Munro::getName).reversed());
    }
}