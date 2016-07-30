package ua.net.tokar.liqpay;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @link https://www.liqpay.com/ru/doc/callback
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class LiqPayResponse {
    public enum Status {
        // Конечные статусы платежа
        success( Type.FINAL ), // успешный платеж
        failure( Type.FINAL ), // неуспешный платеж
        error( Type.FINAL ),   // Неуспешный платеж. Некорректно заполнены данные
        subscribed( Type.FINAL ), // Подписка успешно оформлена
        unsubscribed( Type.FINAL ), // Подписка успешно деактивирована
        reversed( Type.FINAL ), // Платеж возвращен
        sandbox( Type.FINAL ), // тестовый платеж

        // Cтатусы требующие подтверждения платежа
        otp_verify( Type.VERIFY ), //Требуется OTP подтверждение клиента. OTP пароль отправлен на номер телефона Клиента. Для завершения платежа, требуется выполнить otp_verify.
        // 3ds_verify( Type.VERIFY ), // Требуется 3DS верификация. Для завершения платежа, требуется выполнить 3ds_verify.
        cvv_verify( Type.VERIFY ), // Требуется ввод CVV карты отправителя. Заполните параметр card_cvv и повторите запрос.
        sender_verify( Type.VERIFY ), // Требуется ввод данных отправителя. Заполните параметры sender_first_name, sender_last_name, sender_country_code, sender_city, sender_address, sender_postal_code и повторите запрос.
        receiver_verify( Type.VERIFY ), // Требуется ввод данных получателя. Заполните параметры receiver_first_name, receiver_last_name и повторите запрос.

        // Cтатусы ожидающие обработку платежа
        wait_bitcoin( Type.WAIT ), // Ожидается перевод bitcoin от клиента
        wait_secure( Type.WAIT ), // платеж на проверке
        wait_accept( Type.WAIT ), // Деньги с клиента списаны, но магазин еще не прошел проверку
        wait_lc( Type.WAIT ), // Аккредитив. Деньги с клиента списаны, ожидается подтверждение доставки товара
        hold_wait( Type.WAIT ), // сумма успешно заблокирована на счету отправителя
        cash_wait( Type.WAIT ), // Ожидается оплата наличными в ТСО.
        wait_qr( Type.WAIT ), // Ожидается сканировани QR-кода клиентом.
        wait_sender( Type.WAIT ), // Ожидается подтверждение оплаты клиентом в приложении Privat24/Sender.
        processing( Type.WAIT ) // Платеж обрабатывается
        ;

        private enum Type {
            FINAL,
            VERIFY,
            WAIT
        }

        public Type type;

        Status( Type type ) {
            this.type = type;
        }

        public Type getType() {
            return type;
        }
    }

    private Long payment_id;
    private String err_code;
    private String err_description;
    private Status status;
    private String sender_phone;
    private String order_id;
    private String public_key;
    private Float amount;
    private LiqPayRequest.Currency currency;

    public Long getPaymentId() {
        return payment_id;
    }

    public String getErrСode() {
        return err_code;
    }

    public String getErrDescription() {
        return err_description;
    }

    public Status getStatus() {
        return status;
    }

    public String getSenderPhone() {
        return sender_phone;
    }

    public String getOrderId() {
        return order_id;
    }

    public String getPublicKey() {
        return public_key;
    }

    public Float getAmount() {
        return amount;
    }

    public LiqPayRequest.Currency getCurrency() {
        return currency;
    }
}