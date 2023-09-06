package oneplace.com.API;

import android.app.Activity;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import oneplace.com.Ob_Bus_Station_list;
import oneplace.com.Place_Bus_Intercity;

public class Bus_List_Mapping_API {
    Activity activity;
    NodeList nlList;
    Node nValue;

    StringBuilder urlBuilder ;
    URL url;
    HttpURLConnection httpURLConnection;
    BufferedReader rd;
    String line;
    StringBuilder sb;
    Document document;
    Node nNode_item ;
    Element eElement_item;
    Node nNode_item2 ;
    Element eElement_item2 ;
    int start,finall;
    NodeList nList_item,nList_item2;

    FirebaseDatabase database2;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;

    //각 열차가 도착하는 역 발췌 완료
    public void bus_station_Info(ArrayList<Ob_Bus_Station_list> arrayList)throws IOException {

        for (start =0; start <= arrayList.size(); start++) {
            for (finall = 0; finall < arrayList.size(); finall++) {

                try {
                    System.out.println(arrayList.size()+"\n");
                    System.out.println(start+"      "+finall+"\n");
                    System.out.println(start + "번째 출발지 : " + arrayList.get(start).getTerminalNm());
                    System.out.println("도착지 : " + arrayList.get(finall).getTerminalNm() + " " + finall + "번째 시작  " + start);

                    urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/ExpBusInfoService/getStrtpntAlocFndExpbusInfo"); /*URL*/

                     urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=eUOnBahe%2BDndmVjOuchJfBQS29NMywIHXZ4nyfxfWXUgZOKImkTM8ele3iWdA3BDcrXPiqhWar%2BVvjGvmwC8nA%3D%3D"); /*Service Key*/
                   // urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/ //은식
                    //  urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=xqtGro2RZ7GS64DxCIjdBJQt%2B9t0wgxfkVLY8s0I8BHSDYViUtMjayeRWpyr%2BZgS2FsiD%2BVGE5Cv4IcYRae1gA%3D%3D"); /*Service Key*/ //누나
                    urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
                    urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
                    urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*데이터 타입(xml, json)*/
                    urlBuilder.append("&" + URLEncoder.encode("depTerminalId", "UTF-8") + "=" + URLEncoder.encode(arrayList.get(start).getTerminalId(), "UTF-8")); /*출발터미널ID*/
                    urlBuilder.append("&" + URLEncoder.encode("arrTerminalId", "UTF-8") + "=" + URLEncoder.encode(arrayList.get(finall).getTerminalId(), "UTF-8")); /*도착터미널ID*/
                    urlBuilder.append("&" + URLEncoder.encode("depPlandTime", "UTF-8") + "=" + URLEncoder.encode("20230905", "UTF-8")); /*출발일(YYYYMMDD)*/
                    urlBuilder.append("&" + URLEncoder.encode("busGradeId", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*버스등급*/


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

                    if (nList_item.getLength() != 0) {
                        //버스 정류장 정보 계속 업데이트
                        nNode_item = nList_item.item(0);
                        eElement_item = (Element) nNode_item;
                        if (nNode_item.getNodeType() == Node.ELEMENT_NODE) {
                            database2 = FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
                            databaseReference2 = database2.getReference("-고속버스").child("-정류장매칭LIST");

//                                       //26부터 실행  0~25는 새로 해야함
                            HashMap<String, Object> value = new HashMap<>();
                            value.put("final_nodename",getTagValue("arrPlaceNm", eElement_item));
                            value.put("start_nodename",getTagValue("depPlaceNm", eElement_item));   //<- 기차처럼 적용하기 어레이리스트로 돌리니까 값이 엉떵한게 들어감
                            databaseReference2.child(arrayList.get(start).getTerminalNm()).child(getTagValue("arrPlaceNm", eElement_item)).setValue(value);

//                                       databaseReference2.child(arrayList.get(start).getTerminalNm()).child(arrayList.get(finall).getTerminalNm()).child("final_nodeid").setValue(arrayList.get(finall).getTerminalId());
//                                       databaseReference2.child(arrayList.get(start).getTerminalNm()).child(arrayList.get(finall).getTerminalNm()).child("final_nodename").setValue(arrayList.get(finall).getTerminalNm());
//                                       databaseReference2.child(arrayList.get(start).getTerminalNm()).child(arrayList.get(finall).getTerminalNm()).child("start_nodeid").setValue(arrayList.get(start).getTerminalId());
//                                       databaseReference2.child(arrayList.get(start).getTerminalNm()).child(arrayList.get(finall).getTerminalNm()).child("start_nodename").setValue(arrayList.get(start).getTerminalNm());

                            databaseReference3 = database2.getReference("-고속버스").child("-정류장LIST");
                            databaseReference3.child(arrayList.get(start).getTerminalNm()).child("station_use").setValue("운영중");
                           // Toast.makeText(Place_Bus_Intercity.this, start + " 완료", Toast.LENGTH_SHORT).show();
                        }
                    }

                    try {
                        //트래픽 초과로 인한 데이터 오류
                        nList_item2 = document.getElementsByTagName("cmmMsgHeader"); //xml에서 파싱할 리스트명
                        if (nList_item2 != null) {
                            nNode_item2 = nList_item2.item(0);
                            if (nNode_item2.getNodeType() == Node.ELEMENT_NODE) {
                                eElement_item2 = (Element) nNode_item2;
                                System.out.println(getTagValue("returnAuthMsg", eElement_item2) + "     " + arrayList.get(finall).getTerminalNm());

                                //onDestroy(); //트래픽초과로 데이터를 받지 못하면 로그를 남기고 앱 완전 종료
                                return;
                            }

                        }
                    } catch (NullPointerException nullPointerException) {

                    }
                    System.out.println("도착지 : " + arrayList.get(finall).getTerminalNm() + " " + finall + "번째 완료완료" + "\n\n");



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
                }catch (IndexOutOfBoundsException e){

                }
            }
        }



    }


    public  String getTagValue(String tag, Element eElement){ //xml에서 파싱할 데이터 찾기

        nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;

        return nValue.getNodeValue();
    }


}
