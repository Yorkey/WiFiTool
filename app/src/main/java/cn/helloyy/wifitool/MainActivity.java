package cn.helloyy.wifitool;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.helloyy.wifitool.controllers.AccessPointListController;
import cn.helloyy.wifitool.controllers.SimpleHomeController;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.controller_container)
    ViewGroup container;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Router router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new AccessPointListController()));
        }


        router.addChangeListener(new ControllerChangeHandler.ControllerChangeListener() {
            @Override
            public void onChangeStarted(@Nullable Controller to, @Nullable Controller from, boolean isPush, @NonNull ViewGroup container, @NonNull ControllerChangeHandler handler) {

            }

            @Override
            public void onChangeCompleted(@Nullable Controller to, @Nullable Controller from, boolean isPush, @NonNull ViewGroup container, @NonNull ControllerChangeHandler handler) {

                getSupportActionBar().setDisplayHomeAsUpEnabled(router.getBackstackSize() > 1);
                getSupportActionBar().setHomeButtonEnabled(router.getBackstackSize() > 1);
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //监听左上角的返回箭头
        if(item.getItemId()==android.R.id.home){
            router.handleBack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
