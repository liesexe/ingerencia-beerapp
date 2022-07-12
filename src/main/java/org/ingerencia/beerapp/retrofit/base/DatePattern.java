package org.ingerencia.beerapp.retrofit.base;

import java.util.regex.Pattern;

public interface DatePattern {

    String DEFAULT_DATETIME_FORMATTER_PATTERN = "yyyy-MM-dd HH:mm:ss";
    String DEFAULT_DATE_FORMATTER_PATTERN = "yyyy-MM-dd";
    Pattern DEFAULT_DATE_REGEX = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}");
    Pattern DEFAULT_DATETIME_REGEX = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}");
}
