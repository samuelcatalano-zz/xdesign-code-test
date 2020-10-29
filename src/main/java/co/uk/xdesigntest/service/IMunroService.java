package co.uk.xdesigntest.service;

import co.uk.xdesigntest.entity.Munro;
import java.util.List;
import java.util.Optional;

/**
 * @author Samuel Catalano
 * @since October, 2020
 */
interface IMunroService {

    /**
     * Find all Munros.
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return List of {@link Munro} according properties
     * @throws Exception the exception to be launched
     */
    List<Munro> findAllMunros(final String hillCategory, final String orderHeightBy, final String orderNameBy, final Integer limit) throws Exception;

    /**
     * Find Munro by runningNo.
     * @param runningNo the running number
     * @return {@link Munro} by runningNo
     * @throws Exception the exception to be launched
     */
    Optional<Munro> findByRunningNumber(final int runningNo) throws Exception;

    /**
     * Returns list of {@link Munro} by specific range of height.
     * @param minimumHeightInMetre the minimum height acceptable : required
     * @param maximumHeightInMetre the maximum height acceptable : required
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return list of {@link Munro} by specific range of height.
     * @throws Exception the exception to be launched
     */
    List<Munro> getMunroSpecificRangeHeight(final double minimumHeightInMetre, final double maximumHeightInMetre, final String hillCategory, final String orderHeightBy,
                                            final String orderNameBy,
                                            final Integer limit) throws Exception;

    /**
     * Returns list of {@link Munro} by a minimum height.
     * @param heightInMetre the minimum height acceptable : required
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return list of {@link Munro} by a minimum height.
     * @throws Exception the exception to be launched
     */
    List<Munro> getMunrosByMinimumHeight(final double heightInMetre, final String hillCategory, final String orderHeightBy, final String orderNameBy, final Integer limit) throws Exception;

    /**
     * Returns list of {@link Munro} by a minimum height.
     * @param heightInMetre the maximum height acceptable : required
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return list of {@link Munro} by a minimum height.
     * @throws Exception the exception to be launched
     */
    List<Munro> getMunrosByMaximumHeight(final double heightInMetre, final String hillCategory, final String orderHeightBy, final String orderNameBy, final Integer limit) throws Exception;
}