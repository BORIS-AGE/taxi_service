package com.sergey.taxiservice.ui.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.models.UserModel;
import com.sergey.taxiservice.util.ImageLoader;

public class LocationView {

    public static void generateLocationView(Context context, UserModel userModel, onBitmapReadyListener listener) {
        if (context != null) {
            LayoutInflater inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            if(inflater != null) {
                View locationView = inflater.inflate(R.layout.view_location, null);
                ImageView background = locationView.findViewById(R.id.background);
                ImageView userAva = locationView.findViewById(R.id.avatar);

                ImageLoader.OnImageLoadedListener callback = () -> listener.onBitmapReady(createDrawableFromView(context, locationView));

                if (userModel != null) {
                    switch (userModel.getGender()) {
                        case 0:
                            background.setBackgroundResource(R.drawable.ic_location_male);
                            break;

                        case 1:
                            background.setBackgroundResource(R.drawable.ic_location_female);
                            break;
                    }

                    if(userModel.getAvatar() != null && !userModel.getAvatar().isEmpty()) {
                        ImageLoader.load(userModel.getAvatar(), userAva, R.drawable.default_avatar, callback);
                    } else ImageLoader.load(R.drawable.default_avatar, userAva, callback);

                } else {
                    listener.onBitmapReady(createDrawableFromView(context, locationView));
                }
            }
        }
    }

    private static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public interface onBitmapReadyListener {

        void onBitmapReady(Bitmap bitmap);
    }
}
