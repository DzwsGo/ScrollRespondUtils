package cn.dzws.android.scrollrespondUtils.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.dzws.android.scrollrespondUtils.MainAdapter;
import cn.dzws.android.scrollrespondUtils.R;
import cn.dzws.android.scrollrespondUtils.utils.ScrollRespondUtils;

/**
 * @author Lwang
 * @time 2018/1/4.
 */

public class RecyclerViewActivity extends AppCompatActivity {
    public Button tv;
    public RecyclerView recyclerView;
    public String TAG = getClass().getSimpleName();
    public RelativeLayout rlBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        final ScrollRespondUtils scrollRespondUtils = new ScrollRespondUtils(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        rlBottom = (RelativeLayout) findViewById(R.id.rl_bottom);
        tv = (Button) findViewById(R.id.tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MainAdapter());
        scrollRespondUtils.setRespondView(rlBottom);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG,"onScrolled dy : " + dy);
                scrollRespondUtils.start(dy);
            }
        });
    }
    public void helloWord(View v) {
        Toast.makeText(RecyclerViewActivity.this,"hello word",Toast.LENGTH_SHORT).show();
    }
}
