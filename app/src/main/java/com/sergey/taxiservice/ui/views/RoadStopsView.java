package com.sergey.taxiservice.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sergey.taxiservice.R;

public class RoadStopsView extends View {

    private Paint textPainter;
    private Paint linePainter;
    private Paint ringPainter;
    private Paint circlePainter;

    private int textSize = 30;
    private int strokeWidth = 3;
    private int circleRadius = 20;

    private int textColor = Color.BLACK;
    private int strokeColor = Color.BLACK;

    private String[] addressesOfStops;

    public RoadStopsView(Context context) {
        super(context);
        initAttributes(context, null);
        init();
    }

    public RoadStopsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
        init();
    }

    public RoadStopsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
        init();
    }

    public void setAddressesOfStops(String[] addressesOfStops) {
        this.addressesOfStops = addressesOfStops;
        requestLayout();
    }

    private void initAttributes(Context context, @Nullable AttributeSet attrs) {
        if(attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoadStopsView);
            try {
                this.textColor = typedArray.getColor(R.styleable.RoadStopsView_textColor, Color.BLACK);
                this.strokeColor = typedArray.getColor(R.styleable.RoadStopsView_strokeColor, Color.BLACK);

            } finally {
                typedArray.recycle();
            }
        }
    }

    private void init() {
        linePainter = new Paint();
        linePainter.setColor(strokeColor);
        linePainter.setStrokeWidth(strokeWidth);

        ringPainter = new Paint();
        ringPainter.setColor(strokeColor);
        ringPainter.setStrokeWidth(strokeWidth);
        ringPainter.setStyle(Paint.Style.STROKE);
        ringPainter.setAntiAlias(true);

        circlePainter = new Paint();
        circlePainter.setColor(strokeColor);
        circlePainter.setStyle(Paint.Style.FILL);

        textPainter = new Paint();
        textPainter.setColor(textColor);
        textPainter.setStyle(Paint.Style.FILL);
        textPainter.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 500;
        int desiredHeight = 1000;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;

        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);

        } else {
            width = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;

        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);

        } else {
            height = (circleRadius * 3) + (int) (circleRadius * 4.5f * addressesOfStops.length);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = circleRadius * 3;
        float y = circleRadius * 3;

        if(addressesOfStops != null && addressesOfStops.length > 1) {
            for(int i = 0; i < addressesOfStops.length; i++) {
                canvas.drawText(addressesOfStops[i], x + circleRadius * 2, y + (textSize / 3), textPainter);

                if(i != addressesOfStops.length - 1) {
                    canvas.drawCircle(x, y, circleRadius, ringPainter);
                    canvas.drawLine(x, y + circleRadius, x, y + circleRadius * 3.5f, linePainter);

                } else {
                    canvas.drawCircle(x, y, circleRadius, circlePainter);
                }

                y += circleRadius * 4.5f;
            }
        }
    }
}
