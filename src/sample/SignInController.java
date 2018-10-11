package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInController extends ControllerCommuinication implements Initializable {
    @FXML
    ChoiceBox userName;
    @FXML
    PasswordField password;
    private ObservableList<String> users= FXCollections.observableArrayList();//Data inside it  will push at the choiceBox
    public void setChoiceBox(){
        users.add(0,"مدير");  users.add(1,"موظف 1");  users.add(2,"موظف 2");
        userName.setItems(users);
    }
    @FXML
    public void login(Event e){
        String query="SELECT * from users WHERE `id`=?";
        ResultSet rs=null;
        PreparedStatement pr=null;
       try{
           pr=DBOberations.stablishconnection().prepareStatement(query);
           pr.setInt(1,userName.getSelectionModel().getSelectedIndex()+1);
           rs=pr.executeQuery();
           rs.first();
           if (rs.getString(3).trim().equals(password.getText().trim())&&userName.getSelectionModel().getSelectedIndex()!=-1){
              pr=DBOberations.stablishconnection().prepareStatement(
                      "UPDATE users SET is_used=1 WHERE id=? ");
              pr.setInt(1,userName.getSelectionModel().getSelectedIndex()+1);
            if( pr.executeUpdate()==1) super.showScene(e,"payment","بحث/دفع");
           }
           else {
            Alert alert=   new Alert(Alert.AlertType.ERROR);
            alert.setContentText("كلمة المرور غير صحيحة");
            alert.setTitle("كلمة المرور غير صحيحة");
            alert.show();
           }

       }catch(SQLException se){
           se.printStackTrace();
       }finally{
       }
    }
    public  void newAccount(Event event){

        super.showScene(event,"users","مستخدم جديد");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChoiceBox();
    }
}
