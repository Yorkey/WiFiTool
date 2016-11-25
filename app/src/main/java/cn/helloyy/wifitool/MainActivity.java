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
import android.widget.Toast;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.helloyy.wifitool.controllers.AccessPointListController;
import cn.helloyy.wifitool.controllers.SimpleHomeController;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.controller_container)
    ViewGroup container;

    private Router router;

    private long lastBackPress = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new AccessPointListController()));
        }

    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            long now = new Date().getTime();
            if (now - lastBackPress > 2000) {
                lastBackPress = now;
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
            }
        }
    }

}
