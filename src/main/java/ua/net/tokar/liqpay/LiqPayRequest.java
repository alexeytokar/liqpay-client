package ua.net.tokar.liqpay;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @link https://www.liqpay.com/ru/doc/checkout
 */
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class LiqPayRequest {
    public static final int LATEST_VERSION = 3;

    public enum Currency {
        USD,
        EUR,
        RUB,
        UAH;
    }

    public enum Action {
        pay,
        hold,
        paysplit,
        subscribe,
        pay_donate;
    }
    private String public_key;
    private String server_url;

    private final int version = LATEST_VERSION;
    private final LiqPayRequest.Action action = LiqPayRequest.Action.pay;
    private float amount;
    private LiqPayRequest.Currency currency;
    private String description;
    private String order_id;
    private String sandbox;
    private String product_url;
    private String result_url;

    public LiqPayRequest() {

    }

    public void setPublicKey( String publicKey ) {
        public_key = publicKey;
    }

    public void setServerUrl( String serverUrl ) {
        server_url = serverUrl;
    }

    public void setAmount( float amount ) {
        this.amount = amount;
    }

    public void setCurrency( LiqPayRequest.Currency currency ) {
        this.currency = currency;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public void setOrderId( String orderId ) {
        order_id = orderId;
    }

    public void setSandbox( boolean sandbox ) {
        this.sandbox = sandbox ? "1" : null;
    }

    public void setProductUrl( String productUrl ) {
        product_url = productUrl;
    }

    public void setResultUrl( String resultUrl ) {
        result_url = resultUrl;
    }
}