package ru.testwork.incode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testwork.incode.filter.FilterRegistry;
import ru.testwork.incode.vo.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FilterService {

    @Autowired
    private FilterRegistry filterRegistry;

    public List<Element> applyFilters(List<Element> elements) {
        var result = new ArrayList<Element>();
        for (Element element: elements) {
            result.add(applyFilters(element));
        }
        return result;
    }

    private Element applyFilters(Element element) {
        var value = element.getOriginalValue();
        for (Element.FilterDefinition filterDefinition : nullSafe(element.getFilters())) {
            var filter = filterRegistry.find(filterDefinition.getFilterId());
            value = filter.apply(value, params(filterDefinition));
        }
        return element.withResult(value);
    }

    private <T> Iterable<T> nullSafe(Iterable<T> iterable) {
        if (iterable == null) {
            return Collections.emptyList();
        }
        return iterable;
    }

    private String[] params(Element.FilterDefinition filter) {
        return filter.getParams().toArray(new String[0]);
    }
}
