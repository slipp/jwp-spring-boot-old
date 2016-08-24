package core.web.taglibs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Functions {
    private Functions() {}

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
    	if (localDateTime == null) {
    		return "";
    	}
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
