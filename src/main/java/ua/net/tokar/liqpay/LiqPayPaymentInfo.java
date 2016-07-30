package ua.net.tokar.liqpay;

public class LiqPayPaymentInfo {
    private String productUrl;
    private String returnUrl;

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