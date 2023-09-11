package oneplace.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.LinearLayout;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Place_Bus_Station_Info extends AppCompatActivity {
    NodeList nlList;
    Node nValue;
    RecyclerView recyclerview;
    ArrayList<Ob_Bus_Station_Info_list> arrayList;
    RecyclerView.Adapter adapter;
    String num;
    TextView day;
    SearchView search_view;
    LinearLayout yes_date,no_date;
    TextView notice;

    StringBuilder urlBuilder_total = new StringBuilder("http://apis.data.go.kr/1613000/ExpBusInfoService/getStrtpntAlocFndExpbusInfo"); /*URL*/
    StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/ExpBusInfoService/getStrtpntAlocFndExpbusInfo"); /*URL*/
    URL url_total,url;
    HttpURLConnection httpURLConnection_total,httpURLConnection;
    BufferedReader rd_total,rd;
    String line_total,line;
    StringBuilder sb_total,sb;
    Document document_total,document;
    NodeList nList_body,nList;
    Node nNode_body,nNode2;
    Element eElement_body,eElement2;
    TextView terminalNm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_bus_station_info);


        day=(TextView)findViewById(R.id.day);
        day.setText(getIntent().getStringExtra("day_save"));
        terminalNm=(TextView)findViewById(R.id.terminalNm);
        terminalNm.setText(getIntent().getStringExtra("start_nodename")+" 고속버스 터미널");



        notice=(TextView)findViewById(R.id.notice);
        notice.setText("배차 정보 조회 중입니다.\n잠시만 기다려 주세요.");
        yes_date=(LinearLayout) findViewById(R.id.yes_date);
        no_date=(LinearLayout) findViewById(R.id.no_date);
        yes_date.setVisibility(View.GONE);
        no_date.setVisibility(View.VISIBLE);

        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Place_Bus_Station_Info.this);
        //   linearLayoutManager.setStackFromEnd(true); //포커스가 마지막 포지션으로 감
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter= new CusTomAdapter_Bus_Station_Info_list(arrayList,Place_Bus_Station_Info.this);


        //오름차순,내림차순
        Collections.sort(arrayList, new Comparator<Ob_Bus_Station_Info_list>() {
            @Override
            public int compare(Ob_Bus_Station_Info_list o1, Ob_Bus_Station_Info_list o2) {
                return o1.getArrPlaceNm().compareTo(o2.getArrPlaceNm());
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

        try {
            Bus_Station();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void Bus_Station()throws IOException{
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                //    urlBuilder_total.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*///은식
                    urlBuilder_total.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=xqtGro2RZ7GS64DxCIjdBJQt%2B9t0wgxfkVLY8s0I8BHSDYViUtMjayeRWpyr%2BZgS2FsiD%2BVGE5Cv4IcYRae1gA%3D%3D"); /*Service Key*///누나
                //    urlBuilder_total.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=eUOnBahe%2BDndmVjOuchJfBQS29NMywIHXZ4nyfxfWXUgZOKImkTM8ele3iWdA3BDcrXPiqhWar%2BVvjGvmwC8nA%3D%3D"); /*Service Key*/
                    urlBuilder_total.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
                    urlBuilder_total.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
                    urlBuilder_total.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*데이터 타입(xml, json)*/
                    urlBuilder_total.append("&" + URLEncoder.encode("depTerminalId","UTF-8") + "=" + URLEncoder.encode(getIntent().getStringExtra("start_nodeID"), "UTF-8")); /*출발터미널ID*/
                    urlBuilder_total.append("&" + URLEncoder.encode("arrTerminalId","UTF-8") + "=" + URLEncoder.encode(getIntent().getStringExtra("final_nodeID"), "UTF-8")); /*도착터미널ID*/
                    urlBuilder_total.append("&" + URLEncoder.encode("depPlandTime","UTF-8") + "=" + URLEncoder.encode(getIntent().getStringExtra("day_save"), "UTF-8")); /*출발일(YYYYMMDD)*/
                    urlBuilder_total.append("&" + URLEncoder.encode("busGradeId","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*버스등급*/

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

                    nList_body = document_total.getElementsByTagName("body"); //xml에서 파싱할 리스트명
                    nNode_body = nList_body.item(0);
                    eElement_body = (Element) nNode_body;
                    num= getTagValue("totalCount", eElement_body);

                 //   urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*///은식
                  //  urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=xqtGro2RZ7GS64DxCIjdBJQt%2B9t0wgxfkVLY8s0I8BHSDYViUtMjayeRWpyr%2BZgS2FsiD%2BVGE5Cv4IcYRae1gA%3D%3D"); /*Service Key*///누나
                    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=eUOnBahe%2BDndmVjOuchJfBQS29NMywIHXZ4nyfxfWXUgZOKImkTM8ele3iWdA3BDcrXPiqhWar%2BVvjGvmwC8nA%3D%3D"); /*Service Key*/
                    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
                    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(num, "UTF-8")); /*한 페이지 결과 수*/
                    urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*데이터 타입(xml, json)*/
                    urlBuilder.append("&" + URLEncoder.encode("depTerminalId","UTF-8") + "=" + URLEncoder.encode(getIntent().getStringExtra("start_nodeID"), "UTF-8")); /*출발터미널ID*/
                    urlBuilder.append("&" + URLEncoder.encode("arrTerminalId","UTF-8") + "=" + URLEncoder.encode(getIntent().getStringExtra("final_nodeID"), "UTF-8")); /*도착터미널ID*/
                    urlBuilder.append("&" + URLEncoder.encode("depPlandTime","UTF-8") + "=" + URLEncoder.encode(getIntent().getStringExtra("day_save"), "UTF-8")); /*출발일(YYYYMMDD)*/
                    urlBuilder.append("&" + URLEncoder.encode("busGradeId","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*버스등급*/
                    url = new URL(urlBuilder.toString());
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-type", "application/json");

                    if(httpURLConnection.getResponseCode() >= 200 && httpURLConnection.getResponseCode() <= 300) {
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
                   // System.out.println(sb.toString());

                    //xml 데이터를 파싱하기 위한 코드
                    document = DocumentBuilderFactory
                            .newInstance()
                            .newDocumentBuilder()
                            .parse(url + "");
                    document.getDocumentElement().normalize();
                    nList = document.getElementsByTagName("item"); //xml에서 파싱할 리스트명


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //데이터가 있는지 없는지 확인
                            if (num.equals("0")){
                                notice.setText("조회 완료");
                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        yes_date.setVisibility(View.GONE);
                                        no_date.setVisibility(View.VISIBLE);
                                        notice.setText("버스 배차 정보가 없습니다.");
                                    }
                                }, 500);
                            }
                            else {
                                notice.setText("조회 완료");
                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        yes_date.setVisibility(View.VISIBLE);
                                        no_date.setVisibility(View.GONE);
                                    }
                                }, 500);
                            }
                            arrayList.clear();
                            //데이터가 있다면 arraylist에 넣어서 리사이클러뷰에 출력
                            for (int temp = 0; temp < nList.getLength(); temp++) { //api가 작동을 하지않아 item 갯수가 없어서 for문이 작동을 안함
                                nNode2 = nList.item(temp);
                                if (nNode2.getNodeType() == Node.ELEMENT_NODE) { //노드 type이 같을 경우에 실행
                                    //log 확인 작업
                                    eElement2 = (Element) nNode2;
                                    Ob_Bus_Station_Info_list ob_bus_station_info_list = new Ob_Bus_Station_Info_list(); //각 항목들 null포인트 대처
                                    ob_bus_station_info_list.setDepPlandTime(getTagValue("depPlandTime", eElement2)); //출발시간
                                    ob_bus_station_info_list.setArrPlandTime(getTagValue("arrPlandTime", eElement2)); //도착시간
                                    ob_bus_station_info_list.setCharge(getTagValue("charge", eElement2)); //요금
                                    ob_bus_station_info_list.setGradeNm(getTagValue("gradeNm", eElement2)); //등급
                                    ob_bus_station_info_list.setDepPlaceNm(getTagValue("depPlaceNm", eElement2));//출발지
                                    ob_bus_station_info_list.setArrPlaceNm(getTagValue("arrPlaceNm", eElement2)); //도착지
                                    arrayList.add(ob_bus_station_info_list);
                                }
                            }
                            //Toast.makeText(Place_Bus_Station_Info.this,arrayList.size()+"",Toast.LENGTH_SHORT).show();
                            recyclerview.setAdapter(adapter);



                        }
                    });


                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                } catch (ProtocolException e) {
                    throw new RuntimeException(e);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                } catch (SAXException e) {
                    throw new RuntimeException(e);
                }catch (RuntimeException e){
                }

            }
        }).start();




    }

    public void  setSearch_view(String charText){
        ArrayList<Ob_Bus_Station_Info_list> myList = new ArrayList<>();
        for (Ob_Bus_Station_Info_list ob_bus_station_info_list : arrayList){
            if (ob_bus_station_info_list.getArrPlaceNm().toLowerCase().contains(charText.toLowerCase())){
                myList.add(ob_bus_station_info_list);
            }
        }
        adapter = new CusTomAdapter_Bus_Station_Info_list(myList,Place_Bus_Station_Info.this);
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
}