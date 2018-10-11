package sample;

public class NewTreats {
    private Integer id;//client id
    private String emb_name;//embloyee that make this process
    private Integer emb_id;
    private String state;//the process is new client or payment
    private Integer money;//money payed
    /*
     * true for pay
     * false for new account
     * */
    private Boolean state_bo;
    public  NewTreats(int id, int emb_id, Boolean state,int money) {
        this.emb_id = emb_id;
        this.id = id;
        this.state_bo = state;
        this .money=money;
        this.setEmb_name();
        this.setstate();
    }


    public String getState() {
        return state;
    }

    public String getEmb_name() {
        return emb_name;
    }

    public int getId() {
        return id;
    }

    private void setstate() {
        if (state_bo)
            state = "دفع";
        else
            state = "جديد";
    }
    private void setEmb_name(){
        switch (emb_id){
            case 1:{emb_name="المدير";break;}
            case 2:{emb_name="موظف1";break;}
            case 3:{emb_name="موظف2";break;}
 }
    }
    public int getMoney(){return money;}
    public void setMoney(int money){this .money=money;}
    }


