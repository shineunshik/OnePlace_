package oneplace.com.deprecated;

import android.util.Log;

import java.io.IOException;

import kr.co.bootpay.android.models.BootUser;
import kr.co.bootpay.model.response.data.TokenData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Deprecated
public class DemoApiPresenter {
    DemoApiService service;
    BootpayRestImplement parent;
//    EasyPayUserTokenData easyPayUserToken;
//    TokenData token;

    public DemoApiPresenter(DemoApiService service) {
        this(service, null);
    }

    public DemoApiPresenter(DemoApiService service, BootpayRestImplement parent) {
        this.service = service;
        if(parent != null) {
            this.parent = parent;
        }
    }


    public void getRestToken(String restApplicationId, String privateKey) {
        final DemoApiPresenter parentScope = this;

        Call<TokenData> dataCall = service.getApi().getRestToken(restApplicationId, privateKey);
        dataCall.enqueue(new Callback<TokenData>() {
            @Override
            public void onResponse(Call<TokenData> call, Response<TokenData> response) {


                try {
                    if(!response.isSuccessful()) {
                        Log.d("bootpay error", response.errorBody().string());
                        return;
                    }

                    if(parentScope.parent != null) {
                        parentScope.parent.callbackRestToken(response.body());

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TokenData> call, Throwable t) {

            }
        });
    }

    public void getEasyPayUserToken(String restToken, BootUser user) {
        final DemoApiPresenter parentScope = this;

        Call<EasyPayUserTokenData> dataCall = service.getApi().getEasyPayUserToken(
                "Bearer " + restToken,
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getGender(),
                user.getBirth(),
                user.getPhone()
        );
        dataCall.enqueue(new Callback<EasyPayUserTokenData>() {
            @Override
            public void onResponse(Call<EasyPayUserTokenData> call, Response<EasyPayUserTokenData> response) {
                try {
                    if(!response.isSuccessful()) {
                        Log.d("bootpay error", response.errorBody().string());
                        return;
                    }


                    if (parentScope.parent != null) {
                        parentScope.parent.callbackEasyPayUserToken(response.body());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<EasyPayUserTokenData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

//        service.getApi().getEasyPayUserToken(
//                "Bearer " + restToken,
//                user.getId(),
//                user.getEmail(),
//                user.getUsername(),
//                user.getGender(),
//                user.getBirth(),
//                user.getPhone()
//        ).retry(3)
//                .subscribeOn(Schedulers.from(Executors.newCachedThreadPool()))
//                .subscribe(
//                        new Observer<EasyPayUserTokenData>() {
//                            @Override
//                            public void onComplete() {
//                                if(parentScope.parent != null && parentScope.easyPayUserToken != null) {
//                                    parentScope.parent.callbackEasyPayUserToken(easyPayUserToken);
//                                    parentScope.easyPayUserToken = null;
//                                }
//                            }
//
//                            @Override
//                            public void onSubscribe(Disposable d) {
//                            }
//
//                            @Override
//                            public void onNext(EasyPayUserTokenData res) {
//                                if(parentScope.parent != null) {
//                                    parentScope.easyPayUserToken = res;
//                                }
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                e.printStackTrace();
//                            }
//                        }
//                );
//
//    }

}
