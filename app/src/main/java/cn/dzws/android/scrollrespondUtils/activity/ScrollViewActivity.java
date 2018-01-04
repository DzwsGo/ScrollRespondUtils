package cn.dzws.android.scrollrespondUtils.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import cn.dzws.android.scrollrespondUtils.R;
import cn.dzws.android.scrollrespondUtils.utils.ScrollRespondUtils;

/**
 * @author Lwang
 * @time 2018/1/4.
 */

public class ScrollViewActivity extends AppCompatActivity {

    private RelativeLayout rlBottom;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);
        final ScrollRespondUtils scrollRespondUtils = new ScrollRespondUtils(this);
        ScrollView scrollView = (ScrollView) findViewById(R.id.sr);
        rlBottom = (RelativeLayout) findViewById(R.id.rl_bottom);
        btn = (Button) findViewById(R.id.tv);
        scrollRespondUtils.setRespondView(rlBottom);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                scrollRespondUtils.start(view,i,i1,i2,i3);
            }
        });
    }

    public void helloWord(View v) {
        Toast.makeText(ScrollViewActivity.this,"hello word",Toast.LENGTH_SHORT).show();
    }
}
