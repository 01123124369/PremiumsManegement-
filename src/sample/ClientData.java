package sample;

import java.sql.Date;

public class ClientData {
    private String fullname;//for client
    private Integer id;//id is constatant
    private Integer opremuimValue;//old premuim value
    private Integer npremuimValue;//new premuim value
    private Integer tpremuimValue;//total premuim value
    private Integer latedays;//days he late to pay for this current month
    private Integer LateMonths;//number off month he had to pay but he didnt
    private String phone ;//phone number off the client
    private String address;
    private Integer latingMoney;

    public Integer getLatingMoney() {
        return latingMoney;
    }

    public void setLatingMoney(Integer latingMoney) {
        this.latingMoney = latingMoney;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setConst_Remained(Integer const_Remained) {
        this.const_Remained = const_Remained;
    }
    public Integer getConst_Remained() {
        return const_Remained;
    }

    public Integer getLateMonths() {
        return LateMonths;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getLatedays() {
        return latedays;
    }

    public void setLateMonths(Integer lateMonths) {
        LateMonths = lateMonths;
    }

    public void setLatedays(Integer latedays) {
        this.latedays = latedays;
    }
    private  String jop;
    private String government_id;

    public String getGovernment_id() {
        return government_id;
    }

    public void setGovernment_id(String government_id) {
        this.government_id = government_id;
    }

    public void setJop(String jop) {
        this.jop = jop;
    }

    public String getJop() {
        return jop;
    }

    private java.sql.Date premuimDate;//there is only one day for new and old value
    private Integer oremainedmoney;//remained from the old account
    private Integer nremainedmoney;//remained from the new account
    private Integer tremainedmoney=0;//total money remained from the old and new account
    private Integer evaulation;//never exceed 30,30 is max value for evaulation
    private  String dateAfterformatting;//date as string ..i will use it in payment scene table
    private  String village;//town or village the client live at
    private Integer const_Remained;//the root money before paying any premuim..it have no update else if add new account to current one
    ClientData(){
        //embty constructor
    }
    public Integer getNremainedmoney() {
        return nremainedmoney;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public Integer getOremainedmoney() {
        return oremainedmoney;
    }

    public Integer getTremainedmoney() {
        return tremainedmoney;
    }

    public ClientData(Integer oremainedmoney, Integer nremainedmoney, Integer tremainedmoney, Integer tpremuimValue, Integer id, String fullname, Integer opremuimValue, Integer npremuimValue, java.sql.Date premuimDate, Integer evaulation) {
        this.evaulation=evaulation;
        this.fullname=fullname;
        this.opremuimValue=opremuimValue;
        this.premuimDate=premuimDate;
        this.npremuimValue=npremuimValue;
        this.id=id;
        this.tpremuimValue=tpremuimValue;
        this.oremainedmoney=oremainedmoney;
        this.nremainedmoney=nremainedmoney;
        this.tremainedmoney=tremainedmoney;

    }

    public Integer getEvaulation() {
        return evaulation;
    }

    public String getFullname() {
        return fullname;
    }

    public void setOremainedmoney(Integer oremainedmoney) {
        this.oremainedmoney = oremainedmoney;
    }

    public void setTremainedmoney(Integer tremainedmoney) {
        this.tremainedmoney = tremainedmoney;
    }

    public void setNremainedmoney(Integer nremainedmoney) {
        this.nremainedmoney = nremainedmoney;
    }

    public Integer getNpremuimValue() {
        return npremuimValue;
    }

    public String getVillage() {
        return village;
    }

    public Integer getOpremuimValue() {
        return opremuimValue;
    }

    public Date getPremuimDate() {
        return premuimDate;
    }

    public void setEvaulation(Integer evaulation) {
        this.evaulation = evaulation;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setNpremuimValue(Integer npremuimValue) {
        this.npremuimValue = npremuimValue;
    }

    public void setOpremuimValue(Integer opremuimValue) {
        this.opremuimValue = opremuimValue;
    }

    public Integer getTpremuimValue() {
        return tpremuimValue;
    }

    public void setTpremuimValue(Integer tpremuimValue) {
        this.tpremuimValue = tpremuimValue;
    }

    public Integer getId() {
        return id;
    }

    public void setDateAfterformatting(String dateAfterformatting) {
        this.dateAfterformatting = dateAfterformatting;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateAfterformatting() {
        return dateAfterformatting;
    }

    public void setPremuimDate(Date premuimDate) {
        this.premuimDate = premuimDate;
    }
}
