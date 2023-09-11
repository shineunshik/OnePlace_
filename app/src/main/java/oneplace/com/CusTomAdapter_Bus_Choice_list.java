package oneplace.com;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CusTomAdapter_Bus_Choice_list extends RecyclerView.Adapter<CusTomAdapter_Bus_Choice_list.CustomViewHolder> {
    ArrayList<Ob_Station_Choice> arrayList;
    Context context;
    String select_address;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String day_save;
    public CusTomAdapter_Bus_Choice_list(ArrayList<Ob_Station_Choice> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_station_choice_list_form,parent,false);
        CusTomAdapter_Bus_Choice_list.CustomViewHolder customViewHolder = new CusTomAdapter_Bus_Choice_list.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {


        holder.train_station_name.setText(arrayList.get(position).getFinal_nodename()+"역");
//        if (position<9){
//            holder.train_station_name.setText("0"+(position+1)+".  "+arrayList.get(position).getFinal_nodename()+"역");
//        }else {
//            holder.train_station_name.setText(position+1+".  "+arrayList.get(position).getFinal_nodename()+"역");
//        }

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView train_station_name;
        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.train_station_name = itemView.findViewById(R.id.train_station_name);
            view = itemView;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();


                }
            });


        }
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
