package oneplace.com;

public class Ob_Client {

    String c_name;
    String c_phone;
    String c_address;
    String c_image;
    public Ob_Client(){
    }

    public Ob_Client(String c_name,String c_phone,String c_address,String c_image){
        this.c_name=c_name;
        this.c_phone=c_phone;
        this.c_address=c_address;
        this.c_image=c_image;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_phone() {
        return c_phone;
    }

    public void setC_phone(String c_phone) {
        this.c_phone = c_phone;
    }

    public String getC_address() {
        return c_address;
    }

    public void setC_address(String c_address) {
        this.c_address = c_address;
    }

    public String getC_image() {
        return c_image;
    }

    public void setC_image(String c_image) {
        this.c_image = c_image;
    }
}
