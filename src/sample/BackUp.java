package sample;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class BackUp {
    private String DB_userName;
    private String DB_passWord;
    private String DB_dbName;
    private String savePath;
    private  String []executeCmd = {"cd..","cd..","cd xampp","cd mysql\\bin","mysql -u"+ DB_userName +"-p"+DB_passWord +DB_dbName+" > "+savePath};
    private  BackUp(){
        File file =savingPlace("sql");
        this .savePath=file.getPath();
        this .DB_dbName="premiums";
        this .DB_passWord="";
        this .DB_userName="root";
try {
    Process process=Runtime.getRuntime().exec(this .executeCmd);
    if (process.waitFor()!=0)
    {
        Alert alert=new Alert(null);
        alert.setContentText("حدث خطا ..حاول مرة اخري !");
        alert.showAndWait();
    }
}catch (IOException |InterruptedException  i){i.printStackTrace();}
    }
    public static File savingPlace(String extension){
        FileChooser fc=new FileChooser();
        fc.setTitle("حفظ الملف ");
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
        fc.setInitialFileName(formatter.format(new Date())+"."+extension );
        fc.setInitialDirectory(new File("F://"));
        return fc.showSaveDialog(null );//selected file
    }
    public static BackUp  getInstatnce (){
        return new BackUp();
    }

}
