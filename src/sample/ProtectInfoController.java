package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
public class ProtectInfoController extends ControllerCommuinication implements Initializable {
    @FXML
    public void saveAs(){
        java.io.File file =BackUp.savingPlace("pdf");
        new SaveInfo().saveAs(file );
}
   @FXML
   public void onEntered(Event event ){
        super.setonEntered(event);
   }
    @FXML
    public void onExited(Event event ){
        super.setonExit(event);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
