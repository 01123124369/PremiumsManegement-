package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
public class DBOberations {
    private static String url = "jdbc:mysql://localhost:3306/premiums?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
    private static String userName = "root";
    private static String password = "";
    private static Connection conn = null;
    public static ArrayList<String> array = new ArrayList<>();

    public static Connection stablishconnection() {
        try {
            Connection conn = DriverManager.getConnection(url,userName,password);
            return conn;
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null,"افتح برنامج Xampp اولا ! ");
            return conn;
        }
    }

    public static Boolean insertClientData(String full_name, String jop, String credit_id
            , String client_address, String phone, String place
            , String guarantor, String guarantor_phone, int remained_money, int premuimvalue, DatePicker dp,int preMoney) {
        String query = "INSERT INTO `client_info` (id, `full_name`, `jop`, `credit_id`, `client_address`, " +
                "`phone_number`, `place`, `guarantor`, `guarantor_phone` ,`premuim_value` ,`remainedmoney`,`premuim_date`,`evaulation`,`const_remained` ) VALUES " +
                "(?,?, ?, ?, ?,?, ?, ?, ?,?,?,?,?,?); ";
        PreparedStatement preparedStmt =null;
        int id=getUnusedId("client_info");
        try {
           preparedStmt= DBOberations.stablishconnection().prepareStatement(query);
            preparedStmt.setInt(1, id);
            preparedStmt.setString(2, full_name);
            preparedStmt.setString(3, jop);
            preparedStmt.setString(4, credit_id);
            preparedStmt.setString(5, client_address);
            preparedStmt.setString(6, phone);
            preparedStmt.setString(7, place);
            preparedStmt.setString(8, guarantor);
            preparedStmt.setString(9, guarantor_phone);
            preparedStmt.setInt(11, remained_money);
            preparedStmt.setFloat(10, premuimvalue);
            preparedStmt.setDate(12, convertlocaltoDate(dp));
            preparedStmt.setInt(13, 0);
            preparedStmt.setInt(14, remained_money);
            preparedStmt.executeUpdate();
            DBOberations.insertNew(id,false,preMoney);
            return true;

        } catch (SQLException se) {
            JOptionPane.showInputDialog(se.getMessage());
            return false;
        }
        finally {
            try {
              preparedStmt.close();
            }catch (SQLException se){

            }
        }
    }

    public static void setDate(DatePicker dp) {//make default value Databicker
        dp.setValue(LocalDate.now().plusMonths(1));//add 1 month to current date
    }

    public static ArrayList<String> addCities() {//add villages of tahta city
        array.add("طهطا");
        array.add("الجبيرات");
        array.add("الجريدات");
        array.add("الحريدية البحرية");
        array.add("الحريدية القبلية");
        array.add("الخزندارية");
        array.add("السوالم (سوهاج)");
        array.add("الشيخ رحومة");
        array.add("الشيخ زين الدين");
        array.add("الشيخ مسعود (سوهاج)");
        array.add("الصفيحة (سوهاج)");
        array.add("الصوالح (سوهاج)");
        array.add("الصوامعة غرب");
        array.add("القبيصات");
        array.add("الكوم الأصفر");
        array.add("بنجا");
        array.add("بنهو");
        array.add("بني حرب (سوهاج)");
        array.add("بني عمار (سوهاج)");
        array.add("جزيرة الخزندارية");
        array.add("حاجر مشطا");
        array.add("خلوة محفوظ");
        array.add("داود (سوهاج)");
        array.add("ساحل طهطا");
        array.add("شطورة");
        array.add("عرب بخواج");
        array.add("كوم بدر");
        array.add("نجع حمد");
        array.add("نجوع الصوامعة غرب");
        array.add("نزلة القاضي");
        array.add("نزلة عمارة");
        return array;
    }

    public static void setCombobox(ComboBox box) {// push villages value into comboBox
        ObservableList list = FXCollections.observableArrayList(addCities());
        box.setItems(list);
        box.setValue("طهطا");
    }

    public static java.sql.Date convertlocaltoDate(DatePicker dp) {
        LocalDate localDate = dp.getValue();
        java.sql.Date date = java.sql.Date.valueOf(localDate);
        return date;

    }

    /*
     * find the gap producedby deleted some rows
     * i dont to reach to probably id=1000,000,000 when insert in the next row but
     * i want greater id to present accounts number
     * so its only to exploit the gap produced from deleted rows
     * */
    public static int getUnusedId (String table ) {
        int before =1;
        PreparedStatement pr = null;
        ResultSet rst = null;
        try {
            String query = "SELECT id FROM `"+table+"`;";
            pr = stablishconnection().prepareStatement(query);
            rst = pr.executeQuery(query);
            rst.last();//so its ready to get row number
            if (rst.getRow()==0) return before;//so its embety then before=1
            rst.first();
            before = rst.getInt(1);
            while (rst.next() || rst.last()) {
                if (!(rst.getInt(1) == ++before)) {
                    break;
                }
            }
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                pr.close();
                return before;
            } catch (SQLException se) {
                return before;
            }
        }
    }
    public static int  getinsertRow(String table){
        Statement stat = null;
        ResultSet rst = null;
        try {
            String query = "SELECT id FROM`"+table+"`;";
            stat = stablishconnection().createStatement();
            rst = stat.executeQuery(query);
            rst.last();
            System.out.println("dont know ");
            return rst.getRow()+1;
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                stat.close();
                return 1;
            } catch (SQLException se) {
                return 1;
            }
        }
    }

    /*calculate remained money after paying pre_money and add a specific percent*/
    public static int calculateNewaccount(TextField percent, TextField preMoney, TextField totalMoney, TextField npremuim, TextField premuimValue) {
        int remainedMoney = (Integer.parseInt(totalMoney.getText()) - Integer.parseInt(preMoney.getText()));
        remainedMoney = remainedMoney + (remainedMoney * Integer.parseInt(percent.getText()) / 100);
        return remainedMoney;
    }

    public static ResultSet getData(String table) {
        ResultSet rs = null;
        PreparedStatement prepared=null;
        try {
            String query = "SELECT *  FROM `"+table+"`;" ;

            prepared = stablishconnection().prepareStatement(query);
            rs = prepared.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                return rs;


            }catch (Exception se){ return rs;
            }
        }
    }

    public static void updateIntValue(final int remained, final int premuimvalue, final TextField name) {
        PreparedStatement pr=null;
        try {
            String sqlStatement = "UPDATE client_info set premuim_value =?,remainedmoney =?," +
                    "const_remained =? where full_name='" + name.getText() + "';";
            pr = stablishconnection().prepareStatement(sqlStatement);
            pr.setInt(1, premuimvalue);
            System.out.println("up");
            pr.setInt(2, remained);
            pr.setInt(3, remained);
            pr.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        }try{
          pr.close();
        }
        catch (SQLException se){}
    }

    public static ResultSet search(int value) {
        String query = "SELECT * FROM client_info WHERE id =?";
        ResultSet rs = null;
        PreparedStatement pr=null;
        try {
            pr = stablishconnection().prepareStatement(query);
            pr.setInt(1, value);
            rs = pr.executeQuery();

        } catch (SQLException se) {}
        finally {
            return rs;
        }

    }

    public static ResultSet searchid(String table, int value) {
        ResultSet set = null;
        String query = "SELECT * FROM `"+table+"` where id= ? ;";
        PreparedStatement pr=null;
        try {
            pr = stablishconnection().prepareStatement(query);
            pr.setInt(1, value);
            set = pr.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                return set;
            }catch (Exception se){
                return  set;

            }
        }
    }

    public static Boolean payPremuim(TextField name, TextField old_remained, int value) {
        PreparedStatement pr=null;
        boolean rv=false;
        try {

            String sqlStatement = "UPDATE client_info set remainedmoney =? where full_name=?";
             pr = stablishconnection().prepareStatement(sqlStatement);
            pr.setInt(1, Integer.parseInt(old_remained.getText()) - value);
            pr.setString(2, name.getText());
            pr.executeUpdate();
            rv=true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return rv;
        }
        finally {
            try {
               pr.close();
               return rv;
            }catch (SQLException se){return  rv;}
        }

    }

    public static ResultSet searchVillage(String village) {
        String query = "SELECT * from `client_info` where `place`=?;";
        ResultSet rs = null;
        PreparedStatement pr=null;
        try {
            pr = stablishconnection().prepareStatement(query);
            pr.setString(1, village);
            rs = pr.executeQuery();

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {

    return rs;

}


    }

    public static void convertToClientData(ResultSet rs, ObservableList list) {
        try {
            ClientData client = null;
            Lating l = null;
                client = new ClientData();
                client.setFullname(rs.getString(2));
                client.setId(rs.getInt(1));
                client.setVillage(rs.getString(7));
                client.setDateAfterformatting(rs.getDate(12).toString());
                client.setTpremuimValue(rs.getInt(10));
                client.setTremainedmoney(rs.getInt(11));
                client.setConst_Remained(rs.getInt(14));
                client.setDateAfterformatting(rs.getDate(12).toString());
                client.setEvaulation(rs.getInt(13));
                l = new Lating(client);
                client.setLatedays(l.getLateDays());

                list.add(client);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static Boolean isEdintical(String first, String Second) {
        return first.trim().equals(Second);
    }

    public static  void deleteRow( String table ,int id ){
        String query="DELETE FROM `client_info` WHERE `id` = ?";
        PreparedStatement pr=null;
        try {
            pr=DBOberations.stablishconnection().prepareStatement(query);
            pr.setInt(1,id );
            pr.execute();
        }catch (SQLException se){}
        finally {
            try {
                pr.close();
            }catch(SQLException se){se.printStackTrace();}
        }

    }
    public static ResultSet searchName(String table ,String value){
        String query = "SELECT * FROM `"+table+ "` WHERE `full_name` LIKE ?";
        ResultSet rs = null;
        PreparedStatement pr=null;
        try {
            String fname = value+"\u0025";
            pr = stablishconnection().prepareStatement(query);
            pr.setString(1, fname);
            rs = pr.executeQuery();
        } catch (SQLException se) {}
        finally {
            return rs;
        }

    }
    public static void insertNew(int clientId,boolean state,int money){
        String sql="INSERT INTO `new_treats` (`id`, `id_em`, `payer_or_newclient`, `user_id`,`money_payed`) VALUES (?,?,?,?,?);";
        PreparedStatement pr=null;
        try{
            pr=stablishconnection().prepareStatement(sql);
            System.out.println("new teatments");
            pr.setInt(1,getUnusedId("new_treats"));
            pr.setInt(2,clientId);
            pr.setBoolean(3,state);
            ResultSet rs= getcurrentUser();
            rs.last();//because rs will has only 1 row
            pr.setInt(4,rs.getInt(1));
            pr.setInt(5,money);

            pr.execute();
            pr.close();
        }catch(SQLException se){}
    }
    public static  ResultSet getcurrentUser(){
        String sql="SELECT * FROM `users` WHERE `is_used` =1; ";
        PreparedStatement pr=null;
        ResultSet rs=null;
        try{
            pr=stablishconnection().prepareStatement(sql);
            rs=pr.executeQuery();
        }catch(SQLException se){}
        finally {
            return rs;
        }
    }
    public static void updateUsermoney(int value ){
        PreparedStatement pr=null;
        String sql="UPDATE `users` SET `value` = ? WHERE `is_used` = ?; ";
        try{
            pr=stablishconnection().prepareStatement(sql);
            pr.setBoolean(2,true);
            ResultSet rs=getcurrentUser();
            rs.last();//i make sure that it have one row only
            pr.setInt(1,value+rs.getInt(5));
            pr.execute();
        }catch(SQLException se){}
        finally {
            try {
                pr.close();
            }catch(SQLException se){}
        }
    }
    public static void zeroUserMoney( int id ){//make MONEY VALUE = 0
        PreparedStatement pr=null;
        String sql="UPDATE `users` SET `value` = ? WHERE `id` = ?; ";
        try{
            pr=stablishconnection().prepareStatement(sql);
            pr.setInt(2,id);
            pr.setInt(1,0);
            pr.execute();
        }catch(SQLException se){}
        finally {
            try {
                pr.close();
            }catch(SQLException se){}
        }
    }
    public static Boolean isDateChanged(){
        String sql="SELECT * FROM `money` where id=?";
        PreparedStatement pr=null;
        ResultSet rs=null;
        LocalDate l=LocalDate.now();
        Boolean teValue=true;
        try {
            pr=stablishconnection().prepareStatement(sql);
            System.out.println("check date changed");
            pr.setInt(1,getUnusedId("money")-1);
            rs=pr.executeQuery();
            rs.last();
            if (rs.getRow()==0)return  true;
            rs.first();
            if (rs.getDate(2).toLocalDate().equals(l))
                teValue=false;
            rs.close();
        }catch (Exception se){
              se.printStackTrace();
        }
        finally {
          return teValue;
        }
    }
    public static void insertNewday(int value ){
        String sql="INSERT INTO `money` (`id`, `date`, `value`) VALUES (?,?,?);";
        PreparedStatement pr=null;
        ResultSet rs=null;
        try{
            pr=stablishconnection().prepareStatement(sql);
            System.out.println("insert monyey");
            pr.setInt(1,getUnusedId("money"));
            Date current=Date.valueOf(LocalDate.now());
            pr.setDate(2,current);
            pr.setInt(3,value);
            pr.execute();
            pr.close();
        }catch (Exception SE){SE.printStackTrace();}
    }
    public static void updateMoney(int value ){
        PreparedStatement pr=null;
        String sql="UPDATE `money` SET `value` = ? WHERE `money`.`id` = ?; ";
        ResultSet rs=null;
        try{
            pr=stablishconnection().prepareStatement(sql);
            System.out.println("update money");
            pr.setInt(2,getUnusedId("money")-1);
            rs=searchid("money",getUnusedId("money")-1);
            rs.first();//i make sure that it have one row only
            pr.setInt(1,value+rs.getInt(3));
            rs.close();
            pr.executeUpdate();
            pr.close();
        }catch(SQLException se){se.printStackTrace();}
        finally {
            try {
                pr.close();
                rs.close();
            }catch(Exception se){se.printStackTrace();}
        }

    }
   
    public static void fullMoney(int value ){
        if (isDateChanged()) {
            insertNewday(value);
        }
        else
            updateMoney(value);


    }
    public static  int  setCurrentPayed(){
        ResultSet rs=null;
        int  rv=0;
        try{
            rs=DBOberations.getData("money");
            rs.last();
            rv=rs.getInt(3);
        }catch(SQLException se){}
        finally {
            try {
                rs.close();
                return rv;
            }catch (SQLException se){
                return rv;
            }
        }

    }
    public   void printcoupon(){
       try {
           Node node = FXMLLoader.load(getClass().getResource("coupon.fxml"));
           PrinterJob job = PrinterJob.createPrinterJob(Printer.getDefaultPrinter());
           if (job != null) {
               boolean success = job.printPage(node);
               if (success) {
                   job.endJob();
               }
           }
       }catch (Exception e){}
       
    }

    public static int getRandomcoupon(){
        Random random=new Random();
      return   random.nextInt(10000);
    }
    public static void testPrevellige(){

    }


    public static void deleteDir(File file){
        //i make sure that file is directory and existed
        try {
            File[] children =file.listFiles();
            for (int i = 0; i < children.length; i++) {
                children[i].delete();
            }
            file.delete();
        } catch(Exception e){
            e.printStackTrace();
            return;
        }

    }

    /*to make table have a fixed  specific size
     *it will delete the oldest row then the less oldest until reaching to specific point and stop deleting
     * this point called last and this id  the <last> is the end point
     * this similar stack logic
     * here i need to delete all element at bottom until reaching specific size
     * and do NOTHING  only if stack size less or equal requered size
     */
    public static  void deletetheExtraRow(final int rowNum,final String table ){
        int tableRowNum=getUnusedId(table)-1;//get  specific table row  number
        if (tableRowNum<=rowNum)return;
        /*it will delete the old rows until reaching  to specific number of row(rowNum) at table  then end deleting */
        int last=tableRowNum-rowNum;//the last row will be deleted then it will stop deleting
        String Query="DELETE FROM `"+table+ "`WHERE id >0 && id <= ?";
        PreparedStatement pr=null;
        try {
            pr=stablishconnection().prepareStatement(Query);
            pr.setInt(1,last);
            pr.execute();
        }catch (SQLException se){
            se.printStackTrace();
        }
        finally {
            try {
                pr.close();
            }catch (SQLException se){ /*DO NOTHING*/}
        }

    }
}







