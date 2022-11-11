package ru.testwork.incode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.testwork.incode.filter.Filter;
import ru.testwork.incode.filter.FilterBean;
import ru.testwork.incode.filter.FilterRegistry;
import ru.testwork.incode.vo.FilterId;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class IncodeTestWorkApplication {

    @Bean
    public FilterRegistry filterRegistry(ApplicationContext context) {
        return new FilterRegistry(getKnownFilters(context));
    }

    private Map<FilterId, Filter> getKnownFilters(ApplicationContext context) {
        var filterBeans = context.getBeansWithAnnotation(FilterBean.class);
        var result = new HashMap<FilterId, Filter>();
        for (Object filter : filterBeans.values()) {
            var annotation = filter.getClass().getAnnotation(FilterBean.class);
            result.put(new FilterId(annotation.group(), annotation.id()), (Filter) filter);
        }
        return result;
    }

    public static void main(String[] args) {
        SpringApplication.run(IncodeTestWorkApplication.class, args);
    }
}
