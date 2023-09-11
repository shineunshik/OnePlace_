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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CusTomAdapter_Train_Info_list extends RecyclerView.Adapter<CusTomAdapter_Train_Info_list.CustomViewHolder> {
    ArrayList<Ob_Train_Station_Info_list> arrayList;
    Context context;
    String select_address;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    public CusTomAdapter_Train_Info_list(ArrayList<Ob_Train_Station_Info_list> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_station_list_info_form,parent,false);
        CusTomAdapter_Train_Info_list.CustomViewHolder customViewHolder = new CusTomAdapter_Train_Info_list.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        get_save_select_address();

            try {
                holder.start_station.setText("출발지 : "+arrayList.get(position).getDepplacename());
            }
            catch (NullPointerException e){
            }

            try {
                holder.start_station_time.setText("출발시간 : "+arrayList.get(position).getDepplandtime().substring(8,10)+"시 "+arrayList.get(position).getDepplandtime().substring(10,12)+"분");
            }
            catch (NullPointerException e){
            }

            try {
                holder.last_station.setText("도착지 : "+arrayList.get(position).getArrplacename());
            }
            catch (NullPointerException e){
            }

            try {
                holder.last_station_time.setText("도착시간(예상) : "+arrayList.get(position).getArrplandtime().substring(8,10)+"시 "+arrayList.get(position).getArrplandtime().substring(10,12)+"분");
            }
            catch (NullPointerException e){
            }

            try {
                holder.bus_grade.setText("기차 종류 : "+arrayList.get(position).getTraingradename());
            }
            catch (NullPointerException e){
            }

            try {
                int bus_charge = Integer.parseInt(arrayList.get(position).getAdultcharge());
                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                String bus_charge2 = decimalFormat.format(bus_charge);
                if (bus_charge2.equals("0")){
                    holder.charge.setText("요금 : "+"요금 정보 없음");
                }
                else {
                    holder.charge.setText("요금 : "+bus_charge2+"원");
                }

            }
            catch (NullPointerException e){

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
