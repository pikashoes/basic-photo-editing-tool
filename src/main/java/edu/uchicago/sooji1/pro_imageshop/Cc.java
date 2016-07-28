package edu.uchicago.sooji1.pro_imageshop;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public class Cc
{

    private static Cc stateManager;

    private Stage mMainStage, mSaturationStage, mToolbarStage;
    private ImageView imgView; // Value injected by FXMLLoader
    private Image img;
    private Image IMAGE;
    private Image tempImg;
    private Color mColor;
    private Boolean shadowAdded = false;
    private Boolean toolbarOn = false;
    private Boolean eyedropperOn = false;
    private Boolean selectOn = false;
    private Boolean bucketOn = false;
    private Boolean flipVOn = false;
    private Boolean flipHOn = false;

    // I created my own because I wanted to use a doubly linked list
    private static MyLinkedList backImages = new MyLinkedList();

    /**
     * Create private constructor
     */
    private Cc()
    {
        // Empty
    }
    /**
     * Create a static method to get instance.
     */
    public static Cc getInstance(){
        if(stateManager == null){
            stateManager = new Cc();
        }
        return stateManager;
    }

    //================================================================================================
    //from Horstmann
    public static Image transform(Image in, UnaryOperator<Color> f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(
                width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y,
                        f.apply(in.getPixelReader().getColor(x, y)));
        return out;
    }

    public static <T> Image transform(Image in, BiFunction<Color, T, Color> f, T arg) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y,
                        f.apply(in.getPixelReader().getColor(x, y), arg));
        return out;
    }

    public static Image transform(Image in, ColorTransformer f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y,
                        f.apply(x, y, in.getPixelReader().getColor(x, y)));
        return out;
    }

    public Stage getMainStage() {
        return mMainStage;
    }

    public void setMainStage(Stage mMainStage) {
        this.mMainStage = mMainStage;
    }

    public Stage getSaturationStage() {
        return mSaturationStage;
    }

    public void setSaturationStage(Stage mSaturationStage) {
        this.mSaturationStage = mSaturationStage;
    }


    public ImageView getImgView() {
        return imgView;
    }

    public void setImgView(ImageView imgView) {
        this.imgView = imgView;
    }

    public Image getImg() {
        return img;
    }

    //================================================================================================

    /**
     * Changes the color and opacity based on the slider.
     * @param in
     * @param colorChange
     * @param opacityChange
     * @return
     */
    public static Image transform(Image in, double colorChange, double opacityChange)
    {
        PixelReader pixelReader = in.getPixelReader();

        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(
                width, height);
        PixelWriter pixelWriter = out.getPixelWriter();

        for (int y = 0; y < in.getHeight(); y++)
        {
            for (int x = 0; x < in.getWidth(); x++)
            {
                Color color = pixelReader.getColor(x, y);

                color = color.deriveColor(
                        colorChange,
                        1, 1, opacityChange);

                pixelWriter.setColor(x, y, color);
            }
        }
        return out;
    }

    /**
     * Undo function
     */
    public void undo()
    {

        if (backImages.hasPrevious())
        {
            backImages.moveToPrevious();
            this.img = (Image) backImages.returnCurrent();
        }
        else
        {
            this.img = (Image) backImages.returnCurrent();
        }

        imgView.setImage(img);

    }

    /**
     * Redo function
     */
    public void redo()
    {
        if (backImages.hasNext())
        {
            backImages.moveToNext();
            this.img = (Image) backImages.returnCurrent();
        }

        imgView.setImage(img);
    }

    /**
     * Adds an image to the Linked List. If user undos an action and then sets a new effect,
     * all of the actions following are removed (they can no longer be redone), like pixlr does.
     */
    public void addImageToList(Image img)
    {
        // This will automatically delete all things in front
        backImages.insertAndDeleteNext(img);
    }

    /**
     * The original "set image and refresh view"
     * @param img
     */
    public void setImageAndRefreshView(Image img)
    {
//        backImages.add(this.img);
//        imgUndo = this.img; // UNDO

        this.img = img;
        imgView.setImage(img);

        addImageToList(img);
        setNewImage(img);
    }

    /**
     * Specifically for the color slider and the opacity slider.
     * @param img
     */
    public void setImageandRefreshViewNoNew(Image img)
    {
        this.img = img;
        addImageToList(img);
        imgView.setImage(img);
    }

    /**
     * Getters and setters.
     * Stores the very first, original image.
     * @param img
     */
    public void setOGImage(Image img)
    {
        IMAGE = img;
    }

    public Image getOGImage()
    {
        return IMAGE;
    }

    /**
     * These are for the opacity & color sliders. Sets a temporary new image that retains
     * the previous effects, so that changing the opacity and color doesn't remove them.
     * @param img
     */
    public void setNewImage(Image img)
    {
        tempImg = img;
    }

    public Image getNewImage()
    {
        return tempImg;
    }

    /**
     * Flipping the image horizontally.
     */
    public void flipImageH()
    {
        if (imgView.getScaleX() == 1)
        {
            imgView.setScaleX(-1);
        }
        else
        {
            imgView.setScaleX(1);
        }
    }

    /**
     * Flipping the image vertically.
     */
    public void flipImageV()
    {
        if (imgView.getScaleY() == 1)
        {
            imgView.setScaleY(-1);
        }
        else
        {
            imgView.setScaleY(1);
        }
    }

    /**
     * To check if the flip vertical is selected.
     * @return
     */
    public Boolean getFlipV()
    {
        return flipVOn;
    }

    public void setFlipV(Boolean bool)
    {
        flipVOn = bool;
    }


    /**
     * Adds a drop shadow according to the color chosen from the color picker.
     */
    public void addDropShadow()
    {
        DropShadow ds = new DropShadow(15, mColor);

        // Simulates the removal of the drop shadow
        if (shadowAdded)
        {
            ds.setRadius(0);
            imgView.setEffect(ds);
            shadowAdded = false;

        } else // Add the drop shadow
        {
            ds.setRadius(15);
            imgView.setEffect(ds);
            shadowAdded = true;
        }
    }
    /**
     * Getters and setters for the color picker.
     */
    public Color getCurrentColor()
    {
        return mColor;
    }

    public void setCurrentColor(Color color)
    {
        mColor = color;
    }

    // This one is for the dropper tool
    public void setCurrentColor(Image in, double x, double y)
    {
        PixelReader pixelReader = in.getPixelReader();
        mColor = pixelReader.getColor((int) x, (int) y);
    }

    /**
     * So that the user can switch between toolbar tools and menu bar.
     * @param bool
     */
    public void setToolBar(Boolean bool)
    {
        toolbarOn = bool;
    }

    public Boolean getToolBar()
    {
        return toolbarOn;
    }

    public void setEyedropper(Boolean bool)
    {
        eyedropperOn = bool;
    }

    public Boolean getEyedropper()
    {
        return eyedropperOn;
    }

    public void setSelect(Boolean bool)
    {
        selectOn = bool;
    }

    public Boolean getSelect()
    {
        return selectOn;
    }

    public void setBucket(Boolean bool)
    {
        bucketOn = bool;
    }

    public Boolean getBucket()
    {
        return bucketOn;
    }

    /**
     * Closing the image.
     */
    public void close()
    {

        imgView.setImage(null);
    }

}
