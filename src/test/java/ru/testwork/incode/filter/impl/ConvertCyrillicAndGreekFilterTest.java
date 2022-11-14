package ru.testwork.incode.filter.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ConvertCyrillicAndGreekFilterTest {

    private final ConvertCyrillicAndGreekFilter filter = new ConvertCyrillicAndGreekFilter();

    @Test
    public void testNullSafety() {
        assertNull(filter.apply(null));
    }

    @Test
    public void testEmptyValue() {
        assertEquals("", filter.apply(""));
    }

    @Test
    public void testLowerCaseValue() {
        assertEquals("abc abv abg", filter.apply("abc абв αβγ"));
    }

    @Test
    public void testUpperCaseValue() {
        assertEquals("ABC ABZH ADQ", filter.apply("ABC АБЖ ΑΔΘ"));
    }
}
