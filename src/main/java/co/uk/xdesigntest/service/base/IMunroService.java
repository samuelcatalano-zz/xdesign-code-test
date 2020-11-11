package co.uk.xdesigntest.service.base;

import co.uk.xdesigntest.entity.Munro;
import co.uk.xdesigntest.exception.ValidationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Samuel Catalano
 * @since October, 2020
 */
public interface IMunroService {

    /**
     * Find Munro by runningNo.
     * @param runningNo the running number
     * @return {@link Munro} by runningNo
     * @throws ValidationException the exception to be launched
     */
    Optional<Munro> findByRunningNumber(final int runningNo);

    /**
     * Returns list of {@link Munro} by specific range of height.
     * @param minHeightInMetre the minimum height acceptable : optional
     * @param maxHeightInMetre the maximum height acceptable : optional
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return list of {@link Munro} by specific range of height.
     */
    Stream<Munro> getMunros(Double minHeightInMetre, Double maxHeightInMetre, String hillCategory, String orderHeightBy, String orderNameBy, Integer limit) throws ValidationException;
}