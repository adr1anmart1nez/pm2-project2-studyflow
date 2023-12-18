package ch.zhaw.pm2.studyflow.controller;

/**
 * This interface sets the methods for all implementations.
 *
 * @author StackOverFlow
 * @version 1.0
 */
public interface ControlledScreen {
    /**
     * Sets the parent screen for this controlled screen.
     *
     * @param screenPage the parent screen for this controlled screen
     */
    void setScreenParent(ScreensController screenPage);
}
