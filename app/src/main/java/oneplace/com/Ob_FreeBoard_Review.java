package oneplace.com;

import java.util.ArrayList;

public class Ob_FreeBoard_Review {
    String b_review_user_name; //작성자
    String b_review_count; //조회수
    String b_review_content; //내용
    String b_review_time; //시간
    String b_review_key; //키
    String b_review_address; //지역(경상남도)
    String b_review_board_key; //게시글의 키
    String b_review_board_type; //게시판 type


    public Ob_FreeBoard_Review(){
    }


    public String getB_review_user_name() {
        return b_review_user_name;
    }

    public void setB_review_user_name(String b_review_user_name) {
        this.b_review_user_name = b_review_user_name;
    }

    public String getB_review_content() {
        return b_review_content;
    }

    public void setB_review_content(String b_review_content) {
        this.b_review_content = b_review_content;
    }

    public String getB_review_time() {
        return b_review_time;
    }

    public void setB_review_time(String b_review_time) {
        this.b_review_time = b_review_time;
    }

    public String getB_review_key() {
        return b_review_key;
    }

    public void setB_review_key(String b_review_key) {
        this.b_review_key = b_review_key;
    }

    public String getB_review_address() {
        return b_review_address;
    }

    public void setB_review_address(String b_review_address) {
        this.b_review_address = b_review_address;
    }

    public String getB_review_board_key() {
        return b_review_board_key;
    }

    public void setB_review_board_key(String b_review_board_key) {
        this.b_review_board_key = b_review_board_key;
    }

    public String getB_review_board_type() {
        return b_review_board_type;
    }

    public void setB_review_board_type(String b_review_board_type) {
        this.b_review_board_type = b_review_board_type;
    }

    public String getB_review_count() {
        return b_review_count;
    }

    public void setB_review_count(String b_review_count) {
        this.b_review_count = b_review_count;
    }
}
