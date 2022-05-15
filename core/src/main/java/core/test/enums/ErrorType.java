package core.test.enums;

public enum ErrorType {
    DATA_BASE("dataBase"),
    MOCK("mock"),
    CACHE("cache"),
    TIMEOUT("timeout"),
    OTHER("other");

    private final String errorType;

    ErrorType(String errorType) { this.errorType = errorType; }

    public String getErrorType() { return this.errorType; }

    public static ErrorType getErrorType(String error) {
        if (error.contains("javascript evaluation failed: Java.type('common.Scheme')"))
            return ErrorType.DATA_BASE;
        else if (error.contains("javascript evaluation failed: builder.buildAndSend()"))
            return ErrorType.MOCK;
        else if (error.contains("Ошибка при построении и отправке запроса EventListenerRestService'у"))
            return ErrorType.CACHE;
        else if (error.contains("Ошибка: timeout"))
            return ErrorType.TIMEOUT;
        else
            return ErrorType.OTHER;
    }
}
