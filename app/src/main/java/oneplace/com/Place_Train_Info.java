package oneplace.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
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

public class Place_Train_Info extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseDatabase database2;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    NodeList nlList;
    Node nValue;
    String num;
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
    int start,finall;
    NodeList nList_item,nList_item2;

    StringBuilder urlBuilder ;
    URL url;
    HttpURLConnection httpURLConnection;
    BufferedReader rd;
    String line;
    StringBuilder sb;
    Document document;
    Node nNode_item ;
    Element eElement_item;

    Node nNode_item2;

    Element eElement_item2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_train_info);

     //   get_save_set_save_day();
        Calendar_dialog = new Dialog(this);
        Calendar_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 제거
        Calendar_dialog.setContentView(R.layout.calendar_view);
        calendar=(CalendarView) Calendar_dialog.findViewById(R.id.calendar);//달력

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Train_station_list();
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
                        Toast.makeText(Place_Train_Info.this,day_save,Toast.LENGTH_SHORT).show();
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Place_Train_Info.this);
        //   linearLayoutManager.setStackFromEnd(true); //포커스가 마지막 포지션으로 감
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter= new CusTomAdapter_Train_Station_list(arrayList,Place_Train_Info.this);
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



                    // 각 기차역이 도착하는 기차역 발췌 작업중 트래픽 초과로 중단  (원주부터 진행)
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Train_station_Info(arrayList);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();

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
                    adapter2 = new CusTomAdapter_Train_Station_list(myList,Place_Train_Info.this);
                    recyclerview.setAdapter(adapter2);
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

    //각 열차가 도착하는 역 발췌 //75에 250부터 시작
    public void Train_station_Info(ArrayList<Ob_Train_Info> arrayList)throws IOException{

        for (start = 96; start < arrayList.size(); start++) {
            for (finall = 260; finall < arrayList.size(); finall++) {
                try {

                    System.out.println(arrayList.size()+"\n");
                    System.out.println(start+"      "+finall+"\n");

                    System.out.println(start + "번째 출발지 : " + arrayList.get(start).getNodename());
                    System.out.println("도착지 : " + arrayList.get(finall).getNodename() + " " + finall + "번째 시작  " + start);



                    urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/TrainInfoService/getStrtpntAlocFndTrainInfo"); /*URL*/

                  ///  urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=eUOnBahe%2BDndmVjOuchJfBQS29NMywIHXZ4nyfxfWXUgZOKImkTM8ele3iWdA3BDcrXPiqhWar%2BVvjGvmwC8nA%3D%3D"); /*Service Key*/
                    urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/ //은식
                    //urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=xqtGro2RZ7GS64DxCIjdBJQt%2B9t0wgxfkVLY8s0I8BHSDYViUtMjayeRWpyr%2BZgS2FsiD%2BVGE5Cv4IcYRae1gA%3D%3D"); /*Service Key*/ //누나
                    urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
                    urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
                    urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*데이터 타입(xml, json)*/
                    urlBuilder.append("&" + URLEncoder.encode("depPlaceId", "UTF-8") + "=" + URLEncoder.encode(arrayList.get(start).getNodeid(), "UTF-8")); /*출발기차역ID [상세기능3. 시/도별 기차역 목록조회]에서 조회 가능*/
                    urlBuilder.append("&" + URLEncoder.encode("arrPlaceId", "UTF-8") + "=" + URLEncoder.encode(arrayList.get(finall).getNodeid(), "UTF-8")); /*도착기차역ID [상세기능3. 시/도별 기차역 목록조회]에서 조회 가능*/
                    urlBuilder.append("&" + URLEncoder.encode("depPlandTime", "UTF-8") + "=" + URLEncoder.encode("20230903", "UTF-8")); /*출발일(YYYYMMDD)*/
                    urlBuilder.append("&" + URLEncoder.encode("trainGradeCode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*차량종류코드*/

                    url = new URL(urlBuilder.toString());
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-type", "application/json");
                    // System.out.println("Response code: " + conn.getResponseCode());

                    if (httpURLConnection.getResponseCode() >= 200 && httpURLConnection.getResponseCode() <= 300) {
                        rd = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    } else {
                        rd = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                    }

                    sb = new StringBuilder();
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }
                    rd.close();
                    httpURLConnection.disconnect();
                    //API를 사용하기위한 API정보 가져오기(기본 샘플 코드)
                    //xml 데이터를 파싱하기 위한 코드
                    document = DocumentBuilderFactory
                            .newInstance()
                            .newDocumentBuilder()
                            .parse(url + "");
                    document.getDocumentElement().normalize();


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nList_item = document.getElementsByTagName("item"); //xml에서 파싱할 리스트명

                            if (nList_item.getLength() != 0) {
                                //버스 정류장 정보 계속 업데이트
                                 nNode_item = nList_item.item(0);
                                 eElement_item = (Element) nNode_item;
                                if (nNode_item.getNodeType() == Node.ELEMENT_NODE) {
                                    database2 = FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
                                    databaseReference2 = database2.getReference("-기차역").child("-기차역매칭LIST");

                                    HashMap<String, Object> value = new HashMap<>();
                                    value.put("final_nodename",getTagValue("arrplacename", eElement_item));
                                    value.put("start_nodename",getTagValue("depplacename", eElement_item));
                                    databaseReference2.child(arrayList.get(start).getNodename()).child(getTagValue("arrplacename", eElement_item)).setValue(value);


//                                    databaseReference2.child(arrayList.get(start).getNodename()).child(arrayList.get(finall).getNodename()).child("final_nodeid").setValue(arrayList.get(finall).getNodeid());
//                                    databaseReference2.child(arrayList.get(start).getNodename()).child(arrayList.get(finall).getNodename()).child("final_nodename").setValue(arrayList.get(finall).getNodename());
//                                    databaseReference2.child(arrayList.get(start).getNodename()).child(arrayList.get(finall).getNodename()).child("start_nodeid").setValue(arrayList.get(start).getNodeid());
//                                    databaseReference2.child(arrayList.get(start).getNodename()).child(arrayList.get(finall).getNodename()).child("start_nodename").setValue(arrayList.get(start).getNodename());

                                    databaseReference3 = database2.getReference("-기차역").child("-기차역LIST");
                                    databaseReference3.child(arrayList.get(start).getNodename()).child("station_use").setValue("운영중");
                                    Toast.makeText(Place_Train_Info.this, start + " 완료", Toast.LENGTH_SHORT).show();
                                }
                            }

                            try {
                                //트래픽 초과로 인한 데이터 오류
                                nList_item2 = document.getElementsByTagName("cmmMsgHeader"); //xml에서 파싱할 리스트명
                                if (nList_item2 != null) {
                                     nNode_item2 = nList_item2.item(0);
                                    if (nNode_item2.getNodeType() == Node.ELEMENT_NODE) {
                                         eElement_item2 = (Element) nNode_item2;
                                        System.out.println(getTagValue("returnAuthMsg", eElement_item2) + "     " + arrayList.get(finall).getNodename());
                                       // ActivityCompat.finishAffinity(Place_Train_Info.this);
                                        onDestroy(); //트래픽초과로 데이터를 받지 못하면 로그를 남기고 앱 완전 종료
                                        return;
                                    }

                                }
                            } catch (NullPointerException e) {
                            }

                            System.out.println("도착지 : " + arrayList.get(finall).getNodename() + " " + finall + "번째 완료완료" + "\n\n");
                        }
                    });

                }
                  catch (IOException e){
                    System.out.println("IOException : 실패");
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    System.out.println("ParserConfigurationException : 실패");
                    e.printStackTrace();
                } catch (SAXException e) {
                    System.out.println("SAXException : 실패");
                    e.printStackTrace();
                }catch (NullPointerException e){
                    System.out.println("NullPointerException : 실패");
                    e.printStackTrace();
                }
                     catch (IndexOutOfBoundsException e){

                }



                }
            }


    }


    //전국 기차역 리스트 발췌
    public void Train_station_list()throws IOException{
        try {
            StringBuilder urlBuilder_total = new StringBuilder("http://apis.data.go.kr/1613000/TrainInfoService/getCtyAcctoTrainSttnList"); /*URL*/
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/TrainInfoService/getCtyAcctoTrainSttnList"); /*URL*/
            URL url_total;
            URL url;
            HttpURLConnection httpURLConnection_total;
            HttpURLConnection httpURLConnection;
            BufferedReader rd_total;
            BufferedReader rd;
            String line_total;
            String line;
            StringBuilder sb_total;
            StringBuilder sb;
            Document document_total;
            Document document;
         //   urlBuilder_total.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=eUOnBahe%2BDndmVjOuchJfBQS29NMywIHXZ4nyfxfWXUgZOKImkTM8ele3iWdA3BDcrXPiqhWar%2BVvjGvmwC8nA%3D%3D"); /*Service Key*/
         //   urlBuilder_total.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/ //은식
            urlBuilder_total.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=xqtGro2RZ7GS64DxCIjdBJQt%2B9t0wgxfkVLY8s0I8BHSDYViUtMjayeRWpyr%2BZgS2FsiD%2BVGE5Cv4IcYRae1gA%3D%3D"); /*Service Key*/ //누나
            urlBuilder_total.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder_total.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder_total.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*데이터 타입(xml, json)*/
            urlBuilder_total.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode("38", "UTF-8")); /*시/도 ID*/


            url_total = new URL(urlBuilder_total.toString());
            httpURLConnection_total = (HttpURLConnection) url_total.openConnection();
            httpURLConnection_total.setRequestMethod("GET");
            httpURLConnection_total.setRequestProperty("Content-type", "application/json");
            // System.out.println("Response code: " + conn.getResponseCode());

            if (httpURLConnection_total.getResponseCode() >= 200 && httpURLConnection_total.getResponseCode() <= 300) {
                rd_total = new BufferedReader(new InputStreamReader(httpURLConnection_total.getInputStream()));
            } else {
                rd_total = new BufferedReader(new InputStreamReader(httpURLConnection_total.getErrorStream()));
            }

            sb_total = new StringBuilder();
            while ((line_total = rd_total.readLine()) != null) {
                sb_total.append(line_total);
            }
            rd_total.close();
            httpURLConnection_total.disconnect();
            //API를 사용하기위한 API정보 가져오기(기본 샘플 코드)

            //xml 데이터를 파싱하기 위한 코드
            document_total = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(url_total + "");
            document_total.getDocumentElement().normalize();

            NodeList nList_body = document_total.getElementsByTagName("body"); //xml에서 파싱할 리스트명
            Node nNode_body = nList_body.item(0);
            Element eElement_body = (Element) nNode_body;
            num= getTagValue("totalCount", eElement_body);
            //이까지 총 갯수 추출

          //  urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=eUOnBahe%2BDndmVjOuchJfBQS29NMywIHXZ4nyfxfWXUgZOKImkTM8ele3iWdA3BDcrXPiqhWar%2BVvjGvmwC8nA%3D%3D"); /*Service Key*/
          //  urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/ //은식
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=xqtGro2RZ7GS64DxCIjdBJQt%2B9t0wgxfkVLY8s0I8BHSDYViUtMjayeRWpyr%2BZgS2FsiD%2BVGE5Cv4IcYRae1gA%3D%3D"); /*Service Key*/ //누나
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(num, "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*데이터 타입(xml, json)*/
            urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode("38", "UTF-8")); /*시/도 ID*/

            url = new URL(urlBuilder.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-type", "application/json");
            // System.out.println("Response code: " + conn.getResponseCode());

            if (httpURLConnection.getResponseCode() >= 200 && httpURLConnection.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
            }

            sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            httpURLConnection.disconnect();
            //API를 사용하기위한 API정보 가져오기(기본 샘플 코드)
            //xml 데이터를 파싱하기 위한 코드
            document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(url + "");
            document.getDocumentElement().normalize();


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    NodeList nList_item = document.getElementsByTagName("item"); //xml에서 파싱할 리스트명
                    //버스 정류장 정보 계속 업데이트
                    for (int temp = 0; temp < nList_item.getLength(); temp++) {
                        Node nNode_item = nList_item.item(temp);
                        if (nNode_item.getNodeType() == Node.ELEMENT_NODE) {
                            //log 확인 작업
                            Element eElement_item = (Element) nNode_item;

                            database= FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
                            databaseReference=database.getReference("-기차역").child("-기차역LIST");
                            databaseReference.child(getTagValue("nodename", eElement_item)).child("nodeid").setValue(getTagValue("nodeid", eElement_item));
                            databaseReference.child(getTagValue("nodename", eElement_item)).child("nodename").setValue(getTagValue("nodename", eElement_item));
                            databaseReference.child(getTagValue("nodename", eElement_item)).child("address").setValue("경상남도");
                        }
                    }
                }
            });


        }
        catch (IOException e){
            System.out.println("IOException : 실패");
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfigurationException : 실패");
            e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("SAXException : 실패");
            e.printStackTrace();
        }catch (NullPointerException e){
            System.out.println("NullPointerException : 실패");
            e.printStackTrace();
        }
    }

    public void  setSearch_view(String charText){
        ArrayList<Ob_Train_Info> myList = new ArrayList<>();
        for (Ob_Train_Info ob_train_info : arrayList){
            if (ob_train_info.getAddress().toLowerCase().contains(charText.toLowerCase())||ob_train_info.getNodename().toLowerCase().contains(charText.toLowerCase())){
                myList.add(ob_train_info);
            }
        }
        adapter = new CusTomAdapter_Train_Station_list(myList,Place_Train_Info.this);
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public  String getTagValue(String tag, Element eElement){ //xml에서 파싱할 데이터 찾기

            nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
            nValue = (Node) nlList.item(0);
            if (nValue == null)
                return null;

        return nValue.getNodeValue();
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