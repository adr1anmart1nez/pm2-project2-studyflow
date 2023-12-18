package ch.zhaw.pm2.studyflow.controller;

import ch.zhaw.pm2.studyflow.Assessment;
import ch.zhaw.pm2.studyflow.Module;
import ch.zhaw.pm2.studyflow.Objective;
import ch.zhaw.pm2.studyflow.model.OverviewModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The OverviewWindowController class is a controller class responsible
 * for controlling the overview UI of the StudyFlow application.
 * The class provides functionality for displaying all current moduls, assessments and objectives.
 *
 * @author Team StackOverFlow
 * @version 1.0
 */
public class OverviewWindowController extends Controller implements Initializable, ControlledScreen {

    OverviewModel overviewModel;
    @FXML
    public Accordion allModule;

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
     * This method updates the accordion with the data from the {@link OverviewModel#getAllModuleProperty()}.
     * The information are added to the series from the chart.
     */
    private void addDataToAccordion() {
        allModule.getPanes().clear();
        for (Module module : overviewModel.getAllModuleProperty()) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText(module.getTitle());
            VBox vBox = new VBox();
            vBox.getChildren().add(new Label("Credits: " + module.getCredits()));
            vBox.getChildren().add(new Label("Fortschritt: " + module.getPersonalProgressEstimate()));
            vBox.getChildren().add(new Label("Anzahl Assessments: " + module.getAssessmentList().size()));

            Accordion accordionAssessments = new Accordion();
            accordionAssessments.getPanes().addAll(generateAccordionForAssessments(module));

            vBox.getChildren().add(accordionAssessments);
            titledPane.setContent(vBox);

            allModule.getPanes().add(titledPane);
        }
    }

    /**
     * Generates a list of {@link TitledPane} for each assessment in the given module.
     *
     * @param module the module object
     * @return a list of TitledPanes for each assessment in the given module
     */
    private List<TitledPane> generateAccordionForAssessments(Module module) {
        List<TitledPane> allTitledPaneForAssessment = new ArrayList<>();

        for (Assessment assessment : module.getAssessmentList()) {
            TitledPane titledPaneAssessment = new TitledPane();
            titledPaneAssessment.setText(assessment.getTitle());
            VBox vBoxAssessment = new VBox();

            vBoxAssessment.getChildren().add(new Label("Datum: " + assessment.getDateForGUI()));
            vBoxAssessment.getChildren().add(new Label("Note: " + assessment.getGrade().toString()));
            vBoxAssessment.getChildren().add(new Label("Anzahl Lernziele: " + assessment.getObjectiveList().size()));

            Accordion accordionObjectives = new Accordion();
            accordionObjectives.getPanes().addAll(generateAccordionForObjectives(assessment));
            vBoxAssessment.getChildren().add(accordionObjectives);

            titledPaneAssessment.setContent(vBoxAssessment);

            allTitledPaneForAssessment.add(titledPaneAssessment);
        }
        return allTitledPaneForAssessment;
    }

    /**
     * Generates a list of {@link TitledPane} for each objective in the given assessment.
     *
     * @param assessment the assessment object for which to generate the TitledPanes
     * @return a list of TitledPanes for each objective in the given assessment
     */
    private List<TitledPane> generateAccordionForObjectives(Assessment assessment) {
        List<TitledPane> allTitledPaneForObjectives = new ArrayList<>();
        for (Objective objective : assessment.getObjectiveList()) {
            TitledPane titledPaneObjective = new TitledPane();
            titledPaneObjective.setText(objective.getTitle());
            VBox vBoxObjectives = new VBox();
            Label description = new Label("Beschreibung: " + objective.getDescription());
            description.setWrapText(true);
            vBoxObjectives.getChildren().add(description);
            vBoxObjectives.getChildren().add(new Label("Aktueller Status: " + objective.getState().toString()));
            titledPaneObjective.setContent(vBoxObjectives);
            allTitledPaneForObjectives.add(titledPaneObjective);
        }
        return allTitledPaneForObjectives;
    }

    /**
     * This method sets all Properties from the {@link OverviewModel} and reloads all moduls.
     *
     * @param screenParent The parent screen from {@link ScreensController} to set for this controller.
     */
    @Override
    public void setScreenParent(ScreensController screenParent) {
        screensController = screenParent;
        overviewModel = screensController.getOverviewModel();
        overviewModel.getAllModuleProperty().addListener((ListChangeListener<Module>) c -> addDataToAccordion());
        screensController.reloadAllModuls();
    }
}
