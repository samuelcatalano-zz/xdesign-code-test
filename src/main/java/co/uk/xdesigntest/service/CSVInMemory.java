package co.uk.xdesigntest.service;

import co.uk.xdesigntest.entity.Munro;
import co.uk.xdesigntest.utils.MunroVerifier;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.util.ResourceUtils;
import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * @author Samuel Catalano
 * @since October, 2020
 */

public class CSVInMemory {

    /**
     * Returns {@link List} of {@link Munro} converted from a CSV file.
     * @return {@link List} of {@link Munro} converted from a CSV file
     * @throws FileNotFoundException the exception to be launched
     */
    public List<Munro> readAndConvertMunrosFromCSVFile() throws FileNotFoundException {
        var file = ResourceUtils.getFile("src/main/resources/munrotab_v6.2.csv");

        try (final Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            final CsvToBean<Munro> csvToBean = new CsvToBeanBuilder(reader)
                  .withType(Munro.class)
                  .withIgnoreLeadingWhiteSpace(true)
                  .withVerifier(new MunroVerifier())
                  .build();

            return csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}