package ua.net.tokar.liqpay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Base64;

/**
 * @link https://www.liqpay.com/ru/doc
 */
public class LiqPayClient {
    private final String publicKey;
    private final String privateKey;
    private final String callBackUrl;
    private final boolean sandbox;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public LiqPayClient(
            String publicKey,
            String privateKey,
            String callBackUrl,
            boolean sandbox
    ) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.callBackUrl = callBackUrl;
        this.sandbox = sandbox;
    }

    public LiqPayFormData getFormData( LiqPayPaymentData paymentData ) throws LiqPayException {
        LiqPayRequest request = prepareRequest( paymentData );

        try {
            String requestJson = MAPPER.writeValueAsString( request );
            String data = Base64.getEncoder().encodeToString( requestJson.getBytes() );

            return new LiqPayFormData(
                    data,
                    Base64.getEncoder().encodeToString(
                            DigestUtils.sha1( privateKey + data + privateKey )
                    ),
                    "https://www.liqpay.ua/api/" + LiqPayRequest.LATEST_VERSION + "/checkout"
            );
        } catch ( JsonProcessingException e ) {
            throw new LiqPayException( "Couldn't process provided request", e );
        }
    }

    private LiqPayRequest prepareRequest( LiqPayPaymentData paymentData ) throws LiqPayException {
        if ( paymentData.getAmount() == null ) {
            throw new LiqPayException( "Amount must be set" );
        }
        if ( paymentData.getCurrency() == null ) {
            throw new LiqPayException( "Currency must be set" );
        }
        if ( paymentData.getOrderId() == null ) {
            throw new LiqPayException( "OrderId must be set" );
        }
        LiqPayRequest request = new LiqPayRequest();

        request.setPublicKey( publicKey );
        request.setServerUrl( callBackUrl );
        request.setSandbox( sandbox );

        request.setOrderId( paymentData.getOrderId() );
        request.setAmount( paymentData.getAmount() );
        request.setCurrency( paymentData.getCurrency() );

        request.setResultUrl( paymentData.getReturnUrl() );
        request.setProductUrl( paymentData.getProductUrl() );
        request.setDescription( paymentData.getDescription() );

        return request;
    }

    public LiqPayResponse read( String data ) throws LiqPayException {
        try {
            return MAPPER.readValue(
                    Base64.getDecoder().decode( data ),
                    LiqPayResponse.class
            );
        } catch ( Exception e ) {
            throw new LiqPayException( "Couldn't read data", e );
        }
    }

    private String getSignature( String data ) {
        return Base64.getEncoder().encodeToString(
                DigestUtils.sha1( privateKey + data + privateKey )
        );
    }

    public boolean isValidSignature( String data, String signature ) {
        return signature.equals( getSignature( data ) );
    }
}
