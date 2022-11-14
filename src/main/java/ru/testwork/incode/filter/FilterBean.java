package ru.testwork.incode.filter;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface FilterBean {

    /**
     * @return group of the filter
     */
    String group();

    /**
     * @return id of the filter
     */
    String id();
}
