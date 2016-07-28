package edu.uchicago.sooji1.pro_imageshop;

import com.sun.deploy.uitoolkit.Applet2Adapter;
import com.sun.deploy.uitoolkit.DragContext;
import com.sun.deploy.uitoolkit.Window;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import javax.tools.Tool;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ImageShopController implements Initializable {

    //private Desktop desktop = Desktop.getDesktop();
    private ToggleGroup mToggleGroup = new ToggleGroup();
    private Rectangle selRect;

    public enum Pen
    {
        CIR, SQR, FIL, CRS;
    }

    public enum FilterStyle
    {
        SAT, DRK;
    }

    @FXML
    private Button selButton;

    @FXML
    private ComboBox<String> cboSome;

    @FXML
    private ToggleButton tgbSquare;

    @FXML
    private ToggleButton tgbCircle;

    @FXML
    private ToggleButton tbCursor;

    @FXML
    private ToggleButton tgbFilter;

    @FXML
    private ColorPicker cpkColor;

    @FXML
    private Slider sldSize;

    @FXML
    private Slider opacitySlider;

    @FXML
    private Slider colorSlider;

    private int penSize = 50;
    private Pen penStyle = Pen.CIR;
    private FilterStyle mFilterStyle = FilterStyle.DRK;

    @FXML // fx:id="imgView"
    private ImageView imgView; // Value injected by FXMLLoader

    // for mouse clicks
    private double xPos, yPos, hPos, wPos;

    private Color mColor = Color.WHITE;

    ArrayList<Shape> removeShapes = new ArrayList<>(1000);

    //Base code
    //http://java-buddy.blogspot.com/2013/01/use-javafx-filechooser-to-open-image.html
    @FXML
    void mnuOpenAction(ActionEvent event) {

        Cc.getInstance().setImgView(this.imgView);


        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        //openFile(file);


        try {
            BufferedImage bufferedImage = ImageIO.read(file);


            // imgView.setImage(Cc.getInstance().getImg());

            Cc.getInstance().setOGImage(SwingFXUtils.toFXImage(bufferedImage, null)); // Original image
            Cc.getInstance().setImageAndRefreshView(SwingFXUtils.toFXImage(bufferedImage, null));

        } catch (IOException ex) {
            Logger.getLogger(ImageShopController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void mnuReOpenLast(ActionEvent event) {

      //  Cc.getInstance().reOpenLast();
    }

    @FXML
    private AnchorPane ancRoot;

    @FXML
    void mnuSaveAction(ActionEvent event) {
    }

    @FXML
    void mnuSaveAsAction(ActionEvent event) {

    }

    @FXML
    void mnuQuitAction(ActionEvent event) {

        System.exit(0);
    }

    @FXML
    void mnuCloseAction(ActionEvent event) {
        Cc.getInstance().close();
    }

    @FXML
    void mnuGrayscale(ActionEvent event)
    {
        if (Cc.getInstance().getImg() == null)
            return;

        //make sure that we set the image view first, so we can roll back and do other operations to it.
        Cc.getInstance().setImgView(this.imgView);

        Image greyImage = Cc.getInstance().transform(Cc.getInstance().getImg(), Color::grayscale);
        Cc.getInstance().setImageAndRefreshView(greyImage);
    }

    @FXML
    void mnuSaturate(ActionEvent event) {

        Cc.getInstance().setImgView(this.imgView);

        Stage dialogStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/saturation.fxml"));
            Scene scene = new Scene(root);
            dialogStage.setTitle("Saturation");
            dialogStage.setScene(scene);
            scene.getStylesheets().add("/styles/styles.css");
            //set the stage so that I can close it later.
            Cc.getInstance().setSaturationStage(dialogStage);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Undo
     * @param event
     */
    @FXML
    void mnuUndo(ActionEvent event)
    {
        Cc.getInstance().undo();
    }

    @FXML
    void mnuRedo(ActionEvent event)
    {
        Cc.getInstance().redo();
    }


    //##################################################################
    //INITIALIZE METHOD
    //see: http://docs.oracle.com/javafx/2/ui_controls/jfxpub-ui_controls.htm
    //##################################################################
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        //mColor = Color.WHITE;
        Cc.getInstance().setCurrentColor(mColor); // Set the color (which is initialized to white)
        tgbCircle.setToggleGroup(mToggleGroup);
        tgbSquare.setToggleGroup(mToggleGroup);
        tgbFilter.setToggleGroup(mToggleGroup);
        tbCursor.setToggleGroup(mToggleGroup);
        tgbCircle.setSelected(true);
        cboSome.setValue("Darker");

        /**
         * TOGGLE GROUP TO SET THE EFFECTS TO PEN OR FILTERS OR TOOLBAR
         */
        mToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == tgbCircle) {
                penStyle = Pen.CIR;
                Cc.getInstance().setToolBar(false);
                System.out.println("Initialize: resources = "+resources );
            } else if (newValue == tgbSquare) {
                penStyle = Pen.SQR;
                Cc.getInstance().setToolBar(false);
            } else if (newValue == tgbFilter) {
                penStyle = Pen.FIL;
                Cc.getInstance().setToolBar(false);
            } else if (newValue == tbCursor)
            {
                penStyle = Pen.CRS;
                Cc.getInstance().setToolBar(true);
            }
            else {
                penStyle = Pen.CIR;
            }
        });

        /**
         * MOUSE PRESSED
         */
        imgView.addEventFilter(MouseEvent.MOUSE_PRESSED, me -> {
            if (penStyle == Pen.FIL){
                xPos = (int) me.getX();
                yPos = (int) me.getY();
            } else if (Cc.getInstance().getToolBar())
            {
                System.out.println("The toolbar is selected.");
                xPos = (int) me.getX();
                yPos = (int) me.getY();

                penStyle = Pen.CRS;
                tbCursor.setSelected(true);

                if (Cc.getInstance().getEyedropper())
                {
                    Cc.getInstance().setCurrentColor(Cc.getInstance().getImg(), xPos, yPos);
                    mColor = Cc.getInstance().getCurrentColor();
                    cpkColor.setValue(mColor);
                }

            }

            me.consume();
        });


        /**
         * MOUSE RELEASE
         */
        imgView.addEventFilter(MouseEvent.MOUSE_RELEASED, me -> {

            if (penStyle == Pen.CIR || penStyle == Pen.SQR) {

                System.out.println("mouse pressed! " + me.getSource());
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setViewport(new Rectangle2D(0, 0, imgView.getFitWidth(), imgView.getFitHeight()));
                Image snapshot = ancRoot.snapshot(snapshotParameters, null);
                Cc.getInstance().setImageAndRefreshView(snapshot);
                ancRoot.getChildren().removeAll(removeShapes);
                removeShapes.clear();

            } else if (penStyle == Pen.FIL){
                wPos =  (int) me.getX() ;
                hPos = (int) me.getY() ;

                //default value
               Image transformImage;

                switch (mFilterStyle){
                    case DRK:
                        //make darker
                        transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
                                (x, y, c) -> (x > xPos && x < wPos)
                                        && (y > yPos && y < hPos) ?  c.deriveColor(0, 1, .5, 1): c
                        );
                        break;

                    case SAT:
                        //saturate
                        transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
                                (x, y, c) -> (x > xPos && x < wPos)
                                        && (y > yPos && y < hPos) ?  c.deriveColor(0, 1.0 / .1, 1.0, 1.0): c
                        );
                        break;

                    default:
                        //make darker
                        transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
                                (x, y, c) -> (x > xPos && x < wPos)
                                        && (y > yPos && y < hPos) ?  c.deriveColor(0, 1, .5, 1): c
                        );
                        break;

                }

                Cc.getInstance().setImageAndRefreshView(transformImage);


            } else if (penStyle == Pen.CRS)
            {
                Image transformImage;
//                    if (Cc.getInstance().getSelect())
//                    {
//                        wPos =  (int) me.getX();
//                        hPos = (int) me.getY();
//
//                        Image transformImage;
//
//                        transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
//                                (x, y, c) -> (x > xPos && x < wPos)
//                                        && (y > yPos && y < hPos) ?  c.deriveColor(0, 1, 1.0/0.1, 1): c);
//
//                        Cc.getInstance().setImageAndRefreshView(transformImage);
//                    }
                    // THIS DOESN'T WORK: NEED TO USE RECTANGLE and make transparent (no fill)

                if (Cc.getInstance().getBucket())
                {
                    wPos =  (int) me.getX();
                    hPos = (int) me.getY();

                    transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
                            (x, y, c) -> (x > xPos && x < wPos)
                                    && (y > yPos && y < hPos) ?  mColor: c);
                    Cc.getInstance().setImageAndRefreshView(transformImage);
                }

            }

            else {
                //do nothing right now

            }
            me.consume();
        });


        /**
         * MOUSE DRAG
         */
        imgView.addEventFilter(MouseEvent.MOUSE_DRAGGED, me -> {

            if (penStyle == Pen.FIL){
                me.consume();
                return;
            } else if (penStyle == Pen.CRS)
            {
                me.consume();
                return;
            }

            xPos = me.getX();
            yPos = me.getY();

            int nShape = 0;
            //default value
            Shape shape = new Circle(xPos, yPos, 10);
            switch (penStyle) {
                case CIR:
                    shape = new Circle(xPos, yPos, penSize);
                    break;
                case SQR:
                    shape = new Rectangle(xPos, yPos, penSize, penSize);
                    break;


                default:
                    shape = new Circle(xPos, yPos, penSize);
                    break;

            }

           // shape.setStroke(mColor);
            shape.setFill(mColor);

            ancRoot.getChildren().add(shape);
            removeShapes.add(shape);
            me.consume();
        });


        /**
         * Color Picker
         */
        cpkColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mColor = cpkColor.getValue();
                Cc.getInstance().setCurrentColor(mColor);
            }
        });


        /**
         * Listener for the slider (pen size)
         */
        sldSize.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double temp  = (Double) newValue; //automatic unboxing
                penSize = (int) Math.round(temp);
            }
        });


        /**
         * For the filter pickers
         */
        cboSome.getItems().addAll(
                "Darker",
                "Saturate"
        );


        cboSome.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue){
                    case "Saturate":
                        mFilterStyle = FilterStyle.SAT;
                        break;

                    case "Darker":
                        mFilterStyle = FilterStyle.DRK;
                        break;

                    default:
                        mFilterStyle = FilterStyle.DRK;
                        break;

                }
            }
        });

        /**
         * This is for the sliders
         */
        ChangeListener<Number> listener = new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                Image transformImage = Cc.getInstance().transform(Cc.getInstance().getNewImage(),
                        colorSlider.getValue(), opacitySlider.getValue());
                Cc.getInstance().setImageandRefreshViewNoNew(transformImage);

            }
        };

        opacitySlider.valueProperty().addListener(listener);
        colorSlider.valueProperty().addListener(listener);

    } //END INIT


}
