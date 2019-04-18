package com.jewel.libx.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jewel.libx.android.recyclerView.RecyclerViewUtil;
import com.jewel.libx.android.recyclerView.SimpleAdapter;
import com.jewel.libx.android.recyclerView.SimpleViewHolder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/4/17
 */
public class RecyclerViewSample extends AppCompatActivity {

    private SimpleAdapter<String> deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        RecyclerView recyclerView = findViewById(R.id.rv);
        deviceAdapter = new SimpleAdapter<String>(R.layout.adapter_item) {
            @Override
            public void bindView(SimpleViewHolder viewHolder, String data, int position) {
                viewHolder.setText(R.id.item, data);
            }
        };
        deviceAdapter.setItemClickListener((holder, device, position) ->
                Toast.makeText(this, device, Toast.LENGTH_SHORT).show());
        RecyclerViewUtil.setupListView(recyclerView, deviceAdapter, android.R.color.black);

        for (int i = 0; i < 10; i++) {
            String data = "数据 + " + i;
            deviceAdapter.addData(data);
        }
    }


    int headChanged = 0;
    public void addHeaderView(View view) {
        headChanged++;
        if(headChanged % 2 == 1) {
            View head = View.inflate(this, R.layout.adapter_item, null);
            TextView textView = head.findViewById(R.id.item);
            textView.setText("我是头部" + headChanged);
            deviceAdapter.addHeader(head);
        } else {
            deviceAdapter.removeHeader();
        }
    }

    int footChanged = 0;

    public void addFooterView(View view) {
        footChanged++;
        if(footChanged % 2 == 1) {
            View head = View.inflate(this, R.layout.adapter_item, null);
            TextView textView = head.findViewById(R.id.item);
            textView.setText("我是尾部" + footChanged);
            deviceAdapter.addFooter(head);
        } else {
            deviceAdapter.removeFooter();
        }
    }
}
