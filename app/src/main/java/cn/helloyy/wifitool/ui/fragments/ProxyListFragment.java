package cn.helloyy.wifitool.ui.fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import cn.helloyy.infinite.base.BaseListAdapter;
import cn.helloyy.infinite.base.BaseListFragment;
import cn.helloyy.wifitool.App;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.adapter.ProxyListAdapter;
import cn.helloyy.wifitool.model.WifiProxy;
import cn.helloyy.wifitool.model.WifiProxyDao;
import cn.helloyy.wifitool.ui.activities.CommonContainerActivity;
import cn.helloyy.wifitool.ui.viewholders.ProxyListViewHolder;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by wangyu on 2016/9/29.
 */

public class ProxyListFragment extends BaseListFragment<ProxyListViewHolder, WifiProxy> {

//    @Bind(R.id.add_proxy)
//    Button addProxyBtn;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_proxy_list;
    }

    @Override
    public void initView(View view) {
        super.initView(view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getContext(), ProxyDetailActivity.class);
                Intent intent = new Intent(getContext(), CommonContainerActivity.class);
                intent.putExtra(CommonContainerActivity.CONTAINER_FRAGMENT_CLASS, "cn.helloyy.wifitool.ui.fragments.ProxyDetailFragment");
                intent.putExtra(CommonContainerActivity.CONTAINER_FRAGMENT_TITLE, "ProxyDetail");
                startActivity(intent);
            }
        });

//        RxView.clicks(addProxyBtn)
//                .throttleFirst(2, TimeUnit.SECONDS)
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        Toast.makeText(getContext(), "Test", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    @Override
    protected BaseListAdapter<ProxyListViewHolder, WifiProxy> getListAdapter() {
        return new ProxyListAdapter();
    }


    @Override
    public Observable<List<WifiProxy>> getDataObservable() {
        WifiProxyDao wifiProxyDao = App.getInstance().getDaoSession().getWifiProxyDao();
        return wifiProxyDao.rx().loadAll();
    }

    @Override
    public void onResume() {
        super.onResume();

        onRefresh();
    }
}
