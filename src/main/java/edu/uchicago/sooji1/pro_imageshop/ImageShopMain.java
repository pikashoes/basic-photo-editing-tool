package edu.uchicago.sooji1.pro_imageshop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class ImageShopMain extends Application {

     //public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {

       // this.stage = stage;

        //Stage
        //Scene
        //Root

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/imageshop.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/styles.css"); // for the buttons
        stage.setTitle("ImageShop");
        stage.setScene(scene);
        stage.setOnHidden(e -> Platform.exit());
        showToolbar();

        stage.show();

        Cc.getInstance().setMainStage(stage);

    }
    /**
     * This is the toolbar window
     */
    public void showToolbar() throws Exception
    {
        Stage dialogStage = new Stage();
        Parent root2 = null;

        root2 = FXMLLoader.load(getClass().getResource("/fxml/toolbar2.fxml"));
        Scene scene2 = new Scene(root2);
        scene2.getStylesheets().add("/styles/styles.css");

        dialogStage.setTitle("Toolbar");
        dialogStage.setScene(scene2);
        dialogStage.setResizable(false);
        dialogStage.setAlwaysOnTop(true); // So the toolbar is always on top

        // This prevents the toolbar from being closed unless the main window is closed
        dialogStage.setOnCloseRequest(event -> event.consume());
        //set the stage so that I can close it later.
        Cc.getInstance().setSaturationStage(dialogStage);
        dialogStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}