package oneplace.com;

public class Ob_FreeBoard {
    String f_title; //1.제목
    String f_content; //2.내용
    String f_time; //3.작성 시간
    String f_user_name; //4.작성자
    String f_user_address; //4.작성자의 지역
    String f_key; //5.키
    String f_image_key; //6.이미지 url
    String f_image_name; //6.이미지 url
    String f_address; //7.지역(경상남도)
    String f_board_type; //9.A
    int f_board_view; //10.조회수
    int f_board_review; //11.댓글수
    String f_board; //13.자유게시판(부산,울산-경남권)
    String f_Place_Chat; //14.채팅(부산,울산-경남권)

    public Ob_FreeBoard(){
    }

    public String getF_title() {
        return f_title;
    }

    public void setF_title(String f_title) {
        this.f_title = f_title;
    }

    public String getF_content() {
        return f_content;
    }

    public void setF_content(String f_content) {
        this.f_content = f_content;
    }

    public String getF_time() {
        return f_time;
    }

    public void setF_time(String f_time) {
        this.f_time = f_time;
    }

    public String getF_user_name() {
        return f_user_name;
    }

    public void setF_user_name(String f_user_name) {
        this.f_user_name = f_user_name;
    }

    public String getF_key() {
        return f_key;
    }

    public void setF_key(String f_key) {
        this.f_key = f_key;
    }

    public String getF_image_key() {
        return f_image_key;
    }

    public void setF_image_key(String f_image_key) {
        this.f_image_key = f_image_key;
    }

    public String getF_image_name() {
        return f_image_name;
    }

    public void setF_image_name(String f_image_name) {
        this.f_image_name = f_image_name;
    }

    public String getF_address() {
        return f_address;
    }

    public void setF_address(String f_address) {
        this.f_address = f_address;
    }


    public String getF_board_type() {
        return f_board_type;
    }

    public void setF_board_type(String f_board_type) {
        this.f_board_type = f_board_type;
    }

    public int getF_board_view() {
        return f_board_view;
    }

    public void setF_board_view(int f_board_view) {
        this.f_board_view = f_board_view;
    }

    public int getF_board_review() {
        return f_board_review;
    }

    public void setF_board_review(int f_board_review) {
        this.f_board_review = f_board_review;
    }


    public String getF_board() {
        return f_board;
    }

    public void setF_board(String f_board) {
        this.f_board = f_board;
    }

    public String getF_Place_Chat() {
        return f_Place_Chat;
    }

    public void setF_Place_Chat(String f_Place_Chat) {
        this.f_Place_Chat = f_Place_Chat;
    }


    public String getF_user_address() {
        return f_user_address;
    }

    public void setF_user_address(String f_user_address) {
        this.f_user_address = f_user_address;
    }
}
