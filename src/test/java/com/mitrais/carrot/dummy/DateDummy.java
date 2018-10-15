package com.mitrais.carrot.dummy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * helper Date dummy for easier define
 *
 * @author Febri_MW251
 *
 */
public class DateDummy {

    /**
     * dummy LocalDateTime
     *
     * @param yd your custom date of today
     * @return LocalDateTime object
     */
    public static LocalDateTime myLocalDateTime(String yd) {
        String today = yd;
        if (yd == null) {
        	today = "2018-11-09 10:30";
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formatDateTime = LocalDateTime.parse(today, dtf);
        return formatDateTime;
    }

}
