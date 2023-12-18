package ch.zhaw.pm2.studyflow;

import ch.zhaw.pm2.studyflow.controller.ScreensFramework;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main class for the StudyFlow application. This class is responsible
 * for starting the application and launching the {@link ScreensFramework}.
 *
 * @author StackOverFlow
 * @version 1.0
 */
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    /**
     * This method starts the application. It launches the ScreensFramework and logs the application start and end.
     *
     * @param args The command line arguments passed to the application
     */
    public static void main(String[] args) {
        LOGGER.info("Starting application...");
        Application.launch(ScreensFramework.class, args);
        LOGGER.info("Application ended");
    }
}