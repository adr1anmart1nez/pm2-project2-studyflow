package ch.zhaw.pm2.studyflow;

import java.time.ZonedDateTime;

/**
 * This class represents the space in the calendar who are reserved to study.
 *
 * @author StackOverFlow
 * @version 1.0
 */
public class StudyReservation extends TimeFrame {
    /**
     * This constructor creates the reservation.
     *
     * @param start start of the reservation
     * @param end   end of the reservation
     */
    public StudyReservation(ZonedDateTime start, ZonedDateTime end) {
        super(start, end);
    }
}
