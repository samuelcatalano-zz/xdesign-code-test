package co.uk.xdesigntest.dao;

import co.uk.xdesigntest.dao.base.BaseDao;
import co.uk.xdesigntest.entity.Munro;
import co.uk.xdesigntest.service.CSVInMemory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Samuel Catalano
 * @since October, 2020
 */

@Component
@Slf4j
public class MunroDao implements BaseDao<Munro> {

    @Autowired
    private CSVInMemory csvInMemory;

    private static List<Munro> munros;

    /**
     * Returns all munros from CSV file on resources folder.
     * @return all munros from CSV file on resources folder
     */
    public List<Munro> listAll() {
        if (munros == null || munros.isEmpty()) {
            try {
                munros = csvInMemory.readAndConvertMunrosFromCSVFile();
            } catch (final FileNotFoundException e) {
                log.error(e.getMessage());
            }
        }
        return munros;
    }
}