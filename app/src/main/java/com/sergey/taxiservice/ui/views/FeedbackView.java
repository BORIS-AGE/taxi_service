package com.sergey.taxiservice.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.sergey.taxiservice.R;

public class FeedbackView extends LinearLayout {

    private FeedbackParams params;
    private OnFeedbackSelectedListener listener;

    private AppCompatTextView title;
    private LinearLayout gradeContainer;

    public FeedbackView(Context context) {
        super(context);
        init();
    }

    public FeedbackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FeedbackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setFeedbackParams(FeedbackParams params) {
        this.params = params;
        initialize();
    }

    public void setListener(OnFeedbackSelectedListener listener) {
        this.listener = listener;
    }

    private void init() {
        inflate(getContext(), R.layout.view_feedback, this);
        setOrientation(LinearLayout.VERTICAL);

        this.gradeContainer = findViewById(R.id.gradeContainer);
        this.title = findViewById(R.id.title);

        initialize();
    }

    private void initialize() {
        if(params != null) {
            title.setText(params.getTitle());
            title.setTextColor(params.getColor());

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for(int i = 0; i < params.getMaxGrade(); i++) {
                View view = inflater.inflate(R.layout.view_star, null);
                ToggleButton button = view.findViewById(R.id.image);
                button.setBackground(getResources().getDrawable(params.getDrawable()));
                button.setClickable(false);

                if(i <= params.getInitialGrade() - 1) {
                    button.setChecked(true);
                }

                setOnClickListener(button, i);
                gradeContainer.addView(view);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnClickListener(ToggleButton button, int i) {
        button.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                params.initialGrade = i + 1;
                if(listener != null) {
                    listener.feedbackSelected(params.getTitle(), params.getInitialGrade());
                }

                for(int j = 0; j < gradeContainer.getChildCount(); j++) {
                    if(j <= params.getInitialGrade() - 1) {
                        ((ToggleButton) gradeContainer.getChildAt(j).findViewById(R.id.image)).setChecked(true);

                    } else {
                        ((ToggleButton) gradeContainer.getChildAt(j).findViewById(R.id.image)).setChecked(false);
                    }
                }
            }

            return false;
        });
    }

    public static class FeedbackParams {

        private int color;
        private int maxGrade;
        private int initialGrade;
        private int drawable;
        private String title;

        public FeedbackParams(int color, int maxGrade, int initialGrade, String title, int drawable) {
            this.color = color;
            this.maxGrade = maxGrade;
            this.initialGrade = initialGrade;
            this.title = title;
            this.drawable = drawable;
        }

        public int getColor() {
            return color;
        }

        public int getMaxGrade() {
            return maxGrade;
        }

        public int getInitialGrade() {
            return initialGrade;
        }

        public String getTitle() {
            return title;
        }

        public int getDrawable() {
            return drawable;
        }
    }
}
