package oneplace.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import java.util.Map;
import java.util.Objects;

import io.reactivex.internal.util.ArrayListSupplier;

public class Place_FreeBoard_Look extends AppCompatActivity {
    TextView layout_change;
    TextView review_count;
    LinearLayout layout_change_view;
    LinearLayout review_layout,board_layout,magin_layout;
    int count=0;
    TextView title,user_name,time,content;
    ImageView image_key;
    CardView card_view;
    WebView homepage;

    EditText review_content;
    String r_content;
    Button review_save;
    FirebaseDatabase database_review;
    DatabaseReference databaseReference_review,databaseReference_review2,databaseReference_review_all;
    DatabaseReference databaseReference_review_count_add,databaseReference_board_delete,databaseReference_delete_all;


    RecyclerView recyclerview;
    ArrayList<Ob_FreeBoard_Review> arrayList;
    RecyclerView.Adapter adapter;
    String full_name,select_address_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_freeboard_look);

        get_save_user_name();//사용자 이름 가져오기
        get_save_address(); //사용자 지역 가져오기

        review_layout=(LinearLayout)findViewById(R.id.review_layout);
        review_layout.setVisibility(View.GONE);
        magin_layout=(LinearLayout)findViewById(R.id.magin_layout);
        magin_layout.setVisibility(View.GONE);
        board_layout=(LinearLayout) findViewById(R.id.board_layout);
        layout_change_view=(LinearLayout)findViewById(R.id.layout_change_view);
        layout_change=(TextView) findViewById(R.id.layout_change);
        review_count=(TextView) findViewById(R.id.review_count);
        review_count.setText(String.valueOf(getIntent().getIntExtra("f_board_review",0))); //댓글 갯수


        //글삭제시 강제 퇴장
        database_review=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
        databaseReference_board_delete=database_review.getReference("-자유 게시판").child(getIntent().getStringExtra("f_address")).child(getIntent().getStringExtra("f_board_type"))
                .child(getIntent().getStringExtra("f_key")).child("f_title");
        databaseReference_board_delete.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String value = snapshot.getValue().toString();
                }
                catch (NullPointerException nullPointerException){
                    finish();
                    databaseReference_delete_all=database_review.getReference("-자유 게시판").child(getIntent().getStringExtra("f_address")).child(getIntent().getStringExtra("f_board_type"))
                            .child(getIntent().getStringExtra("f_key"));
                    databaseReference_delete_all.removeValue();
                   // Toast.makeText(Place_FreeBoard_Look.this, "글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        layout_change_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count%2==0){
                    review_count.setVisibility(View.GONE);
                    layout_change.setText("댓글창 닫기");
                    board_layout.setVisibility(View.GONE);
                    review_layout.setVisibility(View.VISIBLE);
                    magin_layout.setVisibility(View.VISIBLE);
                    count++;
                }
                else if (count%2==1){
                    review_count.setVisibility(View.VISIBLE);
                    layout_change.setText("댓글창 열기");
                    board_layout.setVisibility(View.VISIBLE);
                    review_layout.setVisibility(View.GONE);
                    magin_layout.setVisibility(View.GONE);
                    count++;
                }
            }
        });
        image_key=(ImageView) findViewById(R.id.image_key);
        card_view =(CardView)findViewById(R.id.card_view);
        if (getIntent().getStringExtra("f_image_key").equals("")){
            image_key.setVisibility(View.GONE);
            card_view.setVisibility(View.GONE);
        }
        else{
            image_key.setVisibility(View.VISIBLE);
            card_view.setVisibility(View.VISIBLE);
            Glide.with(this).load(getIntent().getStringExtra("f_image_key")).into(image_key);
        }

        title=(TextView) findViewById(R.id.title);
        user_name=(TextView) findViewById(R.id.user_name);
        time=(TextView) findViewById(R.id.time);
        content=(TextView) findViewById(R.id.content);
        homepage=(WebView) findViewById(R.id.homepage);

        title.setText(getIntent().getStringExtra("f_title"));//제목
        time.setText(getIntent().getStringExtra("f_time"));//시간
        user_name.setText(getIntent().getStringExtra("f_user_name"));//작성자
        content.setText(getIntent().getStringExtra("f_content")); //내용


        //리뷰 불러오기

        databaseReference_review=database_review.getReference("-자유 게시판 댓글").child(getIntent().getStringExtra("f_address")).child(getIntent().getStringExtra("f_board_type"))
                .child(getIntent().getStringExtra("f_key"));

        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Place_FreeBoard_Look.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter= new CusTomAdapter_FreeBoard_Review(arrayList,Place_FreeBoard_Look.this);
        recyclerview.setAdapter(adapter);
        databaseReference_review.keepSynced(true);
        databaseReference_review.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                HashMap<String, Object> value = (HashMap<String, Object>)snapshot.getValue();
//                arrayList.add(new Ob_FreeBoard_Review((String)value.get("b_review_user_name"),(String)value.get("b_review_count"),(String)value.get("b_review_content"),
//                        (String)value.get("b_review_time"),(String)value.get("b_review_key"),(String)value.get("b_review_user_address"),(String)value.get("b_review_board_key")
//                ,(String)value.get("b_review_board_type")));

                Ob_FreeBoard_Review ob_freeBoard_review = snapshot.getValue(Ob_FreeBoard_Review.class);
                arrayList.add(ob_freeBoard_review);
                adapter.notifyDataSetChanged();
                recyclerview.scrollToPosition(adapter.getItemCount()-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //리뷰 쓰기
        review_content=(EditText) findViewById(R.id.review_content);
        review_save=(Button) findViewById(R.id.review_send);
        review_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r_content = review_content.getText().toString();
                if (r_content.equals("")||r_content.length()==0){
                    Toast.makeText(Place_FreeBoard_Look.this,"댓글을 1자 이상 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //댓글수 불러오기
                        databaseReference_review_count_add=database_review.getReference("-자유 게시판").child(getIntent().getStringExtra("f_address")).child(getIntent().getStringExtra("f_board_type"))
                                .child(getIntent().getStringExtra("f_key")).child("f_board_review");
                        databaseReference_review_count_add.setValue(Integer.parseInt(String.valueOf(arrayList.size()+1)));

                        databaseReference_review_all=database_review.getReference("-자유 게시판").child("-전체")
                                .child(getIntent().getStringExtra("f_key")).child("f_board_review");
                        databaseReference_review_all.setValue(Integer.parseInt(String.valueOf(arrayList.size()+1)));
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date date = new Date();
                        String time = simpleDateFormat.format(date);
                        String key = databaseReference_review.push().getKey();


                        HashMap<String, Object> value = new HashMap<>();
                        value.put("b_review_user_name", full_name); //리뷰 작성자(사용자)
                        value.put("b_review_content",r_content); //내용
                        value.put("b_review_user_address", select_address_spinner); //리뷰 작성자(지역)
                        value.put("b_review_count", String.valueOf(arrayList.size()+1)); //리뷰 수
                        value.put("b_review_time", time); //시간
                        value.put("b_review_key", key); //키
                        value.put("b_review_address", getIntent().getStringExtra("f_address")); //지역(경상남도)
                        value.put("b_review_board_key", getIntent().getStringExtra("f_key")); //게시글의 키
                        value.put("b_review_board_type", getIntent().getStringExtra("f_board_type")); //게시판 type
                        databaseReference_review2=database_review.getReference("-자유 게시판 댓글").child(getIntent().getStringExtra("f_address")).child(getIntent().getStringExtra("f_board_type"))
                                .child(getIntent().getStringExtra("f_key")).child(key);
                        databaseReference_review2.setValue(value);

                    }
                }).start();
                review_content.setText("");


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

    @Override
    public void onBackPressed() {
        if(count%2==1){ //댓글창이 열린 상태에서 뒤로 누르면 댓글창이 닫힘
            review_count.setVisibility(View.VISIBLE);
            layout_change.setText("댓글창 열기");
            board_layout.setVisibility(View.VISIBLE);
            review_layout.setVisibility(View.GONE);
            magin_layout.setVisibility(View.GONE);
            count++;
        }
        else if(count%2==0){ //한번도 뒤로가기 눌리면 글 닫힘
            super.onBackPressed();
        }
    }
}