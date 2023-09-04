package oneplace.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

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

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Place_Hospital_Info extends AppCompatActivity {
    NodeList nlList;
    Node nValue;
    TextView dutyName;
    TextView dutyAddr;
    TextView dutyTel1;
    TextView dutyTel3;
    TextView dutyEmclsName;

    NodeList nList_item;


    TextView hvec,hvec_view,dgidIdName,dgidIdName_view;
    TextView hperyn,hperyn_view; //총 응급실 수용가능 인원
    TextView dutyEryn,dutyEryn_view;//응급실 운영여부  (1/2) 1가능 2불가능
    TextView dutyHayn,dutyHayn_view;//입원실 가용 여부 (1/2) 1가능 2불가능
    TextView hpbdn,hpbdn_view; //총병상수



    TextView hvoc; //남은 수술실 갯수
    TextView hpopyn; //총 수술실 갯수
    TextView dutyHano; //남은 병상수

    TextView dutyTime1c ; //월요일
    TextView dutyTime2c; //화요일
    TextView dutyTime3c; //수요일
    TextView dutyTime4c; //목요일
    TextView dutyTime5c; //금요일
    TextView dutyTime6c; //토요일
    TextView dutyTime7c;//일요일
    TextView dutyTime8c; //공휴일

    TextView dutyTime1s;//월요일
    TextView dutyTime2s; //화요일
    TextView dutyTime3s; //수요일
    TextView dutyTime4s; //목요일
    TextView dutyTime5s;//금요일
    TextView dutyTime6s;//토요일
    TextView dutyTime7s; //일요일
    TextView dutyTime8s;//공휴일


    TextView MKioskTy25; //응급실 여부
    TextView MKioskTy1; //뇌출혈수술 여부
    TextView MKioskTy2;//뇌경색의재관류 여부
    TextView MKioskTy3; //심근경색의재관류 여부
    TextView MKioskTy4; //복부손상의수술 여부
    TextView MKioskTy5; //사지접합의수술 여부
    TextView MKioskTy6; //응급내시경 여부
    TextView MKioskTy7; //응급투석 여부
    TextView MKioskTy8; //조산산모 여부
    TextView MKioskTy9; //정신질환자 여부
    TextView MKioskTy10; //신생아 여부
    TextView MKioskTy11; //중증화상 여부

    StringBuilder urlBuilder;
    URL url;
    HttpURLConnection httpURLConnection;
    BufferedReader rd;
    String line;
    StringBuilder sb;
    Document document;
    Node nNode_body ;
    Element eElement_item ;
    TextView skTy1,skTy2,skTy3,skTy4,skTy5,skTy6,skTy7,skTy8,skTy9,skTy10,skTy11,MKioskTy_title,duty_time_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_hospital_info);

        dutyName=(TextView)findViewById(R.id.dutyName);
        dutyAddr=(TextView)findViewById(R.id.dutyAddr);
        dutyTel1=(TextView)findViewById(R.id.dutyTel1);
        dutyTel3=(TextView)findViewById(R.id.dutyTel3);
        dutyEmclsName=(TextView)findViewById(R.id.dutyEmclsName);
        dutyEmclsName.setVisibility(View.GONE);
        dutyName.setText(getIntent().getStringExtra("dutyName"));
        dutyAddr.setText(getIntent().getStringExtra("dutyAddr"));
        dutyTel1.setText(getIntent().getStringExtra("dutyTel1"));
        dutyTel3.setText(getIntent().getStringExtra("dutyTel3"));
        dutyEmclsName.setText(getIntent().getStringExtra("dutyEmclsName"));

        dgidIdName=(TextView)findViewById(R.id.dgidIdName);  //진료과목
        hvec=(TextView)findViewById(R.id.hvec); //남은 응급실


         hperyn= findViewById(R.id.hperyn); //총 응급실 수용가능 인원
         dutyEryn= findViewById(R.id.dutyEryn); //응급실 운영여부  (1/2) 1가능 2불가능

         hvoc= findViewById(R.id.hvoc); //남은 수술실 갯수 //하이드
         hvoc.setVisibility(View.GONE);
         hpopyn= findViewById(R.id.hpopyn); //총 수술실 갯수
         hpopyn.setVisibility(View.GONE);

         dutyHayn= findViewById(R.id.dutyHayn); //입원실 가용 여부 (1/2) 1가능 2불가능
         dutyHano= findViewById(R.id.dutyHano); //남은 병상수 //하이드
         dutyHano.setVisibility(View.GONE);
         hpbdn= findViewById(R.id.hpbdn); //총병상수

         dutyTime1c = findViewById(R.id.dutyTime1c); //월요일
         dutyTime2c= findViewById(R.id.dutyTime2c); //화요일
         dutyTime3c= findViewById(R.id.dutyTime3c); //수요일
         dutyTime4c= findViewById(R.id.dutyTime4c); //목요일
         dutyTime5c= findViewById(R.id.dutyTime5c); //금요일
         dutyTime6c= findViewById(R.id.dutyTime6c); //토요일
         dutyTime7c= findViewById(R.id.dutyTime7c); //일요일
         dutyTime8c= findViewById(R.id.dutyTime8c); //공휴일

         dutyTime1s= findViewById(R.id.dutyTime1s); //월요일
         dutyTime2s= findViewById(R.id.dutyTime2s); //화요일
         dutyTime3s= findViewById(R.id.dutyTime3s); //수요일
         dutyTime4s= findViewById(R.id.dutyTime4s); //목요일
         dutyTime5s= findViewById(R.id.dutyTime5s); //금요일
         dutyTime6s= findViewById(R.id.dutyTime6s); //토요일
         dutyTime7s= findViewById(R.id.dutyTime7s); //일요일
         dutyTime8s= findViewById(R.id.dutyTime8s); //공휴일


         MKioskTy25= findViewById(R.id.MKioskTy25); //응급실 여부
         MKioskTy25.setVisibility(View.GONE);
         MKioskTy1= findViewById(R.id.MKioskTy1); //뇌출혈수술 여부
         MKioskTy2= findViewById(R.id.MKioskTy2); //뇌경색의재관류 여부
         MKioskTy3= findViewById(R.id.MKioskTy3); //심근경색의재관류 여부
         MKioskTy4= findViewById(R.id.MKioskTy4); //복부손상의수술 여부
         MKioskTy5= findViewById(R.id.MKioskTy5); //사지접합의수술 여부
         MKioskTy6= findViewById(R.id.MKioskTy6); //응급내시경 여부
         MKioskTy7= findViewById(R.id.MKioskTy7); //응급투석 여부
         MKioskTy8= findViewById(R.id.MKioskTy8); //조산산모 여부
         MKioskTy9= findViewById(R.id.MKioskTy9); //정신질환자 여부
         MKioskTy10= findViewById(R.id.MKioskTy10); //신생아 여부
         MKioskTy11= findViewById(R.id.MKioskTy11); //중증화상 여부

         skTy1= findViewById(R.id.skTy1);
         skTy2= findViewById(R.id.skTy2);
         skTy3= findViewById(R.id.skTy3);
         skTy4= findViewById(R.id.skTy4);
         skTy5= findViewById(R.id.skTy5);
         skTy6= findViewById(R.id.skTy6);
         skTy7= findViewById(R.id.skTy7);
         skTy8= findViewById(R.id.skTy8);
         skTy9= findViewById(R.id.skTy9);
         skTy10= findViewById(R.id.skTy10);
         skTy11= findViewById(R.id.skTy11);
         MKioskTy_title= findViewById(R.id.MKioskTy_title);
         duty_time_title= findViewById(R.id.duty_time_title);

         hvec_view= findViewById(R.id.hvec_view);
         dgidIdName_view= findViewById(R.id.dgidIdName_view);
         hperyn_view= findViewById(R.id.hperyn_view);
         dutyEryn_view= findViewById(R.id.dutyEryn_view);
         dutyHayn_view= findViewById(R.id.dutyHayn_view);
         hpbdn_view= findViewById(R.id.hpbdn_view);





    new Thread(new Runnable() {
        @Override
        public void run() {

            try {

                urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire"); /*URL*/
                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/ //은식
                urlBuilder.append("&" + URLEncoder.encode("HPID","UTF-8") + "=" + URLEncoder.encode(getIntent().getStringExtra("hpid"), "UTF-8")); /*기관ID*/
                urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
                urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*목록 건수*/

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

                nList_item = document.getElementsByTagName("item"); //xml에서 파싱할 리스트명
                nNode_body = nList_item.item(0);
                eElement_item = (Element) nNode_body;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    hvec.setText(" : "+getTagValue("hvec", eElement_item));
                                }catch (NullPointerException e){
                                    hvec.setText("");
                                    hvec.setVisibility(View.GONE);
                                    hvec_view.setVisibility(View.GONE);
                                }
                            }
                        }

                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dgidIdName.setText(getTagValue("dgidIdName", eElement_item));
                                }catch (NullPointerException e){
                                    dgidIdName.setText("");
                                    dgidIdName.setVisibility(View.GONE);
                                    dgidIdName_view.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    hperyn.setText(" : "+getTagValue("hperyn", eElement_item));
                                }catch (NullPointerException e){
                                    hperyn.setText("");
                                    hperyn.setVisibility(View.GONE);
                                    hperyn_view.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("dutyEryn", eElement_item).contains("1")){
                                        dutyEryn.setText(" : O");
                                    }else {
                                        dutyEryn.setText(" : X");
                                    }
                                }catch (NullPointerException e){
                                    dutyEryn.setText("");
                                    dutyEryn.setVisibility(View.GONE);
                                    dutyEryn_view.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    hvoc.setText(getTagValue("hvocv", eElement_item));
                                }catch (NullPointerException e){
                                    hvoc.setText("");
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    hpopyn.setText(getTagValue("hpopyn", eElement_item));
                                }catch (NullPointerException e){
                                    hpopyn.setText("");


                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("dutyHayn", eElement_item).contains("1")){
                                        dutyHayn.setText(" : O");
                                    }else {
                                        dutyHayn.setText(" : X");
                                    }

                                }catch (NullPointerException e){
                                    dutyHayn.setText("");
                                    dutyHayn.setVisibility(View.GONE);
                                    dutyHayn_view.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyHano.setText(getTagValue("dutyHano", eElement_item));
                                }catch (NullPointerException e){
                                    dutyHano.setText("");
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    hpbdn.setText(" : "+getTagValue("hpbdn", eElement_item));
                                }catch (NullPointerException e){
                                    hpbdn.setText("");
                                    hpbdn.setVisibility(View.GONE);
                                    hpbdn_view.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime1c.setText(getTagValue("dutyTime1c", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime1c", eElement_item).substring(2,4));
                                }catch (NullPointerException e){
                                    dutyTime1c.setText("77 : 77");
                                    dutyTime1c.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime2c.setText(getTagValue("dutyTime2c", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime2c", eElement_item).substring(2,4));
                                }catch (NullPointerException e){
                                    dutyTime2c.setText("77 : 77");
                                    dutyTime2c.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime3c.setText(getTagValue("dutyTime3c", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime3c", eElement_item).substring(2,4));
                                }catch (NullPointerException e){
                                    dutyTime3c.setText("77 : 77");
                                    dutyTime3c.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime4c.setText(getTagValue("dutyTime4c", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime4c", eElement_item).substring(2,4));
                                }catch (NullPointerException e){
                                    dutyTime4c.setText("77 : 77");
                                    dutyTime4c.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime5c.setText(getTagValue("dutyTime5c", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime5c", eElement_item).substring(2,4));
                                }catch (NullPointerException e){
                                    dutyTime5c.setText("77 : 77");
                                    dutyTime5c.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime6c.setText(getTagValue("dutyTime6c", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime6c", eElement_item).substring(2,4));
                                }catch (NullPointerException e){
                                    dutyTime6c.setText("77 : 77");
                                    dutyTime6c.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime7c.setText(getTagValue("dutyTime7c", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime7c", eElement_item).substring(2,4));
                                }catch (NullPointerException e){
                                    dutyTime7c.setText("77 : 77");
                                    dutyTime7c.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime8c.setText(getTagValue("dutyTime8c", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime8c", eElement_item).substring(2,4));
                                }catch (NullPointerException e){
                                    dutyTime8c.setText("77 : 77");
                                    dutyTime8c.setVisibility(View.INVISIBLE);

                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime1s.setText(getTagValue("dutyTime1s", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime1s", eElement_item).substring(2,4)+" ~ ");
                                }catch (NullPointerException e){
                                    dutyTime1s.setText("77 : 77 ~ ");
                                    dutyTime1s.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime2s.setText(getTagValue("dutyTime2s", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime2s", eElement_item).substring(2,4)+" ~ ");
                                }catch (NullPointerException e){
                                    dutyTime2s.setText("77 : 77 ~ ");
                                    dutyTime2s.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime3s.setText(getTagValue("dutyTime3s", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime3s", eElement_item).substring(2,4)+" ~ ");
                                }catch (NullPointerException e){
                                    dutyTime3s.setText("77 : 77 ~ ");
                                    dutyTime3s.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime4s.setText(getTagValue("dutyTime4s", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime4s", eElement_item).substring(2,4)+" ~ ");
                                }catch (NullPointerException e){
                                    dutyTime4s.setText("77 : 77 ~ ");
                                    dutyTime4s.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime5s.setText(getTagValue("dutyTime5s", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime5s", eElement_item).substring(2,4)+" ~ ");
                                }catch (NullPointerException e){
                                    dutyTime5s.setText("77 : 77 ~ ");
                                    dutyTime5s.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime6s.setText(getTagValue("dutyTime6s", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime6s", eElement_item).substring(2,4)+" ~ ");
                                }catch (NullPointerException e){
                                    dutyTime6s.setText("77 : 77 ~ ");
                                    dutyTime6s.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime7s.setText(getTagValue("dutyTime7s", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime7s", eElement_item).substring(2,4)+" ~ ");
                                }catch (NullPointerException e){
                                    dutyTime7s.setText("77 : 77 ~ ");
                                    dutyTime7s.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    dutyTime8s.setText(getTagValue("dutyTime8s", eElement_item).substring(0,2)+" : "+getTagValue("dutyTime8s", eElement_item).substring(2,4)+" ~ ");
                                }catch (NullPointerException e){
                                    dutyTime8s.setText("77 : 77 ~ ");
                                    dutyTime8s.setVisibility(View.INVISIBLE);

                                }
                            }
                        }
                        if (dutyTime1s.getText().toString().contains("77")&&dutyTime2s.getText().toString().contains("77")&&dutyTime3s.getText().toString().contains("77")&&dutyTime4s.getText().toString().contains("77")&&dutyTime5s.getText().toString().contains("77")
                                &&dutyTime6s.getText().toString().contains("77")&&dutyTime7s.getText().toString().contains("77")&&dutyTime8s.getText().toString().contains("77")){
                            duty_time_title.setVisibility(View.GONE);
                        }







                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("MKioskTy25", eElement_item).contains("Y")|getTagValue("MKioskTy25", eElement_item).contains("y")){
                                        MKioskTy25.setText(" : 있음");

                                    }
                                    else {
                                        MKioskTy25.setText(" : 없음");
                                    }

                                }catch (NullPointerException e){
                                    MKioskTy25.setText("");
                                    MKioskTy25.setVisibility(View.GONE);
                                }
                            }
                        }


                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("MKioskTy1", eElement_item).contains("Y")|getTagValue("MKioskTy1", eElement_item).contains("y")){
                                        MKioskTy1.setText(" : O");
                                    }else {
                                        MKioskTy1.setText(" : X");
                                    }
                                }catch (NullPointerException e){
                                    MKioskTy1.setText("");
                                    MKioskTy1.setVisibility(View.GONE);
                                    skTy1.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("MKioskTy2", eElement_item).contains("Y")|getTagValue("MKioskTy2", eElement_item).contains("y")){
                                        MKioskTy2.setText(" : O");
                                    }else {
                                        MKioskTy2.setText(" : X");
                                    }
                                }catch (NullPointerException e){
                                    MKioskTy2.setText("");
                                    MKioskTy2.setVisibility(View.GONE);
                                    skTy2.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("MKioskTy3", eElement_item).contains("Y")|getTagValue("MKioskTy3", eElement_item).contains("y")){
                                        MKioskTy3.setText(" : O");
                                    }else {
                                        MKioskTy3.setText(" : X");
                                    }
                                }catch (NullPointerException e){
                                    MKioskTy3.setText("");
                                    MKioskTy3.setVisibility(View.GONE);
                                    skTy3.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("MKioskTy4", eElement_item).contains("Y")|getTagValue("MKioskTy4", eElement_item).contains("y")){
                                        MKioskTy4.setText(" : O");
                                    }else {
                                        MKioskTy4.setText(" : X");
                                    }

                                }catch (NullPointerException e){
                                    MKioskTy4.setText("");
                                    MKioskTy4.setVisibility(View.GONE);
                                    skTy4.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("MKioskTy5", eElement_item).contains("Y")|getTagValue("MKioskTy5", eElement_item).contains("y")){
                                        MKioskTy5.setText(" : O");
                                    }else {
                                        MKioskTy5.setText(" : X");
                                    }

                                }catch (NullPointerException e){
                                    MKioskTy5.setText("");
                                    MKioskTy5.setVisibility(View.GONE);
                                    skTy5.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("MKioskTy6", eElement_item).contains("Y")|getTagValue("MKioskTy6", eElement_item).contains("y")){
                                        MKioskTy6.setText(" : O");
                                    }else {
                                        MKioskTy6.setText(" : X");
                                    }

                                }catch (NullPointerException e){
                                    MKioskTy6.setText("");
                                    MKioskTy6.setVisibility(View.GONE);
                                    skTy6.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("MKioskTy7", eElement_item).contains("Y")|getTagValue("MKioskTy7", eElement_item).contains("y")){
                                        MKioskTy7.setText(" : O");
                                    }else {
                                        MKioskTy7.setText(" : X");
                                    }

                                }catch (NullPointerException e){
                                    MKioskTy7.setText("");
                                    MKioskTy7.setVisibility(View.GONE);
                                    skTy7.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("MKioskTy8", eElement_item).contains("Y")|getTagValue("MKioskTy8", eElement_item).contains("y")){
                                        MKioskTy8.setText(" : O");
                                    }else {
                                        MKioskTy8.setText(" : X");
                                    }

                                }catch (NullPointerException e){
                                    MKioskTy8.setText("");
                                    MKioskTy8.setVisibility(View.GONE);
                                    skTy8.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("MKioskTy9", eElement_item).contains("Y")|getTagValue("MKioskTy9", eElement_item).contains("y")){
                                        MKioskTy9.setText(" : O");
                                    }else {
                                        MKioskTy9.setText(" : X");
                                    }

                                }catch (NullPointerException e){
                                    MKioskTy9.setText("");
                                    MKioskTy9.setVisibility(View.GONE);
                                    skTy9.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("MKioskTy10", eElement_item).contains("Y")|getTagValue("MKioskTy10", eElement_item).contains("y")){
                                        MKioskTy10.setText(" : O");
                                    }else {
                                        MKioskTy10.setText(" : X");
                                    }


                                }catch (NullPointerException e){
                                    MKioskTy10.setText("");
                                    MKioskTy10.setVisibility(View.GONE);
                                    skTy10.setVisibility(View.GONE);
                                }
                            }
                        }
                        if (nList_item.getLength() != 0) {
                            if (nNode_body.getNodeType() == Node.ELEMENT_NODE) {
                                try {
                                    if (getTagValue("MKioskTy11", eElement_item).contains("Y")|getTagValue("MKioskTy11", eElement_item).contains("y")){
                                        MKioskTy11.setText(" : O");
                                    }else {
                                        MKioskTy11.setText(" : X");
                                    }

                                }catch (NullPointerException e){
                                    MKioskTy11.setText("");
                                    MKioskTy11.setVisibility(View.GONE);
                                    skTy11.setVisibility(View.GONE);
                                }
                            }
                        }

                        if (MKioskTy1.length()==0&&MKioskTy2.length()==0&&MKioskTy3.length()==0&&MKioskTy4.length()==0&&MKioskTy5.length()==0&&MKioskTy6.length()==0
                                &&MKioskTy7.length()==0&&MKioskTy8.length()==0&&MKioskTy9.length()==0&&MKioskTy10.length()==0&&MKioskTy11.length()==0){
                            MKioskTy_title.setVisibility(View.GONE);
                        }













































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
                }catch (NullPointerException nullPointerException){

            }

        }
        }).start();

    }

    public  String getTagValue(String tag, Element eElement){ //xml에서 파싱할 데이터 찾기

            nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
            nValue = (Node) nlList.item(0);
            if (nValue == null)
                return null;

        return nValue.getNodeValue();
    }


}