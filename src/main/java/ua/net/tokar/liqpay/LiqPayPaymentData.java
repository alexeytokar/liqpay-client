package ua.net.tokar.liqpay;

public class LiqPayPaymentData {
    private String orderId;
    private String description;
    private Float amount;
    private LiqPayRequest.Currency currency;
    private String productUrl;
    private String returnUrl;

    public LiqPayPaymentData() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId( String orderId ) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount( float amount ) {
        this.amount = amount;
    }

    public LiqPayRequest.Currency getCurrency() {
        return currency;
    }

    public void setCurrency( LiqPayRequest.Currency currency ) {
        this.currency = currency;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl( String productUrl ) {
        this.productUrl = productUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl( String returnUrl ) {
        this.returnUrl = returnUrl;
    }
}