package ch.zhaw.pm2.studyflow;

import java.time.ZonedDateTime;

import java.util.List;

/**
 * Represents an assessment in a study plan, with a start and end date, a list of objectives, a grade, a finish status, and a title.
 *
 * @author StackOverFlow
 * @version 1.0
 */
public class Assessment extends TimeFrame {
    private final List<Objective> objectiveList;
    private Grade grade;
    private final boolean isFinished;
    private final String title;

    /**
     * Constructs a new Assessment object with the specified start and end date, list of objectives, grade, finish status, and title.
     *
     * @param start         The start date of the assessment.
     * @param end           The end date of the assessment.
     * @param objectiveList The list of objectives for the assessment.
     * @param grade         The grade received for the assessment.
     * @param isFinished    A boolean indicating whether the assessment is finished.
     * @param title         The title of the assessment.
     */
    public Assessment(ZonedDateTime start, ZonedDateTime end, List<Objective> objectiveList, Grade grade, boolean isFinished, String title) {
        super(start, end);
        this.objectiveList = objectiveList;
        this.grade = grade;
        this.isFinished = isFinished;
        this.title = title;
    }

    /**
     * Returns a boolean indicating whether the assessment is finished.
     *
     * @return A boolean indicating whether the assessment is finished.
     */
    public boolean isFinished() {
        return isFinished;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public List<Objective> getObjectiveList() {
        return objectiveList;
    }

    /**
     * Returns a string representation of the start date of the assessment, formatted for display in the GUI.
     *
     * @return start date for the objectives.
     */
    public String getDateForGUI() {
        return getStart().getDayOfMonth() + "." + getStart().getMonth().getValue() + ".";
    }

    public String getTitle() {
        return title;
    }
}
