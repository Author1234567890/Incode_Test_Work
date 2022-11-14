package ru.testwork.incode.filter;

import lombok.NonNull;
import lombok.extern.java.Log;
import ru.testwork.incode.vo.FilterId;

import java.util.Map;

/**
 * Registry of all known filters in application
 */
@Log
public class FilterRegistry {

    private final Map<FilterId, Filter> filters;

    public FilterRegistry(@NonNull Map<FilterId, Filter> filters) {
        this.filters = filters;
        log.info(String.format("Known filters %s", filters.keySet()));
    }

    /**
     * @param filterId id of filter
     * @return filter implementation
     * @throws FilterNotFoundException if filter not found
     */
    public Filter find(FilterId filterId) {
        var filter = filters.get(filterId);
        if (filter == null) {
            throw new FilterNotFoundException(filterId);
        }
        return filter;
    }

    /**
     * Exception which indicates that filter is unknown
     */
    public static class FilterNotFoundException extends IllegalArgumentException {
        public FilterNotFoundException(FilterId filterId) {
            super(String.format("Filter not found: %s", filterId));
        }
    }
}
