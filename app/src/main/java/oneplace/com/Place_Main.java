package oneplace.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;

public class Place_Main extends AppCompatActivity {

    LinearLayout company_info,company;
    ImageView arrow_down,arrow_up;
    int count=0;
    ScrollView main_scroll;
    Button shop_info,free_board,free_chat,bus_intercity,train_info,hospital;

    RecyclerView recyclerview_board;
    RecyclerView recyclerview_chat;
    ArrayList<Ob_FreeBoard> arrayList;
    ArrayList<Ob_Chat_Main> arrayList2;
    RecyclerView.Adapter adapter;
    RecyclerView.Adapter adapter2;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    int position;
    int position2;
    String board_main_title,chat_main_title,select_address;
    String select_address_spinner,full_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_main);

        board_main_title = getIntent().getStringExtra("board"); //게시판 메인 타이틀
        chat_main_title = getIntent().getStringExtra("Place_Chat_Main"); //채팅 메인 타이틀
        select_address = getIntent().getStringExtra("board_address"); //경상남도 등
        set_save_board_main_title(); //자유 게시판(부산,울산-경남권)
        set_save_chat_main_title(); //채팅(부산,울산-경남권)
        set_save_select_address(); //경상남도 등
        get_save_address(); //사용자의 지역
        get_save_user_name(); //사용자 닉네임

        main_scroll = (ScrollView)findViewById(R.id.main_scroll);
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

        shop_info=(Button)findViewById(R.id.shop_info);
        free_board=(Button)findViewById(R.id.free_board);
        free_chat=(Button)findViewById(R.id.free_chat);
        bus_intercity=(Button)findViewById(R.id.bus_intercity);
        train_info=(Button)findViewById(R.id.train_info);
        hospital=(Button)findViewById(R.id.hospital);
        shop_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Main.this, Place_Shop.class);
                intent.putExtra("board",board_main_title);
                intent.putExtra("Place_Chat_Main",chat_main_title);
                intent.putExtra("area",getIntent().getStringExtra("area"));
                intent.putExtra("board_address",select_address);
                startActivity(intent);
            }
        });
        free_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Main.this,Place_FreeBoard.class);
                intent.putExtra("board",board_main_title); //자유게시판(부산,울산-경남권)
                intent.putExtra("Place_Chat_Main",chat_main_title); //채팅(부산,울산-경남권)
                intent.putExtra("board_address",select_address); //경상남도
                startActivity(intent);
            }
        });
        free_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Main.this, Place_Chat_Main.class);
                intent.putExtra("board",board_main_title);
                intent.putExtra("Place_Chat_Main",chat_main_title);
                intent.putExtra("board_address",select_address);
                startActivity(intent);
            }
        });
        bus_intercity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Main.this, Place_Bus_Intercity.class);
                startActivity(intent);
            }
        });
        train_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Main.this, Place_Train_Info.class);
                startActivity(intent);
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Place_Main.this,Place_Hospital.class);
                startActivity(intent);

            }
        });
        //채팅방 글 리스트
        recyclerview_chat=(RecyclerView)findViewById(R.id.recyclerview_chat);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(Place_Main.this);
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        recyclerview_chat.setLayoutManager(linearLayoutManager2);
        recyclerview_chat.setHasFixedSize(true);
        arrayList2 = new ArrayList<>();
        adapter2= new CusTomAdapter_Chat_Main_View(arrayList2,Place_Main.this);
        recyclerview_chat.setAdapter(adapter2);

        database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
        databaseReference2=database.getReference("-채팅방").child("-전체");
        databaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Ob_Chat_Main ob_chat_main = snapshot.getValue(Ob_Chat_Main.class);
                arrayList2.add(ob_chat_main);
                adapter2.notifyDataSetChanged();
                recyclerview_chat.scrollToPosition(adapter2.getItemCount()-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String snap_key = snapshot.getKey(); //데이터가 변경된 항목의 키값
                Ob_Chat_Main ob_chat_main_change = snapshot.getValue(Ob_Chat_Main.class); //변경된 데이터

                position2 =0;
                for (Ob_Chat_Main ob_chat_main : arrayList2){ //arraylist에 들어있는 데이터를 fro문으로 돌려서 변경된 키값과 비교하여 변경된 항목을 찾는다
                    if (ob_chat_main.getC_key().equals(snap_key)){ //변경된 데이터의 키값과 일치하는지 확인
                        arrayList2.get(position2).setC_title(ob_chat_main_change.getC_title()); //변경된 데이터를 넣는다
                        arrayList2.get(position2).setC_title_serve(ob_chat_main_change.getC_title_serve());
                        arrayList2.get(position2).setC_user_address(ob_chat_main_change.getC_user_address());
                        arrayList2.get(position2).setC_board_view_check(ob_chat_main_change.getC_board_view_check());
                    }
                    position2++;
                }
                adapter2.notifyDataSetChanged(); //데이터 변경 및 저장
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String snap_key = snapshot.getKey(); //데이터가 변경된 항목의 키값
                //ConcurrentModificationException 오류로 인한 처리 코드 for문안에서 데이터 삭제해서 나는 오류라고 함
                for(Iterator<Ob_Chat_Main> itr =arrayList2.iterator(); itr.hasNext();){
                    Ob_Chat_Main ob_chat_main = itr.next();
                    if (ob_chat_main.getC_key().equals(snap_key)){ //변경된 데이터의 키값과 일치하는지 확인
                        itr.remove();
                    }
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //게시판 글 리스트
        recyclerview_board=(RecyclerView)findViewById(R.id.recyclerview_board);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Place_Main.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerview_board.setLayoutManager(linearLayoutManager);
        recyclerview_board.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter= new CusTomAdapter_FreeBoard_Main_View(arrayList,Place_Main.this);
        recyclerview_board.setAdapter(adapter);

        databaseReference=database.getReference("-자유 게시판").child("-전체");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Ob_FreeBoard ob_freeBoard = snapshot.getValue(Ob_FreeBoard.class);
                arrayList.add(ob_freeBoard);
                adapter.notifyDataSetChanged();
                recyclerview_board.scrollToPosition(adapter.getItemCount()-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String snap_key = snapshot.getKey(); //데이터가 변경된 항목의 키값
                Ob_FreeBoard ob_freeBoard_change = snapshot.getValue(Ob_FreeBoard.class); //변경된 데이터

                position =0;
                for (Ob_FreeBoard ob_freeBoard : arrayList){ //arraylist에 들어있는 데이터를 fro문으로 돌려서 변경된 키값과 비교하여 변경된 항목을 찾는다
                    if (ob_freeBoard.getF_key().equals(snap_key)){ //변경된 데이터의 키값과 일치하는지 확인
                        arrayList.get(position).setF_board_view(ob_freeBoard_change.getF_board_view()); //변경된 데이터를 넣는다
                        arrayList.get(position).setF_board_review(ob_freeBoard_change.getF_board_review());
                        arrayList.get(position).setF_title(ob_freeBoard_change.getF_title());
                    }
                    position++;
                }
                adapter.notifyDataSetChanged(); //데이터 변경 및 저장
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String snap_key = snapshot.getKey(); //데이터가 변경된 항목의 키값
                //ConcurrentModificationException 오류로 인한 처리 코드 for문안에서 데이터 삭제해서 나는 오류라고 함
                for(Iterator<Ob_FreeBoard> itr =arrayList.iterator(); itr.hasNext();){
                    Ob_FreeBoard ob_freeBoard = itr.next();
                    if (ob_freeBoard.getF_key().equals(snap_key)){ //변경된 데이터의 키값과 일치하는지 확인
                        itr.remove();
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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