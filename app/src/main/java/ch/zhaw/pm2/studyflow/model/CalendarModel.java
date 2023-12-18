package ch.zhaw.pm2.studyflow.model;

import ch.zhaw.pm2.studyflow.Algorithm;
import ch.zhaw.pm2.studyflow.Module;
import ch.zhaw.pm2.studyflow.StudyReservation;
import ch.zhaw.pm2.studyflow.UserDataManager;
import ch.zhaw.pm2.studyflow.exceptions.NoMoreStudyReservationsException;
import com.calendarfx.model.Entry;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The CalendarModel class represents the model of the calendar view.
 * It manages the entries to be displayed on the calendar and assessment entries.
 */
public class CalendarModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarModel.class);
    private final ListProperty<Entry<?>> calendarEntries = new SimpleListProperty<>();
    private final ListProperty<Entry<?>> assessmentEntries = new SimpleListProperty<>();

    /**
     * Returns the list property of all calendar entries.
     *
     * @return the list property of all calendar entries
     */
    public ListProperty<Entry<?>> getAllEntryProperty() {
        return calendarEntries;
    }

    /**
     * Returns the list property of all assessment entries.
     *
     * @return the list property of all assessment entries
     */
    public ListProperty<Entry<?>> getAllAssessmentEntryProperty() {
        return assessmentEntries;
    }

    /**
     * Sets the assessment entries to be displayed on the calendar.
     *
     * @param allEntries a list of assessment entries
     */
    public void setAssessmentEntries(List<Entry<?>> allEntries) {
        assessmentEntries.set(FXCollections.observableArrayList(allEntries));
    }

    /**
     * This method runs the algorithm to generate the calendar entries based on the provided {@link UserDataManager#getModules()}
     * and {@link UserDataManager#getReservations()}.
     *
     * @param allModuls       a list of all modules
     * @param allReservations a list of all study reservations
     */
    public void runAlgorithmus(List<Module> allModuls, List<StudyReservation> allReservations) {
        try {
            List<Entry<?>> allEntries = Algorithm.generateCalendarEntries(allModuls, allReservations);
            calendarEntries.set(FXCollections.observableArrayList(allEntries));
        } catch (NoMoreStudyReservationsException e) {
            LOGGER.error("There were no more studyReservations available for the remaining objectives.");
        }
    }
}
