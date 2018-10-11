package sample;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
public class Lating  {
    private int monthNumber = 0;//month number between now and date off buying
    private int lateDays = 0;//
    private int value;//money he should be already paid
    private int absoluteValue;//money client pay
    private  ClientData client;
    private   LocalDateTime lo;//premuim Date
    private Integer latingMoney=0;//value off money he had laten

    public Integer getLatingMoney() {
        return value-absoluteValue;
    }

    private  void setLatingMoney() {
        this.latingMoney = (monthNumber<0)?0:value-absoluteValue;
        if (latingMoney<0)latingMoney=0;
    }

    public Boolean getNotYet() {
        return notYet;
    }

    private Boolean notYet=false;//the first premuim date do not come yet
    public ClientData getClient() {
        return client;
    }

    private void setClient(ClientData client) {
        this.client = client;
    }

    public Lating(ClientData client) {
        this.client = client;
        setLateperiod();
    }
    public Lating (ResultSet rs){
        try {
            client=new ClientData();
            client.setFullname(rs.getString(2));
            client.setId(rs.getInt(1));
            client.setVillage(rs.getString(7));
            client.setDateAfterformatting(rs.getDate(12).toString());
            client.setTpremuimValue(rs.getInt(10));
            client.setTremainedmoney(rs.getInt(11));
            client.setConst_Remained(rs.getInt(14));
            client.setDateAfterformatting(rs.getDate(12).toString());
            client.setPhone(rs.getString(6));
            setLateperiod();
            client.setLatedays(this.getLateDays());
            client.setLatingMoney(this .latingMoney);
        }catch (SQLException se){se.printStackTrace();}
    }
    private void setLateperiod() {
       java.time.LocalDateTime current= LocalDateTime.now();
try {
     lo=LocalDateTime.parse(client.getDateAfterformatting()+" 12:00:01.0".replace(" ","T"));
     Long month=lo.until(current,ChronoUnit.MONTHS)+1;
     if (lo .isAfter(current)) {
              return;
     }
     monthNumber=month.intValue();
     value=monthNumber*client.getTpremuimValue();
     absoluteValue=client.getConst_Remained()-client.getTremainedmoney();
     setLatingMoney();
     if (absoluteValue<(value+client.getTpremuimValue()))
    lateDays =current.getDayOfMonth()>=lo.getDayOfMonth()?
            current.getDayOfMonth()-lo.getDayOfMonth():
            30-lo.getDayOfMonth()+current.getDayOfMonth();
}catch (Exception e){
e.printStackTrace();
}}

    public int getMonthNumber() {
        return monthNumber;
    }

    public Boolean isLate(){ return  latingMoney!=0;}
 public int getLateDays(){
        return this.lateDays;
    }

 private void setValue(){
        this.value=this.monthNumber-this.client.getTremainedmoney();
     }

 private void setAbsoluteValue(){
        this.absoluteValue=client.getConst_Remained()-this.client.getTremainedmoney();
     }
}









    

