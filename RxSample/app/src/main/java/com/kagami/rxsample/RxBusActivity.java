package com.kagami.rxsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.kagami.rxsample.rx.RxBus;

import rx.Subscription;
import rx.functions.Action1;

public class RxBusActivity extends AppCompatActivity {

    private Subscription subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);
        subscription= RxBus.getDefault().register(String.class, new Action1<String>() {
            @Override
            public void call(String s) {
                TextView textView=(TextView)findViewById(R.id.textView);
                textView.setText(s);
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getDefault().post("message");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
