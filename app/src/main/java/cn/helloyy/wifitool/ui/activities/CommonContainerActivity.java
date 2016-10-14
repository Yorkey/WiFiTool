package cn.helloyy.wifitool.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import cn.helloyy.infinite.base.BaseActivity;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.ui.fragments.ProxyDetailFragment;

import static cn.helloyy.wifitool.R.id.toolbar;

/**
 * Created by wangyu on 2016/10/11.
 */

public class CommonContainerActivity extends BaseActivity {

    public static String CONTAINER_FRAGMENT_CLASS = "containerFragmentClass";
    public static String CONTAINER_FRAGMENT_TITLE = "containerFragmentTitle";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_fragment_container;
    }

    @Override
    public void initView() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        String fragmentName = intent.getStringExtra(CONTAINER_FRAGMENT_CLASS);
        String title = intent.getStringExtra(CONTAINER_FRAGMENT_TITLE);

        setTitle(title);

        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            Class fragmentClass = Class.forName(fragmentName);
            Fragment fragment = (Fragment)fragmentClass.newInstance();
            fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.fragment_container, fragment)
                .commit();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initData() {

    }
}
