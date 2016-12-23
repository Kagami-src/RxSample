package com.kagami.rxsample;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class FromActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dosomething();
            }
        });
    }
    private void dosomething(){
        List<Task> tasks=new ArrayList<>();
        tasks.add(new Task(500,R.id.r));
        tasks.add(new Task(1000,R.id.b));
        tasks.add(new Task(1500,R.id.g));
        Observable.from(tasks)
                .observeOn(Schedulers.newThread())
                .map(new Func1<Task, View>() {
                    @Override
                    public View call(Task task) {
                        try {
                            Thread.sleep(task.cost);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return findViewById(task.viewId);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<View>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(FromActivity.this,"onCompleted",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(View view) {
                            view.setVisibility(View.VISIBLE);
                    }
                });
    }

    class Task{
        public final long cost;
        public final int viewId;
        public Task(long cost,@IdRes int rid){
            this.cost=cost;
            viewId=rid;
        }
    }
}
