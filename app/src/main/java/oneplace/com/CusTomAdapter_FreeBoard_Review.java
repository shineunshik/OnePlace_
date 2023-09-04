package oneplace.com;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CusTomAdapter_FreeBoard_Review extends RecyclerView.Adapter<CusTomAdapter_FreeBoard_Review.CustomViewHolder> {

    ArrayList<Ob_FreeBoard_Review> arrayList;
    Context context;
    String full_name;

    public CusTomAdapter_FreeBoard_Review(ArrayList<Ob_FreeBoard_Review> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_form,parent,false);
        CusTomAdapter_FreeBoard_Review.CustomViewHolder customViewHolder = new CusTomAdapter_FreeBoard_Review.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        get_save_user_name();
        holder.b_review_user_name.setText(arrayList.get(position).getB_review_user_name());
        holder.b_review_content.setText(arrayList.get(position).getB_review_content());
        holder.b_review_time.setText(arrayList.get(position).getB_review_time());


        if (full_name.equals(arrayList.get(position).getB_review_user_name())){
            holder.b_review_user_name.setText("(나)"+arrayList.get(position).getB_review_user_name());
            holder.b_review_user_name.setTextColor(Color.parseColor("#000000"));
            holder.b_review_content.setText(arrayList.get(position).getB_review_content());
            holder.b_review_time.setText(arrayList.get(position).getB_review_time());
        }
        else{
            holder.b_review_user_name.setText(arrayList.get(position).getB_review_user_name());
            holder.b_review_content.setText(arrayList.get(position).getB_review_content());
            holder.b_review_time.setText(arrayList.get(position).getB_review_time());
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
        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.b_review_time=itemView.findViewById(R.id.b_review_time);
            this.b_review_user_name=itemView.findViewById(R.id.b_review_user_name);
            this.b_review_content=itemView.findViewById(R.id.b_review_content);
            view = itemView;


        }
    }

    //사용자
    public void get_save_user_name(){
        SharedPreferences get_save_address = context.getSharedPreferences("save_user_nam",MODE_PRIVATE);
        full_name = get_save_address.getString("save_user_nam_key","");
    }
}

