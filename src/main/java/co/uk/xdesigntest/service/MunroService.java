package co.uk.xdesigntest.service;

import co.uk.xdesigntest.entity.Munro;
import co.uk.xdesigntest.exception.ValidationException;
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

    /**
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
     * Find all Munros.
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return List of {@link Munro} according properties
     * @throws Exception the exception to be launched
     */
    @Override
    public List<Munro> findAllMunros(final String hillCategory, final String orderHeightBy, final String orderNameBy, final Integer limit) throws ValidationException {
        var result = munros.stream()
                    .filter(munro -> !munro.getPost1997().equals(""))
                    .filter(munro -> hillCategory != null ? munro.getPost1997().equals(hillCategory) :
                            munro.getPost1997().equals("MUN") || munro.getPost1997().equals("TOP"))
                    .collect(Collectors.toList());

        if (orderHeightBy != null)
            result = this.applySortedHeight(result, orderHeightBy);

        if (orderNameBy != null)
            result = this.applySortedName(result, orderNameBy);

        result = result.stream().limit(limit != null ? limit : munros.size()).collect(Collectors.toList());
        return result;
    }

    /**
     * Find Munro by runningNo.
     * @param runningNo the running number
     * @return {@link Munro} by runningNo
     */
    @Override
    public Optional<Munro> findByRunningNumber(final int runningNo) {
        return munros.stream()
               .filter(m -> m.getRunningNo() == runningNo)
               .filter(munro -> (!munro.getPost1997().equals("")))
               .findFirst();
    }

    /**
     * Returns list of {@link Munro} by a minimum height.
     * @param heightInMetre the minimum height acceptable : required
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return list of {@link Munro} by a minimum height.
     * @throws ValidationException the exception to be launched
     */
    @Override
    public List<Munro> getMunrosByMinimumHeight(final double heightInMetre,
                                                final String hillCategory,
                                                final String orderHeightBy,
                                                final String orderNameBy,
                                                final Integer limit) throws ValidationException {
        if (heightInMetre < 0) {
            throw new ValidationException("Invalid value for height");
        }

        var result = munros.stream()
                     .filter(munro -> munro.getHeightInMetre() >= heightInMetre)
                     .filter(munro -> !munro.getPost1997().equals(""))
                     .filter(munro -> hillCategory != null ? munro.getPost1997().equals(hillCategory) :
                             munro.getPost1997().equals("MUN") || munro.getPost1997().equals("TOP"))
                     .collect(Collectors.toList());

        if (orderHeightBy != null)
            result = this.applySortedHeight(result, orderHeightBy);

        if (orderNameBy != null)
            result = this.applySortedName(result, orderNameBy);

        result = result.stream().limit(limit != null ? limit : munros.size()).collect(Collectors.toList());
        return result;
    }

    /**
     * Returns list of {@link Munro} by a maximum height.
     * @param heightInMetre the maximum height acceptable : required
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return list of {@link Munro} by a maximum height.
     * @throws ValidationException the exception to be launched
     */
    @Override
    public List<Munro> getMunrosByMaximumHeight(final double heightInMetre,
                                                final String hillCategory,
                                                final String orderHeightBy,
                                                final String orderNameBy,
                                                final Integer limit) throws ValidationException {
        if (heightInMetre < 0) {
            throw new ValidationException("Invalid value for height");
        }

        var result = munros.stream()
                    .filter(munro -> munro.getHeightInMetre() < heightInMetre)
                    .filter(munro -> !munro.getPost1997().equals(""))
                    .filter(munro -> hillCategory != null ? munro.getPost1997().equals(hillCategory) :
                            munro.getPost1997().equals("MUN") || munro.getPost1997().equals("TOP"))
                    .collect(Collectors.toList());

        if (orderHeightBy != null)
            result = this.applySortedHeight(result, orderHeightBy);

        if (orderNameBy != null)
            result = this.applySortedName(result, orderNameBy);

        result = result.stream().limit(limit != null ? limit : munros.size()).collect(Collectors.toList());
        return result;
    }

    /**
     * Returns list of {@link Munro} by specific range of height.
     * @param minimumHeightInMetre the minimum height acceptable : required
     * @param maximumHeightInMetre the maximum height acceptable : required
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return list of {@link Munro} by specific range of height.
     * @throws ValidationException the exception to be launched
     */
    @Override
    public List<Munro> getMunroSpecificRangeHeight(final double minimumHeightInMetre,
                                                   final double maximumHeightInMetre,
                                                   final String orderHeightBy,
                                                   final String orderNameBy,
                                                   final String hillCategory,
                                                   final Integer limit) throws ValidationException {

        if (minimumHeightInMetre < 0 || maximumHeightInMetre < 0) {
            throw new ValidationException("Invalid value for height");
        }
        if (minimumHeightInMetre > maximumHeightInMetre) {
            throw new ValidationException("Maximum height is less than the minimum height");
        }

        var result = munros.stream()
                .filter(munro -> munro.getHeightInMetre() >= minimumHeightInMetre && munro.getHeightInMetre() < maximumHeightInMetre)
                .filter(munro -> !munro.getPost1997().equals(""))
                .filter(munro -> hillCategory != null ? munro.getPost1997().equals(hillCategory) :
                        munro.getPost1997().equals("MUN") || munro.getPost1997().equals("TOP"))
                .collect(Collectors.toList());

        if (orderHeightBy != null)
            result = this.applySortedHeight(result, orderHeightBy);

        if (orderNameBy != null)
            result = this.applySortedName(result, orderNameBy);

        result = result.stream().limit(limit != null ? limit : munros.size()).collect(Collectors.toList());
        return result;
    }

    /**
     * Applies a sorted result ordered by height asc or desc.
     * @param result the sorted result
     * @param orderHeightBy the conditional <code>asc</code> or <code>desc</code> : optional
     * @return sorted result ordered by height asc or desc
     */
    private List<Munro> applySortedHeight(List<Munro> result, final String orderHeightBy) {
        result = orderHeightBy.equalsIgnoreCase("asc") ?
                 result.stream().sorted(Comparator.comparing(Munro::getHeightInMetre)).collect(Collectors.toList()) :
                 result.stream().sorted(Comparator.comparing(Munro::getHeightInMetre).reversed()).collect(Collectors.toList());

        return result;
    }

    /**
     * Applies a sorted result ordered by orderNameBy asc or desc.
     * @param result the sorted result
     * @param orderNameBy the conditional <code>asc</code> or <code>desc</code> : optional
     * @return sorted result ordered by height asc or desc
     */
    private List<Munro> applySortedName(List<Munro> result, final String orderNameBy) {
        result = orderNameBy.equalsIgnoreCase("asc") ?
                 result.stream().sorted(Comparator.comparing(Munro::getName)).collect(Collectors.toList()) :
                 result.stream().sorted(Comparator.comparing(Munro::getName).reversed()).collect(Collectors.toList());

        return result;
    }
}