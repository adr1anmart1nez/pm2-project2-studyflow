package ch.zhaw.pm2.studyflow;

import java.time.ZonedDateTime;

/**
 * This class represents the aims of an {@link Assessment}.
 *
 * @author StackOverFlow
 * @version 1.0
 */
public class Objective {
    private final String title;
    private final String description;
    private State state;
    private final ZonedDateTime assessmentDate;

    /**
     * This is the constructor to creat an objective.
     *
     * @param title          short title
     * @param description    whole description
     * @param state          a value from the enum {@link State#DELAYED}, {@link State#DONE} or {@link State#PENDING}
     * @param assessmentDate the date when it has to be finished
     */
    public Objective(String title, String description, State state, ZonedDateTime assessmentDate) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.assessmentDate = assessmentDate;
    }

    public ZonedDateTime getAssessmentDate() {
        return assessmentDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    /**
     * This enum represents the state of an object. When the {@link TimeFrame} is not done after its terminated, the state is delayed.
     * The pending state is, when the {@link TimeFrame} is not already in the past, and done is, when the objective is finished.
     */
    public enum State {
        DONE, DELAYED, PENDING
    }
}
