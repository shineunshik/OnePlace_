package oneplace.com;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CusTomAdapter_Chat_Room extends RecyclerView.Adapter<CusTomAdapter_Chat_Room.CustomViewHolder> {
    ArrayList<Ob_Chat_Room> arrayList;
    Context context;
    String full_name,select_address;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    public CusTomAdapter_Chat_Room(ArrayList<Ob_Chat_Room> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_form,parent,false);
        CusTomAdapter_Chat_Room.CustomViewHolder customViewHolder = new CusTomAdapter_Chat_Room.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        get_save_user_name();
        get_save_select_address();


        if (arrayList.get(position).getC_first().equals("입장")||arrayList.get(position).getC_first().equals("퇴장")){
            holder.b_review_user_name.setText(arrayList.get(position).getCr_review_user_name()+"님이 "+arrayList.get(position).getC_first()+"하였습니다.");
            holder.b_review_user_name.setTextSize(12);
            holder.b_review_user_name.setVisibility(View.VISIBLE);
            holder.b_review_content.setVisibility(View.GONE);
            holder.b_review_time.setVisibility(View.GONE);
            holder.backcolor.setBackgroundColor(Color.rgb(255,255,255));
        }
        else {
            holder.b_review_user_name.setVisibility(View.VISIBLE);
            holder.b_review_user_name.setTextSize(14);
            holder.b_review_content.setVisibility(View.VISIBLE);
            holder.b_review_content.setTextSize(18);
            holder.b_review_time.setVisibility(View.VISIBLE);
            holder.backcolor.setBackgroundColor(Color.rgb(255,250,240));
            if (full_name.equals(arrayList.get(position).getCr_review_user_name())){
                holder.b_review_user_name.setText("(나)"+arrayList.get(position).getCr_review_user_name());
                holder.b_review_user_name.setTextColor(Color.parseColor("#000000"));
                holder.b_review_content.setText(arrayList.get(position).getCr_review_content());
                holder.b_review_time.setText(arrayList.get(position).getCr_review_time());
            }
            else{
                holder.b_review_user_name.setText(arrayList.get(position).getCr_review_user_name());
                holder.b_review_content.setText(arrayList.get(position).getCr_review_content());
                holder.b_review_time.setText(arrayList.get(position).getCr_review_time());
            }

        }


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView b_review_time;
        TextView b_review_user_name;
        TextView b_review_content;
        LinearLayout backcolor;
        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.b_review_time=itemView.findViewById(R.id.b_review_time);
            this.b_review_user_name=itemView.findViewById(R.id.b_review_user_name);
            this.b_review_content=itemView.findViewById(R.id.b_review_content);
            this.backcolor=itemView.findViewById(R.id.backcolor);
            view = itemView;


        }
    }
    //사용자
    public void get_save_user_name(){
        SharedPreferences get_save_address = context.getSharedPreferences("save_user_nam",MODE_PRIVATE);
        full_name = get_save_address.getString("save_user_nam_key","");
    }

    public void get_save_select_address(){
        SharedPreferences get_save_select_address = context.getSharedPreferences("save_select_address",MODE_PRIVATE);
        select_address = get_save_select_address.getString("save_select_address_key","");
    }

}
