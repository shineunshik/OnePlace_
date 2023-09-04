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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CusTomAdapter_Bus_Station_Info_list extends RecyclerView.Adapter<CusTomAdapter_Bus_Station_Info_list.CustomViewHolder> {
    ArrayList<Ob_Bus_Station_Info_list> arrayList;
    Context context;
    String select_address;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    public CusTomAdapter_Bus_Station_Info_list(ArrayList<Ob_Bus_Station_Info_list> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_station_list_info_form,parent,false);
        CusTomAdapter_Bus_Station_Info_list.CustomViewHolder customViewHolder = new CusTomAdapter_Bus_Station_Info_list.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        get_save_select_address();
        if (arrayList.get(position).getArrPlaceNm()==null||arrayList.get(position).getDepPlaceNm()==null){
            arrayList.remove(position);
        }
        else {
            holder.start_station.setText(position+1+"출발지 : "+arrayList.get(position).getDepPlaceNm());
            holder.start_station_time.setText("출발시간 : "+arrayList.get(position).getDepPlandTime().substring(8,10)+"시 "+arrayList.get(position).getDepPlandTime().substring(10,12)+"분");
            holder.last_station.setText("도착지 : "+arrayList.get(position).getArrPlaceNm());
            holder.last_station_time.setText("도착시간(예상) : "+arrayList.get(position).getArrPlandTime().substring(8,10)+"시 "+arrayList.get(position).getArrPlandTime().substring(10,12)+"분");
            holder.bus_grade.setText("버스등급 : "+arrayList.get(position).getGradeNm());

            int bus_charge = Integer.parseInt(arrayList.get(position).getCharge());

            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            String bus_charge2 = decimalFormat.format(bus_charge);
            holder.charge.setText("요금 : "+bus_charge2+"원");

        }




    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView start_station;
        TextView start_station_time;
        TextView last_station;
        TextView last_station_time;
        TextView bus_grade;
        TextView charge;

        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.start_station = itemView.findViewById(R.id.start_station);
            this.start_station_time = itemView.findViewById(R.id.start_station_time);
            this.last_station = itemView.findViewById(R.id.last_station);
            this.last_station_time = itemView.findViewById(R.id.last_station_time);
            this.bus_grade = itemView.findViewById(R.id.bus_grade);
            this.charge = itemView.findViewById(R.id.charge);
            view = itemView;


        }
    }

    public void get_save_select_address(){
        SharedPreferences get_save_select_address = context.getSharedPreferences("save_select_address",MODE_PRIVATE);
        select_address = get_save_select_address.getString("save_select_address_key","");
    }
}
