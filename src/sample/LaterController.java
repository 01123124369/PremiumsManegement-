package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class LaterController extends ControllerCommuinication implements Initializable {
    @FXML
    TableView<ClientData> table;
     @FXML
     TableColumn <ClientData,String>name;
     @FXML
     TableColumn <ClientData,String>village;
     @FXML
     TableColumn <ClientData,String>phone;
     @FXML
     TableColumn<ClientData,Integer> lateMoney;
     @FXML
     TableColumn <ClientData,Integer>lateDays;
     @FXML
     TableColumn<ClientData,Integer> premuimValue;
    @FXML
    Label report;
    @FXML
    Label report2;
    protected ObservableList<ClientData> list= FXCollections.observableArrayList();
    public void fillList( ){
        list.clear();
        int totalLatemoney = 0;
        String qurery="SELECT * FROM `client_info`;";
        try {
            PreparedStatement pr=DBOberations.stablishconnection().prepareStatement(qurery);
            ResultSet rs=pr.executeQuery();
            Lating l=null;
            rs.last();
            int rowNumber=rs.getRow();
            rs.first();
            for (int i = 0; i < rowNumber; i++) {
                l=new Lating(rs);
                if (l.getLatingMoney()>0){
                    list.add(l.getClient());
                    totalLatemoney+=l.getLatingMoney();
                }
                rs.next();
                report.setText(String.valueOf(list.size()));
                report2.setText(String.valueOf(totalLatemoney));
            }



        }catch (SQLException se){se.printStackTrace();}



}

 @FXML
 public  void  getLaters(){
    fillList();
    table.setItems(list);
 }
public void intialTable(){
    village.setCellValueFactory(new PropertyValueFactory<ClientData,String>("village"));
    name.setCellValueFactory(new PropertyValueFactory<ClientData,String>("fullname"));
    lateMoney.setCellValueFactory(new PropertyValueFactory<ClientData,Integer>("latingMoney"));
    lateDays.setCellValueFactory(new PropertyValueFactory<ClientData,Integer>("latedays"));
    phone.setCellValueFactory(new PropertyValueFactory<ClientData,String>("phone"));
    premuimValue.setCellValueFactory(new PropertyValueFactory<ClientData,Integer>("tpremuimValue"));
}
    @FXML
    public void signUP(Event event){
        super.signUp(event);
    }
    @FXML
    public void account_new(Event event){
        super.showScene(event,"addclient","اضافة حساب");
    }
    @FXML
    public void payment(Event event){
        super.showScene(event,"payment"," دفع/بحث");

    }
    @FXML
    public void payments(Event event){
        super.showScene(event,"manage", "ادارة الخزينة");

    }
    @FXML
    public  void  setEntered(Event event){
        super.setonEntered(event);
    }
    @FXML
    public  void  setExit(Event event){
        super.setonExit(event );
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        intialTable();

    }
}
