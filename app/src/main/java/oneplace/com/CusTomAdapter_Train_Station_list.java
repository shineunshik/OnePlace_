package oneplace.com;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class CusTomAdapter_Train_Station_list extends RecyclerView.Adapter<CusTomAdapter_Train_Station_list.CustomViewHolder> {
    ArrayList<Ob_Train_Info> arrayList;
    Context context;
    String select_address;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String day_save;
    Dialog dialog;
    public CusTomAdapter_Train_Station_list(ArrayList<Ob_Train_Info> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_station_list_form,parent,false);
        CusTomAdapter_Train_Station_list.CustomViewHolder customViewHolder = new CusTomAdapter_Train_Station_list.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        get_save_set_save_day();

        holder.train_station_number.setText("ID : "+arrayList.get(position).getNodeid());
        holder.train_station_name.setText("기차역 명 : "+arrayList.get(position).getNodename()+"역");
        if (position<9){
            holder.address.setText("0"+(position+1)+".  "+arrayList.get(position).getAddress());
        }else {
            holder.address.setText(position+1+".  "+arrayList.get(position).getAddress());
        }

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView train_station_name;
        TextView train_station_number;
        TextView address;

        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.train_station_number = itemView.findViewById(R.id.train_station_number);
            this.train_station_name = itemView.findViewById(R.id.train_station_name);
            this.address = itemView.findViewById(R.id.address);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Intent intent = new Intent(context, Place_Train_Choice.class);
                    intent.putExtra("nodeid",arrayList.get(position).getNodeid());
                    intent.putExtra("nodename",arrayList.get(position).getNodename());
                    intent.putExtra("address",arrayList.get(position).getAddress());
                    context.startActivity(intent);

//                    database= FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
//                    databaseReference=database.getReference("-기차역").child("-기차역LIST");
//                    databaseReference.child(arrayList.get(position).getNodename()).child("station_use").setValue("중단");

                }
            });


        }
    }

    public void get_save_select_address(){
        SharedPreferences get_save_select_address = context.getSharedPreferences("save_select_address",MODE_PRIVATE);
        select_address = get_save_select_address.getString("save_select_address_key","");
    }


    public void set_save_day(){
        SharedPreferences set_save_day = context.getSharedPreferences("save_save_day",MODE_PRIVATE);
        SharedPreferences.Editor set_save_day_editor =set_save_day.edit();
        set_save_day_editor.putString("save_day_key",day_save);
        set_save_day_editor.apply();
    }
    public void get_save_set_save_day(){
        SharedPreferences get_save_day = context.getSharedPreferences("save_save_day",MODE_PRIVATE);
        day_save = get_save_day.getString("save_day_key","");
    }



}
