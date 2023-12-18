package ch.zhaw.pm2.studyflow;

import java.time.ZonedDateTime;

/**
 * A class representing a time frame with a start and end date and time.
 *
 * @author StackOverFlow
 * @version 1.0
 */
public class TimeFrame {
    private ZonedDateTime start;
    private ZonedDateTime end;

    /**
     * Constructs a new timeframe with the specified start and end date and time.
     *
     * @param start the start date and time of the time frame
     * @param end   the end date and time of the time frame
     */
    public TimeFrame(ZonedDateTime start, ZonedDateTime end) {
        this.start = start;
        this.end = end;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }
}
