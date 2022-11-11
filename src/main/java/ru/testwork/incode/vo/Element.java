package ru.testwork.incode.vo;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.util.List;

@Value
@Builder
public class Element {

    String originalValue;
    List<FilterDefinition> filters;
    @With String result;

    @Value
    public static class FilterDefinition {
        @NonNull FilterId filterId;
        List<String> params;
    }
}
