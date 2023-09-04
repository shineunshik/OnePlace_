package oneplace.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.bootpay.android.Bootpay;
import kr.co.bootpay.android.events.BootpayEventListener;
import kr.co.bootpay.android.models.BootExtra;
import kr.co.bootpay.android.models.BootItem;
import kr.co.bootpay.android.models.BootUser;
import kr.co.bootpay.android.models.Payload;

public class DefaultPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_payment);

    }



    public void PaymentTest(View v) {

        BootUser user = new BootUser()
                .setPhone("010-1234-5678")
                .setUsername("신은식")
                .setBirth("890324")
                .setAddr("경기도 성남시 성남동 1404번지"); // 구매자 정보

        //  user = new BootUser().setUsername("신은식"); //이렇게 받아올수있음

        BootExtra extra = new BootExtra() //결제 옵션
                .setCardQuota("0,2,3")// 일시불, 2개월, 3개월 할부 허용, 할부는 최대 12개월까지 사용됨 (5만원 이상 구매시 할부허용 범위)
                .setOpenType("iframe")
                .setDisplaySuccessResult(true) //결제 완료 페이지
                .setDisplayErrorResult(true); //결제 에러 페이지


        List<BootItem> items = new ArrayList<>(); //상품의 정보
        BootItem item1 = new BootItem()
                .setName("마우's 스")
                .setId("ITEM_CODE_MOUSE")
                .setQty(1)
                .setPrice(5000d);
        BootItem item2 = new BootItem()
                .setName("키보드")
                .setId("ITEM_KEYBOARD_MOUSE")
                .setQty(1)
                .setPrice(5000d);
        items.add(item1);
        items.add(item2);
        //item이 합산되어 결제 진행 됨

        Payload payload = new Payload();
        payload.setApplicationId("64d8e2b600c78a001a677627")
                .setOrderName("부트페이 결제테스트")
                .setPg("다날")
                .setMethod("카드수기")
                .setOrderId("1234")
                .setPrice(10000d)
                .setUser(user)
                .setExtra(extra)
                .setItems(items);

        Map<String, Object> map = new HashMap<>();
        map.put("1", "abcdef");
        map.put("2", "abcdef55");
        map.put("3", 1234);
        payload.setMetadata(map);

        //  payload.setMetadata(new Gson().toJson(map));



        Bootpay.init(getSupportFragmentManager(), getApplicationContext())
                .setPayload(payload)
                .setEventListener(new BootpayEventListener() {
                    @Override
                    public void onCancel(String data) {
                        Log.d("bootpay", "cancel: " + data);
                        Toast.makeText(DefaultPayment.this,"결제 취소",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String data) {
                        Log.d("bootpay", "error: " + data);
//                        Toast.makeText(DefaultPayment.this,"결제 에러(잔액 부족)",Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject = new JSONObject(data); //json  형식 파싱 (간단한 key-value만 있는 경우)

                            String event = jsonObject.getString("event"); //결제 결과
                            String message = jsonObject.getString("message");
                            String error_code = jsonObject.getString("error_code");

                            Toast.makeText(DefaultPayment.this,message,Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }

                    @Override
                    public void onClose() {
                        Bootpay.removePaymentWindow();
                        Toast.makeText(DefaultPayment.this,"결제 창 닫기",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onIssued(String data) {
                        Log.d("bootpay", "issued: " +data);
                    }

                    @Override
                    public boolean onConfirm(String data) {
                        Log.d("bootpay", "confirm: " + data);
//                        Bootpay.transactionConfirm(data); //재고가 있어서 결제를 진행하려 할때 true (방법 1)

                        try {
                            JSONObject jsonObject = new JSONObject(data); //json  형식 파싱 (간단한 key-value만 있는 경우)

                            String event = jsonObject.getString("event"); //결제 결과
                            String order_id = jsonObject.getString("order_id");
                            String receipt_id = jsonObject.getString("receipt_id"); //해당 결제 건의 고유 영수증 ID

                            Toast.makeText(DefaultPayment.this,receipt_id,Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        return true; //재고가 있어서 결제를 진행하려 할때 true (방법 2)
//                        return false; //결제를 진행하지 않을때 false


                    }

                    @Override
                    public void onDone(String data) {
                        Log.d("done", data);
                        // Toast.makeText(DefaultPayment.this,"결제 완료",Toast.LENGTH_SHORT).show();

                        //user.getUsername() DB에 결제 정보 업데이트 후
                        //실시간으로 DB정보를 받아와서 결제확인하기


                        try {
                            JSONObject jsonObject = new JSONObject(data); //json  형식 파싱 (간단한 key-value만 있는 경우)
                            String event = jsonObject.getString("event"); //결제 결과

                            JSONObject jsonObject_list = jsonObject.getJSONObject("data"); //json  형식 파싱 (하위에 여러 오브젝트가 있는 경우) 하위
                            String receipt_id = jsonObject_list.getString("receipt_id"); //영수증 ID
                            String order_id = jsonObject_list.getString("order_id"); //주문 ID
                            String purchased_at = jsonObject_list.getString("purchased_at"); //결제 완료 시간
                            String status = jsonObject_list.getString("status"); //결제 상태값
                            String status_locale = jsonObject_list.getString("status_locale"); //결제 메세지(결제 완료 등등)
                            String receipt_url = jsonObject_list.getString("receipt_url"); //부트페이에서 제공하는 영수증 URL

                            Toast.makeText(DefaultPayment.this,"결제 완료 메세지 : "+status_locale,Toast.LENGTH_SHORT).show();


                            JSONObject jsonObject_list_end = jsonObject_list.getJSONObject("card_data"); //하위에 하위 (결제 카드정보)
                            String card_approve_no = jsonObject_list_end.getString("card_approve_no"); //카드 승인번호
                            String card_no = jsonObject_list_end.getString("card_no"); //가운데 번호가 마스킹된 카드 번호
                            String card_company = jsonObject_list_end.getString("card_company"); //카드회사
                            String card_quota = jsonObject_list_end.getString("card_quota"); //할부
                            String card_company_code = jsonObject_list_end.getString("card_company_code"); //pg사에서 정의한 카드사 식별 코드

                            Toast.makeText(DefaultPayment.this,"카드회사 : "+card_company,Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }





                    }

                }).requestPayment();
    }
}