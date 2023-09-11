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

public class CusTomAdapter_Bus_Station_Choice_list extends RecyclerView.Adapter<CusTomAdapter_Bus_Station_Choice_list.CustomViewHolder> {
    ArrayList<Ob_Station_Choice> arrayList;
    Context context;
    String select_address;
    FirebaseDatabase database;
    DatabaseReference databaseReference_start,databaseReference_final;
    String start_nodeID="";
    String final_nodeID="";
    String day_save;
    public CusTomAdapter_Bus_Station_Choice_list(ArrayList<Ob_Station_Choice> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_station_choice_list_form,parent,false);
        CusTomAdapter_Bus_Station_Choice_list.CustomViewHolder customViewHolder = new CusTomAdapter_Bus_Station_Choice_list.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        get_save_set_save_day();

        holder.train_station_name.setText(arrayList.get(position).getFinal_nodename()+"역");

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

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int position = getLayoutPosition();
                            database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
                            databaseReference_start=database.getReference("-고속버스").child("-정류장LIST").child(arrayList.get(position).getStart_nodename()).child("terminalId");
                            databaseReference_start.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    start_nodeID = snapshot.getValue().toString();

                                    databaseReference_final=database.getReference("-고속버스").child("-정류장LIST").child(arrayList.get(position).getFinal_nodename()).child("terminalId");
                                    databaseReference_final.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            final_nodeID = snapshot.getValue().toString();
                                            Intent intent = new Intent(context,Place_Bus_Station_Info.class);
                                            intent.putExtra("start_nodeID",start_nodeID);
                                            intent.putExtra("final_nodeID",final_nodeID);
                                            intent.putExtra("start_nodename",arrayList.get(position).getStart_nodename());
                                            intent.putExtra("day_save",day_save);
                                            context.startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }).start();

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
