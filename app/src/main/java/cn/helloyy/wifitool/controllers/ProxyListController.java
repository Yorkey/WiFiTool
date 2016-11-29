package cn.helloyy.wifitool.controllers;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import be.shouldit.proxy.lib.WiFiApConfig;
import butterknife.Bind;
import cn.helloyy.wifitool.App;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.base.BaseController;
import cn.helloyy.wifitool.base.ControllerWithToolbar;
import cn.helloyy.wifitool.model.WifiProxy;
import cn.helloyy.wifitool.model.WifiProxyDao;
import cn.helloyy.wifitool.viewholders.ApListViewHolder;
import cn.helloyy.wifitool.viewholders.ProxyListViewHolder;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangyu on 2016/11/27.
 */

public class ProxyListController extends ControllerWithToolbar {


    @Bind(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    private RecyclerArrayAdapter adapter;

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_proxy_list, container, false);
    }

    @Override
    protected String getTitle() {
        return "ProxyList";
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        this.showBackNavigator();

        Toolbar toolbar = this.getToolbar();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRouter().pushController(RouterTransaction.with(new ProxyEditController())
                        .pushChangeHandler(new HorizontalChangeHandler())
                        .popChangeHandler(new HorizontalChangeHandler()));
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        DividerDecoration decoration = new DividerDecoration(Color.GRAY, 1, 15, 15);
        recyclerView.addItemDecoration(decoration);

        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter(view.getContext()) {

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ProxyListViewHolder(parent);
            }
        });

        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getRouter().popCurrentController();
                WifiProxy wifiProxy = (WifiProxy)adapter.getItem(position);
                EventBus.getDefault().post(new AccessPointDetail.ProxySelectEvent(wifiProxy));
            }
        });

        this.loadProxyList();

    }

    public void loadProxyList() {
        WifiProxyDao wifiProxyDao = App.getInstance().getDaoSession().getWifiProxyDao();
        wifiProxyDao.rx().loadAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<WifiProxy>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<WifiProxy> wifiProxies) {
                        adapter.clear();
                        adapter.addAll(wifiProxies);
                    }
                });
    }
}
