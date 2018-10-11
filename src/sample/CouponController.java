package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CouponController implements Initializable {
    @FXML
    javafx.scene.control.Label coupon_Number;
    @FXML
    javafx.scene.control.TextField lname;
    @FXML
    javafx.scene.control.Label address;
    @FXML
    javafx.scene.control.Label money;
    @FXML
    javafx.scene.control.Label date;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
           coupon_Number.setText(String.valueOf(PrintingInfo.getCouponNumber()));
           lname.setText(PrintingInfo.getName());
           address.setText(PrintingInfo.getAddress());
           money.setText(String.valueOf(PrintingInfo.getValue()));
           date.setText(new java.util.Date().toString());
    }
}
