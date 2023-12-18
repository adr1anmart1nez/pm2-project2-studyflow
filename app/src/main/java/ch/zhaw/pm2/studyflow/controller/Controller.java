package ch.zhaw.pm2.studyflow.controller;

/**
 * This abstract class serves as the parent for all controllers used in the StudyFlow application.
 * It provides methods for switching to different screens within the application by calling the corresponding methods in the ScreensController.
 * Each child controller will have access to the ScreensController instance through the "screensController" field.
 *
 * @author Team StackOverFlow
 * @version 1.0
 */
abstract class Controller {

    protected ScreensController screensController;

    /**
     * This method is called by the plus button in the navigation of every .fxml-file.
     * It changes the current screen to the new one over the {@link ScreensFramework}
     */
    public void switchToNewPage() {
        screensController.setScreen(ScreensFramework.SCREEN_NEW);
    }

    /**
     * This method is called by the calendar button in the navigation of every .fxml-file.
     * It changes the current screen to the new one over the {@link ScreensFramework}
     */
    public void switchToCalendarPage() {
        screensController.setScreen(ScreensFramework.SCREEN_CALENDAR);
    }

    /**
     * This method is called by the list button in the navigation of every .fxml-file.
     * It changes the current screen to the new one over the {@link ScreensFramework}
     */
    public void switchToOverviewPage() {
        screensController.setScreen(ScreensFramework.SCREEN_OVERVIEW);
    }

    /**
     * This method is called by the logo button in the navigation of every .fxml-file.
     * It changes the current screen to the new one over the {@link ScreensFramework}
     */
    public void switchToDashboardPage() {
        screensController.setScreen(ScreensFramework.SCREEN_DASHBOARD);
    }
}
