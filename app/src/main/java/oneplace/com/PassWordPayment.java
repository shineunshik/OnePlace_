package oneplace.com;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.bootpay.android.constants.BootpayBuildConfig;
import kr.co.bootpay.android.events.BootpayEventListener;
import kr.co.bootpay.android.models.BootExtra;
import kr.co.bootpay.android.models.BootItem;
import kr.co.bootpay.android.models.BootUser;
import kr.co.bootpay.android.models.Payload;
import kr.co.bootpay.bio.BootpayBio;
import kr.co.bootpay.bio.models.BioExtra;
import kr.co.bootpay.bio.models.BioPayload;
import kr.co.bootpay.bio.models.BioPrice;
import kr.co.bootpay.model.request.UserToken;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.response.data.TokenData;
import oneplace.com.deprecated.BootpayRest;
import oneplace.com.deprecated.BootpayRestImplement;
import oneplace.com.deprecated.EasyPayUserTokenData;


//import kr.co.bootpay.android.Bootpay;

public class PassWordPayment extends AppCompatActivity implements BootpayRestImplement {

    @Deprecated
    String restApplicationId = "64d8e2b600c78a001a677629"; //production
    @Deprecated
    String privateKey = "Rq38exiXmXLqr9n2Rk9myST+E5ztfsIM4CAFfN27p+s="; //production
    String applicationId = "64d8e2b600c78a001a677627";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_payment);

        if(BootpayBuildConfig.DEBUG) {
            restApplicationId = "64d8e2b600c78a001a677629";
            privateKey = "Rq38exiXmXLqr9n2Rk9myST+E5ztfsIM4CAFfN27p+s=";
            applicationId = "64d8e2b600c78a001a677627";
        }

    }

    public void PaymentTest(View v) {

        BootpayRest.getRestToken(this, this, restApplicationId, privateKey);
    }

    public BootUser getBootUser() {
        String userId = "0108260319712"; //판매자가 관리하는 user의 고유 Id (빌링키 발급으로 비밀번호 자동결제를 위해 인식)

        BootUser user = new BootUser();  //구매자 토큰 발급
        user.setId(userId); //회원번호를 서버에 전달하면 서버에서 구매자 토큰을 발급해줌
        user.setArea("부산");
        user.setGender(1); //1: 남자, 0: 여자
        user.setEmail("dmstlrgkxm@naver.com");
        user.setPhone("010-4152-3197");
        user.setBirth("1989-03-24");
        user.setUsername("신은식");
        return user;
    }


    void BootpayTest(String userToken) {
        BootUser user = new BootUser(); // 구매자 정보

        BioExtra extra = new BioExtra()
                .setCardQuota("0,2,3")// 일시불, 2개월, 3개월 할부 허용, 할부는 최대 12개월까지 사용됨 (5만원 이상 구매시 할부허용 범위)
                .setOpenType("iframe")
                .setDisplaySuccessResult(true) //결제 완료 페이지
                .setDisplayErrorResult(true); //결제 에러 페이지


//        List items = new ArrayList<>();
//        BootItem item1 = new BootItem().setName("마우's 스").setId("ITEM_CODE_MOUSE").setQty(1).setPrice(500d);
//        BootItem item2 = new BootItem().setName("키보드").setId("ITEM_KEYBOARD_MOUSE").setQty(1).setPrice(500d);
//        items.add(item1);
//        items.add(item2);

        double  a=1000.0,//상품가격
                b=-500.0,//쿠폰할인
                c=+100.0,//배송비
                d=a+b+c; //결제금액

        String orderid ="123";
        String ordername ="건식 마사지";
        String ordertype ="(90분)";

        BioPayload payload = new BioPayload();
        payload.setApplicationId(applicationId)
                .setOrderName("부트페이 결제테스트")
                .setPg("나이스페이먼츠")
                .setOrderId(orderid)
                .setOrderName(ordername)
                .setUserToken(userToken) //구매자 토큰(결제 완료시 만료됨)
                .setPrice(d) //총 결제 금액
                .setUser(user)
                .setExtra(extra)
               // .setItems(items)
                .setNames(Arrays.asList(ordertype)) //결제창에 나타날 상품목록
                .setPrices(Arrays.asList(
                        new BioPrice("상품가격", a),  //결제창에 나타날 가격목록
                        new BioPrice("쿠폰적용", b),
                        new BioPrice("배송비", c)));

//        Map map = new HashMap<>();
//        map.put("1", "abcdef");
//        map.put("2", "abcdef55");
//        map.put("3", 1234);
//        payload.setMetadata(map);

        BootpayBio.init(getApplicationContext())
                .setBioPayload(payload)
                .setEventListener(new BootpayEventListener() {
                    @Override
                    public void onCancel(String data) {
                        Log.d("bootpay", "cancel: " + data);
                    }

                    @Override
                    public void onError(String data) {
                        Log.d("bootpay", "error: " + data);


                        //Toast.makeText(PassWordPayment.this,"유저 토큰 : "+userToken,Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onClose() {
                        BootpayBio.removePaymentWindow();
                    }

                    @Override
                    public void onIssued(String data) {
                        Log.d("bootpay", "issued: " +data);
                    }

                    @Override
                    public boolean onConfirm(String data) {
                        Log.d("bootpay", "confirm: " + data);
//                        Bootpay.transactionConfirm(data); //재고가 있어서 결제를 진행하려 할때 true (방법 1)
                        return true; //재고가 있어서 결제를 진행하려 할때 true (방법 2)
//                        return false; //결제를 진행하지 않을때 false
                    }

                    @Override
                    public void onDone(String data) {
                        Log.d("done", data);
                        Toast.makeText(PassWordPayment.this,"결제 완료",Toast.LENGTH_SHORT).show();


                        try {
                            JSONObject jsonObject = new JSONObject(data); //json  형식 파싱 (간단한 key-value만 있는 경우)
                            String receipt_id = jsonObject.getString("receipt_id"); //영수증 ID(빌링키 조회를 위한)
                            String order_id = jsonObject.getString("order_id"); //주문ID
                            String price = jsonObject.getString("price"); //거래 가격
                            String order_name = jsonObject.getString("order_name"); //상품명
                            String company_name = jsonObject.getString("company_name"); //판매자
                            String pg = jsonObject.getString("pg"); //PG사명
                            String method = jsonObject.getString("method"); //결제를 완료한 결제 수단
                            String purchased_at = jsonObject.getString("purchased_at"); //결제 완료 시간
                            String requested_at = jsonObject.getString("requested_at"); //결제 요청 시간
                            String status_locale = jsonObject.getString("status_locale"); //결제 상태
                            String status = jsonObject.getString("status"); //결제 상태값
                            String receipt_url = jsonObject.getString("receipt_url"); //부트페이에서 제공하는 영수증

                            JSONObject jsonObject_list = jsonObject.getJSONObject("card_data"); //하위 항목
                            String tid = jsonObject_list.getString("tid"); //카드 정보 PG tid
                            String card_approve_no = jsonObject_list.getString("card_approve_no"); //카드 승인 번호
                            String card_no = jsonObject_list.getString("card_no"); //카드 번호
                            String card_quota = jsonObject_list.getString("card_quota"); //할부
                            String card_company_code = jsonObject_list.getString("card_company_code"); //카드회사의 코드번호
                            String card_company = jsonObject_list.getString("card_company"); //카드회사명
                            String card_type = jsonObject_list.getString("card_type"); //카드종류

                           // Toast.makeText(PassWordPayment.this,"비밀번호 자동 결제 완료  "+company_name+card_no,Toast.LENGTH_SHORT).show();

                        }
                        catch (JSONException e){
                            throw new RuntimeException(e);
                        }

                    }
                }).requestPassword();
    }


    @Override
    public void callbackRestToken(TokenData token) {
        BootpayRest.getEasyPayUserToken(this, this, token.access_token, getBootUser());
    }

    @Override
    public void callbackEasyPayUserToken(EasyPayUserTokenData userToken) {
        BootpayTest(userToken.user_token);
    }
}