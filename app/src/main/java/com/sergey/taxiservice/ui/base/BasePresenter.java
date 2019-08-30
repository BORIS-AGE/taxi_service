package com.sergey.taxiservice.ui.base;

public abstract class BasePresenter<V extends BaseView> {

    private V view;

    public void onCreateView(V view) {
        this.view = view;
    }

    public V getView() {
        return view;
    }
}
