package sample;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Alert;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
public class SaveInfo {
    public final static float []TableDimension=new float[]{5f,2.3f,4f,4.3f,9.5f,2.2f};
    private static Alert alert=null;
    private static Document my_pdf_report = new Document();
    private static PdfPTable my_report_table = new PdfPTable(TableDimension);
    private static PdfPCell table_cell=null;//used along off class ..its very variable value

    public void saveAs(File file ){
   try {
       my_pdf_report.open();
       PdfWriter.getInstance(my_pdf_report, new FileOutputStream(file ));
       my_pdf_report.open();
       my_pdf_report.add(fillTable());
   }catch(IOException notfound ){alert.setContentText("لم يتم حفظ الملف ");alert.show();}
   catch(DocumentException de){alert.setContentText("حدث خطا ما ");alert.show();}
   finally {
       my_pdf_report.close();
   }
}
    private  static PdfPTable fillTable (){
        /*set Headers*/
        setHeaders();
        try{
            BaseFont bf=BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf",BaseFont.IDENTITY_H,true);
            Font f=new Font(bf,12);
            Statement stmt = DBOberations.stablishconnection().createStatement();
           ResultSet query_set = stmt.executeQuery("SELECT *FROM client_info");
           String dbResult;
            int lateMoney;
            SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
            for (int i=0;i<200;i++) {
                while (query_set.next()) {
                    dbResult = query_set.getString(6);
                    table_cell = new PdfPCell(new Paragraph(dbResult, f));
                    my_report_table.addCell(table_cell);
                    lateMoney = new Lating(query_set).getLatingMoney();
                    dbResult = lateMoney == 0 ? "لا يوجد" : String.valueOf(lateMoney);
                    table_cell = new PdfPCell(new Paragraph(dbResult, f));
                    table_cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    my_report_table.addCell(table_cell);
                    dbResult = String.valueOf(query_set.getInt(11));
                    table_cell = new PdfPCell(new Paragraph(dbResult, f));
                    my_report_table.addCell(table_cell);
                    dbResult = formatter.format(query_set.getDate(12));
                    table_cell = new PdfPCell(new Paragraph(dbResult, f));
                    my_report_table.addCell(table_cell);
                    dbResult = query_set.getString(2);
                    table_cell = new PdfPCell(new Paragraph(dbResult, f));
                    table_cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    my_report_table.addCell(table_cell);
                    dbResult = String.valueOf(query_set.getInt(1));
                    table_cell = new PdfPCell(new Paragraph(dbResult, f));
                    my_report_table.addCell(table_cell);
                }
                }
           query_set.close();
           stmt.close();
           return my_report_table;
       }catch(Exception se){
            se.printStackTrace();
          return my_report_table;
       }
    }
    private static void setHeaders(){
        try {
            BaseFont bf=BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf",BaseFont.IDENTITY_H,true);
            Font f=new Font(bf,12);
            table_cell=new PdfPCell(new Phrase("الهاتف",f));
            table_cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table_cell.setHorizontalAlignment(table_cell.ALIGN_CENTER);
            my_report_table.addCell(table_cell);
            table_cell=new PdfPCell(new Phrase("تاخرات",f));
            table_cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table_cell.setHorizontalAlignment(table_cell.ALIGN_CENTER);
            my_report_table.addCell(table_cell);
            table_cell=new PdfPCell(new Phrase("متبقي",f));
            table_cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table_cell.setHorizontalAlignment(table_cell.ALIGN_CENTER);
            my_report_table.addCell(table_cell);
            table_cell=new PdfPCell(new Phrase("تاريخ الاستحقاق",f));
            table_cell.setHorizontalAlignment(table_cell.ALIGN_CENTER);
            table_cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            my_report_table.addCell(table_cell);
            table_cell=new PdfPCell(new Phrase("اسم العميل",f));
            table_cell.setHorizontalAlignment(table_cell.ALIGN_CENTER);
            table_cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            my_report_table.addCell(table_cell);
            table_cell=new PdfPCell(new Phrase("متسلسل",f));
            table_cell.setHorizontalAlignment(table_cell.ALIGN_CENTER);
            table_cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            my_report_table.addCell(table_cell);
        }catch(Exception de){de.printStackTrace();}
    }


}
