package cn.helloyy.wifitool.ui.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import cn.helloyy.infinite.base.BaseActivity;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.ui.fragments.ProxyAndPacListFragment;

/**
 * Created by wangyu on 2016/9/28.
 */

public class ProxyAndPacListActivity extends BaseActivity {

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        ProxyAndPacListFragment fragment = new ProxyAndPacListFragment();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.fragment_container, fragment)
                .commit();

    }

    @Override
    public void initData() {

    }
}
