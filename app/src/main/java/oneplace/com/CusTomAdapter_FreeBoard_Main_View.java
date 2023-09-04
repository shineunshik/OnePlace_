package oneplace.com;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CusTomAdapter_FreeBoard_Main_View extends RecyclerView.Adapter<CusTomAdapter_FreeBoard_Main_View.CustomViewHolder> {
    ArrayList<Ob_FreeBoard> arrayList;
    Context context;
    FirebaseDatabase database;
    DatabaseReference databaseReference,databaseReference_all;
    String full_name;

    public CusTomAdapter_FreeBoard_Main_View(ArrayList<Ob_FreeBoard> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_board_list_form,parent,false);
        CusTomAdapter_FreeBoard_Main_View.CustomViewHolder customViewHolder = new CusTomAdapter_FreeBoard_Main_View.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.f_title.setText(arrayList.get(position).getF_title()+"  ("+arrayList.get(position).getF_board_review()+")");
        holder.f_address.setText(arrayList.get(position).getF_user_address());
        holder.f_time.setText(arrayList.get(position).getF_time());


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView f_title; //제목
        TextView f_address; //지역
        TextView f_time; //시간

        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.f_title = itemView.findViewById(R.id.f_title);
            this.f_address = itemView.findViewById(R.id.f_address);
            this.f_time = itemView.findViewById(R.id.f_time);
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

                    context.startActivity(intent);
                }
            });

        }
    }

    //사용자
    public void get_save_user_name(){
        SharedPreferences get_save_address = context.getSharedPreferences("save_user_nam",MODE_PRIVATE);
        full_name = get_save_address.getString("save_user_nam_key","");
    }
}
