package oneplace.com;

import androidx.appcompat.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import oneplace.com.API.Bus_List_Add_API;
import oneplace.com.API.Bus_List_Mapping_API;

public class Place_Bus_Intercity extends AppCompatActivity  {
    Spinner address_choice;
    RecyclerView recyclerview;
    ArrayList<Ob_Bus_Station_list> arrayList;
    RecyclerView.Adapter adapter,adapter2;
    String num;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String select_area_spinner;
    TextView day;
    String day_save;
    String month_;
    String dayOfMonth_;
    Dialog Calendar_dialog;
    CalendarView calendar;
    SearchView search_view;
    String select_address;
    Bus_List_Mapping_API bus_list_mapping_api;

    FirebaseDatabase database_real;
    DatabaseReference databaseReference_real;
    ArrayList<Ob_Bus_Station_Info_list> arrayList_real;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_bus_intercity);

        get_save_set_save_day();


        bus_list_mapping_api=new Bus_List_Mapping_API();

//        bus_list_Add_api = new Bus_List_Add_API();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    bus_list_Add_api.Bus_select();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        }).start();


        Calendar_dialog = new Dialog(this);
        Calendar_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 제거
        Calendar_dialog.setContentView(R.layout.calendar_view);
        calendar=(CalendarView) Calendar_dialog.findViewById(R.id.calendar);//달력

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        day=(TextView)findViewById(R.id.day);
        day.setText(time);
        day_save=day.getText().toString();
        set_save_day();
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar_dialog.show();

                calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        if (month<10){
                            month_ = "0"+month;
                        }
                        if (dayOfMonth<10){
                            dayOfMonth_ = "0"+dayOfMonth;
                        }
                        else {
                            dayOfMonth_=String.valueOf(dayOfMonth);
                        }
                        day.setText(String.format("%d",year,dayOfMonth)+month_+dayOfMonth_);
                        day_save=day.getText().toString();
                        set_save_day();
                        Calendar_dialog.dismiss();
                    }
                });

            }
        });


        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Place_Bus_Intercity.this);
     //   linearLayoutManager.setStackFromEnd(true); //포커스가 마지막 포지션으로 감
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter= new CusTomAdapter_Bus_Station_list(arrayList,Place_Bus_Intercity.this);
        recyclerview.setAdapter(adapter);

        database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
        databaseReference=database.getReference("-고속버스").child("-정류장LIST");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    arrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        arrayList.add(dataSnapshot.getValue(Ob_Bus_Station_list.class));
                    }


                    arrayList_real = new ArrayList<>();
                    database_real = FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
                    databaseReference_real = database_real.getReference("-고속버스").child("-정류장매칭 check");
                    databaseReference_real.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            try {
                                arrayList_real.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    arrayList_real.add(dataSnapshot.getValue(Ob_Bus_Station_Info_list.class));
                                }
                                System.out.println("\n\n\n 총 어레이"+arrayList_real.size()+"\n\n\n");


                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            bus_list_mapping_api.bus_station_Info(arrayList,arrayList_real);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }).start();


                            }
                            catch (NullPointerException e){

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });





                    adapter.notifyDataSetChanged();
                }
                catch (NullPointerException nullPointerException){
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Collections.sort(arrayList, new Comparator<Ob_Bus_Station_list>() {
            @Override
            public int compare(Ob_Bus_Station_list o1, Ob_Bus_Station_list o2) {
                return o1.getAddress().compareTo(o2.getAddress());
            }
        });


        search_view=(SearchView)findViewById(R.id.search_view);
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                setSearch_view(text);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                setSearch_view(text);
                return true;
            }
        });


        //지역 선택
        address_choice =(Spinner) findViewById(R.id.address_choice);
        ArrayAdapter<CharSequence> adapter_spinner = ArrayAdapter.createFromResource(this,R.array.intercity_bus_address,R.layout.address_spinner_login);
        address_choice.setAdapter(adapter_spinner);
        address_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (adapter_spinner.getItem(position).equals("전체")|adapter_spinner.getItem(position).equals("-")){
                    recyclerview.setAdapter(adapter);
                }
                else {
                    select_area_spinner =address_choice.getSelectedItem().toString();//선택한 spinner항목 문자열 변환
                    ArrayList<Ob_Bus_Station_list> myList = new ArrayList<>();
                    for (Ob_Bus_Station_list ob_bus_station_list : arrayList){
                        if (ob_bus_station_list.getAddress().toLowerCase().contains(select_area_spinner)){
                            myList.add(ob_bus_station_list);
                        }
                    }
                    adapter2 = new CusTomAdapter_Bus_Station_list(myList,Place_Bus_Intercity.this);
                    recyclerview.setAdapter(adapter2);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

     }



    public void  setSearch_view(String charText){
        ArrayList<Ob_Bus_Station_list> myList1 = new ArrayList<>();
        for (Ob_Bus_Station_list ob_bus_station_list : arrayList){
            if (ob_bus_station_list.getAddress().toLowerCase().contains(charText.toLowerCase())||ob_bus_station_list.getTerminalNm().toLowerCase().contains(charText.toLowerCase())){
                myList1.add(ob_bus_station_list);
            }
        }
        adapter = new CusTomAdapter_Bus_Station_list(myList1,Place_Bus_Intercity.this);
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    public void set_save_day(){
        SharedPreferences set_save_day = getSharedPreferences("save_save_day",MODE_PRIVATE);
        SharedPreferences.Editor set_save_day_editor =set_save_day.edit();
        set_save_day_editor.putString("save_day_key",day_save);
        set_save_day_editor.apply();
    }
    public void get_save_set_save_day(){
        SharedPreferences get_save_day = getSharedPreferences("save_save_day",MODE_PRIVATE);
        day_save = get_save_day.getString("save_day_key","");
    }


    public void get_save_select_address(){
        SharedPreferences get_save_select_address = getSharedPreferences("save_select_address",MODE_PRIVATE);
        select_address = get_save_select_address.getString("save_select_address_key","");
    }

}