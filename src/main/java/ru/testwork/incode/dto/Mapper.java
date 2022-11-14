package ru.testwork.incode.dto;

import ru.testwork.incode.vo.Element;
import ru.testwork.incode.vo.FilterId;

import java.util.List;

public class Mapper {

    public static List<Element> toModel(FilterRequest request) {
        return request.elements().stream()
            .map(Mapper::toModel)
            .toList();
    }

    private static Element toModel(FilterRequest.Element dto) {
        return Element.builder()
            .originalValue(dto.value())
            .filters(toModel(dto.filters()))
            .build();
    }

    private static List<Element.FilterDefinition> toModel(List<FilterRequest.Filter> dto) {
        if (dto == null) {
            return null;
        }
        return dto.stream()
            .map(filter -> new Element.FilterDefinition(
                new FilterId(filter.filterGroupId(), filter.filterId()),
                filter.parameters()
            ))
            .toList();
    }

    public static FilterResponse toFilterResponse(List<Element> elements) {
        return new FilterResponse(
            elements.stream()
                .map(Mapper::toResponse)
                .toList()
        );
    }

    private static FilterResponse.Element toResponse(Element element) {
        return new FilterResponse.Element(
            element.getResult(),
            element.getOriginalValue(),
            toResponse(element.getFilters())
        );
    }

    private static List<FilterResponse.Filter> toResponse(List<Element.FilterDefinition> filters) {
        if (filters == null) {
            return null;
        }
        return filters.stream()
            .map(filter -> new FilterResponse.Filter(
                filter.getFilterId().getGroup(),
                filter.getFilterId().getId(),
                filter.getParams()
            ))
            .toList();
    }
}
