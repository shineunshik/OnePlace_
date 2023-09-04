package oneplace.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Place_Chat_Room extends AppCompatActivity {
    long backKeyPressedTime = 0; //뒤로가기 버튼을 누른 시간
    TextView cr_delete;
    int room_delete_count=0;
    TextView cr_title;
    Button cr_review_send;
    EditText cr_review;
    String c_content;

    RecyclerView recyclerview;
    FirebaseDatabase database;
    DatabaseReference databaseReference_room_out,databaseReference,
            databaseReference_check,databaseReference_check_all,databaseReference_review,databaseReference_review_add,databaseReference_room_in,databaseReference_room_delete,databaseReference_room_delete_check;
    ArrayList<Ob_Chat_Room> arrayList;
    RecyclerView.Adapter adapter;
    int room_in_check;
    BackPressClose backPressClose;
    String full_name,select_address_spinner;
    String board_main_title,chat_main_title,select_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_chat_room);

        backPressClose = new BackPressClose(this); //뒤로가기를 위한 현재 엑티비티를 저장과 객체 생성

        get_save_user_name();//사용자 이름 가져오기
        get_save_address(); //사용자 지역 가져오기
        get_save_board_main_title(); //게시판 지역 타이틀
        get_save_chat_main_title(); //체팅방 지역 타이틀
        get_save_select_address();

        cr_title=(TextView) findViewById(R.id.cr_title);
        cr_title.setText(getIntent().getStringExtra("c_title"));

        //방삭제시 강제 퇴장
        database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
        databaseReference_room_delete_check=database.getReference("-채팅방").child(select_address)
                .child(getIntent().getStringExtra("c_key")).child("c_title");
        databaseReference_room_delete_check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String value = snapshot.getValue().toString();
                }
                catch (NullPointerException nullPointerException){
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //리뷰 불러오기
        databaseReference_review=database.getReference("-채팅방댓글").child(select_address)
                .child(getIntent().getStringExtra("c_key"));
        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Place_Chat_Room.this);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter= new CusTomAdapter_Chat_Room(arrayList,Place_Chat_Room.this);
        recyclerview.setAdapter(adapter);
        databaseReference_review.keepSynced(true);
        databaseReference_review.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Ob_Chat_Room ob_chat_room = snapshot.getValue(Ob_Chat_Room.class);
                arrayList.add(ob_chat_room);
                adapter.notifyDataSetChanged();
                recyclerview.scrollToPosition(adapter.getItemCount()-1);

                if (arrayList.size()==50){
                    databaseReference_review.removeValue();
                    arrayList.clear();
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //룸 입장
        room_in_check=0;
        if (room_in_check==0){
            databaseReference_room_in=database.getReference("-채팅방댓글").child(select_address)
                    .child(getIntent().getStringExtra("c_key"));
            //댓글 데이터랑 글 데이터랑 따로 구분하기
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = new Date();
            String time = simpleDateFormat.format(date);
            String key_in = databaseReference_room_in.push().getKey();
            HashMap<String, Object> value = new HashMap<>();
            value.put("cr_review_user_name", full_name); //리뷰 작성자(사용자)
            value.put("cr_review_content",""); //내용
            //  value.put("cr_review_user_address", select_address_spinner); //리뷰 작성자(지역)
            value.put("cr_review_time", time); //시간
            value.put("cr_review_key", key_in); //키
            value.put("c_key", getIntent().getStringExtra("c_key")); //게시글의 키
            value.put("c_first", "입장"); //게시글의 지역
            databaseReference_room_in.child(key_in).setValue(value);


            //방 인원 수 추가
            databaseReference=database.getReference("-채팅방").child(select_address).child(getIntent()
                    .getStringExtra("c_key")).child("c_board_view");
            databaseReference_check_all=database.getReference("-채팅방").child("-전체")
                    .child(getIntent().getStringExtra("c_key")).child("c_board_view_check");

            databaseReference_check=database.getReference("-채팅방").child(select_address)
                    .child(getIntent().getStringExtra("c_key")).child("c_board_view_check");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   try {
                        int count = Integer.parseInt(dataSnapshot.getValue().toString());
                        databaseReference.setValue(count+1);
                        databaseReference_check.setValue(count+1);
                        databaseReference_check_all.setValue(count+1);
                        databaseReference=null;  //null을 주는 이유는  addValueEventListener 메서드가 실시간으로 반영되어 +1이 될때마다 무한으로 숫자가 올라가서 null을 넣고 초기화 시킴

                    }
                    catch (NullPointerException nullPointerException){
                        //  Toast.makeText(context,"null",Toast.LENGTH_SHORT).show(); //+1후에 =null을 줘서 에러남
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            room_in_check++;

        }


        //채팅글 작성
        cr_review=(EditText) findViewById(R.id.cr_review);
        cr_review_send=(Button) findViewById(R.id.cr_review_send);
        cr_review_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_content = cr_review.getText().toString();
                if (c_content.equals("")||c_content.length()==0){
                    Toast.makeText( Place_Chat_Room.this,"내용을 1자 이상 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        databaseReference_review_add=database.getReference("-채팅방댓글").child(select_address)
                                .child(getIntent().getStringExtra("c_key"));

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date date = new Date();
                        String time = simpleDateFormat.format(date);
                        String key = databaseReference_review_add.push().getKey();
                        HashMap<String, Object> value = new HashMap<>();
                        value.put("cr_review_user_name", full_name); //리뷰 작성자(사용자)
                        value.put("cr_review_content",c_content); //내용
                        value.put("cr_review_time", time); //시간
                        value.put("cr_review_key", key); //키
                        value.put("c_key", getIntent().getStringExtra("c_key")); //게시글의 키
                        value.put("c_first", ""); //게시글의 지역
                        databaseReference_review_add.child(key).setValue(value);

                    }
                }).start();
                cr_review.setText("");
            }
        });

        //방삭제
        cr_delete=(TextView) findViewById(R.id.cr_delete);
        cr_delete.setVisibility(View.GONE);
        if (getIntent().getStringExtra("c_user_name").equals(full_name)){
            cr_delete.setVisibility(View.VISIBLE);
        }
        else {
            cr_delete.setVisibility(View.GONE);
        }
        cr_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("c_user_name").equals(full_name)){
                    //한 번의 뒤로가기 버튼의 눌린 뒤 현재 시간을 변수에 저장 한 후 토스트 룰력
                    if(System.currentTimeMillis() > backKeyPressedTime + 2000){
                        backKeyPressedTime = System.currentTimeMillis();
                        Toast.makeText(Place_Chat_Room.this,"한 번 더 누르면 방이 삭제됩니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //한 번의 뒤로가기 버튼이 눌린 후 0~2초 사이에 한 번더 눌리게 되면 현재 엑티비티를 호출
                    if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
                        databaseReference_room_delete=database.getReference("-채팅방").child(select_address)
                                .child(getIntent().getStringExtra("c_key"));
                        databaseReference_room_delete.removeValue();
                        databaseReference_review.removeValue();
                        finish();
                    }
                }
            }
        });
    }

    //사용자 이름
    public void get_save_user_name(){
        SharedPreferences get_save_address = getSharedPreferences("save_user_nam",MODE_PRIVATE);
        full_name = get_save_address.getString("save_user_nam_key","");
    }
    //사용자 지역
    public void get_save_address(){
        SharedPreferences get_save_address = getSharedPreferences("save_address",MODE_PRIVATE);
        select_address_spinner = get_save_address.getString("save_address_key","");
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

    @Override
    public void onBackPressed() {
        backPressClose.onBackPressed3();

    }

    @Override
    protected void onPause() { //앱을 내렸을때
        //방 인원 수 삭제
        database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
        databaseReference=database.getReference("-채팅방").child(select_address)
                .child(getIntent().getStringExtra("c_key")).child("c_board_view");
        databaseReference_check_all=database.getReference("-채팅방").child("-전체")
                .child(getIntent().getStringExtra("c_key")).child("c_board_view_check");
        databaseReference_check=database.getReference("-채팅방").child(select_address)
                .child(getIntent().getStringExtra("c_key")).child("c_board_view_check");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    int count = Integer.parseInt(dataSnapshot.getValue().toString());
                    databaseReference.setValue(count-1);
                    databaseReference_check.setValue(count-1);
                    databaseReference_check_all.setValue(count-1);
                    databaseReference=null;  //null을 주는 이유는  addValueEventListener 메서드가 실시간으로 반영되어 +1이 될때마다 무한으로 숫자가 올라가서 null을 넣고 초기화 시킴

                    databaseReference_room_out=database.getReference("-채팅방댓글").child(select_address)
                            .child(getIntent().getStringExtra("c_key"));
                    //댓글 데이터랑 글 데이터랑 따로 구분하기
                    String key_out = databaseReference_room_out.push().getKey();
                    HashMap<String, Object> value = new HashMap<>();
                    value.put("cr_review_user_name", full_name); //리뷰 작성자(사용자)
                    value.put("cr_review_content",""); //내용
                    value.put("cr_review_time", ""); //시간
                    value.put("cr_review_key", key_out); //키
                    value.put("c_key", getIntent().getStringExtra("c_key")); //게시글의 키
                    value.put("c_first", "퇴장"); //게시글의 지역
                    databaseReference_room_out.child(key_out).setValue(value);

                }
                catch (NullPointerException nullPointerException){
                    //  Toast.makeText(context,"null",Toast.LENGTH_SHORT).show(); //+1후에 =null을 줘서 에러남
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        finish();
        super.onPause();
    }
}