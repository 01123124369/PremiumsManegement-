package sample;

public class PrintingInfo {
    private static String name;
    private static int couponNumber;
    private static String address;
    private static  int value ;
    public void setPrintingInfo(String name ,String address,int value ){
        PrintingInfo.setCouponNumber(DBOberations.getRandomcoupon());
        PrintingInfo.setName(name);
        PrintingInfo.setAddress(address);
        PrintingInfo.setValue(value );
    }
    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        PrintingInfo.address = address;
    }

    public static int getValue() {
        return value;
    }

    public static void setValue(int value) {
        PrintingInfo.value = value;
    }

    public static int getCouponNumber() {
        return couponNumber;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        PrintingInfo.name = name;
    }

    public static void setCouponNumber(int couponNumber) {
        PrintingInfo.couponNumber = couponNumber;
    }

}

