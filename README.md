[![Release](https://jitpack.io/v/alexeytokar/liqpay-client.svg)](https://jitpack.io/#alexeytokar/liqpay-client)

[HowTo add this dependency to your project](https://jitpack.io/#alexeytokar/liqpay-client)

# liqpay-client
java client for liqpay integration

# Sample usage
@RestController
@RequestMapping( "/v1/liq-pay/" )
public class LiqPayController {
    private static final Logger log = LoggerFactory.getLogger( LiqPayController.class );

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Value("${liqpay.private_key}")
    private String liqPayPrivateKey;

    @Value("${liqpay.public_key}")
    private String liqPayPublicKey;

    @Value("${liqpay.debug:false}")
    private boolean liqPayIsDebug;
    
    private LiqPayClient liqPayClient = new LiqPayClient(
            liqPayPublicKey,
            liqPayPrivateKey,
            "http://<your_controller_callback_url>/v1/liq-pay/callback",
            liqPayIsDebug
    );

    @RequestMapping( value = "/formData", method = RequestMethod.POST )
    public LiqPayFormData getPaymentData(
            @RequestParam String orderId,
            @RequestBody LiqPayPaymentInfo paymentInfo
    ) {
        Order order = orderService.find( orderId );
        Cart cart = order.getCart();

        Money totalToPay = cartService.getPrice( cart, CurrencyUnit.of( "UAH" ) );

        LiqPayPaymentData paymentData = new LiqPayPaymentData();
        paymentData.setProductUrl( paymentInfo.getProductUrl() );
        paymentData.setReturnUrl( paymentInfo.getReturnUrl() );
        paymentData.setOrderId( order.getId() );
        paymentData.setAmount( totalToPay.getAmount().floatValue() );
        paymentData.setDescription( "Some description of the items" );
        paymentData.setCurrency(
                LiqPayRequest.Currency.valueOf(
                        totalToPay.getCurrencyUnit().getCode()
                )
        );

        try {
            return liqPayClient.getFormData( paymentData );
        } catch ( LiqPayException e ) {}
    }

    @RequestMapping( value = "/callback", consumes = "application/x-www-form-urlencoded", method = RequestMethod.POST )
    public void callback(
            @RequestParam String data,
            @RequestParam String signature
    ) throws IOException {
        if ( !liqPayClient.isValidSignature( data, signature ) ) {
            // ... process incorrect signature
        }

        try {
            LiqPayResponse resp = liqPayClient.read( data );
            Order order = orderService.find( resp.getOrderId() );

            // ... process your order
            
        } catch ( LiqPayException e ) {}
    }
}
