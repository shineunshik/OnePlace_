package oneplace.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import oneplace.com.API.Hospital_List_Add_API;

public class Place_Hospital extends AppCompatActivity {
    NodeList nlList;
    Node nValue;
    Spinner address_choice;
    SearchView search_view;
    RecyclerView recyclerview;
    ArrayList<Ob_Hospital_list> arrayList;
    RecyclerView.Adapter adapter,adapter2,adapter3;
    String num;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String select_area_spinner;
    ArrayList<Ob_Hospital_list> myList;
    Hospital_List_Add_API hospital_list_add_api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_hospital);


        hospital_list_add_api  = new Hospital_List_Add_API();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    hospital_list_add_api.Hospital_Input();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();



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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Place_Hospital.this);
        //   linearLayoutManager.setStackFromEnd(true); //포커스가 마지막 포지션으로 감
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter= new CusTomAdapter_Hospital_List(arrayList,Place_Hospital.this);
        recyclerview.setAdapter(adapter);

        database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
        databaseReference=database.getReference("-병원").child("-응급실LIST");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    arrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        arrayList.add(dataSnapshot.getValue(Ob_Hospital_list.class));
                    }
                    myList=arrayList; //기존 arraylist를 mylist에 할당하고  스피너를 선택하게되면 mylist 를 활용한다
                    adapter.notifyDataSetChanged();
                }
                catch (NullPointerException nullPointerException){
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Collections.sort(arrayList, new Comparator<Ob_Hospital_list>() {
            @Override
            public int compare(Ob_Hospital_list o1, Ob_Hospital_list o2) {
                return o1.getDutyAddr().compareTo(o2.getDutyAddr());
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
                    myList = new ArrayList<>();  //mylist를 새로 만들어서  mylist와 같은 데이터를 담고있는 arraylist를 다시 담는다
                    for (Ob_Hospital_list ob_hospital_list : arrayList){
                        if (ob_hospital_list.getDutyAddr().toLowerCase().contains(select_area_spinner)){
                            myList.add(ob_hospital_list);
                        }
                    }
                    adapter2 = new CusTomAdapter_Hospital_List(myList,Place_Hospital.this);
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
        ArrayList<Ob_Hospital_list> myList2 = new ArrayList<>();
        for (Ob_Hospital_list ob_hospital_list : myList){
            if (ob_hospital_list.dutyAddr.toLowerCase().contains(charText.toLowerCase())||ob_hospital_list.getDutyName().toLowerCase().contains(charText.toLowerCase())){
                myList2.add(ob_hospital_list);
            }
        }
        adapter3 = new CusTomAdapter_Hospital_List(myList2,Place_Hospital.this);
        recyclerview.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();
    }


}