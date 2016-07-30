package ua.net.tokar.liqpay;

public class LiqPayFormData {
    private final String data;
    private final String signature;
    private final String liqPayPostUrl;

    public LiqPayFormData(
            String data,
            String signature,
            String liqPayPostUrl
    ) {
        this.data = data;
        this.signature = signature;
        this.liqPayPostUrl = liqPayPostUrl;
    }

    public String getData() {
        return data;
    }

    public String getSignature() {
        return signature;
    }

    public String getLiqPayUrl() {
        return liqPayPostUrl;
    }
}