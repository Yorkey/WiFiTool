package cn.helloyy.wifitool.controllers;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
import cn.helloyy.wifitool.App;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.base.BaseController;
import cn.helloyy.wifitool.model.WifiProxy;
import cn.helloyy.wifitool.model.WifiProxyDao;
import cn.helloyy.wifitool.util.Utils;
import cn.helloyy.wifitool.viewholders.ProxyListViewHolder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangyu on 2016/11/27.
 */

public class ProxyListOverlayController extends BaseController {


    @Bind(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    @Bind(R.id.blank)
    View blankView;

    @Bind(R.id.close)
    View closeView;

    private RecyclerArrayAdapter adapter;

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_proxy_list_overlay, container, false);
    }


    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        int padding =(int) Utils.convertDpToPixel(15, getApplicationContext());
        DividerDecoration decoration = new DividerDecoration(R.color.listDivider, 1, padding, padding);
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
                EventBus.getDefault().post(new AccessPointDetailController.ProxySelectEvent(wifiProxy));
            }
        });

        this.loadProxyList();

    }

    @OnClick({R.id.blank, R.id.close})
    public void OnCloseClick(View view) {
        getRouter().popCurrentController();
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
