package ch.zhaw.pm2.studyflow.controller;

import java.io.IOException;
import java.util.HashMap;

import ch.zhaw.pm2.studyflow.UserData;
import ch.zhaw.pm2.studyflow.UserDataManager;
import ch.zhaw.pm2.studyflow.model.CalendarModel;
import ch.zhaw.pm2.studyflow.model.DashboardModel;
import ch.zhaw.pm2.studyflow.model.OverviewModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ScreensController controls the {@link ScreensFramework}
 *
 * @author Team StackOverFlow
 * @version 1.0
 */
public class ScreensController extends StackPane {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreensController.class);

    private final HashMap<String, Node> screens = new HashMap<>();
    private UserData userData;
    private DashboardModel dashboardModel;
    private CalendarModel calendarModel;
    private OverviewModel overviewModel;

    public DashboardModel getDashboardModel() {
        return dashboardModel;
    }

    public CalendarModel getCalendarModel() {
        return calendarModel;
    }

    public OverviewModel getOverviewModel() {
        return overviewModel;
    }

    /**
     * This constructor creates all models and the userDataManger with the start data.
     */
    public ScreensController() {
        super();
        try {
            userData = new UserDataManager("/user-modules.json");
            dashboardModel = new DashboardModel();
            calendarModel = new CalendarModel();
            overviewModel = new OverviewModel();
            calendarModel.runAlgorithmus(userData.getModules(), userData.getReservations());
            reloadAllModuls();
        } catch (IOException ex) {
            LOGGER.error("Something went wrong during the reading of the data files!", ex);
            System.exit(1);
        }
    }


    /**
     * This method creates a new {@link UserDataManager}, runs the algorithm again and reloads all models.
     *
     * @param resource the filename of the new json-file
     */
    public void getNewData(String resource) {
        try {
            userData = new UserDataManager(resource);
            calendarModel.runAlgorithmus(userData.getModules(), userData.getReservations());
            reloadAllModuls();
        } catch (IOException ex) {
            LOGGER.error("Something went wrong during the reading of the data files!", ex);
        }

    }

    /**
     * This method loads all moduls new, to update all properties.
     */
    public void reloadAllModuls() {
        try {
            dashboardModel.setTotalProgressProperty(String.valueOf(userData.generateTotalProgress()));
            dashboardModel.setCurrentGradesProperty(String.valueOf(userData.calculateCurrentAverage()));
            dashboardModel.setPossibleProgressGradeProperty(String.valueOf(userData.calculatePossibleGrade()));
            dashboardModel.updateCharData(userData.getModules());
            dashboardModel.updateNextAssessments(userData.getNextAssessments());

            overviewModel.updateAllModule(userData.getModules());
            calendarModel.setAssessmentEntries(userData.giveBackTheAssessmentEntries());
        } catch (Exception e) {
            System.out.println("While reloading the Moduls is an error accused. " + e.getMessage());
        }
    }


    /**
     * This method adds the screen to the list.
     *
     * @param name   name of the new screen
     * @param screen parent screen from the {@link ScreensController#loadScreen(String, String)}
     */
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    /**
     * This method loads the screen at the beginning of the application start.
     *
     * @param name     the name of the screen
     * @param resource the filepath for to the fxml-file
     */
    public void loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            addScreen(name, loadScreen);
        } catch (IOException ex) {
            LOGGER.error("An error occurred during the loading of the screen!", ex);
        }
    }

    /**
     * This method sets the definitive screen.
     *
     * @param name the name of the screen
     */
    public void setScreen(String name) {
        if (screens.get(name) != null) {
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(200), t -> {
                            getChildren().remove(0);
                            getChildren().add(0, screens.get(name));

                            Timeline fadeIn = new Timeline(
                                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                    new KeyFrame(new Duration(200), new KeyValue(opacity, 1.0)));
                            fadeIn.play();


                        }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(200), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
        } else {
            LOGGER.warn("Screen hasn't been loaded!");
        }
    }
}
