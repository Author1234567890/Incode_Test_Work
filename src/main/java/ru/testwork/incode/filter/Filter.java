package ru.testwork.incode.filter;

/**
 * Filter that do some manipulation on input value and returns result
 */
public interface Filter {

    /**
     * @param value string value for filter
     * @return filtered string
     * @throws NotEnoughParamsProvidedException if params count less than expected
     */
    String apply(String value, String... params);

    /**
     * Exception which indicates that filter need more parameters
     */
    class NotEnoughParamsProvidedException extends IllegalArgumentException {
        public NotEnoughParamsProvidedException(int expected, int provided) {
            super(String.format("Need more params: expected=%d, provided=%d", expected, provided));
        }
    }

    /**
     * Exception which indicates that one of filters param is malformed
     */
    class IncorrectParamException extends IllegalArgumentException {
        public IncorrectParamException(String message, Throwable e) {
            super(String.format("Invalid parameter: %s", message), e);
        }
    }
}
