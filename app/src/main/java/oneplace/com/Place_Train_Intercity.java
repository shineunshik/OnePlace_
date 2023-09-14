package oneplace.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import oneplace.com.API.Train_List_Add_API;
import oneplace.com.API.Train_List_Mapping_API;

public class Place_Train_Intercity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    String day_save;
    String select_address;
    Dialog Calendar_dialog;
    CalendarView calendar;
    TextView day;
    String month_;
    String dayOfMonth_;
    RecyclerView.Adapter adapter,adapter2;
    ArrayList<Ob_Train_Info> arrayList;
    Spinner address_choice;
    RecyclerView recyclerview;
    String key,select_area_spinner;
    SearchView search_view;
    Train_List_Mapping_API train_list_mapping_api;
    FirebaseDatabase database_real;
    DatabaseReference databaseReference_real;
    ArrayList<Ob_Station_Choice> arrayList_real;
    Train_List_Add_API train_list_add_api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_train_intercity);

     //   get_save_set_save_day();
        Calendar_dialog = new Dialog(this);
        Calendar_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 제거
        Calendar_dialog.setContentView(R.layout.calendar_view);
        calendar=(CalendarView) Calendar_dialog.findViewById(R.id.calendar);//달력

        train_list_mapping_api = new Train_List_Mapping_API();

//        train_list_add_api = new Train_List_Add_API();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    train_list_add_api.Train_station_list();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();

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
                        Toast.makeText(Place_Train_Intercity.this,day_save,Toast.LENGTH_SHORT).show();
                        Calendar_dialog.dismiss();
                    }
                });

            }
        });

        //검색
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

        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Place_Train_Intercity.this);
        //   linearLayoutManager.setStackFromEnd(true); //포커스가 마지막 포지션으로 감
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter= new CusTomAdapter_Train_Station_list(arrayList, Place_Train_Intercity.this);
        recyclerview.setAdapter(adapter);

        database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
        databaseReference=database.getReference("-기차역").child("-기차역LIST");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    arrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        arrayList.add(dataSnapshot.getValue(Ob_Train_Info.class));
                    }


                    arrayList_real = new ArrayList<>();
                    database_real=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
                    databaseReference_real=database_real.getReference("-기차역").child("-기차역매칭 check");
                    databaseReference_real.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                arrayList_real.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    arrayList_real.add(dataSnapshot.getValue(Ob_Station_Choice.class));
                                }

                                System.out.println("\n\n\n check 총 어레이"+arrayList_real.size()+"\n\n\n");
                                System.out.println("\n\n\n list 총 어레이"+arrayList.size()+"\n\n\n");

                                // 각 기차역이 도착하는 기차역 발췌 작업중 트래픽 초과로 중단  (원주부터 진행)
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try {
//                                            train_list_mapping_api.Train_station_Info(arrayList,arrayList_real);
//                                        } catch (IOException e) {
//                                            throw new RuntimeException(e);
//                                        }
//                                    }
//                                }).start();


                            }
                            catch (NullPointerException nullPointerException){
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

        Collections.sort(arrayList, new Comparator<Ob_Train_Info>() {
            @Override
            public int compare(Ob_Train_Info o1, Ob_Train_Info o2) {
                return o1.getAddress().compareTo(o2.getAddress());
            }
        });







        //지역 선택
        address_choice =(Spinner) findViewById(R.id.address_choice);
        ArrayAdapter<CharSequence> adapter_spinner = ArrayAdapter.createFromResource(this,R.array.train_station_address,R.layout.address_spinner_login);
        address_choice.setAdapter(adapter_spinner);
        address_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (adapter_spinner.getItem(position).equals("전체")|adapter_spinner.getItem(position).equals("-")){
                    recyclerview.setAdapter(adapter);
                }
                else {
                    select_area_spinner =address_choice.getSelectedItem().toString();//선택한 spinner항목 문자열 변환
                    ArrayList<Ob_Train_Info> myList = new ArrayList<>();
                    for (Ob_Train_Info ob_train_info : arrayList){
                        if (ob_train_info.getAddress().toLowerCase().contains(select_area_spinner)){
                            myList.add(ob_train_info);
                        }
                    }
                    adapter2 = new CusTomAdapter_Train_Station_list(myList, Place_Train_Intercity.this);
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
        ArrayList<Ob_Train_Info> myList = new ArrayList<>();
        for (Ob_Train_Info ob_train_info : arrayList){
            if (ob_train_info.getAddress().toLowerCase().contains(charText.toLowerCase())||ob_train_info.getNodename().toLowerCase().contains(charText.toLowerCase())){
                myList.add(ob_train_info);
            }
        }
        adapter = new CusTomAdapter_Train_Station_list(myList, Place_Train_Intercity.this);
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