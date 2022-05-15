package ru.sbrf.esa.aft.healthcheck.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConcurrentDateFormatAccess {

    private final ThreadLocal<DateFormat> df = ThreadLocal.withInitial(() -> new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));

    public String convertToString(Date date) {
        return df.get().format(date);
    }
}