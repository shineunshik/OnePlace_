package oneplace.com;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CusTomAdapter_FreeBoard extends RecyclerView.Adapter<CusTomAdapter_FreeBoard.CustomViewHolder> {
    ArrayList<Ob_FreeBoard> arrayList;
    Context context;
    FirebaseDatabase database;
    DatabaseReference databaseReference,databaseReference_all,databaseReference_review_delete;
    String full_name;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    public CusTomAdapter_FreeBoard(ArrayList<Ob_FreeBoard> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_list_form,parent,false);
        CusTomAdapter_FreeBoard.CustomViewHolder customViewHolder = new CusTomAdapter_FreeBoard.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        get_save_user_name();


        if (full_name.equals(arrayList.get(position).getF_user_name())){
            holder.f_title.setText(arrayList.get(position).getF_title());
            holder.f_user_name.setText("(나)"+arrayList.get(position).getF_user_name());
            holder.f_user_name.setTextColor(Color.parseColor("#000000"));
            holder.f_time.setText(arrayList.get(position).getF_time());
            holder.f_review.setText(String.valueOf(arrayList.get(position).getF_board_review()));
            holder.f_board_view.setText(String.valueOf(arrayList.get(position).getF_board_view()));
        }
        else{
            holder.f_title.setText(arrayList.get(position).getF_title());
            holder.f_user_name.setText(arrayList.get(position).getF_user_name());
            holder.f_time.setText(arrayList.get(position).getF_time());
            holder.f_review.setText(String.valueOf(arrayList.get(position).getF_board_review()));
            holder.f_board_view.setText(String.valueOf(arrayList.get(position).getF_board_view()));
        }



    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView f_title; //제목
        TextView f_user_name; //작성자
        TextView f_time; //시간
        TextView f_review; //리뷰수
        TextView f_board_view; //조회수
        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.f_title = itemView.findViewById(R.id.f_title);
            this.f_user_name = itemView.findViewById(R.id.f_user_name);
            this.f_time = itemView.findViewById(R.id.f_time);
            this.f_review = itemView.findViewById(R.id.f_review);
            this.f_board_view = itemView.findViewById(R.id.f_board_view);
            view = itemView;



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =getLayoutPosition();
                    Intent intent = new Intent(context, Place_FreeBoard_Look.class);
                    intent.putExtra("f_title",arrayList.get(position).getF_title()); //1.제목
                    intent.putExtra("f_content",arrayList.get(position).getF_content()); //2.내용
                    intent.putExtra("f_time",arrayList.get(position).getF_time()); //3.시간
                    intent.putExtra("f_user_name",arrayList.get(position).getF_user_name()); //4.작성자
                    intent.putExtra("f_user_address",arrayList.get(position).getF_user_address()); //4.작성자의 지역
                    intent.putExtra("f_key",arrayList.get(position).getF_key()); //5.키
                    intent.putExtra("f_image_key",arrayList.get(position).getF_image_key()); //6.이미지
                    intent.putExtra("f_address",arrayList.get(position).getF_address()); //7.경상남도
                    intent.putExtra("f_board_type",arrayList.get(position).getF_board_type()); //9.A
                    intent.putExtra("f_board_view",arrayList.get(position).getF_board_view()); //10.조회수
                    intent.putExtra("f_board_review",arrayList.get(position).getF_board_review()); //11.리뷰수

                  //  intent.putExtra("f_board",arrayList.get(position).getF_board()); //13.자유게시판(부산,울산-경남권)
                  //  intent.putExtra("f_Place_Chat",arrayList.get(position).getF_Place_Chat()); //4.채팅(부산,울산-경남권)

                    //조회수

                    database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
                    databaseReference=database.getReference("-자유 게시판").child(arrayList.get(position).getF_address()).child(arrayList.get(position).getF_board_type())
                            .child(arrayList.get(position).getF_key()).child("f_board_view");

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {

                                int count = Integer.parseInt(dataSnapshot.getValue().toString());
                                databaseReference.setValue(count+1);
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

//                    databaseReference.setValue(arrayList.get(position).getF_board_view()+1);


                    context.startActivity(intent);
                }
            });



            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getLayoutPosition();
                    if (full_name.equals(arrayList.get(position).getF_user_name())){

                        //글에 이미지가 첨부되어있을때 스토리지에서 이미지 삭제
                        if (arrayList.get(position).getF_image_name().contains("png")){
                            StorageReference storageRef = storage.getReference();
                            StorageReference fileRef2 =storageRef.child("자유게시판/"+arrayList.get(position).getF_address()+"/"+arrayList.get(position).getF_image_name());

                            fileRef2.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                 //   Toast.makeText(context,"삭제 완료",Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }

                        database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
                        databaseReference=database.getReference("-자유 게시판").child(arrayList.get(position).getF_address()).child(arrayList.get(position).getF_board_type())
                                .child(arrayList.get(position).getF_key());
                        databaseReference_all=database.getReference("-자유 게시판").child("-전체").child(arrayList.get(position).getF_key());
                        databaseReference.removeValue();
                        databaseReference_all.removeValue();
                        databaseReference_review_delete=database.getReference("-자유 게시판 댓글").child(arrayList.get(position).getF_address()).child(arrayList.get(position).getF_board_type())
                                .child(arrayList.get(position).getF_key());
                        databaseReference_review_delete.removeValue();
                        Toast.makeText(context,"글 삭제",Toast.LENGTH_SHORT).show();
                        arrayList.remove(position); //해당 글 삭제 후 main의 onChildRemoved 에서  adapter.notifyDataSetChanged(); 해주면 사라짐

                    }

                    return false;
                }
            });
        }
    }


    private void uploadToFirebase(Uri uri){



    }

    //사용자
    public void get_save_user_name(){
        SharedPreferences get_save_address = context.getSharedPreferences("save_user_nam",MODE_PRIVATE);
        full_name = get_save_address.getString("save_user_nam_key","");
    }
}
