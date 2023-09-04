package oneplace.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class Place_FreeBoard_Write extends AppCompatActivity {
    private  final int GALLERY_CODE = 10;
    BackPressClose backPressClose;
    private Uri imageUri;
    int count=0;
    ImageView image_view;
    EditText title,content;
    CheckBox check;
    Button save,image_upload;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference_all;
    String key;
    private final StorageReference reference = FirebaseStorage.getInstance().getReference();;
    String select_address_spinner,full_name;
    String board_main_title,chat_main_title,select_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_freeboard_write);

        backPressClose = new BackPressClose(this); //뒤로가기를 위한 현재 엑티비티를 저장과 객체 생성
        get_save_address();
        get_save_user_name();
        get_save_board_main_title();
        get_save_chat_main_title();
        get_save_select_address();

        image_view = (ImageView) findViewById(R.id.image_view);
        image_upload=(Button) findViewById(R.id.image_upload);
        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        check=(CheckBox)findViewById(R.id.check);
        title=(EditText) findViewById(R.id.title);
        content=(EditText) findViewById(R.id.content);
        content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
        save=(Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title==null||title.length()<=1){
                    Toast.makeText(Place_FreeBoard_Write.this,"제목을 2자 이상 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (content==null||content.length()<=5){
                    Toast.makeText(Place_FreeBoard_Write.this,"내용을 6자 이상 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (imageUri !=null){

                    if(count%12==0){
                        Toast.makeText(Place_FreeBoard_Write.this,"업로드 중 입니다. 조금만 기다려 주세요.",Toast.LENGTH_LONG).show();
                        uploadToFirebase(imageUri);
                        count++;
                    }
                    else if(count%12==1){
                        Toast.makeText(Place_FreeBoard_Write.this,"업로드 중 입니다. 조금만 기다려 주세요.",Toast.LENGTH_LONG).show();
                        count++;
                    }
                    else if(count%12==2){
                        Toast.makeText(Place_FreeBoard_Write.this,"업로드 중 입니다. 조금만 기다려 주세요.",Toast.LENGTH_LONG).show();
                        count++;
                    }
                    else if(count%12==3){
                        Toast.makeText(Place_FreeBoard_Write.this,"업로드 중 입니다. 조금만 기다려 주세요.",Toast.LENGTH_LONG).show();
                        count++;
                    }
                    else if(count%12==4){
                        Toast.makeText(Place_FreeBoard_Write.this,"업로드 중 입니다. 조금만 기다려 주세요.",Toast.LENGTH_LONG).show();
                        count++;
                    }
                    else if(count%12==5){
                        Toast.makeText(Place_FreeBoard_Write.this,"업로드 중 입니다. 조금만 기다려 주세요.",Toast.LENGTH_LONG).show();
                        count++;
                    }
                    else if(count%12==6){
                        Toast.makeText(Place_FreeBoard_Write.this,"업로드 중 입니다. 조금만 기다려 주세요.",Toast.LENGTH_LONG).show();
                        count++;
                    }
                    else if(count%12==7){
                        Toast.makeText(Place_FreeBoard_Write.this,"업로드 중 입니다. 조금만 기다려 주세요.",Toast.LENGTH_LONG).show();
                        count++;
                    }
                    else if(count%12==8){
                        Toast.makeText(Place_FreeBoard_Write.this,"업로드 중 입니다. 조금만 기다려 주세요.",Toast.LENGTH_LONG).show();
                        count++;
                    }
                    else if(count%12==9){
                        Toast.makeText(Place_FreeBoard_Write.this,"업로드 중 입니다. 조금만 기다려 주세요.",Toast.LENGTH_LONG).show();
                        count++;
                    }
                    else if(count%12==10){
                        Toast.makeText(Place_FreeBoard_Write.this,"업로드 중 입니다. 조금만 기다려 주세요.",Toast.LENGTH_LONG).show();
                        count++;
                    }  else if(count%12==11){
                        Toast.makeText(Place_FreeBoard_Write.this,"업로드 중 입니다. 조금만 기다려 주세요.",Toast.LENGTH_LONG).show();
                        count++;
                    }

                }else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            Date date = new Date();
                            String time = simpleDateFormat.format(date);

                            database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
                            databaseReference=database.getReference("-자유 게시판").child(select_address).child(getIntent().getStringExtra("board_type"));
                            databaseReference_all=database.getReference("-자유 게시판").child("-전체");
                            key = databaseReference.push().getKey();

                            HashMap<String, Object> value = new HashMap<>();
                          //  value.put("f_board",getIntent().getStringExtra("board")); //13.자유게시판(부산,울산-경남권)
                         //   value.put("f_Place_Chat",getIntent().getStringExtra("Place_Chat_Main")); //14.채팅(부산,울산-경남권)

                            value.put("f_address",select_address);//7.경상남도
                            value.put("f_board_type",getIntent().getStringExtra("board_type"));//9.A

                            value.put("f_title",title.getText().toString()); //1.제목
                            value.put("f_content",content.getText().toString()); //2.내용
                            value.put("f_time",time); //3.시간

                            if (check.isChecked()){
                                value.put("f_user_name","익명"); //4.작성자
                            }else {
                                value.put("f_user_name",full_name); //4.작성자
                            }
                            value.put("f_user_address",select_address_spinner); //4.작성자의 지역
                            value.put("f_key",key); //5.키
                            value.put("f_image_key",""); //6.이미지
                            value.put("f_image_name",""); //6.이미지 이름
                            value.put("f_board_review",0); //11.리뷰수
                            databaseReference_all.child(key).setValue(value);
                            value.put("f_board_view",1); //10.조회수
                            databaseReference.child(key).setValue(value);

                        }
                    }).start();



                    Intent intent = new Intent(Place_FreeBoard_Write.this,Place_FreeBoard.class);
                    intent.putExtra("board",board_main_title);
                    intent.putExtra("Place_Chat_Main",select_address_spinner);
                    intent.putExtra("board_address",select_address);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                    startActivity(intent);
                    finish();

                }



            }
        });


    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //가져온 이미지  뷰에 띄우기                                     //데이터가 있으면 담고 없으면 그냥 뒤로가기
        //data != null  이거 안해주면 뒤로 눌렀을때 안에 데이터가 없이 나왔기떄문에
        //오류남 팅김
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            image_view.setImageURI(imageUri);

        }

    }

    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void uploadToFirebase(Uri uri){
        String image_name = System.currentTimeMillis() + "." + getFileExtension(uri);
        final StorageReference fileRef =reference.child("자유게시판").child(select_address).child(image_name);

        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                Date date = new Date();
                                String time = simpleDateFormat.format(date);

                                database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
                                databaseReference=database.getReference("-자유 게시판").child(select_address).child(getIntent().getStringExtra("board_type"));
                                databaseReference_all=database.getReference("-자유 게시판").child("-전체");
                                key = databaseReference.push().getKey();

                                HashMap<String, Object> value = new HashMap<>();
                             //   value.put("f_board",getIntent().getStringExtra("board")); //13.자유게시판(부산,울산-경남권)
                             //   value.put("f_Place_Chat",getIntent().getStringExtra("Place_Chat_Main")); //14.채팅(부산,울산-경남권)

                                value.put("f_address",select_address);//7.경상남도
                                value.put("f_board_type",getIntent().getStringExtra("board_type"));//9.A

                                value.put("f_title",title.getText().toString()); //1.제목
                                value.put("f_content",content.getText().toString()); //2.내용
                                value.put("f_time",time); //3.시간

                                if (check.isChecked()){
                                    value.put("f_user_name","익명"); //4.작성자
                                }else {
                                    value.put("f_user_name",full_name); //4.작성자
                                }
                                value.put("f_user_address",select_address_spinner); //4.작성자의 지역
                                value.put("f_key",key); //5.키
                                value.put("f_image_key",uri.toString()); //6.이미지
                                value.put("f_image_name",image_name); //6.이미지 이름
                                value.put("f_board_review",0); //11.리뷰수
                                databaseReference_all.child(key).setValue(value);
                                value.put("f_board_view",1); //10.조회수
                                databaseReference.child(key).setValue(value);

                            }
                        }).start();

                        Intent intent = new Intent(Place_FreeBoard_Write.this,Place_FreeBoard.class);
                        intent.putExtra("board",board_main_title);  //13.자유게시판(부산,울산-경남권)
                        intent.putExtra("Place_Chat_Main",select_address_spinner); //14.채팅(부산,울산-경남권)
                        intent.putExtra("board_address",select_address);  //7.경상남도
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//중복방지
                        startActivity(intent);
                        finish();

                    }
                });

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



    @Override
    public void onBackPressed() {
        backPressClose.onBackPressed2();
    }
}