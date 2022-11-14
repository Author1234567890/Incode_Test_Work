package ru.testwork.incode.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Schema(description = "Request for filter service")
public record FilterRequest(
    @Schema(description = "Elements for filtering", required = true)
    @NotEmpty @Valid List<Element> elements
) {
    public record Element(
        @Schema(description = "Value that will be filtered")
        String value,
        @Schema(description = "List of filters to apply on value", required = true)
        @Valid List<Filter> filters
    ) {}
    public record Filter(
        @Schema(description = "Group id of filter", required = true)
        @NotEmpty String filterGroupId,
        @Schema(description = "Id of filter", required = true)
        @NotEmpty String filterId,
        @Schema(description = "List of parameters")
        List<String> parameters
    ) {}
}
