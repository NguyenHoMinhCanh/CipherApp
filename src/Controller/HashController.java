package Controller;

import Model.HashModel;
import UIComponent.HashPanel;

public class HashController {
    private HashPanel view;
    private HashModel model;

    public HashController(HashPanel view, HashModel model ) {
        this.view = view;
        this.model = model;
    }
}
