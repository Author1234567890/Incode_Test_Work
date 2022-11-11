package ru.testwork.incode.filter.impl;

import ru.testwork.incode.filter.Filter;
import ru.testwork.incode.filter.FilterBean;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Filter that remove regex pattern from value
 */
@FilterBean(group = "regex", id = "remove")
public class RegexRemoveFilter implements Filter {

    @Override
    public String apply(String value, String... params) {
        if (value == null) {
            return null;
        }
        if (params.length < 1) {
            throw new NotEnoughParamsProvidedException(1, params.length);
        }
        var pattern = toPattern(params[0]);
        return pattern.matcher(value).replaceAll(EMPTY);
    }

    private Pattern toPattern(String regex) {
        try {
            return Pattern.compile(regex);
        } catch (PatternSyntaxException | NullPointerException e) {
            throw new IncorrectParamException("Not regexp", e);
        }
    }
}
