package sample;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ControllerCommuinication {
    private static int previousId ;//previous used id ..its the last id have been used at AddClient interface
    protected final void setPreviousId(int id ){
        previousId=id ;
    }
    protected  final int getPreviousId(){
        return previousId;
    }
    protected final void showScene(Event event, String fxmlname, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlname + ".fxml"));
            Scene sc = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(sc);
            Rectangle2D rect = Screen.getPrimary().getVisualBounds();
            stage.setResizable(true);
            stage.setTitle(title);
            stage.setX((rect.getWidth() - stage.getWidth()) / 2);
            stage.setY((rect.getHeight() - stage.getHeight()) / 2);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void signUp(Event event) {
        String sql = "SELECT * FROM `users` where `is_used`=1";
        ResultSet rs = null;
        PreparedStatement pr = null;
        try {
            pr = DBOberations.stablishconnection().prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean(4)) {
                    String sql2 = "UPDATE users SET is_used=0 WHERE id=? ";
                    pr = DBOberations.stablishconnection().prepareStatement(sql2);
                    pr.setInt(1, rs.getInt(1));
                    pr.executeUpdate();
}
            }
            System.exit(0);
        } catch (SQLException se) {
        } finally {
            try {
                pr.close();
                rs.close();
            } catch (SQLException se) {

            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setonEntered(Event event ){
        Button btn=(Button)event .getSource();
        btn .setScaleX(1.1);
        btn .setScaleY(1.1);
 }
 public void setonExit(Event event ){
     Button btn=(Button)event .getSource();
     btn .setScaleX(1);
     btn .setScaleY(1);
 }
}
