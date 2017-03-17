package com.zhangyu.rxbinging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private Button btn_first;

    private Button btn_second;

    private ListView list_view;
    List<String> list = new ArrayList<>();


    private CheckBox checkBox;

    private Button text_test;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_first = (Button) findViewById(R.id.btn_first);
        btn_second = (Button) findViewById(R.id.btn_second);
        list_view = (ListView) findViewById(R.id.list_view);
        checkBox= (CheckBox) findViewById(R.id.checkBox);
        text_test= (Button) findViewById(R.id.text_test);
        for (int i = 0; i < 40; i++) {
            list.add("呵呵"+i);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
        adapter.addAll(list);

        list_view.setAdapter(adapter);
        RxView.clicks(btn_first)
                .throttleFirst(2, TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .subscribe(new RecordingObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        super.onNext(o);
//                        Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();

          startActivity(new Intent(MainActivity.this,SearchActivity.class));

                    }
                });

        RxView.longClicks(btn_second)
                .subscribe(new RecordingObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        super.onNext(o);
                        Toast.makeText(MainActivity.this, "点击了2", Toast.LENGTH_SHORT).show();
                    }


                });


        RxAdapterView.itemClicks(list_view)
                .subscribe(new RecordingObserver<Integer>() {
                    @Override
                    public void onNext(Integer o) {
                        super.onNext(o);
                        Toast.makeText(MainActivity.this, "item click " + o , Toast.LENGTH_SHORT).show();
                    }
                });



        RxCompoundButton.checkedChanges(checkBox)
                .subscribe(new RecordingObserver<Boolean>() {
                                 @Override
                    public void onNext(Boolean aBoolean) {
                        super.onNext(aBoolean);
                        text_test.setEnabled( aBoolean );
                        text_test.setBackgroundResource( aBoolean ? R.color.colorPrimary :R.color.colorAccent  );
                    }
                }) ;

    }
}
