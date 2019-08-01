package com.example.constraint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ScrollView svData;
    ConstraintLayout clData;
    EditText et1;
    Button bt1;
    TextView tv3;
    int spaceHeight;
    int marginInPx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getWindow().getCurrentFocus() != null) getWindow().getCurrentFocus().clearFocus();

        svData = findViewById(R.id.sv_data);
        clData = findViewById(R.id.cl_data);
        et1 = findViewById(R.id.et_1);
        bt1 = findViewById(R.id.btn_1);
        tv3 = findViewById(R.id.tv_3);
        marginInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, getResources().getDisplayMetrics());

        svData.post(new Runnable() {
            @Override
            public void run() {
                tv3.post(new Runnable() {
                    @Override
                    public void run() {
                        et1.post(new Runnable() {
                            @Override
                            public void run() {
                                bt1.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        spaceHeight = tv3.getBottom() - et1.getBottom();
                                        lol();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private void lol() {
        clData.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                clData.getWindowVisibleDisplayFrame(r);
                int screenHeight = clData.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(clData);
                if (keypadHeight > screenHeight * 0.15) {
                    constraintSet.setVerticalBias(R.id.btn_1, 0.0f);
                    constraintSet.connect(R.id.tv_3, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID,
                            ConstraintSet.BOTTOM, spaceHeight + marginInPx);
                    constraintSet.applyTo(clData);
                    bt1.post(new Runnable() {
                        @Override
                        public void run() {
                            svData.smoothScrollTo(0, spaceHeight + marginInPx);
                        }
                    });
                } else {
                    constraintSet.setVerticalBias(R.id.btn_1, 0.5f);
                    constraintSet.connect(R.id.tv_3, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, marginInPx);
                    constraintSet.applyTo(clData);
                    bt1.post(new Runnable() {
                        @Override
                        public void run() {
                            svData.smoothScrollTo(0, 0);
                        }
                    });
                }
            }
        });
    }
}
