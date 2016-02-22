package com.example.administrator.imgcomposition;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 涂鸦
 * Created by Administrator on 2015-8-25.
 */
public class ImgScrawlActivity extends Activity implements View.OnClickListener{
    private Button backBtn;
    private Button saveBtn;
    private Button clearBtn;

    private HandWrite handwriteview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_scrawl_activity);

        initView();
    }

    private void initView() {
        backBtn = (Button)findViewById(R.id.backBtn);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        clearBtn = (Button)findViewById(R.id.clearBtn);
        backBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);

        handwriteview = (HandWrite)findViewById(R.id.handwriteview);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backBtn:
                ImgScrawlActivity.this.finish();
                break;
            case R.id.saveBtn:
                break;
            case R.id.clearBtn:
                handwriteview.clear();
                break;
        }
    }
}
