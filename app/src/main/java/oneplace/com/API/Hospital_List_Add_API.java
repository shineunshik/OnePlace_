package oneplace.com.API;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Hospital_List_Add_API {

    NodeList nlList;
    Node nValue;
    String num;
    FirebaseDatabase database;
    DatabaseReference databaseReference;


    public void Hospital_Input()throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    StringBuilder urlBuilder_total = new StringBuilder("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytListInfoInqire"); /*URL*/
                    StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytListInfoInqire"); /*URL*/
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

                    urlBuilder_total.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/ //은식
//            urlBuilder_total.append("&" + URLEncoder.encode("Q0","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*주소(시도)*/
//            urlBuilder_total.append("&" + URLEncoder.encode("Q1","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*주소(시군구)*/
//            urlBuilder_total.append("&" + URLEncoder.encode("QT","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*월~일요일(1~7), 공휴일(8)*/
//            urlBuilder_total.append("&" + URLEncoder.encode("QZ","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*CODE_MST의'H000' 참조(A~H, J~O, Q)*/
//            urlBuilder_total.append("&" + URLEncoder.encode("QD","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*CODE_MST의'D000' 참조(D000~D029)*/
//            urlBuilder_total.append("&" + URLEncoder.encode("QN","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*기관명*/
//            urlBuilder_total.append("&" + URLEncoder.encode("ORD","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*순서*/
                    urlBuilder_total.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
                    urlBuilder_total.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*목록 건수*/


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
                    System.out.println("\n\n\n\n\n"+num+"\n\n\n\n\n");


                    urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/ //은식
//            urlBuilder.append("&" + URLEncoder.encode("Q0","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*주소(시도)*/
//            urlBuilder.append("&" + URLEncoder.encode("Q1","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*주소(시군구)*/
//            urlBuilder.append("&" + URLEncoder.encode("QT","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*월~일요일(1~7), 공휴일(8)*/
//            urlBuilder.append("&" + URLEncoder.encode("QZ","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*CODE_MST의'H000' 참조(A~H, J~O, Q)*/
//            urlBuilder.append("&" + URLEncoder.encode("QD","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*CODE_MST의'D000' 참조(D000~D029)*/
//            urlBuilder.append("&" + URLEncoder.encode("QN","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*기관명*/
//            urlBuilder.append("&" + URLEncoder.encode("ORD","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*순서*/
                    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
                    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(num, "UTF-8")); /*목록 건수*/

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

                            //  Toast.makeText(Place_Hospital.this,num+"",Toast.LENGTH_SHORT).show();
                    NodeList nList_item = document.getElementsByTagName("item"); //xml에서 파싱할 리스트명
                    //버스 정류장 정보 계속 업데이트
                    for (int temp = 0; temp < nList_item.getLength(); temp++) {
                        Node nNode_item = nList_item.item(temp);
                        if (nNode_item.getNodeType() == Node.ELEMENT_NODE) {
                            //log 확인 작업
                            Element eElement_item = (Element) nNode_item;

                            database= FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
                            databaseReference=database.getReference("-병원").child("-응급실LIST");

                            HashMap<String, Object> value = new HashMap<>();
                            value.put("dutyAddr",getTagValue("dutyAddr", eElement_item));
                            value.put("dutyEmcls",getTagValue("dutyEmcls", eElement_item));
                            value.put("dutyEmclsName",getTagValue("dutyEmclsName", eElement_item));
                            value.put("dutyName",getTagValue("dutyName", eElement_item));
                            value.put("dutyTel1",getTagValue("dutyTel1", eElement_item));
                            value.put("dutyTel3",getTagValue("dutyTel3", eElement_item));
                            value.put("hpid",getTagValue("hpid", eElement_item));
                            value.put("phpid",getTagValue("phpid", eElement_item));
                            value.put("rnum",getTagValue("rnum", eElement_item));
                            value.put("wgs84Lat",getTagValue("wgs84Lat", eElement_item));
                            value.put("wgs84Lon",getTagValue("wgs84Lon", eElement_item));
                            databaseReference.child(getTagValue("hpid", eElement_item)).setValue(value);

//                            databaseReference.child(getTagValue("hpid", eElement_item)).child("dutyAddr").setValue(getTagValue("dutyAddr", eElement_item));
//                            databaseReference.child(getTagValue("hpid", eElement_item)).child("dutyEmcls").setValue(getTagValue("dutyEmcls", eElement_item));
//                            databaseReference.child(getTagValue("hpid", eElement_item)).child("dutyEmclsName").setValue(getTagValue("dutyEmclsName", eElement_item));
//                            databaseReference.child(getTagValue("hpid", eElement_item)).child("dutyName").setValue(getTagValue("dutyName", eElement_item));
//                            databaseReference.child(getTagValue("hpid", eElement_item)).child("dutyTel1").setValue(getTagValue("dutyTel1", eElement_item));
//                            databaseReference.child(getTagValue("hpid", eElement_item)).child("dutyTel3").setValue(getTagValue("dutyTel3", eElement_item));
//                            databaseReference.child(getTagValue("hpid", eElement_item)).child("hpid").setValue(getTagValue("hpid", eElement_item));
//                            databaseReference.child(getTagValue("hpid", eElement_item)).child("phpid").setValue(getTagValue("phpid", eElement_item));
//                            databaseReference.child(getTagValue("hpid", eElement_item)).child("rnum").setValue(getTagValue("rnum", eElement_item));
//                            databaseReference.child(getTagValue("hpid", eElement_item)).child("wgs84Lat").setValue(getTagValue("wgs84Lat", eElement_item));
//                            databaseReference.child(getTagValue("hpid", eElement_item)).child("wgs84Lon").setValue(getTagValue("wgs84Lon", eElement_item));

                        }
                    }
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
