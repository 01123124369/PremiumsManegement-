package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class ManageController extends ControllerCommuinication implements Initializable {
    @FXML
    TextField c_name;
    @FXML
    TextField c_village;
    @FXML
    TextField c_address;
    @FXML
    TextField premiumDate;
    @FXML
    TextField premiumValue;
    @FXML
    TextField RemainedMony;

    @FXML
    TextField latedays;
    @FXML
    TextField guarantorName;
    @FXML
    TextField guarantorId;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    TextField embloyee1;
    @FXML
    TextField embloyee2;
    @FXML
    TextField manager;
    @FXML
    TextField currentPayed;
    @FXML
    TextField allaccountMony;

    @FXML
    DatePicker from;
    @FXML
    DatePicker to;
    @FXML
    TextField val;


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    TableView<NewTreats> table;
    @FXML
    TableColumn<NewTreats, Integer> tid;
    @FXML
    TableColumn<NewTreats, String> newOrpay;//is it new account or pay premuim
    @FXML
    TableColumn<NewTreats, String> employee;//employee that make this process
    @FXML
    TableColumn<NewTreats, String> tMoney;//money payed even it premium or pre_money for new client
    private ObservableList<NewTreats> list = FXCollections.observableArrayList();

    public void initializeTable() {
        tid.setCellValueFactory(new PropertyValueFactory<NewTreats, Integer>("id"));
        employee.setCellValueFactory(new PropertyValueFactory<NewTreats, String>("emb_name"));
        newOrpay.setCellValueFactory(new PropertyValueFactory<NewTreats, String>("state"));
        tMoney.setCellValueFactory(new PropertyValueFactory<NewTreats, String>("money"));
    }

    @FXML
    public void fillFields() {
        //delete the oldest the less oldest until table row equal the required row of `new_treats` table
        DBOberations.deletetheExtraRow(50,"new_treats");
        list.clear();//then cant push at old event action result
        String SQL = "SELECT * from new_treats";
        ResultSet rs = null;//ResultSet @@POINTER TO ROW IN TABLE
        NewTreats treat = null;//NULL POINTER OBJECT..VALUE CAHNGE DINAMICALLY at spesific while Loop
        try {
            rs = DBOberations.stablishconnection().createStatement().executeQuery(SQL);
            rs.afterLast();
            while (rs.previous()) {
                treat = new NewTreats(rs.getInt(2), rs.getInt(4), rs.getBoolean(3),rs.getInt(5));
                list.add(treat);
            }
            rs.close();
            table.setItems(list);
            embloyee1.setText(String.valueOf(getMoney(2)));//set embloyee 1 recieved money
            embloyee2.setText(String.valueOf(getMoney(3)));//set embloyee 1 recieved money
            manager.setText(String.valueOf(getMoney(1)));//set MANAGE recieved money
            currentPayed.setText(String.valueOf(DBOberations.setCurrentPayed()));
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void printData(ResultSet set) {
        Lating l = null;
        try {
            set.last();
            if (set.getRow()==0){
                c_name.setText("عذرا!..الحساب منتهي نتيجة لدفع اخر قسط");
                return;
            }
            set.first();//its produced from searching by id THUS IT HAVE ONLY 1 ROW
            c_name.setText(set.getString(2));
            c_address.setText(set.getString(5));
            c_village.setText(set.getString(7));
            premiumDate.setText(set.getDate(12).toString());
            premiumValue.setText(String.valueOf(set.getInt(10)));
            RemainedMony.setText(String.valueOf(set.getInt(11)));
            l = new Lating(set);
            latedays.setText(String.valueOf(l.getLateDays()));
            guarantorName.setText(set.getString(8));
            guarantorId.setText(set.getString(9));
        } catch (SQLException se) {
        }
    }

    @FXML
    public void onSelected() {
        ResultSet rs = null;
        if (table.getSelectionModel().getSelectedIndex() != -1)
            rs = DBOberations.searchid("client_info", list.get(table.getSelectionModel().getSelectedIndex()).getId());
        printData(rs);
        try {
            rs.close();
        }catch (SQLException se){

        }
    }

    public int getMoney(int id) {
        ResultSet rs = null;
        Integer rv = 0;
        try {
            rs = DBOberations.searchid("users", id);
            rs.first();
            rv = rs.getInt(5);
        } catch (SQLException se) {
        } finally {
            try {
                rs.close();
                return rv;
            } catch (SQLException se) {
                return  rv;
            }
        }
    }
    @FXML
    public void getRecievedEMB1(){
        DBOberations.zeroUserMoney(2);
        embloyee1.setText(String.valueOf(getMoney(2)));//set embloyee 1 recieved money
}
    @FXML
    public void getRecievedEMB2(){
        DBOberations.zeroUserMoney(3);
        embloyee2.setText(String.valueOf(getMoney(3)));//set embloyee 1 recieved money
    }
    @FXML
    public void getRecievedManager(){
        DBOberations.zeroUserMoney(1);
        manager.setText(String.valueOf(getMoney(1)));//set embloyee 1 recieved money
    }
    @FXML
    public void getAllmoney(){
        ResultSet rs=null;
        Integer result =0;
        try {
            rs= DBOberations.getData("client_info");
            while (rs.next()){
                result+=rs.getInt(11);
            }
            allaccountMony.setText(String.valueOf(result));
        }catch (SQLException se){

        }
        finally {
            try {
           rs.close();
            }catch (SQLException se){}
        }
    }
    @FXML
    public  void setFromtoTO(){
        if (from.getValue()==null||to.getValue()==null)return;
        java.sql.Date fro =DBOberations.convertlocaltoDate(from);
        java.sql.Date t =DBOberations.convertlocaltoDate(to);
        String SQL="select * from `money` where `date` BETWEEN ? and ? ";
        PreparedStatement pr=null;
        ResultSet rs=null;
        Integer result=0;
        try {
           pr=DBOberations.stablishconnection().prepareStatement(SQL);
           pr.setDate(1,fro);
           pr.setDate(2,t);
           rs=pr.executeQuery();
           while (rs.next()){
               result+=rs.getInt(3);
           }
           val.setText(String.valueOf(result));

        }catch (SQLException se){
            se.printStackTrace();
            val.setText("خارج النطاق");

        }finally {
            try {
                pr.close();
            }catch (SQLException se){

            }
        }


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
        super.showScene(event,"laters"," المتأخرين عن الدفع");

    }
    @FXML
    public void payment(Event event){
        super.showScene(event,"payment", "بحث/دفع");

    }
    @FXML
    public  void  setEntereds(Event event){
        super.setonEntered(event);
    }
    @FXML
    public  void  setExits(Event event){
        super.setonExit(event );
    }
    @FXML
    public void protect_info(Event event ){
        super.showScene(event ,"protectinfo","حماية البيانات");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
          initializeTable();
          DBOberations.deletetheExtraRow(367,"money");
    }
}
