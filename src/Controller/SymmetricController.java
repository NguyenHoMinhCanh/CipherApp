package Controller;


import Model.SymmetricModel;
import UIComponent.SymmetricPanel;

public class SymmetricController {
    private SymmetricPanel view;
    private SymmetricModel model;

    public SymmetricController(SymmetricPanel view, SymmetricModel model ) {
        this.view = view;
        this.model = model;
    }
}
