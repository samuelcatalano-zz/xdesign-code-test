package co.uk.xdesigntest.utils;

import co.uk.xdesigntest.entity.Munro;
import com.opencsv.bean.BeanVerifier;
import com.opencsv.exceptions.CsvConstraintViolationException;
import org.springframework.stereotype.Component;

/**
 * @author Samuel Catalano
 * @since October, 2020
 */

@Component
public class MunroVerifier implements BeanVerifier<Munro> {

    @Override
    public boolean verifyBean(final Munro munro) throws CsvConstraintViolationException {
        return (munro.getRunningNo() != 0);
    }
}