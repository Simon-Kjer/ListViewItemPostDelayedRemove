package message.kjer.simon.com.cn.newmessagelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listview_list)
    Button listviewList;
    @BindView(R.id.recyclerview_list)
    Button recyclerviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }


    @OnClick({R.id.listview_list, R.id.recyclerview_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.listview_list:
                startActivity(new Intent(MainActivity.this, ListViewActivity.class));
                break;
            case R.id.recyclerview_list:
                startActivity(new Intent(MainActivity.this, RecyclerviewActivity.class));
                break;
        }
    }
}
