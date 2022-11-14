package ru.testwork.incode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Result of filters applying")
public record FilterResponse(
    @Schema(description = "Original list of elements", nullable = true)
    List<Element> elements,
    @Schema(description = "Error description", nullable = true)
    String error
) {
    public FilterResponse(List<Element> elements) {
        this(elements, null);
    }
    public FilterResponse(String error) {
        this(null, error);
    }

    public record Element(
        @Schema(description = "Value after filters applying")
        String result,
        @Schema(description = "Original value from request")
        String value,
        @Schema(description = "Original filters from request")
        List<Filter> filters
    ) {}
    public record Filter(
        @Schema(description = "Group id of filter")
        String filterGroupId,
        @Schema(description = "Id of filter")
        String filterId,
        @Schema(description = "List of parameters")
        List<String> parameters
    ) {}
}
