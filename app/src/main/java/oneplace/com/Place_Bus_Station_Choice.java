package oneplace.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Place_Bus_Station_Choice extends AppCompatActivity {
    RecyclerView recyclerview;
    TextView start_station;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    RecyclerView.Adapter adapter;
    ArrayList<Ob_Station_Choice> arrayList;
    TextView year,month;
    String day_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.place_bus_station_choice);

        get_save_set_save_day();

        start_station = (TextView)findViewById(R.id.start_station);
        start_station.setText(getIntent().getStringExtra("terminalNm")+"역에서 출발");
        year=(TextView)findViewById(R.id.year);
        year.setText(day_save.substring(0,4)+"년");
        month=(TextView)findViewById(R.id.month);
        month.setText(day_save.substring(4,6)+"월"+day_save.substring(6,8)+"일");



        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Place_Bus_Station_Choice.this);
        //   linearLayoutManager.setStackFromEnd(true); //포커스가 마지막 포지션으로 감
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter= new CusTomAdapter_Bus_Station_Choice_list(arrayList,Place_Bus_Station_Choice.this);
        recyclerview.setAdapter(adapter);

        database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
        databaseReference=database.getReference("-고속버스").child("-정류장매칭LIST").child(getIntent().getStringExtra("terminalNm"));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    arrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        arrayList.add(dataSnapshot.getValue(Ob_Station_Choice.class));
                    }

                    adapter.notifyDataSetChanged();
                }
                catch (NullPointerException nullPointerException){
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Collections.sort(arrayList, new Comparator<Ob_Station_Choice>() {
            @Override
            public int compare(Ob_Station_Choice o1, Ob_Station_Choice o2) {
                return o1.getFinal_nodename().compareTo(o2.getFinal_nodename());
            }
        });



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

}