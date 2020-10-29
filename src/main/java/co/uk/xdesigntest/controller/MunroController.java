package co.uk.xdesigntest.controller;

import co.uk.xdesigntest.exception.ValidationException;
import co.uk.xdesigntest.json.ErrorMessage;
import co.uk.xdesigntest.service.MunroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Samuel Catalano
 * @since October, 2020
 */

@RestController
@RequestMapping(value = "/api/xdesign/munro")
@Slf4j
public class MunroController {

    @Autowired
    private MunroService service;

    /**
     * Find all Munros.
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return List of Munro according properties
     */
    @GetMapping
    public ResponseEntity<?> getAllMunros(@RequestParam(value = "hillCategory", required = false) String hillCategory,
                                          @RequestParam(value = "orderHeightBy", required = false) String orderHeightBy,
                                          @RequestParam(value = "orderNameBy", required = false) String orderNameBy,
                                          @RequestParam(value = "limit", required = false) Integer limit) {
        try {
            return ResponseEntity.ok(this.service.findAllMunros(hillCategory, orderHeightBy, orderNameBy, limit));
        } catch (final ValidationException e) {
            log.error(e.getMessage());
            var error = new ErrorMessage()
                .status(HttpStatus.NOT_FOUND.name())
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage());

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }

    /**
     * Find Munro by runningNo.
     * @param runningNo the running number
     * @return Munro by runningNo
     */
    @GetMapping(value = "/{runningNo}")
    public ResponseEntity<?> getMunroById(@PathVariable(value = "runningNo") final int runningNo) {
        try {
            return ResponseEntity.ok(this.service.findByRunningNumber(runningNo)
                                                 .orElseThrow(() -> new ValidationException("There is no Munro with this runningNo: " + runningNo)));
        } catch (final ValidationException e) {
            log.error(e.getMessage());
            var error = new ErrorMessage()
                .status(HttpStatus.NOT_FOUND.name())
                .code(HttpStatus.NOT_FOUND.value())
                .message("There is no Munro with this runningNo: " + runningNo);

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }

    /**
     * Returns list of Munro by a minimum height.
     * @param heightInMetre the minimum height acceptable : required
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return list of Munro by a minimum height.
     */
    @GetMapping(value = "/minimumHeight/{heightInMetre}")
    public ResponseEntity<?> getMunrosByMinimumHeight(@PathVariable(value = "heightInMetre") final double heightInMetre,
                                                      @RequestParam(value = "hillCategory", required = false) String hillCategory,
                                                      @RequestParam(value = "orderHeightBy", required = false) String orderHeightBy,
                                                      @RequestParam(value = "orderNameBy", required = false) String orderNameBy,
                                                      @RequestParam(value = "limit", required = false) Integer limit) {
        try {
            return ResponseEntity.ok(this.service.getMunrosByMinimumHeight(heightInMetre, hillCategory, orderHeightBy, orderNameBy, limit));
        } catch (final ValidationException e) {
            log.error(e.getMessage());
            var error = new ErrorMessage()
               .status(HttpStatus.BAD_REQUEST.name())
               .code(HttpStatus.BAD_REQUEST.value())
               .message(e.getMessage());

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }

    /**
     * Returns list of Munro by a maximum height.
     * @param heightInMetre the maximum height acceptable : required
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return list of Munro by a maximum height.
     */
    @GetMapping(value = "/maximumHeight/{heightInMetre}")
    public ResponseEntity<?> getMunrosByMaximumHeight(@PathVariable(value = "heightInMetre") final double heightInMetre,
                                                      @RequestParam(value = "hillCategory", required = false) String hillCategory,
                                                      @RequestParam(value = "orderHeightBy", required = false) String orderHeightBy,
                                                      @RequestParam(value = "orderNameBy", required = false) String orderNameBy,
                                                      @RequestParam(value = "limit", required = false) Integer limit) {
        try {
            return ResponseEntity.ok(this.service.getMunrosByMaximumHeight(heightInMetre, hillCategory, orderHeightBy, orderNameBy, limit));
        } catch (final ValidationException e) {
            log.error(e.getMessage());
            var error = new ErrorMessage()
               .status(HttpStatus.BAD_REQUEST.name())
               .code(HttpStatus.BAD_REQUEST.value())
               .message(e.getMessage());

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }

    /**
     * Returns list of Munro by specific range of height.
     * @param minimumHeightInMetre the minimum height acceptable : required
     * @param maximumHeightInMetre the maximum height acceptable : required
     * @param hillCategory the hill category : optional
     * @param orderHeightBy <code>asc</code> or <code>desc</code>  : optional
     * @param orderNameBy <code>asc</code> or <code>desc</code>  : optional
     * @param limit <code>size</code> or <code>size</code>  : optional
     * @return list of Munro by specific range of height.
     */
    @GetMapping(value = "/minimumHeight/{minimumHeightInMetre}/maximumHeight/{maximumHeightInMetre}")
    public ResponseEntity<?> getMunroSpecificRangeHeight(@PathVariable(value = "minimumHeightInMetre") final double minimumHeightInMetre,
                                                         @PathVariable(value = "maximumHeightInMetre") final double maximumHeightInMetre,
                                                         @RequestParam(value = "hillCategory", required = false) String hillCategory,
                                                         @RequestParam(value = "orderHeightBy", required = false) String orderHeightBy,
                                                         @RequestParam(value = "orderNameBy", required = false) String orderNameBy,
                                                         @RequestParam(value = "limit", required = false) Integer limit) {
        try {
            return ResponseEntity.ok(this.service.getMunroSpecificRangeHeight(minimumHeightInMetre, maximumHeightInMetre, hillCategory, orderHeightBy, orderNameBy, limit));
        } catch (final ValidationException e) {
            log.error(e.getMessage());
            var error = new ErrorMessage()
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage());

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }
}