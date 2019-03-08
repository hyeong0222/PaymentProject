package com.example.paymentproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.simplify.android.sdk.Card;
import com.simplify.android.sdk.CardEditor;
import com.simplify.android.sdk.CardToken;
import com.simplify.android.sdk.Simplify;

public class MainActivity extends AppCompatActivity {

    Simplify simplify;
    EditText cardNumberEditText, monthEditText, yearEditText, cvvEditText, addressEditText;
    CardEditor cardEditor;
    Button submit, submit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cardNumberEditText = (EditText) findViewById(R.id.editTextCardNumber);
        monthEditText = (EditText) findViewById(R.id.editTextMonth);
        yearEditText = (EditText) findViewById(R.id.editTextYear);
        cvvEditText = (EditText) findViewById(R.id.editTextYear);
        addressEditText = (EditText) findViewById(R.id.editTextAddress);
        cardEditor = (CardEditor) findViewById(R.id.card_editor);
        submit = (Button) findViewById(R.id.buttonSubmit);
        submit2 = (Button) findViewById(R.id.buttonSubmit2);

        simplify = new Simplify();
        simplify.setApiKey("sbpb_OGJmN2I3ZTgtZTMxMi00Y2ViLThmYjQtZDBlZjVmOTY1MjRi");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = new Card().setNumber(cardNumberEditText.getText().toString())
                        .setExpMonth(monthEditText.getText().toString())
                        .setExpYear(yearEditText.getText().toString())
                        .setCvc(cvvEditText.getText().toString())
                        .setAddressCity(addressEditText.getText().toString());

                simplify.createCardToken(card, new CardToken.Callback() {
                    @Override
                    public void onSuccess(CardToken cardToken) {
                        Log.d("Card Activity", cardToken.getId());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("Card Activity", throwable.getMessage());
                    }
                });
            }
        });

        cardEditor.addOnStateChangedListener(new CardEditor.OnStateChangedListener() {
            @Override
            public void onStateChange(CardEditor cardEditor) {
                submit2.setEnabled(cardEditor.isValid());
            }
        });

        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simplify.createCardToken(cardEditor.getCard(), new CardToken.Callback() {
                    @Override
                    public void onSuccess(CardToken cardToken) {
                        Log.d("Card Activity 2", cardToken.getId());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("Card Activity 2", throwable.getMessage());
                    }
                });
            }
        });

    }
}

