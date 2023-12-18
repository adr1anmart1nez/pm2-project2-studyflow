package ch.zhaw.pm2.studyflow.controller;

import ch.zhaw.pm2.studyflow.model.CalendarModel;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.page.WeekPage;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * The CalendarViewController class is a controller class responsible
 * for controlling the calendarView UI of the StudyFlow application.
 * The class provides functionality for displaying the user's calendar.
 *
 * @author Team StackOverFlow
 * @version 1.0
 */
public class CalendarViewController extends Controller implements ControlledScreen, Initializable {

    private CalendarModel calendarModel;
    @FXML
    public WeekPage weekPage;

    /**
     * This method sets all Properties from the {@link CalendarModel} and reloads all moduls.
     *
     * @param screenParent The parent screen from {@link ScreensController} to set for this controller.
     */
    @Override
    public void setScreenParent(ScreensController screenParent) {
        screensController = screenParent;
        calendarModel = screensController.getCalendarModel();
        calendarModel.getAllEntryProperty().addListener((ListChangeListener<Entry<?>>) c -> updateCalendar());
        calendarModel.getAllAssessmentEntryProperty().addListener((ListChangeListener<Entry<?>>) c -> updateCalendar());
        screensController.reloadAllModuls();
    }

    /**
     * This method is from the {@link Initializable} interface.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * This method updates the calendar with the data from {@link CalendarModel#getAllEntryProperty()}.
     */
    private void updateCalendar() {
        weekPage.getCalendarSources().clear();
        Calendar<Entry<?>> calendarObjectives = new Calendar<>("Objectives");
        calendarObjectives.addEntries(calendarModel.getAllEntryProperty());

        Calendar<Entry<?>> calendarAssessments = new Calendar<>("Assessments");
        calendarAssessments.addEntries(calendarModel.getAllAssessmentEntryProperty());

        CalendarSource calendarSource = new CalendarSource();
        calendarSource.getCalendars().add(calendarObjectives);
        calendarSource.getCalendars().add(calendarAssessments);
        weekPage.getCalendarSources().add(calendarSource);
    }
}
