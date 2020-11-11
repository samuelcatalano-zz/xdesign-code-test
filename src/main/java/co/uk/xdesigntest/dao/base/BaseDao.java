package co.uk.xdesigntest.dao.base;

import co.uk.xdesigntest.entity.base.BaseEntity;
import java.util.List;

/**
 * @author Samuel Catalano
 * @since October, 2020
 */

public interface BaseDao <E extends BaseEntity> {

    List<E> listAll();
}