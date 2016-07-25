package edu.uchicago.sooji1.pro_imageshop;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pikashoes on 7/24/16.
 *
 * When user clicks a toolbar item, change the cursor.
 */


public class ToolbarController implements Initializable
{
//    private double xPos, yPos, hPos, wPos;
//    public enum Pen
//    {
//        SEL, GRAY, CROP;
//    }
//
//    public Pen penStyleTB = Pen.GRAY;

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
    void grayButtonAction(ActionEvent event)
    {
        if (Cc.getInstance().getImg() == null)
            return;

        Cc.getInstance().setImgView(Cc.getInstance().getImgView());

        Image greyImage = Cc.getInstance().transform(Cc.getInstance().getImg(), Color::grayscale);
        Cc.getInstance().setImageAndRefreshView(greyImage);
    }

    @FXML
    void selectButtonAction(ActionEvent event)
    {

    }

    @FXML
    void eyedropButtonAction(ActionEvent event)
    {

    }

    @FXML
    void bucketButtonAction(ActionEvent event)
    {

    }

    @FXML
    void flipHButtonAction(ActionEvent event)
    {

    }

    @FXML
    void flipVButtonAction(ActionEvent event)
    {

    }

    @FXML
    void cropButtonAction(ActionEvent event)
    {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
//        Cc.getInstance().getImgView().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>()
//        {
//            @Override
//            public void handle(MouseEvent me)
//            {
//                if (penStyleTB == Pen.GRAY)
//                {
//                    xPos = (int) me.getX();
//                    yPos = (int) me.getY();
//                }
//
//                me.consume();
//            }
//        });
//
//        Cc.getInstance().getImgView().addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>()
//        {
//            @Override
//            public void handle(MouseEvent me)
//            {
//                if (penStyleTB == Pen.GRAY)
//                {
//                    Image transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
//                            (x, y, c) -> (x > xPos && x < wPos)
//                                    && (y > yPos && y < hPos) ?  c.deriveColor(0, 1, .5, 1): c);
////
////                    Image greyImage = Cc.getInstance().transform(Cc.getInstance().getImg(), Color::grayscale);
//                    Cc.getInstance().setImageAndRefreshView(transformImage);
//                } else {
//                    // Do nothing for now
//                }
//                me.consume();
//            }
//        });





//
//        Cc.getInstance().getImgView().addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>()
//        {
//            @Override
//            public void handle(MouseEvent me)
//            {
//                penStyleTB = Pen.CROP;
//                me.consume();
//            }
//        });


    }


}
