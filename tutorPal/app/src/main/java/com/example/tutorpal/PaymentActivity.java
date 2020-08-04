package com.example.tutorpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.cardform.utils.CardType;
import com.braintreepayments.cardform.view.CardForm;
import com.braintreepayments.cardform.view.SupportedCardTypesView;

public class PaymentActivity extends AppCompatActivity {

    protected CardForm cardForm;

    private static final CardType[] SUPPORTED_CARD_TYPES = { CardType.VISA, CardType.MASTERCARD, CardType.DISCOVER,
            CardType.AMEX };

    private SupportedCardTypesView mSupportedCardTypesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final Button pay = (Button) findViewById(R.id.pay);

        final Button log_out = (Button) findViewById(R.id.log_out2);

//        https://github.com/braintree/android-card-form/blob/master/README.md
        cardForm = (CardForm) findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .maskCardNumber(true)
                .maskCvv(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .saveCardCheckBoxChecked(true)
                .saveCardCheckBoxVisible(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .setup(this);

        mSupportedCardTypesView = findViewById(R.id.supported_card_types);
        mSupportedCardTypesView.setSupportedCardTypes(SUPPORTED_CARD_TYPES);

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });
    }
}