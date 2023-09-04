package oneplace.com;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class BackPressClose {

    long backKeyPressedTime = 0; //뒤로가기 버튼을 누른 시간
    Toast toast; //안내문을 띄어 주기 위한 토스트 변수
    Activity activity;  //엑티비티를 입력 받을 변수
    String board_main_title,chat_main_title,select_address;

    //현재 엑티비티를 입력 받기 위한 생성자
    public  BackPressClose(Activity context){
        this.activity = context;
    }


    //사용자에게 두 번의 뒤로가기 입력을 받은 경우
    public void onBackPressed(){
        //한 번의 뒤로가기 버튼의 눌린 뒤 현재 시간을 변수에 저장 한 후 토스트 룰력
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        //한 번의 뒤로가기 버튼이 눌린 후 0~2초 사이에 한 번더 눌리게 되면 현재 엑티비티를 호출
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            ActivityCompat.finishAffinity(activity);
            activity.finish();
            //앱 완전 종료를 api16 이하 버전에서는 이렇게 사용하고 api16 이상에서는 activity.finishAffinity(); 이렇게 사용 함
        }
    }

    public void onBackPressed2(){
        //한 번의 뒤로가기 버튼의 눌린 뒤 현재 시간을 변수에 저장 한 후 토스트 룰력
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            showGuide2();
            return;
        }
        //한 번의 뒤로가기 버튼이 눌린 후 0~2초 사이에 한 번더 눌리게 되면 현재 엑티비티를 호출
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            //한번 더 누르면 이전 엑티비티로 이동
            ActivityCompat.finishAfterTransition(activity);


        }
    }

    public void onBackPressed3(){
        get_save_board_main_title(); //게시판 지역 타이틀
        get_save_chat_main_title(); //체팅방 지역 타이틀
        get_save_select_address();

        //한 번의 뒤로가기 버튼의 눌린 뒤 현재 시간을 변수에 저장 한 후 토스트 룰력
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            showGuide3();
            return;
        }
        //한 번의 뒤로가기 버튼이 눌린 후 0~2초 사이에 한 번더 눌리게 되면 현재 엑티비티를 호출
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            Intent intent = new Intent(activity,Place_Chat_Main.class);
            intent.putExtra("board",board_main_title);
            intent.putExtra("Place_Chat_Main",chat_main_title);
            intent.putExtra("board_address",select_address);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
          //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//이전 활동을 모두 지움
        //    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//이미 시작되고있는 엑티비티가있으면 실행되지않음
            activity.startActivity(intent);
            activity.finish();
         //   ActivityCompat.finishAffinity(activity);
            //앱 완전 종료를 api16 이하 버전에서는 이렇게 사용하고 api16 이상에서는 activity.finishAffinity(); 이렇게 사용 함
        }
    }

    public void showGuide(){
        toast = Toast.makeText(activity,"한 번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showGuide2(){
        toast = Toast.makeText(activity,"한 번 더 누르면 뒤로 갑니다.\n(작성된 내용은 저장되지 않습니다.) ",Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showGuide3(){
        toast = Toast.makeText(activity,"방을 나가려면 한 번 더 눌러주세요.",Toast.LENGTH_SHORT);
        toast.show();
    }

    public void get_save_board_main_title(){
        SharedPreferences get_board_main_title = activity.getSharedPreferences("save_board_main_title",MODE_PRIVATE);
        board_main_title = get_board_main_title.getString("save_board_main_title_key","");
    }

    public void get_save_chat_main_title(){
        SharedPreferences get_save_address = activity.getSharedPreferences("save_chat_main_title",MODE_PRIVATE);
        chat_main_title = get_save_address.getString("save_chat_main_title_key","");
    }

    public void get_save_select_address(){
        SharedPreferences get_save_select_address = activity.getSharedPreferences("save_select_address",MODE_PRIVATE);
        select_address = get_save_select_address.getString("save_select_address_key","");
    }

}