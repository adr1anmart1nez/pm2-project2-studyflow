package ch.zhaw.pm2.studyflow.controller;

import javafx.fxml.FXMLLoader;
import ch.zhaw.pm2.studyflow.Grade;
import ch.zhaw.pm2.studyflow.Objective.State;
import ch.zhaw.pm2.studyflow.Assessment;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The NewWindowController class is a controller class responsible
 * for controlling the newOverview UI of the StudyFlow application.
 * The class provides functionality for displaying the edit possibilities and provides the json changes.
 *
 * @author Team StackOverFlow
 * @version 1.0
 */
public class NewWindowController extends Controller implements Initializable, ControlledScreen {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewWindowController.class);

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
     * This method sets the controller.
     *
     * @param screenParent The parent screen from {@link ScreensController} to set for this controller.
     */
    @Override
    public void setScreenParent(ScreensController screenParent) {
        screensController = screenParent;
    }

    /**
     * This method opens the new window on button click.
     */
    public void openAddModul() {
        openNewWindow();
    }

    /**
     * This method opens a new window.
     */
    private void openNewWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newModul.fxml"));
            Pane rootNode = loader.load();
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/logo.png")).openStream()));
        } catch (IOException ex) {
            LOGGER.error("Failed to load FXML resource!", ex);
        }
    }

    /**
     * This method loads new json-files over the {@link ScreensController#getNewData(String)}.
     * The new json changes the date for the THIN test from 30.5. to 20.5.
     */
    public void loadChangedAssessmentDate() {
        screensController.getNewData("/changing-date-for-thin.json");
    }

    /**
     * This method loads new json-files over the {@link ScreensController#getNewData(String)}.
     * The new json changes the already existing {@link Grade} to 1.0.
     */
    public void loadChangedGrade() {
        screensController.getNewData("/changing-previews-grade-thin.json");
    }

    /**
     * This method loads new json-files over the {@link ScreensController#getNewData(String)}.
     * The new json adds more modules.
     */
    public void loadMoreModules() {
        screensController.getNewData("/adding-more-modules.json");
    }

    /**
     * This method loads new json-files over the {@link ScreensController#getNewData(String)}.
     * The new json deletes the Communication Competence 2 modul.
     */
    public void loadDeletedModule() {
        screensController.getNewData("/removing-com-2-module.json");
    }

    /**
     * This method loads new json-files over the {@link ScreensController#getNewData(String)}.
     * The new json sets nearly every objective to {@link State#DELAYED}
     */
    public void loadDelayedObjectives() {
        screensController.getNewData("/delay-objectives.json");
    }

    /**
     * This method loads new json-files over the {@link ScreensController#getNewData(String)}.
     * The new json sets nearly every objective to {@link State#DONE}
     */
    public void loadFinishedObjectives() {
        screensController.getNewData("/finish-objectives.json");
    }
}
