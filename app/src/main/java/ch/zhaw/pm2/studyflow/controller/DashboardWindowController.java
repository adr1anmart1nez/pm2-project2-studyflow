package ch.zhaw.pm2.studyflow.controller;

import ch.zhaw.pm2.studyflow.Assessment;
import ch.zhaw.pm2.studyflow.model.DashboardModel;
import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The DashboardWindowController class is a controller class responsible
 * for controlling the dashboard window UI of the StudyFlow application.
 * The class provides functionality for displaying the user's progress in various modules,
 * as well as displaying upcoming assessments.
 *
 * @author Team StackOverFlow
 * @version 1.0
 */
public class DashboardWindowController extends Controller implements Initializable, ControlledScreen {
    private DashboardModel dashboardModel;
    @FXML
    public Label potentialGrade;
    @FXML
    public Label currentGrades;
    @FXML
    public Label dateAssessment1;
    @FXML
    public Label dateAssessment2;
    @FXML
    public Label dateAssessment3;
    @FXML
    public Label titelAssessment1;
    @FXML
    public Label titelAssessment2;
    @FXML
    public Label titelAssessment3;
    @FXML
    public Label totalProgress;

    @FXML
    private StackedBarChart<String, Integer> barChart;

    /**
     * This method updates the chart with the data from the {@link DashboardModel#getCharDataProperty()}.
     * The information are addet to the series from the chart.
     */
    private void addDataToChar() {
        XYChart.Series<String, Integer> finishedObjectives = new XYChart.Series<>();
        finishedObjectives.setName("erledigt");
        XYChart.Series<String, Integer> planedObjectives = new XYChart.Series<>();
        planedObjectives.setName("geplante");
        XYChart.Series<String, Integer> deferredObjectives = new XYChart.Series<>();
        planedObjectives.setName("verschobene");
        Map<String, int[]> data = dashboardModel.getCharDataProperty();
        for (Map.Entry<String, int[]> entry : data.entrySet()){
            finishedObjectives.getData().add(new XYChart.Data<>(entry.getKey().substring(0, 3).toUpperCase(), entry.getValue()[0]));
            planedObjectives.getData().add(new XYChart.Data<>(entry.getKey().substring(0, 3).toUpperCase(), entry.getValue()[1]));
            deferredObjectives.getData().add(new XYChart.Data<>(entry.getKey().substring(0, 3).toUpperCase(), entry.getValue()[2]));
        }
        ObservableList<XYChart.Series<String, Integer>> chartData = FXCollections.observableArrayList();
        chartData.addAll(finishedObjectives, planedObjectives, deferredObjectives);
        barChart.getData().clear();
        barChart.setData(chartData);
    }

    /**
     * This method updates the three next upcoming assessments with the data from the {@link DashboardModel#getNextAssessmentsProperty()}.
     */
    private void changeAssessment() {
        ListProperty<Assessment> nextAssessment = dashboardModel.getNextAssessmentsProperty();
        int numberOfAssessments = nextAssessment.size();

        dateAssessment1.setText("");
        titelAssessment1.setText("");
        dateAssessment2.setText("");
        titelAssessment2.setText("");
        dateAssessment3.setText("");
        titelAssessment3.setText("");

        if (numberOfAssessments > 0) {
            dateAssessment1.setText(nextAssessment.get(0).getDateForGUI());
            titelAssessment1.setText(nextAssessment.get(0).getTitle());
            if (numberOfAssessments > 1) {
                dateAssessment2.setText(nextAssessment.get(1).getDateForGUI());
                titelAssessment2.setText(nextAssessment.get(1).getTitle());
                if (numberOfAssessments > 2) {
                    dateAssessment3.setText(nextAssessment.get(2).getDateForGUI());
                    titelAssessment3.setText(nextAssessment.get(2).getTitle());
                }
            }
        }
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
     * This method sets all Properties from the {@link DashboardModel} and reloads all moduls.
     *
     * @param screenParent The parent screen from {@link ScreensController} to set for this controller.
     */
    @Override
    public void setScreenParent(ScreensController screenParent) {
        screensController = screenParent;
        dashboardModel = screensController.getDashboardModel();
        dashboardModel.getNextAssessmentsProperty().addListener((ListChangeListener<Assessment>) c -> changeAssessment());
        totalProgress.textProperty().bindBidirectional(dashboardModel.getTotalProgressProperty());
        currentGrades.textProperty().bindBidirectional(dashboardModel.getCurrentGradesProperty());
        potentialGrade.textProperty().bindBidirectional(dashboardModel.getPossibleProgressGradeProperty());
        dashboardModel.getCharDataProperty().addListener((MapChangeListener<String, int[]>) c -> addDataToChar());
        screensController.reloadAllModuls();
    }

}