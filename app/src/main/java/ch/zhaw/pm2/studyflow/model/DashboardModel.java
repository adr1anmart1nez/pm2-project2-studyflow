package ch.zhaw.pm2.studyflow.model;

import ch.zhaw.pm2.studyflow.Assessment;
import ch.zhaw.pm2.studyflow.controller.DashboardWindowController;
import ch.zhaw.pm2.studyflow.Objective.State;
import ch.zhaw.pm2.studyflow.Module;
import ch.zhaw.pm2.studyflow.Objective;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The DashboardModel class represents the model of the dashboard view.
 * It provides the information for the {@link DashboardWindowController}.
 */
public class DashboardModel {
    private final ListProperty<Assessment> nextAssessments = new SimpleListProperty<>();
    private final MapProperty<String, int[]> charData = new SimpleMapProperty<>();
    private final StringProperty possibleProgressGrade = new SimpleStringProperty();
    private final StringProperty currentGrades = new SimpleStringProperty();
    private final StringProperty totalProgress = new SimpleStringProperty();

    public ListProperty<Assessment> getNextAssessmentsProperty() {
        return nextAssessments;
    }

    public MapProperty<String, int[]> getCharDataProperty() {
        return charData;
    }

    public StringProperty getPossibleProgressGradeProperty() {
        return possibleProgressGrade;
    }

    public StringProperty getCurrentGradesProperty() {
        return currentGrades;
    }

    public StringProperty getTotalProgressProperty() {
        return totalProgress;
    }

    public void setPossibleProgressGradeProperty(String input) {
        possibleProgressGrade.setValue(input);
    }

    public void setCurrentGradesProperty(String input) {
        currentGrades.setValue(input);
    }

    public void setTotalProgressProperty(String input) {
        totalProgress.setValue(input + "%");
    }

    /**
     * This method updates the {@link #nextAssessments} to be displayed in the dashboard view.
     *
     * @param allNext the list of assessments to update the nextAssessments property
     */
    public void updateNextAssessments(List<Assessment> allNext) {
        int index = Math.min(allNext.size(), 3);
        nextAssessments.set(FXCollections.observableList(allNext.subList(0, index)));
    }

    /**
     * This method updates the chart data for the modules and the count of objectives depending
     * on {@link State#DONE}, {@link State#PENDING} and {@link State#DELAYED}.
     *
     * @param allModuls the list of modules to update the chart data property
     */
    public void updateCharData(List<Module> allModuls) {
        Map<String, int[]> allObjectivesCount = new HashMap<>();
        for (Module module : allModuls) {
            int doneObjectives = 0;
            int pendingObjectives = 0;
            int delayedObjectives = 0;
            for (Assessment assessment : module.getAssessmentList()) {
                for (Objective objective : assessment.getObjectiveList()) {
                    switch (objective.getState()) {
                        case DONE -> doneObjectives++;
                        case DELAYED -> delayedObjectives++;
                        case PENDING -> pendingObjectives++;
                    }
                }
            }
            allObjectivesCount.put(module.getTitle(), new int[]{doneObjectives, pendingObjectives, delayedObjectives});
        }
        charData.set(FXCollections.observableMap(allObjectivesCount));
    }
}
