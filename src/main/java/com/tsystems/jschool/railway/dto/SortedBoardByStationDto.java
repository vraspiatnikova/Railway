package com.tsystems.jschool.railway.dto;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

public class SortedBoardByStationDto implements Comparator<BoardByStationDto> {

    private static final Logger LOGGER = Logger.getLogger(SortedBoardByStationDto.class);
    public final String DATE_FORMAT = "yyyy/MM/dd HH:mm";

    @Override
    public int compare(BoardByStationDto o1, BoardByStationDto o2) {
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        int res = 0;
        try {
            c1.setTime(format.parse(o1.getArrivaDatelTime()));
            c2.setTime(format.parse(o2.getArrivaDatelTime()));
            if (c1.before(c2)) res = -1;
            if (c1.after(c2)) res = 1;
            if (c1.equals(c2)) res = 0;
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return res;
    }
}