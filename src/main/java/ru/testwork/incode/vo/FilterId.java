package ru.testwork.incode.vo;

import lombok.Value;

/**
 * Identifier (group and id) of the filter
 */
@Value
public class FilterId {
    String group;
    String id;
}
