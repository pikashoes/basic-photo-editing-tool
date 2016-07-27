package edu.uchicago.sooji1.pro_imageshop;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pikashoes on 7/24/16.
 *
 * When user clicks a toolbar item, change the cursor.
 */


public class ToolbarController implements Initializable
{
    private double xPos, yPos, hPos, wPos;

    @FXML
    private Button buttonGray;

    @FXML
    private Button buttonSelect;

    @FXML
    private Button buttonEyedrop;

    @FXML
    private Button buttonBucket;

    @FXML
    private Button buttonFlipV;

    @FXML
    private Button buttonFlipH;

    @FXML
    private Button buttonCrop;

    @FXML
    private Button buttonDropShadow;

    private Boolean eyedropperOn = false;

    @FXML
    void grayButtonAction(ActionEvent event)
    {
        Cc.getInstance().setEyedropper(false);
        Cc.getInstance().setSelect(false);
        Cc.getInstance().setBucket(false);

        if (Cc.getInstance().getImg() == null)
            return;

        Cc.getInstance().setImgView(Cc.getInstance().getImgView());

        Image greyImage = Cc.getInstance().transform(Cc.getInstance().getImg(), Color::grayscale);
        Cc.getInstance().setImageAndRefreshView(greyImage);

        Cc.getInstance().addImageToList();
        Cc.getInstance().incPointer();
    }

    @FXML
    void selectButtonAction(ActionEvent event)
    {
        Cc.getInstance().setToolBar(true);
        Cc.getInstance().setEyedropper(false);
        Cc.getInstance().setSelect(true);
        Cc.getInstance().setBucket(false);

    }

    @FXML
    void eyedropButtonAction(ActionEvent event)
    {

        Cc.getInstance().setToolBar(true);
        Cc.getInstance().setEyedropper(true);
        Cc.getInstance().setSelect(false);
        Cc.getInstance().setBucket(false);

    }

    @FXML
    void bucketButtonAction(ActionEvent event)
    {
        Cc.getInstance().setToolBar(true);
        Cc.getInstance().setEyedropper(false);
        Cc.getInstance().setSelect(false);
        Cc.getInstance().setBucket(true);
    }

    @FXML
    void flipHButtonAction(ActionEvent event)
    {

        Cc.getInstance().setEyedropper(false);
        Cc.getInstance().setSelect(false);
        Cc.getInstance().setBucket(false);

        if (Cc.getInstance().getImg() == null)
            return;

        Cc.getInstance().setImgView(Cc.getInstance().getImgView());
        Cc.getInstance().flipImageH();
    }

    @FXML
    void flipVButtonAction(ActionEvent event)
    {
        Cc.getInstance().setEyedropper(false);
        Cc.getInstance().setSelect(false);
        Cc.getInstance().setBucket(false);

        if (Cc.getInstance().getImg() == null)
            return;

        Cc.getInstance().setImgView(Cc.getInstance().getImgView());
        Cc.getInstance().flipImageV();
    }

    @FXML
    void cropButtonAction(ActionEvent event)
    {
        Cc.getInstance().setEyedropper(false);
        Cc.getInstance().setSelect(false);
        Cc.getInstance().setBucket(false);
    }

    @FXML
    void dropshadowButtonAction(ActionEvent event)
    {
        Cc.getInstance().setToolBar(true);
        Cc.getInstance().setEyedropper(false);
        Cc.getInstance().setSelect(false);
        Cc.getInstance().setBucket(false);

        Cc.getInstance().setImgView(Cc.getInstance().getImgView());
        Cc.getInstance().addDropShadow(Cc.getInstance().getImg());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // TOOL TIPS for text when hovering over toolbar items
        Tooltip grayTP = new Tooltip("Grayscale");
        Tooltip.install(buttonGray, grayTP);

        Tooltip selectTP = new Tooltip("Select");
        Tooltip.install(buttonSelect, selectTP);

        Tooltip eyedropTP = new Tooltip("Dropper tool");
        Tooltip.install(buttonEyedrop, eyedropTP);

        Tooltip bucketTP = new Tooltip("Bucket tool");
        Tooltip.install(buttonBucket, bucketTP);

        Tooltip flipVTP = new Tooltip("Flip Vertically");
        Tooltip.install(buttonFlipV, flipVTP);

        Tooltip flipHTP = new Tooltip("Flip Horizontally");
        Tooltip.install(buttonFlipH, flipHTP);

        Tooltip cropTP = new Tooltip("Crop");
        Tooltip.install(buttonCrop, cropTP);

        Tooltip dropshadowTP = new Tooltip("Drop Shadow");
        Tooltip.install(buttonDropShadow, dropshadowTP);

    }


}
