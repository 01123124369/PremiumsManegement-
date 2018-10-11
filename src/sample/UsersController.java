package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class UsersController extends ControllerCommuinication implements Initializable {
    @FXML
    ChoiceBox userName;
    @FXML
    PasswordField password1;
    @FXML
    PasswordField password2;
    private ObservableList<String> users= FXCollections.observableArrayList();//Data inside it  will push at the choiceBox
    @FXML
    public void signIn(Event e){
        if (DBOberations.isEdintical(password1.getText(),password2.getText())){
            if (password1.getText()==null)return;
            if (password1.getText().toCharArray().length<4)return ;//mustn't be lowwer than 4 char
            String query="INSERT INTO `users` (`id`, `user_name`, `user_pass`, `is_used`,`value`) VALUES (?, ?, ?, 1,0);";
            PreparedStatement pr=null;
            try{
                pr=DBOberations.stablishconnection().prepareStatement(query);
                pr.setString(2,userName.getValue().toString());
                pr.setString(3,password1.getText());
                pr.setInt(1,userName.getSelectionModel().getSelectedIndex()+1);
                pr.execute();
                super.showScene(e,"payment","بحث/دفع");
            }catch (SQLException se){se.printStackTrace();}
            finally {
                try {
                    pr.close();
                }catch (SQLException se){se.printStackTrace(); }
            }
        }
    }
    @FXML
    public  void exit(){
        System.exit(0);
    }
    public void setChoiceBox(){
        users.add(0,"مدير");  users.add(1,"موظف 1");  users.add(2,"موظف 2");
        userName.setItems(users);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
          setChoiceBox();//push user name to users arraylist then  be ready to be bushed at ChoiceBox
    }
}
