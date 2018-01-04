package cn.dzws.android.scrollrespondUtils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import cn.dzws.android.scrollrespondUtils.utils.DIntent;

public class MainActivity extends AppCompatActivity {
    public Button tv;
    public RecyclerView recyclerView;
    public String TAG = getClass().getSimpleName();
    public RelativeLayout rlBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void recyclerView(View view) {
        DIntent.toRecyclerView(MainActivity.this);
    }

    public void scrollView(View view) {
        DIntent.toScrollView(MainActivity.this);
    }
}
