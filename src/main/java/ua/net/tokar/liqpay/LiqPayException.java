package ua.net.tokar.liqpay;

public class LiqPayException extends Exception {
    public LiqPayException( String message ) {
        super( message );
    }

    public LiqPayException( String message, Throwable cause ) {
        super( message, cause );
    }
}