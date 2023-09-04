package oneplace.com;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class Login_View extends AppCompatActivity {

    Spinner address_spinner;
    SharedPreferences last_address_spinner;
    SharedPreferences.Editor last_address_spinner_editor;

    Button address_join;
    CheckBox login_check;
    LinearLayout company_info,company;
    ImageView arrow_down,arrow_up;
    int count=0;

    private final int PERMISSION_ALL = 100;

    Dialog permission_dialog,permission_setting_dialog;
    TextView accept,close,set;
    private final String[] PERMISSIONS = {
            android.Manifest.permission.READ_PHONE_NUMBERS,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.ACCESS_NETWORK_STATE

    };

    private final String[] PERMISSIONS2 = {
            android.Manifest.permission.READ_PHONE_NUMBERS,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION

    };

    String random_name_a,random_name_b,random_name_c,random_number,random_address;
    String select_address_spinner,full_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        //퍼미션 Dialog
        permission_dialog = new Dialog(this);
        permission_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        permission_dialog.setContentView(R.layout.permission_dialog);
        permission_dialog.setCanceledOnTouchOutside(false);//바탕화면 터치 비활성
        permission_dialog.setCancelable(false);//뒤로가기 비활성
        accept=(TextView) permission_dialog.findViewById(R.id.accept);//허용하기 버튼

        //권한 안내 Dialog
        permission_setting_dialog = new Dialog(this);
        permission_setting_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        permission_setting_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        permission_setting_dialog.setContentView(R.layout.permission_setting_dialog);
        permission_setting_dialog.setCanceledOnTouchOutside(false);//바탕화면 터치 비활성
        permission_setting_dialog.setCancelable(false);//뒤로가기 비활성
        close=(TextView)permission_setting_dialog.findViewById(R.id.close);
        set=(TextView)permission_setting_dialog.findViewById(R.id.set);

        arrow_up=(ImageView)findViewById(R.id.arrow_up);
        arrow_up.setVisibility(View.GONE);
        company=(LinearLayout)findViewById(R.id.company);
        company.setVisibility(View.GONE);
        arrow_down =(ImageView) findViewById(R.id.arrow_down);

        company_info=(LinearLayout)findViewById(R.id.company_info);
        company_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count%2==0){
                    arrow_up.setVisibility(View.VISIBLE);
                    company.setVisibility(View.VISIBLE);
                    arrow_down.setVisibility(View.GONE);
                    count++;
                }
                else if(count%2==1){
                    arrow_up.setVisibility(View.GONE);
                    company.setVisibility(View.GONE);
                    arrow_down.setVisibility(View.VISIBLE);
                    count++;
                }
            }
        });
        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count%2==0){
                    arrow_up.setVisibility(View.VISIBLE);
                    company.setVisibility(View.VISIBLE);
                    arrow_down.setVisibility(View.GONE);
                    count++;
                }
                else if(count%2==1){
                    arrow_up.setVisibility(View.GONE);
                    company.setVisibility(View.GONE);
                    arrow_down.setVisibility(View.VISIBLE);
                    count++;
                }
            }
        });

        address_spinner=(Spinner)findViewById(R.id.address_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.address,R.layout.address_spinner_login);
        address_spinner.setAdapter(adapter);

        last_address_spinner = getSharedPreferences("last_address_spinner",MODE_PRIVATE);
        last_address_spinner_editor = last_address_spinner.edit();
        final int last_address_Click = last_address_spinner.getInt("last_address_spinner_key",0);
        address_spinner.setSelection(last_address_Click);//마지막에 선택한 스피너 항목 유지하기

        address_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                last_address_spinner_editor.putInt("last_address_spinner_key",position).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        login_check=(CheckBox)findViewById(R.id.login_check);
        login_check.setVisibility(View.GONE);
        get_save_checkbox();
        get_save_address();
        get_save_user_name();

        //개발중에는 잠시 비활성상태
//        if (login_check.isChecked()){ //최초 로그인 후에는 자동 로그인
//            direct_login();
//            return;
//        }

        address_join=(Button)findViewById(R.id.address_join);
        address_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                select_address_spinner =address_spinner.getSelectedItem().toString();//선택한 spinner항목 문자열 변환
                if (select_address_spinner.equals("-")||select_address_spinner.equals("선택해 주세요.")){
                    Toast.makeText(getApplicationContext(),"지역을 선택해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }//지역 선택


                //26이상 33 이하
                if (Build.VERSION.SDK_INT < 33) {
                    if (hasPermission(Login_View.this,PERMISSIONS2)){//요청한 권한이 허용돠었을때
                        permission_dialog.dismiss();
                        login_check.setChecked(true);
                        set_save_checkbox();
                        set_save_address();

                        Random rnd = new Random();

                        random_name_a = String.valueOf((char) ((int) (rnd.nextInt(26))+97)); //영어 소문자 a-z
                        random_name_b = String.valueOf((char) ((int) (rnd.nextInt(26))+65)); //영어  대문자
                        random_name_c = String.valueOf((char) ((int) (rnd.nextInt(26))+65)); //영어  대문자
                        random_number = String.valueOf(rnd.nextInt(99999)); //숫자
                        full_name = select_address_spinner+"-"+random_name_a+random_name_b+random_name_c+random_number;
                        set_save_user_name();
                        direct_login();
                    }
                    else{ //권한이 거부되었을때
                        permission_dialog.show();// 권한 dialog 띄우기
                        accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //26이상 33 이하
                                Toast.makeText(Login_View.this, Build.VERSION.SDK_INT+"",Toast.LENGTH_SHORT).show();
                                requestPermissions(PERMISSIONS2, PERMISSION_ALL);
                            }
                        });

                    }
                }
                else {
                    if (hasPermission(Login_View.this,PERMISSIONS)){//요청한 권한이 허용돠었을때
                        permission_dialog.dismiss();
                        login_check.setChecked(true);
                        set_save_checkbox();
                        set_save_address();

                        Random rnd = new Random();

                        random_name_a = String.valueOf((char) ((int) (rnd.nextInt(26))+97)); //영어 소문자 a-z
                        random_name_b = String.valueOf((char) ((int) (rnd.nextInt(26))+65)); //영어  대문자
                        random_name_c = String.valueOf((char) ((int) (rnd.nextInt(26))+65)); //영어  대문자
                        random_number = String.valueOf(rnd.nextInt(99999)); //숫자
                        full_name = select_address_spinner+"-"+random_name_a+random_name_b+random_name_c+random_number;
                        set_save_user_name();
                        direct_login();
                    }
                    else{ //권한이 거부되었을때
                        permission_dialog.show();// 권한 dialog 띄우기
                        accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                    Toast.makeText(Login_View.this, Build.VERSION.SDK_INT+"",Toast.LENGTH_SHORT).show();
                                    requestPermissions(PERMISSIONS, PERMISSION_ALL);
                            }
                        });

                    }
                }





            }
        });

    }

    public void direct_login(){
        get_save_address();
        get_save_user_name();
        switch (select_address_spinner){
            case "부산" :
                Toast.makeText(getApplicationContext(),"부산에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(Login_View.this, Place_Main.class);
                intent1.putExtra("board","자유 게시판(부산,울산-경남권)");
                intent1.putExtra("Place_Chat_Main","채팅(부산,울산-경남권)");
                intent1.putExtra("area","부산(51)");
                intent1.putExtra("board_address","경상남도");
                intent1.putExtra("user_name",full_name);
                intent1.putExtra("user_address",select_address_spinner);
                startActivity(intent1);
                finish();
                break;

            case "울산" :
                Toast.makeText(getApplicationContext(),"울산에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(Login_View.this, Place_Main.class);
                intent2.putExtra("board","자유 게시판(부산,울산-경남권)");
                intent2.putExtra("Place_Chat_Main","채팅(부산,울산-경남권)");
                intent2.putExtra("area","울산(52)");
                intent2.putExtra("board_address","경상남도");
                intent2.putExtra("user_name",full_name);
                intent2.putExtra("user_address",select_address_spinner);
                startActivity(intent2);
                finish();
                break;

            case "경남" :
                Toast.makeText(getApplicationContext(),"경남에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(Login_View.this, Place_Main.class);
                intent3.putExtra("board","자유 게시판(부산,울산-경남권)");
                intent3.putExtra("Place_Chat_Main","채팅(부산,울산-경남권)");
                intent3.putExtra("area","경남(55)");
                intent3.putExtra("board_address","경상남도");
                intent3.putExtra("user_name",full_name);
                intent3.putExtra("user_address",select_address_spinner);
                startActivity(intent3);
                finish();
                break;

            case "대구" :
                Toast.makeText(getApplicationContext(),"대구에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(Login_View.this, Place_Main.class);
                intent4.putExtra("board","자유 게시판(대구-경북권)");
                intent4.putExtra("Place_Chat_Main","채팅(대구-경북권)");
                intent4.putExtra("area","대구(53)");
                intent4.putExtra("board_address","경상북도");
                intent4.putExtra("user_name",full_name);
                intent4.putExtra("user_address",select_address_spinner);
                startActivity(intent4);
                finish();
                break;

            case "경북" :
                Toast.makeText(getApplicationContext(),"경북에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent5 = new Intent(Login_View.this, Place_Main.class);
                intent5.putExtra("board","자유 게시판(대구-경북권)");
                intent5.putExtra("Place_Chat_Main","채팅(대구-경북권)");
                intent5.putExtra("area","경북(54)");
                intent5.putExtra("board_address","경상북도");
                intent5.putExtra("user_name",full_name);
                intent5.putExtra("user_address",select_address_spinner);
                startActivity(intent5);
                finish();
                break;

            case "서울" :
                Toast.makeText(getApplicationContext(),"서울에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent6 = new Intent(Login_View.this, Place_Main.class);
                intent6.putExtra("board","자유 게시판(서울,인천-경기권)");
                intent6.putExtra("Place_Chat_Main","채팅(서울,인천-경기권)");
                intent6.putExtra("area","서울(02)");
                intent6.putExtra("board_address","경기도");
                intent6.putExtra("user_name",full_name);
                intent6.putExtra("user_address",select_address_spinner);
                startActivity(intent6);
                finish();
                break;

            case "인천" :
                Toast.makeText(getApplicationContext(),"인천에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent7 = new Intent(Login_View.this, Place_Main.class);
                intent7.putExtra("board","자유 게시판(서울,인천-경기권)");
                intent7.putExtra("Place_Chat_Main","채팅(서울,인천-경기권)");
                intent7.putExtra("area","인천(32)");
                intent7.putExtra("board_address","경기도");
                intent7.putExtra("user_name",full_name);
                intent7.putExtra("user_address",select_address_spinner);
                startActivity(intent7);
                finish();
                break;

            case "경기" :
                Toast.makeText(getApplicationContext(),"경기에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent8 = new Intent(Login_View.this, Place_Main.class);
                intent8.putExtra("board","자유 게시판(서울,인천-경기권)");
                intent8.putExtra("Place_Chat_Main","채팅(서울,인천-경기권)");
                intent8.putExtra("area","경기(31)");
                intent8.putExtra("board_address","경기도");
                intent8.putExtra("user_name",full_name);
                intent8.putExtra("user_address",select_address_spinner);
                startActivity(intent8);
                finish();
                break;

            case "대전" :
                Toast.makeText(getApplicationContext(),"대전에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent9 = new Intent(Login_View.this, Place_Main.class);
                intent9.putExtra("board","자유 게시판(대전-충남권)");
                intent9.putExtra("Place_Chat_Main","채팅(대전-충남권)");
                intent9.putExtra("area","대전(42)");
                intent9.putExtra("board_address","충청남도");
                intent9.putExtra("user_name",full_name);
                intent9.putExtra("user_address",select_address_spinner);
                startActivity(intent9);
                finish();
                break;

            case "충남" :
                Toast.makeText(getApplicationContext(),"충남에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent10 = new Intent(Login_View.this, Place_Main.class);
                intent10.putExtra("board","자유 게시판(대전-충남권)");
                intent10.putExtra("Place_Chat_Main","채팅(대전-충남권)");
                intent10.putExtra("area","충남(41)");
                intent10.putExtra("board_address","충청남도");
                intent10.putExtra("user_name",full_name);
                intent10.putExtra("user_address",select_address_spinner);
                startActivity(intent10);
                finish();
                break;

            case "충북" :
                Toast.makeText(getApplicationContext(),"충북에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent11 = new Intent(Login_View.this, Place_Main.class);
                intent11.putExtra("board","자유 게시판(충북권)");
                intent11.putExtra("Place_Chat_Main","채팅(충북권)");
                intent11.putExtra("area","충북(43)");
                intent11.putExtra("board_address","충청북도");
                intent11.putExtra("user_name",full_name);
                intent11.putExtra("user_address",select_address_spinner);
                startActivity(intent11);
                finish();
                break;

            case "전북" :
                Toast.makeText(getApplicationContext(),"전북에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent12 = new Intent(Login_View.this, Place_Main.class);
                intent12.putExtra("board","자유 게시판(전북권)");
                intent12.putExtra("Place_Chat_Main","채팅(전북권)");
                intent12.putExtra("area","전북(63)");
                intent12.putExtra("board_address","전라북도");
                intent12.putExtra("user_name",full_name);
                intent12.putExtra("user_address",select_address_spinner);
                startActivity(intent12);
                finish();
                break;

            case "광주" :
                Toast.makeText(getApplicationContext(),"광주에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent13 = new Intent(Login_View.this, Place_Main.class);
                intent13.putExtra("board","자유 게시판(광주-전남권)");
                intent13.putExtra("Place_Chat_Main","채팅(광주-전남권)");
                intent13.putExtra("area","광주(62)");
                intent13.putExtra("board_address","전라남도");
                intent13.putExtra("user_name",full_name);
                intent13.putExtra("user_address",select_address_spinner);
                startActivity(intent13);
                finish();
                break;

            case "전남" :
                Toast.makeText(getApplicationContext(),"전남에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent14 = new Intent(Login_View.this, Place_Main.class);
                intent14.putExtra("board","자유 게시판(광주-전남권)");
                intent14.putExtra("Place_Chat_Main","채팅(광주-전남권)");
                intent14.putExtra("area","전남(61)");
                intent14.putExtra("board_address","전라남도");
                intent14.putExtra("user_name",full_name);
                intent14.putExtra("user_address",select_address_spinner);
                startActivity(intent14);
                finish();
                break;

            case "강원" :
                Toast.makeText(getApplicationContext(),"강원도에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent15 = new Intent(Login_View.this, Place_Main.class);
                intent15.putExtra("board","자유 게시판(강원도)");
                intent15.putExtra("Place_Chat_Main","채팅(강원도)");
                intent15.putExtra("area","강원(33)");
                intent15.putExtra("board_address","강원도");
                intent15.putExtra("user_name",full_name);
                intent15.putExtra("user_address",select_address_spinner);
                startActivity(intent15);
                finish();
                break;

            case "제주" :
                Toast.makeText(getApplicationContext(),"제주도에 오신 걸 환영합니다.",Toast.LENGTH_SHORT).show();
                Intent intent16 = new Intent(Login_View.this, Place_Main.class);
                intent16.putExtra("board","자유 게시판(제주도)");
                intent16.putExtra("Place_Chat_Main","채팅(제주도)");
                intent16.putExtra("area","제주(64)");
                intent16.putExtra("board_address","제주도");
                intent16.putExtra("user_name",full_name);
                intent16.putExtra("user_address",select_address_spinner);
                startActivity(intent16);
                finish();
                break;

        }

    }

    public void set_save_checkbox(){
        SharedPreferences set_save_checkbox = getSharedPreferences("save_checkbox",MODE_PRIVATE);// 내가 원하는 정보를 저장
        SharedPreferences.Editor set_save_checkbox_editor = set_save_checkbox.edit(); // Eidtor를 사용해서 값을 넣음
        if (login_check.isChecked()){
            set_save_checkbox_editor.putString("save_checkbox_key","0");
        }
        set_save_checkbox_editor.apply();//저장
    }
    public void get_save_checkbox(){
        SharedPreferences get_save_checkbox = getSharedPreferences("save_checkbox",MODE_PRIVATE);
        String str_get_save_checkbox = get_save_checkbox.getString("save_checkbox_key","");
        if("0".equals(str_get_save_checkbox)){
            login_check.setChecked(true);
        }
    }

    public void set_save_address(){
        SharedPreferences set_save_address = getSharedPreferences("save_address",MODE_PRIVATE);
        SharedPreferences.Editor set_save_address_editor =set_save_address.edit();
        set_save_address_editor.putString("save_address_key",select_address_spinner);
        set_save_address_editor.apply();
    }
    public void get_save_address(){
        SharedPreferences get_save_address = getSharedPreferences("save_address",MODE_PRIVATE);
        select_address_spinner = get_save_address.getString("save_address_key","");
    }

    public void set_save_user_name(){
        SharedPreferences set_save_address = getSharedPreferences("save_user_nam",MODE_PRIVATE);
        SharedPreferences.Editor set_save_address_editor =set_save_address.edit();
        set_save_address_editor.putString("save_user_nam_key",full_name);
        set_save_address_editor.apply();
    }
    public void get_save_user_name(){
        SharedPreferences get_save_address = getSharedPreferences("save_user_nam",MODE_PRIVATE);
        full_name = get_save_address.getString("save_user_nam_key","");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //26이상 33 이하
        if (Build.VERSION.SDK_INT < 33) {
            if (hasPermission(Login_View.this,PERMISSIONS2)){//요청한 권한이 허용돠었을때
                permission_dialog.dismiss();
            }
            else{ //권한이 거부되었을때

                permission_setting_dialog.show();
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        permission_setting_dialog.dismiss();
                        ActivityCompat.finishAffinity(Login_View.this); //앱 종료
                    }
                });
                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        permission_setting_dialog.dismiss();
                        try {
                            Intent appDetail = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",getPackageName(),null);
                            appDetail.setData(uri);
                            appDetail.addCategory(Intent.CATEGORY_DEFAULT);
                            startActivity(appDetail);
                        }
                        catch (Exception e){

                        }
                        ActivityCompat.finishAffinity(Login_View.this); //앱 종료
                    }
                });
            }

        }
        else{
            if (hasPermission(Login_View.this,PERMISSIONS)){//요청한 권한이 허용돠었을때
                permission_dialog.dismiss();
            }
            else{ //권한이 거부되었을때

                permission_setting_dialog.show();
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        permission_setting_dialog.dismiss();
                        ActivityCompat.finishAffinity(Login_View.this); //앱 종료
                    }
                });
                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        permission_setting_dialog.dismiss();
                        try {
                            Intent appDetail = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",getPackageName(),null);
                            appDetail.setData(uri);
                            startActivity(appDetail);
                        }
                        catch (Exception e){

                        }
                        ActivityCompat.finishAffinity(Login_View.this); //앱 종료
                    }
                });
            }
        }



    }

    private boolean hasPermission(Context context,String... permissions){
        if(context !=null && permissions !=null){
            for(String permission : permissions){
                if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }



}