package ru.testwork.incode.filter.impl;

import org.junit.jupiter.api.Test;
import ru.testwork.incode.filter.Filter;

import static org.junit.jupiter.api.Assertions.*;

public class RegexRemoveFilterTest {

    private final static String REGEX_DIGIT = "\\d+";
    private final RegexRemoveFilter filter = new RegexRemoveFilter();

    @Test
    public void testNullSafety() {
        assertNull(filter.apply(null));
    }

    @Test
    public void testNoParametersProvided() {
        assertThrows(Filter.NotEnoughParamsProvidedException.class, () -> filter.apply(""));
    }

    @Test
    public void testNullParametersProvided() {
        assertThrows(Filter.IncorrectParamException.class, () -> filter.apply("", null, null));
    }

    @Test
    public void testNotRegexParameterProvided() {
        assertThrows(Filter.IncorrectParamException.class, () -> filter.apply("", "[]"));
    }

    @Test
    public void testEmptyValue() {
        assertEquals("", filter.apply("", REGEX_DIGIT));
    }

    @Test
    public void testRemoveDigitsFromValue() {
        assertEquals("abcdef", filter.apply("abc123def567", REGEX_DIGIT));
    }
}
