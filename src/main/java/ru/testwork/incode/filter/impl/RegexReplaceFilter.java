package ru.testwork.incode.filter.impl;

import ru.testwork.incode.filter.Filter;
import ru.testwork.incode.filter.FilterBean;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * Filter that replace regex pattern in value on static text from second parameter
 */
@FilterBean(group = "regex", id = "replace")
public class RegexReplaceFilter implements Filter {

    @Override
    public String apply(String value, String... params) {
        if (value == null) {
            return null;
        }
        if (params.length < 2) {
            throw new NotEnoughParamsProvidedException(2, params.length);
        }
        var pattern = toPattern(params[0]);
        return pattern.matcher(value).replaceAll(defaultString(params[1]));
    }

    private Pattern toPattern(String regex) {
        try {
            return Pattern.compile(regex);
        } catch (PatternSyntaxException | NullPointerException e) {
            throw new IncorrectParamException("Not regexp", e);
        }
    }
}
