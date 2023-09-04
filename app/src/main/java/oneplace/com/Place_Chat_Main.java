package oneplace.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Iterator;

public class Place_Chat_Main extends AppCompatActivity {

    TextView area;
    Spinner area_move;
    String select_area_spinner;
    SharedPreferences last_area_spinner;
    SharedPreferences.Editor last_area_spinner_editor;
    EditText c_title,c_title_serve;
    Button c_add;
    int position_add;

    RecyclerView recyclerview;
    ArrayList<Ob_Chat_Main> arrayList;
    RecyclerView.Adapter c_adapter;
    FirebaseDatabase database;
    DatabaseReference c_databaseReference,databaseReference,databaseReference_room,databaseReference_review,databaseReference_all;
    String key;
    String board_main_title,chat_main_title,select_address;
    String select_address_spinner,full_name;
    ArrayList<Ob_Chat_Main> arrayList2;
    int position_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_chat_main);

        database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");

        board_main_title = getIntent().getStringExtra("board");
        chat_main_title = getIntent().getStringExtra("Place_Chat_Main");
        select_address = getIntent().getStringExtra("board_address");
        set_save_board_main_title();
        set_save_chat_main_title();
        set_save_select_address();
        get_save_address();
        get_save_user_name();

        area = (TextView)findViewById(R.id.area);
        area.setText(chat_main_title);

        //지역 이동
        area_move =(Spinner) findViewById(R.id.area_move);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.area_move,R.layout.address_spinner);
        area_move.setAdapter(adapter);
        area_move.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position).equals("지역 이동")){

                }
                else {
                    select_area_spinner =area_move.getSelectedItem().toString();//선택한 spinner항목 문자열 변환
                    Intent intent =new Intent(Place_Chat_Main.this, Place_Chat_Main.class);
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

        //방생성
        c_title = (EditText) findViewById(R.id.c_title);
        c_title_serve = (EditText) findViewById(R.id.c_title_serve);
        c_add=(Button) findViewById(R.id.c_add);
        c_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c_title.getText().toString().equals("")||c_title.getText().toString().length()<=2){
                    Toast.makeText(Place_Chat_Main.this,"방 이름을 3자 이상 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (c_title_serve.getText().toString().equals("")||c_title_serve.getText().toString().length()<=4){
                    Toast.makeText(Place_Chat_Main.this,"부제를 5자 이상 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date();
                String time = simpleDateFormat.format(date);


                databaseReference=database.getReference("-채팅방").child(select_address);
                databaseReference_all=database.getReference("-채팅방").child("-전체");
                key = databaseReference.push().getKey();

                HashMap<String, Object> value = new HashMap<>();

                value.put("c_title",c_title.getText().toString()); //방이름
                value.put("c_title_serve",c_title_serve.getText().toString()); //부제
                value.put("c_time",time); //3.시간
                value.put("c_user_name",full_name); //4.작성자
                value.put("c_user_address",select_address_spinner); //4.작성자의 지역
                value.put("c_key",key); //5.키
                value.put("c_board_view_check",1); //방 인원수
                value.put("c_board_view",0); //방 인원수
                databaseReference.child(key).setValue(value);
                databaseReference_all.child(key).setValue(value);

                Intent intent = new Intent(Place_Chat_Main.this,Place_Chat_Room.class);
                intent.putExtra("c_title",c_title.getText().toString());//방이름
                intent.putExtra("c_title_serve",c_title_serve.getText().toString()); //부제
                intent.putExtra("c_time",time); //3.시간
                intent.putExtra("c_user_name",full_name); //방 생성자
                intent.putExtra("c_user_address",select_address_spinner); //생성자의 지역
                intent.putExtra("c_key",key); //5.키
                startActivity(intent);

                c_title.setText("");
                c_title_serve.setText("");

            }
        });

        //채팅방 리스트
        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Place_Chat_Main.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        c_adapter= new CusTomAdapter_Chat_Main(arrayList, Place_Chat_Main.this);
        recyclerview.setAdapter(c_adapter);
        c_databaseReference=database.getReference("-채팅방").child(select_address);
        c_databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Ob_Chat_Main ob_chat_main = snapshot.getValue(Ob_Chat_Main.class);
                arrayList.add(ob_chat_main);
                c_adapter.notifyDataSetChanged();
                recyclerview.scrollToPosition(c_adapter.getItemCount()-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String snap_key = snapshot.getKey(); //데이터가 변경된 항목의 키값
                Ob_Chat_Main ob_chat_main_change = snapshot.getValue(Ob_Chat_Main.class); //변경된 데이터

                position_add =0;
                for (Ob_Chat_Main ob_chat_main : arrayList){ //arraylist에 들어있는 데이터를 fro문으로 돌려서 변경된 키값과 비교하여 변경된 항목을 찾는다
                    if (ob_chat_main.getC_key().equals(snap_key)){ //변경된 데이터의 키값과 일치하는지 확인
                        arrayList.get(position_add).setC_board_view(ob_chat_main_change.getC_board_view()); //변경된 데이터를 넣는다

                    }
                    position_add++;
                }
                c_adapter.notifyDataSetChanged(); //데이터 변경 및 저장
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String snap_key = snapshot.getKey(); //데이터가 변경된 항목의 키값
                //ConcurrentModificationException 오류로 인한 처리 코드 for문안에서 데이터 삭제해서 나는 오류라고 함
                for(Iterator<Ob_Chat_Main> itr = arrayList.iterator(); itr.hasNext();){
                    Ob_Chat_Main ob_chat_main = itr.next();
                    if (ob_chat_main.getC_key().equals(snap_key)){ //변경된 데이터의 키값과 일치하는지 확인
                        itr.remove();
                    }
                }
                c_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //인원수가 0이면 방 삭제
        arrayList2 = new ArrayList<>();
        c_databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    arrayList2.add(dataSnapshot.getValue(Ob_Chat_Main.class));

                }

                position_check =0;
                for (Ob_Chat_Main ob_chat_main : arrayList2){ //arraylist에 들어있는 데이터를 fro문으로 돌려서 변경된 키값과 비교하여 변경된 항목을 찾는다
                    if (arrayList2.get(position_check).getC_board_view_check()==0){ //변경된 데이터의 키값과 일치하는지 확인
                        databaseReference_room=database.getReference("-채팅방").child(select_address).child(arrayList.get(position_check).getC_key());
                        databaseReference_review=database.getReference("-채팅방댓글").child(select_address).child(arrayList.get(position_check).getC_key());
                        databaseReference_room.removeValue();
                        databaseReference_review.removeValue();
                    //    arrayList2.remove(position_check); //방 삭제 코드

                    }
                    position_check++;
                }
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