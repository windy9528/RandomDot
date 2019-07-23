package com.randomdot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.randomdot.widget.RandomDot;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RandomDot randomDot;
    private Button addDot;
    private Button clearDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {


    }

    /**
     * 初始化控件
     */
    private void initView() {

        randomDot = (RandomDot) findViewById(R.id.randomDot);
        addDot = (Button) findViewById(R.id.add_dot);
        clearDot = (Button) findViewById(R.id.clear_dot);

        addDot.setOnClickListener(this);
        clearDot.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_dot://添加随机圆点
                randomDot.addRandDot();
                break;
            case R.id.clear_dot://删除矩形框内的圆点
                randomDot.clearCircle();
                break;
        }
    }
}
