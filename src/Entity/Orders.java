package Entity;

public class Orders {
    private String oid;
    private String date;
    private String customerID;

    public Orders() {
    }

    public Orders(String oid, String date, String customerID) {
        this.setOid(oid);
        this.setDate(date);
        this.setCustomerID(customerID);
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "oid='" + oid + '\'' +
                ", date='" + date + '\'' +
                ", customerID='" + customerID + '\'' +
                '}';
    }
}
