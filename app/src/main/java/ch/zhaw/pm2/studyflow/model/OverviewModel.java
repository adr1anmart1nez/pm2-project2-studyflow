package ch.zhaw.pm2.studyflow.model;

import ch.zhaw.pm2.studyflow.Module;
import ch.zhaw.pm2.studyflow.controller.OverviewWindowController;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.List;

/**
 * The OverviewModel class represents the model of the overview view.
 * It provides the information for the {@link OverviewWindowController}.
 */
public class OverviewModel {
    private final ListProperty<Module> allModule;

    /**
     * Initializes an empty list property for all modules.
     */
    public OverviewModel() {
        allModule = new SimpleListProperty<>();
    }

    public ListProperty<Module> getAllModuleProperty() {
        return allModule;
    }

    /**
     * Updates the list of all modules.
     *
     * @param data a list of all modules to be displayed.
     */
    public void updateAllModule(List<Module> data) {
        allModule.set(FXCollections.observableArrayList(data));
    }
}
