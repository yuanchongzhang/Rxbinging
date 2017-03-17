package com.zhangyu.rxbinging;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/17.
 */
public class SearchActivity extends Activity {


    private EditText editText;
    private ListView listView;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editText = (EditText) findViewById(R.id.search_item);
        listView = (ListView) findViewById(R.id.list_view);

        for (int i = 0; i < 40; i++) {
            list.add("哈哈" + i);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
        adapter.addAll(list);

        listView.setAdapter(adapter);


        RxTextView.textChanges(editText).debounce(600, TimeUnit.MILLISECONDS).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Exception {
                String key = charSequence.toString();
                return key;
            }
        }).observeOn(Schedulers.io())
                .map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(String s) throws Exception {


                        List<String> dataList = new ArrayList<String>();
                        if (!TextUtils.isEmpty(editText.getText().toString().trim())) {

                            for (int i = 0; i < list.size(); i++) {
                                if (!list.get(i).isEmpty()) {
                                    if (list.get(i).contains(editText.getText().toString().trim())) {
                                        dataList.add(list.get(i));
                                    }
                                }
                            }
                        }
                        return dataList;

                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RecordingObserver<List<String>>() {
                    @Override
                    public void onNext(List<String> strings) {
                        super.onNext(strings);
                        adapter.clear();
                        adapter.addAll(strings);
                        adapter.notifyDataSetChanged();
                    }

                });


    }

    public void getData() {

    }


}
