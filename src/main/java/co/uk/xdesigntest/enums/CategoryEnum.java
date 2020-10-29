package co.uk.xdesigntest.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Samuel Catalano
 * @since October, 2020
 */

public enum CategoryEnum {

    MUN("MUN"),
    TOP("TOP");

    private String name;
    private static final Map<String, CategoryEnum> ENUM_MAP;

    CategoryEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    static {
        Map<String, CategoryEnum> map = new ConcurrentHashMap<>();
        for (CategoryEnum instance : CategoryEnum.values()) {
            map.put(instance.getName(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static CategoryEnum get(final String name) {
        return ENUM_MAP.get(name.toUpperCase());
    }
}