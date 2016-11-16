package cn.helloyy.wifitool.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.bluelinelabs.conductor.rxlifecycle.RxController;

import butterknife.ButterKnife;

/**
 * Created by wangyu on 2016/11/16.
 */

public abstract class BaseController extends RxController {

    private boolean hasExited;

    protected BaseController() { }

    protected BaseController(Bundle args) {
        super(args);
    }

    // Note: This is just a quick demo of how an ActionBar *can* be accessed, not necessarily how it *should*
    // be accessed. In a production app, this would use Dagger instead.
    protected ActionBar getActionBar() {
        AppCompatActivity  activity = (AppCompatActivity)getActivity();
        return activity != null ? activity.getSupportActionBar() : null;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        setTitle();
        super.onAttach(view);
    }

    protected void setTitle() {

        String title = getTitle();
        ActionBar actionBar = getActionBar();
        if (title != null && actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected String getTitle() {
        return null;
    }

    protected abstract View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container);

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflateView(inflater, container);
        ButterKnife.bind(this, view);
        onViewBound(view);
        return view;
    }

    protected void onViewBound(@NonNull View view) { }


    @Override
    protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        super.onChangeEnded(changeHandler, changeType);

        hasExited = !changeType.isEnter;
        if (isDestroyed()) {
            //DemoApplication.refWatcher.watch(this);
        }
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        ButterKnife.unbind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (hasExited) {
            //DemoApplication.refWatcher.watch(this);
        }
    }
}
