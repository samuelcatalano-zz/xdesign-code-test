package co.uk.xdesigntest.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Samuel Catalano
 * @since October, 2020
 */

public enum SortTypeEnum {

    ASC("ASC"),
    DESC("DESC");

    private String name;
    private static final Map<String, SortTypeEnum> ENUM_MAP;

    SortTypeEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    static {
        Map<String, SortTypeEnum> map = new ConcurrentHashMap<>();
        for (SortTypeEnum instance : SortTypeEnum.values()) {
            map.put(instance.getName(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static SortTypeEnum get(final String name) {
        return ENUM_MAP.get(name.toUpperCase());
    }
}
