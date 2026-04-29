package Controller;

import Model.AsymmetricModel;
import UIComponent.ASymmetricPanel;

public class AsymmetricController {
    private ASymmetricPanel view;
    private AsymmetricModel model;

    public AsymmetricController(ASymmetricPanel view, AsymmetricModel model ) {
        this.view = view;
        this.model = model;
    }

}
