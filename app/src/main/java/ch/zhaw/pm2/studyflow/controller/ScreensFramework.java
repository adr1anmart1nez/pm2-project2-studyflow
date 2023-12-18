package ch.zhaw.pm2.studyflow.controller;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * This class extends the {@link Application} and opens the window for the whole
 * studyflow application.
 *
 * @author Team StackOverFlow
 * @version 1.0
 */
public class ScreensFramework extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreensFramework.class);

    public static final String SCREEN_DASHBOARD = "dashboard";
    public static final String SCREEN_1_FILE = "dashboard.fxml";
    public static final String SCREEN_CALENDAR = "calendar";
    public static final String SCREEN_2_FILE = "calendarWeeklyView.fxml";
    public static final String SCREEN_OVERVIEW = "overview";
    public static final String SCREEN_3_FILE = "overview.fxml";
    public static final String SCREEN_NEW = "newOverview";
    public static final String SCREEN_4_FILE = "newOverview.fxml";

    /**
     * This method is called by the JavaFX application thread when the application is started.
     * It opens the dashboard window.
     *
     * @param primaryStage the primary stage for this application, onto which the application scene can be set
     * @throws IOException if an I/O error occurs while loading the dashboard FXML file
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        openDashWindow(primaryStage);
    }

    /**
     * This method creates a new window to display the dashboard screen using the specified Stage object.
     * The dashboard screen is loaded using the {@link ScreensController}. The method also loads the other
     * screens available in the application: calendar, overview, and new. The dashboard screen is set as
     * the initial screen, and the window is set {@link Stage#setResizable(boolean)} false.
     *
     * @param stage the Stage object to use for the new window
     */
    private void openDashWindow(Stage stage) {
        ScreensController screensController = new ScreensController();
        screensController.loadScreen(ScreensFramework.SCREEN_DASHBOARD, ScreensFramework.SCREEN_1_FILE);
        screensController.loadScreen(ScreensFramework.SCREEN_CALENDAR, ScreensFramework.SCREEN_2_FILE);
        screensController.loadScreen(ScreensFramework.SCREEN_OVERVIEW, ScreensFramework.SCREEN_3_FILE);
        screensController.loadScreen(ScreensFramework.SCREEN_NEW, ScreensFramework.SCREEN_4_FILE);

        screensController.setScreen(ScreensFramework.SCREEN_DASHBOARD);

        Group root = new Group();
        root.getChildren().addAll(screensController);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        try {
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/logo.png")).openStream()));
        } catch (IOException ex) {
            LOGGER.error("Couldn't load logo image!", ex);
        }
    }
}