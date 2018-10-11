package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        if (isLogin()) {
            Parent root = FXMLLoader.load(getClass().getResource("payment.fxml"));
            primaryStage.setTitle("بحث/دفع");
            primaryStage.setScene(new Scene(root, 1208, 649));
            primaryStage.show();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("signin.fxml"));
            primaryStage.setTitle("تسجيل الدخول");
            primaryStage.setScene(new Scene(root, 500, 380));
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        DBOberations.stablishconnection();
        launch();
    }

    public static boolean isLogin() {
        String sql = "SELECT * FROM `users`";
        ResultSet rs = null;
        PreparedStatement pr = null;
        boolean returned = false;
        try {
            pr = DBOberations.stablishconnection().prepareStatement(sql);
            rs = pr.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean(4)) {
                    returned = true;
                    break;
                }
            }
        } catch (SQLException se) {


        } finally {
            try {
                pr.close();
                rs.close();
                return returned;
            } catch (SQLException se) {
                return returned;
            }
        }
    }



}
