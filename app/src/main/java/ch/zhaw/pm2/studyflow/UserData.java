package ch.zhaw.pm2.studyflow;

import com.calendarfx.model.Entry;
import ch.zhaw.pm2.studyflow.exceptions.GradeWeightZeroException;

import java.util.List;

/**
 * This interface is the main interface for the UserData. It has all methods to calculate the
 * average and the progress.
 *
 * @author StackOverFlow
 * @version 1.0
 */

public interface UserData {
    /**
     * This method goes over all moduls and generates an entry. This entry has an exact title
     * and an intervall from the start to the end.
     *
     * @return all entries for the {@link Assessment}
     */
    List<Entry<?>> giveBackTheAssessmentEntries();

    /**
     * This method iterates over all {@link Module} and calculates the current average with
     * the weight and the grade. When the weight is 0 a {@link GradeWeightZeroException} is thrown.
     * The return double is rounded to half grades.
     *
     * @return current average rounded to half grades
     */
    double calculateCurrentAverage();

    /**
     * This method goes over all {@link Module} and over all {@link Assessment} and calculates
     * the average of the all {@link Assessment#getGrade()}. When the weight is 0 a {@link GradeWeightZeroException} is thrown.
     * * The return double is rounded to half grades.
     *
     * @return possible average rounded to half grades
     */
    double calculatePossibleGrade();

    /**
     * This method calculates the total progress of all assessments wich are done.
     * First it iterates over all {@link Module} and {@link Assessment} and checks if the
     * assessment is finished and calculates them.
     *
     * @return (allFinishedAssessments / numberOfAssessments) * 100
     */

    int generateTotalProgress();

    /**
     * This method iterates over all {@link Module} and then over all {@link Assessment}.
     * When the assessment date is in the future it is saved in the return list. At the ending the list
     * is sorted by the start. At index 0 is the next Assessment to now.
     *
     * @return a sorted list with all assessment in the future.
     */
    List<Assessment> getNextAssessments();

    List<Module> getModules();

    List<StudyReservation> getReservations();
}