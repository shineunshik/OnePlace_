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

import java.util.ArrayList;

public class CusTomAdapter_Chat_Main_View extends RecyclerView.Adapter<CusTomAdapter_Chat_Main_View.CustomViewHolder> {
    ArrayList<Ob_Chat_Main> arrayList;
    Context context;
    String select_address;

    public CusTomAdapter_Chat_Main_View(ArrayList<Ob_Chat_Main> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_chat_list_form,parent,false);
        CusTomAdapter_Chat_Main_View.CustomViewHolder customViewHolder = new CusTomAdapter_Chat_Main_View.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        get_save_select_address();
        holder.c_title.setText(arrayList.get(position).getC_title()+"  ("+arrayList.get(position).getC_board_view_check()+")");
        holder.c_address.setText(arrayList.get(position).getC_user_address());
        holder.c_title_serve.setText(arrayList.get(position).getC_title_serve());


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView c_title; //제목
        TextView c_address; //지역
        TextView c_title_serve; //부제

        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.c_title = itemView.findViewById(R.id.c_title);
            this.c_address = itemView.findViewById(R.id.c_address);
            this.c_title_serve = itemView.findViewById(R.id.c_title_serve);
            view = itemView;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =getLayoutPosition();

                    Intent intent = new Intent(context, Place_Chat_Room.class);
                    intent.putExtra("c_title",arrayList.get(position).getC_title());//방이름
                    intent.putExtra("c_title_serve",arrayList.get(position).getC_title_serve()); //부제
                    intent.putExtra("c_time",arrayList.get(position).getC_time()); //3.시간
                    intent.putExtra("c_user_name",arrayList.get(position).getC_user_name()); //방 생성자
                    intent.putExtra("c_user_address",arrayList.get(position).getC_user_address()); //생성자의 지역
                    intent.putExtra("c_key",arrayList.get(position).getC_key()); //5.키
                    intent.putExtra("c_board_view",arrayList.get(position).getC_board_view()); //인원수
                    intent.putExtra("c_board_view_check",arrayList.get(position).getC_board_view_check()); //인원수 체크
                    context.startActivity(intent);


                }
            });

        }
    }

    public void get_save_select_address(){
        SharedPreferences get_save_select_address = context.getSharedPreferences("save_select_address",MODE_PRIVATE);
        select_address = get_save_select_address.getString("save_select_address_key","");
    }

}
