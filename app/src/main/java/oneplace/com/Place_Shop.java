package oneplace.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class Place_Shop extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewpager;
    FragmentPagerAdapter fragmentPagerAdapter;
    int position;
    ImageView home_move;

    BottomNavigationView bottom_menu;
    String board_main_title,chat_main_title,select_address;
    String select_address_spinner,full_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_shop);

        board_main_title = getIntent().getStringExtra("board");
        chat_main_title = getIntent().getStringExtra("Place_Chat_Main");
        select_address = getIntent().getStringExtra("board_address"); //경상남도 등
        set_save_board_main_title();
        set_save_chat_main_title();
        set_save_select_address(); //경상남도 등
        get_save_address();
        get_save_user_name();

        home_move =(ImageView)findViewById(R.id.home_move);
        home_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Shop.this,Place_Main.class);
                intent.putExtra("board",getIntent().getStringExtra("board"));
                intent.putExtra("Place_Chat_Main",getIntent().getStringExtra("Place_Chat_Main"));
                intent.putExtra("area",getIntent().getStringExtra("area"));
                intent.putExtra("board_address",getIntent().getStringExtra("board_address"));
                intent.putExtra("user_name",full_name);
                intent.putExtra("user_address",select_address_spinner);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                startActivity(intent);
                finish();
            }
        });

        tablayout=(TabLayout)findViewById(R.id.tablayout);
        tablayout.addTab(tablayout.newTab().setText("마사지"));
        tablayout.addTab(tablayout.newTab().setText("에스테틱"));
        tablayout.addTab(tablayout.newTab().setText("왁싱"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewpager=(ViewPager)findViewById(R.id.viewpager);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));//화면 넘길때 탭이랑 뷰 같이 이동
        fragmentPagerAdapter = new ViewPagerAdapter_Shop(getSupportFragmentManager(),3);
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

        bottom_menu=(BottomNavigationView)findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.move){
                    Intent intent1 = new Intent(Place_Shop.this,Place_Move.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                    startActivity(intent1);
                }
                else if(item.getItemId()==R.id.board){
                    Intent intent1 = new Intent(Place_Shop.this,Place_FreeBoard.class);
                    intent1.putExtra("board",board_main_title);//자유게시판(부산,울산-경남권)
                    intent1.putExtra("Place_Chat_Main",chat_main_title);  //채팅(부산,울산-경남권)
                    intent1.putExtra("board_address",select_address);  //경상남도
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                    startActivity(intent1);
                }
                else if(item.getItemId()==R.id.chat){
                    Intent intent1 = new Intent(Place_Shop.this, Place_Chat_Main.class);
                    intent1.putExtra("board",board_main_title);//자유게시판(부산,울산-경남권)
                    intent1.putExtra("Place_Chat_Main",chat_main_title); //채팅(부산,울산-경남권)
                    intent1.putExtra("board_address",select_address);//경상남도
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                    startActivity(intent1);
                }
                else if(item.getItemId()==R.id.list){
                    Intent intent1 = new Intent(Place_Shop.this,Place_Move.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                    startActivity(intent1);
                }
                else if(item.getItemId()==R.id.myinfo){
                    Intent intent1 = new Intent(Place_Shop.this,Place_Move.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                    startActivity(intent1);
                }

                return true;
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