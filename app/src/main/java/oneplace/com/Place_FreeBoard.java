package oneplace.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class Place_FreeBoard extends AppCompatActivity {
    TextView area;
    Spinner area_move;
    String select_area_spinner;
    SharedPreferences last_area_spinner;
    SharedPreferences.Editor last_area_spinner_editor;
    TabLayout tablayout;
    ViewPager viewpager;
    FragmentPagerAdapter fragmentPagerAdapter;
    int position;
    String board_main_title,chat_main_title,select_address;
    String select_address_spinner,full_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_freeboard);

        board_main_title = getIntent().getStringExtra("board");
        chat_main_title = getIntent().getStringExtra("Place_Chat_Main");
        select_address = getIntent().getStringExtra("board_address"); //경상남도 등
        set_save_board_main_title();
        set_save_chat_main_title();
        set_save_select_address(); //경상남도 등
        get_save_address();
        get_save_user_name();

        area = (TextView)findViewById(R.id.area);
        area.setText(board_main_title);

        area_move =(Spinner)findViewById(R.id.area_move);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.area_move,R.layout.address_spinner);
        area_move.setAdapter(adapter);

//        last_area_spinner = getSharedPreferences("last_area_spinner",MODE_PRIVATE);
//        last_area_spinner_editor = last_area_spinner.edit();
//        final int last_address_Click = last_area_spinner.getInt("last_area_spinner_key",0);
//        area_move.setSelection(last_address_Click);//마지막에 선택한 스피너 항목 유지하기

        area_move.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position).equals("지역 이동")){

                }
                else {
                    select_area_spinner =area_move.getSelectedItem().toString();//선택한 spinner항목 문자열 변환
                    Intent intent =new Intent(Place_FreeBoard.this,Place_FreeBoard.class);
                    switch (select_area_spinner){
                        case"경남":
                            intent.putExtra("board","자유 게시판(부산,울산-경남권)");
                            intent.putExtra("Place_Chat_Main","채팅(부산,울산-경남권)");
                            intent.putExtra("board_address","경상남도");
                            break;
                        case"경북":
                            intent.putExtra("board","자유 게시판(대구-경북권)");
                            intent.putExtra("Place_Chat_Main","채팅(대구-경북권)");
                            intent.putExtra("board_address","경상북도");
                            break;
                        case"경기":
                            intent.putExtra("board","자유 게시판(서울,인천-경기권)");
                            intent.putExtra("Place_Chat_Main","채팅(서울,인천-경기권)");
                            intent.putExtra("board_address","경기도");
                            break;
                        case"충남":
                            intent.putExtra("board","자유 게시판(대전-충남권)");
                            intent.putExtra("Place_Chat_Main","채팅(대전-충남권)");
                            intent.putExtra("board_address","충청남도");
                            break;
                        case"충북":
                            intent.putExtra("board","자유 게시판(충북권)");
                            intent.putExtra("Place_Chat_Main","채팅(충북권)");
                            intent.putExtra("board_address","충청북도");
                            break;
                        case"전북":
                            intent.putExtra("board","자유 게시판(전북권)");
                            intent.putExtra("Place_Chat_Main","채팅(전북권)");
                            intent.putExtra("board_address","전라북도");
                            break;
                        case"전남":
                            intent.putExtra("board","자유 게시판(광주-전남권)");
                            intent.putExtra("Place_Chat_Main","채팅(광주-전남권)");
                            intent.putExtra("board_address","전라남도");
                            break;
                        case"강원":
                            intent.putExtra("board","자유 게시판(강원도)");
                            intent.putExtra("Place_Chat_Main","채팅(강원도)");
                            intent.putExtra("board_address","강원도");
                            break;
                        case"제주":
                            intent.putExtra("board","자유 게시판(제주도)");
                            intent.putExtra("Place_Chat_Main","채팅(제주도)");
                            intent.putExtra("board_address","제주도");
                            break;
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tablayout=(TabLayout)findViewById(R.id.tablayout);
        tablayout.addTab(tablayout.newTab().setText("유머"));
        tablayout.addTab(tablayout.newTab().setText("정보\n공유"));
        tablayout.addTab(tablayout.newTab().setText("업체\n추천"));
        tablayout.addTab(tablayout.newTab().setText("고독"));
        tablayout.addTab(tablayout.newTab().setText("공지\n사항"));
        tablayout.addTab(tablayout.newTab().setText("고객\n센터"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewpager=(ViewPager)findViewById(R.id.viewpager);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));//화면 넘길때 탭이랑 뷰 같이 이동
        fragmentPagerAdapter = new ViewPagerAdapter_FreeBoard(getSupportFragmentManager(),6);
      //  ((ViewPagerAdapter_FreeBoard)fragmentPagerAdapter).getText(getIntent().getStringExtra("board_address"));
        viewpager.setAdapter(fragmentPagerAdapter);

        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position=tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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

    public void set_save_board_main_title(){
        SharedPreferences save_board_main_title = getSharedPreferences("save_board_main_title",MODE_PRIVATE);
        SharedPreferences.Editor save_board_main_title_editor =save_board_main_title.edit();
        save_board_main_title_editor.putString("save_board_main_title_key",board_main_title);
        save_board_main_title_editor.apply();
    }
    public void get_save_board_main_title(){
        SharedPreferences get_board_main_title = getSharedPreferences("save_board_main_title",MODE_PRIVATE);
        board_main_title = get_board_main_title.getString("save_board_main_title_key","");
    }

    public void set_save_chat_main_title(){
        SharedPreferences set_save_chat_main_title = getSharedPreferences("save_chat_main_title",MODE_PRIVATE);
        SharedPreferences.Editor set_save_chat_main_title_editor =set_save_chat_main_title.edit();
        set_save_chat_main_title_editor.putString("save_chat_main_title_key",chat_main_title);
        set_save_chat_main_title_editor.apply();
    }
    public void get_save_chat_main_title(){
        SharedPreferences get_save_address = getSharedPreferences("save_chat_main_title",MODE_PRIVATE);
        chat_main_title = get_save_address.getString("save_chat_main_title_key","");
    }

    public void set_save_select_address(){
        SharedPreferences set_save_select_address = getSharedPreferences("save_select_address",MODE_PRIVATE);
        SharedPreferences.Editor set_save_select_address_editor =set_save_select_address.edit();
        set_save_select_address_editor.putString("save_select_address_key",select_address);
        set_save_select_address_editor.apply();
    }
    public void get_save_select_address(){
        SharedPreferences get_save_select_address = getSharedPreferences("save_select_address",MODE_PRIVATE);
        select_address = get_save_select_address.getString("save_select_address_key","");
    }

}