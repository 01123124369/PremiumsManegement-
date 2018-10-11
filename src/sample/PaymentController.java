package sample;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 import java.util.ResourceBundle;
public class PaymentController extends ControllerCommuinication implements Initializable {
    @FXML
    TableView<ClientData> table;
    @FXML
    TableColumn<ClientData, String> tname;
    @FXML
    TableColumn<ClientData, String> tvillage;
    @FXML
    TableColumn<ClientData, String> tdate;
    @FXML
    TableColumn<ClientData, Integer> tid;
    @FXML
    TextField searchingField;
    @FXML
    CheckBox sname;
    @FXML
    CheckBox id;
    @FXML
    TextField name;
    @FXML
    TextField village;
    @FXML
    TextField date;//date he should pay in
    @FXML
    TextField address;
    @FXML
    TextField premuimValue;
    @FXML
    TextField remaimedMoney;
    @FXML
    TextField payed_money;
    @FXML
    Label report;
    @FXML
    TextField lateDays;
    @FXML
    TextField lateMoney;
    @FXML
    Button pay;
    @FXML
    TextField jop;
    @FXML
    TextField guarntor;
    @FXML
    TextField G_id;//guarantor official id
    @FXML
    TextField phone ;
    @FXML
    TextField clientId;

    private ObservableList<ClientData> list = FXCollections.observableArrayList();//STORE SUGGESTED DATA IN
    private int index;//index off selected row
    private ResultSet rs = null;
    private String previousName;

    public void initialTable() {
        /*
        * get the last id used at AddClient scene so tht if user
        * forget can see it in clientId TextField
        * */
        System.out.println("used id is  "+getPreviousId());
        clientId.setText(String.valueOf(super.getPreviousId()));
        tid.setCellValueFactory(new PropertyValueFactory<ClientData, Integer>("id"));
        tname.setCellValueFactory(new PropertyValueFactory<ClientData, String>("fullname"));
        tdate.setCellValueFactory(new PropertyValueFactory<ClientData, String>("dateAfterformatting"));
        tvillage.setCellValueFactory(new PropertyValueFactory<ClientData, String>("village"));


    }

    @FXML
    public void searchDirectly() {
        table.setVisible(true);
        rs = DBOberations.getData("client_info");
        searchingField.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                try {
                    list.clear();//push Result off search in list
                    if (chooseSearchtype() == "full_name") //it depend on which checkBox is selected
                    {
                        if (searchingField.getText().toCharArray().length <= 1) return;
                        while (rs.next()) {
                            if (rs.getString(2).trim().startsWith(searchingField.getText()))
                                DBOberations.convertToClientData(rs, list);
                        }
                    } else {
                        while (rs.next()) {
                            if (rs.getInt(1) == Integer.parseInt(searchingField.getText()))
                                DBOberations.convertToClientData(rs, list);
                        }
                    }
                    rs.last();//do that to get rs row number
                    if (rs.getRow() == 0 || searchingField.getText() == "") return;//no result
                    rs.beforeFirst();
                    table.setItems(list);
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        });

    }

    public void setfalse(CheckBox ch) {//set un selected checkBox =false
        CheckBox c[] = {sname, id};
        for (CheckBox e :
                c) {
            if (e != ch) e.setSelected(false);
        }
    }

    public String chooseSearchtype() {
        if (sname.isSelected()) return "full_name";
        else if (id.isSelected()) return "id";
        else return "place";
    }

    /*its for action off the three checkbox in this scene*/
    @FXML
    public void checkboxAction(Event event) {
        setfalse((CheckBox) event.getSource());
    }

    @FXML
    public void setIndex() {
        index = table.getSelectionModel().getSelectedIndex();
        showSelectedRowinfields();
    }

    public void showSelectedRowinfields() {
        pay.setDisable(false);
        ResultSet result = DBOberations.search(list.get(index).getId());
        printData(result, name, address, village, premuimValue, remaimedMoney, date, payed_money, lateDays, lateMoney, jop, guarntor, G_id);
    }

    public void printData(ResultSet set, TextField name, TextField address, TextField village, TextField premuimValue, TextField remaimedMoney, TextField date, TextField payed_money, TextField LateDays, TextField lateMoney, TextField jop, TextField guarantor, TextField guarantorId) {
        try {
            set.first();
            name.setText(set.getString(2));
            address.setText(set.getString(5));
            village.setText(set.getString(7));
            premuimValue.setText(String.valueOf(set.getInt(10)));
            remaimedMoney.setText(String.valueOf(set.getInt(11)));
            date.setText(set.getDate(12).toString());
            payed_money.setText(String.valueOf(set.getInt(10)));
            jop.setText(set.getString(3));
            guarantor.setText(set.getString(8));
            guarantorId.setText(set.getString(9));
            clientId.setText(String.valueOf(set.getInt(1)));
            setLatefields(LateDays, lateMoney);
            phone.setText(set.getString(6));

        } catch (SQLException se) {
        }
    }

    @FXML
    public void payPremuim() {
        if (name.getText().equals(previousName))
            pay.setDisable(true);
        else {
            int value = list.get(index).getTremainedmoney() - Integer.parseInt(payed_money.getText());
            if (value > 0) {
                if (DBOberations.payPremuim(name, remaimedMoney, Integer.parseInt(payed_money.getText()))) {
                    report.setText(" تم دفع مبلغ " + payed_money.getText() + " جنيه ");
                    previousName = name.getText();
                }
            } else if (value == 0) {
                DBOberations.deleteRow("ended_accounts", list.get(index).getId());
                insertEnded(name.getText(), list.get(index).getEvaulation());
                report.setText(" تم دفع مبلغ " + payed_money.getText() + " جنيه ");
               DBOberations.deleteDir(new File("E:\\" + clientId.getText()));
            } else
                report.setText("المبلغ المراد دفعه اكثر من المبلغ المتبقي !");
        }
        DBOberations.updateUsermoney(Integer.parseInt(payed_money.getText()));
        DBOberations.insertNew(list.get(index).getId(), true,Integer.parseInt(payed_money.getText()));
        DBOberations.fullMoney(Integer.parseInt(payed_money.getText()));
        new PrintingInfo().setPrintingInfo(name.getText(), address.getText(), Integer.parseInt(payed_money.getText()));
        new DBOberations().printcoupon();
        filterLate();
    }



 public void setLatefields(TextField lateDays,TextField lateMoney){
         Lating late = new Lating(list.get(index));
         lateDays.setText(String.valueOf(late.getLateDays()));
         if (!late.isLate()) {
             lateMoney.setText("لا يوجد");
             lateDays.setText("لا يوجد");
             System.out.println("t");
         } else {
             lateMoney.setText(String.valueOf(late.getLatingMoney()));
             System.out.println("f");
             lateDays.setText(String.valueOf(late.getLateDays()));

         }

 }
 public void filterLate(){
     Lating l=null;
     list.clear();
    try {
       ResultSet rs=DBOberations.searchVillage(village.getText());
       while (rs.next()){
          l=new Lating(rs);
          if (l.isLate()){
              list.add(l.getClient());
          }
        }
    }catch (Exception se){se.printStackTrace();}
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
    public void getLaters(Event event){
        ResultSet rs= DBOberations.getcurrentUser();
        try {
            rs.first();
            if (rs.getInt(1)==1)
                super.showScene(event,"laters"," المتأخرين عن الدفع");
            rs.close();
        }catch (SQLException se){}


    }
    @FXML
    public void payments(Event event){
        ResultSet rs= DBOberations.getcurrentUser();
        try {
            rs.first();
            if (rs.getInt(1)==1)
                super.showScene(event,"manage", "ادارة الخزينة");
            rs.close();
        }catch (SQLException se){}


    }

public void insertEnded(String name,int evaulation){
     String sql="INSERT INTO `ended_accounts` (`id`, `full_name`, `evaulation`) VALUES (?, ?, ?);";
     PreparedStatement pr=null;
     try{
       pr=DBOberations.stablishconnection().prepareStatement(sql);
       pr.setInt(1,DBOberations.getUnusedId("ended_accounts"));
       pr.setString(2,name);
       pr.setInt(3,evaulation);
       pr.execute();
     }catch (SQLException se){
         se.printStackTrace();
     }finally {
         try {
                pr.close();
         }catch (SQLException se){

         }
     }

}



@FXML
    public  void  setEntered(Event event){
        super.setonEntered(event);
    }
    @FXML
    public  void  setExit(Event event){
        super.setonExit(event );
    }
@FXML
public void showanotherScene(){//show protectInfo scene ..make another stage
    try {
        Parent root = FXMLLoader.load(getClass().getResource("protectinfo.fxml"));
        Scene sc = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(sc);
        stage.setResizable(true);
        stage.setTitle("حماية البيانات");
        stage.show();
} catch (IOException e) {
        e.printStackTrace();
    }

}
    @Override
public void initialize(URL location, ResourceBundle resources) {
  initialTable();
  table.setVisible(false);
  sname.setSelected(true);
    rs=DBOberations.getData("client_info");

}
}
