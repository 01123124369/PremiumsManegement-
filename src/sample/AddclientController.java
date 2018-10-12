package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class AddclientController extends ControllerCommuinication implements Initializable {
  /*the date default is now+ one month its the first date the client will pay in..
  *when inserted into DB you can not change it any way
  * its direct go to database
  */
  @FXML
  DatePicker datePicker;
  /*the name of village which the client live in its constant you can not change it any way
  * its direct go to database
  * */
  @FXML
  ComboBox villaage;
  /*
  *full name must be un duplecite its
  * no row mus t have the same identical full_name
  * so when search for if it existed in past it prevent to take place such this problem
  * its direct go to database
  */
  @FXML
  TextField fullname;
  /*area that the client live at
  * you constatnt you can not modify it
  * its direct go to database
  */
  @FXML
  TextField address;
  @FXML
  TextField phone;//client phone .you can not modify it..its direct go to database
  @FXML
  TextField jop;//client jop ..you cannot modify it
  /*client presonal id in government it some thing as 2152300858856
   *...cant modify it any way
   * its direct go to database
   */
  @FXML
  TextField government_id;
  /*the person who come to guarante the client ..
  * can not modify it any way ..perhaps in another vesion i will allow for that
  * its direct go to database
  * */
  @FXML
  TextField guarantor_name;
  @FXML
  TextField guarantor_phone;//the person government id off guarantor...its direct go to database
  /*the value off money that client should pay per month
  * its direct go to database
  * you can chang it only when adding new account to current account
  * we add new to old account thus produce prenuimnalue for the twice accounts
  * */
  @FXML
  TextField premuimvalue;
  @FXML
  TextField npremuim;//number of premuim..its not going to database but i multibly it to premuimnalue the produce remained mony
  /*percent that add because off paying in several month..
  * its not going to DB
  * */
  @FXML
  TextField percrnt;
  /*
  * its price off good if was not bought in premuim
  * not go to DB ..i use it to calculate new_remainedmoney
  * actually its price before adding the percent
  * */
  @FXML
  TextField total;
  /*
  * given money that client pay during buying
  * its part off total money
  * i delete it from totalmoney
  */
  @FXML
  TextField preMoney;
  /*
  * its the money off the account which its informatin dont go to DB yet
  * its equal totalmoney that will get premuim if only its first time of client
  * */
  @FXML
  TextField newAccount;
  @FXML
  Label report;//print messege if account not found in DB
  @FXML
  /*
   *old remained money off old account
   * ..its actually remained money that stored in dataBase now(before pressing add) ,
   * have value off 0 if its first time to client to buy
   * else its value come from DB as remained money stored in DB
   * actully before the client come to buy another good in premuims to modify its account
   * its not going to database directly
   */
  TextField old_remainedmoney;
  /*new account + old account to get new premuim which present this field
  *its directly go to  DB
  * its equal old_premuim if only its first time for client
  * you can modify it when add some premuim good to this account
  * i call the premuim in Db as old
  * if its the the third time to buy from the same account the second time is the old ..
  * and when he bought in second time second premuim was called new
  * */
  @FXML
  TextField addNewtoOLd;
  /*
  * table in which the suggested dat showen on ..
  * its default is hidden but this mode is changed when some data is suggested..
  * */
  @FXML
  TextField id;
  @FXML
  TableView<ClientData> table;
    @FXML
    TableColumn <ClientData,String>tname;
    @FXML
    TableColumn<ClientData,Integer> tevaultion;//its <30..never be great than 30
    @FXML
    TableColumn <ClientData,Integer>topremuimvalue;//old premuim value
    @FXML
    TableColumn <ClientData,Integer>tnpremuimvalue;//new premuim value
    @FXML
    TableColumn <ClientData,Integer>ttpremuimvalue;//total premuim value
    @FXML
    TableColumn <ClientData,Integer>toremainedmoney;//old remained money
    @FXML
    TableColumn<ClientData,Integer> tnremainedmoney;//new remained money
    @FXML
    TableColumn <ClientData,Integer>ttremainedmoney;//total remained money
    @FXML
    TableColumn <ClientData,Integer>tid;//id of client ..the old id is the only
    @FXML
    TableColumn<ClientData,java.sql.Date> tdatepremuim;//old premuim value will be the only...user cant change it in DB..but may can do it in interface

  private ObservableList<ClientData> list= FXCollections.observableArrayList();// suggested Data that will add to table
  private static  int index;//selected index in table
  ResultSet rs=null;//all result of search be in this object
  public void add(Event event){
    /*if account is existed(not first time off client)
     *then only update number off premuims and remained money
     * but if its new account i will insert all mentined data to DB specifically in table `client_info`
    * */
    if (checkData()) {//check data firstly
      if (Integer.parseInt(old_remainedmoney.getText()) > 0)
        //update some data
        DBOberations.updateIntValue(Integer.parseInt(addNewtoOLd.getText()),
                Integer.parseInt(premuimvalue.getText()), fullname);
      else {
        //insert data
        DBOberations.insertClientData(fullname.getText(), jop.getText(), government_id.getText(), address.getText()
                , phone.getText(), (String) villaage.getValue(), guarantor_name.getText(), guarantor_phone.getText(),
                Integer.parseInt(addNewtoOLd.getText()), Integer.parseInt(premuimvalue.getText()), datePicker,Integer.parseInt(preMoney.getText()));
        makeNewDir(id.getText());

      }
      DBOberations.updateUsermoney(Integer.parseInt(preMoney.getText()));//add the pre money to user account
      DBOberations.fullMoney(Integer.parseInt(preMoney.getText()));//update the paiments off thid current day
      /*
       * set the last id used at AddClient scene so tht if user
       * forget can see it in clientId TextField which exist at
       * Payment scene
       * */
      try {
        super.setPreviousId(Integer.parseInt(id.getText()));
      }catch (Exception ex){ex.printStackTrace();}
      super.showScene(event, "payment", "بحث/دفع");
      makeDir(id.getText());
      new PrintingInfo().setPrintingInfo(fullname.getText(), address.getText(), Integer.parseInt(preMoney.getText()));
      new DBOberations().printcoupon();
    }
    }
    public void makeDir(String name){
    File file=new File ("E:\\"+name );
    if (file.exists())
    {
      DBOberations.deleteDir(file );
    }
    file.mkdir();
    }
@FXML
public void suggestPremuim(Event event){
    //it controlles only at premuim value ..its value depend on number of premuim the user want but i male it equal 10 as default
premuimvalue.setText(String.valueOf(Integer.parseInt(addNewtoOLd.getText())/Integer.parseInt(npremuim.getText())));
}
/*
* search for the part or full name in `client_info`
* data we get store at rs
* */
@FXML
public void searchforClient(Event event){
  //data store in object off ObservableList called rs
  list.clear();//so if pressing again ,the result off old search shown again
  try {
     rs = DBOberations.searchName("client_info" ,fullname.getText());//search for account
    ResultSet pr=rs;
    if (pr!=null)
     addRows(rs);
     rs=DBOberations.searchName("ended_accounts",fullname.getText());
    if (rs!=null)
      addRowsended(rs);
     old_remainedmoney.setText("0");//set the default value off this field
    if (list.size()==0)
      report.setText("العميل غير موجود في مسبقا حاول استخدام جزء من الاسم اذا كنت متاكد انه موجود");
    else {
      table.setVisible(true);
      table.setItems(list);
    }
  }catch(Exception se){se.printStackTrace();}

}
public boolean checkData() {
  boolean rv = true;
 try {
   if (fullname.getText().length() < 9) {
     rv = false;
     JOptionPane.showMessageDialog(null, "اسم المستخدم قصير جدا");
   }
   if (Integer.valueOf(preMoney.getText()) < 0) {
     rv = false;
     JOptionPane.showMessageDialog(null, "ادخل المقدم المدفوع");
   }
   if (Integer.valueOf(npremuim.getText()) < 0 || Integer.valueOf(preMoney.getText()) < 0) {
     rv = false;
     JOptionPane.showMessageDialog(null, "خطا في قيمة القسط او عدد الافساط");
   }
   if (Integer.valueOf(old_remainedmoney.getText()) < 0) {
     rv = false;
     JOptionPane.showMessageDialog(null, "ادخل المقدم المدفوع");
   }
 }catch (Exception e){
   rv=false;
   JOptionPane.showMessageDialog(null,"خطأ في المدخلات");
 }
 return rv;
}
public void initialTable(){//initiall coloumn off the table
  tid.setCellValueFactory(new PropertyValueFactory<ClientData,Integer>("id"));
  tnpremuimvalue.setCellValueFactory(new PropertyValueFactory<ClientData,Integer>("npremuimValue"));
  topremuimvalue.setCellValueFactory(new PropertyValueFactory<ClientData,Integer>("opremuimValue"));
  ttpremuimvalue.setCellValueFactory(new PropertyValueFactory<ClientData,Integer>("tpremuimValue"));
  toremainedmoney.setCellValueFactory(new PropertyValueFactory<ClientData,Integer>("oremainedmoney"));
  tnremainedmoney.setCellValueFactory(new PropertyValueFactory<ClientData,Integer>("nremainedmoney"));
  ttremainedmoney.setCellValueFactory(new PropertyValueFactory<ClientData,Integer>("tremainedmoney"));
  tevaultion.setCellValueFactory(new PropertyValueFactory<ClientData,Integer>("latingMoney"));
  tname.setCellValueFactory(new PropertyValueFactory<ClientData,String>("fullname"));
  tdatepremuim.setCellValueFactory(new PropertyValueFactory<ClientData, Date>("premuimDate"));
  table.setItems(list);
}
/*
*add row produced from resultSet current object
* to the table ,thus it may produce any number off rows ..it
* depend on  search result
*/
public void addRows(ResultSet rs){
  ClientData client=null ;//this variable to store data in it Temporarily
  Lating l=null;
  try {
    while (rs.next()) {
      client=new ClientData();//each once it have new value
      l=new Lating(rs);//calculate lating off client
      client.setFullname(rs.getString("full_name"));
      client.setId(rs.getInt("id"));
      client.setOpremuimValue(rs.getInt("premuim_value"));
      client.setPremuimDate(rs.getDate("premuim_date"));
      client.setOremainedmoney(rs.getInt("remainedmoney"));
      client.setJop(rs.getString(3));
      client.setGovernment_id(rs.getString(4));
      client.setAddress(rs.getString(5));
      client.setVillage(rs.getString(7));
      int late =l.getLatingMoney();
      if (late<0)late =0;
      client.setLatingMoney(late );
      list.add(client);
      }
}catch (SQLException se){}

}
public void addRowsended(ResultSet rs){
  ClientData client=null ;//this variable to store data in it Temporarily
  try {
    while (rs.next()) {
      client=new ClientData();//each once it have new value
      client.setFullname(rs.getString(2));
      client.setOremainedmoney(0);
      client.setEvaulation(rs.getInt(3));
      list.add(client);
    }
  }catch (SQLException se){}
}
@FXML
 public void calculateNewaccount(Event event){
  //calculate remained money of new account
  newAccount.setText(String.valueOf(DBOberations.calculateNewaccount(percrnt,preMoney,total,npremuim,premuimvalue)));
}
@FXML
public void onSelectedrow(){
  index=table.getSelectionModel().getSelectedIndex();
  if(old_remainedmoney.getText()!="0")
   setFieldsautomatically(list.get(index),fullname,jop,government_id,villaage,address,phone,guarantor_name,guarantor_phone);
else
  setFieldsautomatically(list.get(index),fullname);
}
/*
* when you make a search then you have more than one result often..so ResultSet object have often more than one row
* i used rs to show data in fields off interface and now the question which row i will use
* i will use the row which user select on table
* so i get the index of row selected in table but can not say that they have same intiale off index
* as table row start from 0 but ResultSet start  from 1 so the selected row which u should get in resultSet is table row selected+1
* so now i know which row i need from rs
* and i will delete others
* */
public ResultSet makeOneRowResultSet(ResultSet res){
  try {
    index=table.getSelectionModel().getSelectedIndex();//index off row in table
    while (rs.next()){
      if (rs.getRow()!=index+1)rs.deleteRow();//row i need is row in table+1 and i will delete others
    }
  }catch (SQLException se){
  }
  return  res;
}
public int getIndex(){//i should get index so that i know which row off rs i need because when i delete other row im rs its exusted but equal NULL
  //if even delete it rs rows number is constant so i need it when i pass it to method that will use rs
  return   table.getSelectionModel().getSelectedIndex();
}
  public   void setFieldsautomatically(ClientData client,TextField name, TextField jop, TextField government_id, ComboBox place , TextField address, TextField phon, TextField guarantor, TextField guranor_phone){
      name.setText(client.getFullname());
      jop.setText(client.getJop());
      place.setValue(client.getVillage());
      government_id.setText(client.getGovernment_id());
      address.setText(client.getAddress());
      phon.setText(client.getPhone());
      place.setValue(client.getVillage());
      old_remainedmoney.setText(String.valueOf(client.getOremainedmoney()));
  }
  //if the row come from searching at ended_accounts i will use this overload mehod
  public   void setFieldsautomatically(ClientData client,TextField name){
    name.setText(client.getFullname());
  }
  public void addNewtoOld(){//set addnew to old field value
  int old=Integer.parseInt(old_remainedmoney.getText());
  int newa=Integer.parseInt(newAccount.getText());
  addNewtoOLd.setText(String.valueOf(old+newa));
}
  @FXML
  public void signUP(Event event){
    super.signUp(event);
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
    try {
      ResultSet rs= DBOberations.getcurrentUser();
      rs.first();
      if (rs.getInt(1)==1)
        super.showScene(event,"manage", "ادارة الخزينة");
      rs.close();
    }catch (SQLException se){}
 }
  @FXML
  public void payment(Event event){
    super.showScene(event,"payment", "بحث/دفع");
}


  public void makeNewDir (final String name ){
    java .io.File file =new File("F://"+name+".pdf");
    file.mkdir();
  }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         DBOberations.setDate(datePicker);//set datepicker default value
         DBOberations.setCombobox(villaage);//push villages name into comboBox
         npremuim.setText("10");
         table.setVisible(false);
         id.setText(String.valueOf(DBOberations.getUnusedId("client_info")));//id off the nearest place at table ready for inserting
         initialTable();
    }
  @FXML
    public  void  setEntered(Event event){
     super.setonEntered(event);
    }
  @FXML
  public  void  setExit(Event event){
  super.setonExit(event );
  }
}
