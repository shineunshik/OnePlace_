package oneplace.com;

public class Ob_Chat_Room {

    String cr_review_user_name; //리뷰 작성자(사용자)
    String cr_review_content; //내용
    String cr_review_user_address; //리뷰 작성자(지역)
    String cr_review_time; //시간
    String cr_review_key; //키
    String c_key; //게시글의 키
    String c_first; //입장

    public Ob_Chat_Room(){

    }


    public String getCr_review_user_name() {
        return cr_review_user_name;
    }

    public void setCr_review_user_name(String cr_review_user_name) {
        this.cr_review_user_name = cr_review_user_name;
    }

    public String getCr_review_content() {
        return cr_review_content;
    }

    public void setCr_review_content(String cr_review_content) {
        this.cr_review_content = cr_review_content;
    }

    public String getCr_review_user_address() {
        return cr_review_user_address;
    }

    public void setCr_review_user_address(String cr_review_user_address) {
        this.cr_review_user_address = cr_review_user_address;
    }

    public String getCr_review_time() {
        return cr_review_time;
    }

    public void setCr_review_time(String cr_review_time) {
        this.cr_review_time = cr_review_time;
    }

    public String getCr_review_key() {
        return cr_review_key;
    }

    public void setCr_review_key(String cr_review_key) {
        this.cr_review_key = cr_review_key;
    }

    public String getC_key() {
        return c_key;
    }

    public void setC_key(String c_key) {
        this.c_key = c_key;
    }

    public String getC_first() {
        return c_first;
    }

    public void setC_first(String c_first) {
        this.c_first = c_first;
    }
}
