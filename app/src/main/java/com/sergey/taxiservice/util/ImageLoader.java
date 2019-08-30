package com.sergey.taxiservice.util;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageLoader {

    private static final String BASE_URL = "https://taxi-1plus1.pp.ua/storage/profile/";

    public static void load(@DrawableRes int imageRes, ImageView imageView, OnImageLoadedListener listener) {
        Picasso.get()
                .load(imageRes)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if(listener != null) {
                            listener.onImageLoaded();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        if(listener != null) {
                            listener.onImageLoaded();
                        }
                    }
                });
    }

    public static void load(String path, ImageView imageView, @DrawableRes int errorRes, OnImageLoadedListener listener) {
        Picasso.get()
                .load(BASE_URL + path)
                .placeholder(errorRes)
                .error(errorRes)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if(listener != null) {
                            listener.onImageLoaded();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        if(listener != null) {
                            listener.onImageLoaded();
                        }
                    }
                });
    }

    public static void invalidateUrl(String path) {
        Picasso.get().invalidate(BASE_URL + path);
    }

    public interface OnImageLoadedListener {
        void onImageLoaded();
    }
}
