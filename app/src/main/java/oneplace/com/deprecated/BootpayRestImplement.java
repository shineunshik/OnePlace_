package oneplace.com.deprecated;

import kr.co.bootpay.model.response.data.TokenData;

@Deprecated
public interface BootpayRestImplement {
    @Deprecated
    void callbackRestToken(TokenData acessToken);

    @Deprecated
    void callbackEasyPayUserToken(EasyPayUserTokenData userToken);
}
