package com.example.userguidearrowview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "MainActivity";

    private ImageView ivFirst,ivSecond,ivThird;
    private RelativeLayout guideContainer;
    private ArrowRunnable runnable;

    private Handler guideHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (runnable == null) {
                runnable = new ArrowRunnable(1);
            } else {
                runnable.setPosition(1);
                runnable.setCount(0);
                this.removeCallbacks(runnable);
            }
            this.post(runnable);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivFirst = findViewById(R.id.iv_first);
        ivSecond = findViewById(R.id.iv_second);
        ivThird = findViewById(R.id.iv_third);
        guideContainer = findViewById(R.id.guide_container);
        guideContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guideHandler != null && runnable != null) {
                    guideHandler.removeCallbacks(runnable);
                }
                guideContainer.setVisibility(View.GONE);
            }
        });
    }



    public void onBtnClick(View view) {
        Message msg = guideHandler.obtainMessage();
        msg.what = 1;
        guideHandler.sendMessage(msg);
        guideContainer.setVisibility(View.VISIBLE);
    }

    class ArrowRunnable implements Runnable {

        private int position;
        private int count = 0;
        public ArrowRunnable(int position) {
            this.position = position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            count++;
            Log.i("maybe", "count: " + count);
            if (position == 1) {
                ivFirst.setVisibility(View.VISIBLE);
                ivSecond.setVisibility(View.GONE);
                ivThird.setVisibility(View.GONE);
                this.setPosition(2);
                guideHandler.postDelayed(this, 400);
            } else if (position == 2) {
                ivFirst.setVisibility(View.VISIBLE);
                ivSecond.setVisibility(View.VISIBLE);
                ivThird.setVisibility(View.GONE);
                this.setPosition(3);
                guideHandler.postDelayed(this, 400);
            } else if (position == 3) {
                ivFirst.setVisibility(View.VISIBLE);
                ivSecond.setVisibility(View.VISIBLE);
                ivThird.setVisibility(View.VISIBLE);
                this.setPosition(1);
                guideHandler.postDelayed(this, 400);
            }
            if (count == 50) {
                ivFirst.setVisibility(View.VISIBLE);
                ivSecond.setVisibility(View.VISIBLE);
                ivThird.setVisibility(View.VISIBLE);
                guideHandler.removeCallbacks(this);
            }
        }
    }
}