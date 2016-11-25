package cn.helloyy.wifitool.controllers;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.RouterTransaction;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.List;

import be.shouldit.proxy.lib.WiFiApConfig;
import butterknife.Bind;
import cn.helloyy.wifitool.App;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.base.BaseController;
import cn.helloyy.wifitool.viewholders.ApListViewHolder;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangyu on 2016/11/17.
 */

public class AccessPointListController extends BaseController implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    private RecyclerArrayAdapter adapter;

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_ap_list, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        toolbar.setTitle("APList");

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        DividerDecoration decoration = new DividerDecoration(Color.GRAY, 1, 15, 15);
        recyclerView.addItemDecoration(decoration);

        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter(view.getContext()) {

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ApListViewHolder(parent);
            }
        });

        recyclerView.setRefreshListener(this);

        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                WiFiApConfig wifiApConfig = (WiFiApConfig)adapter.getItem(position);
                getRouter().pushController(RouterTransaction.with(new AccessPointDetail(wifiApConfig)));
            }
        });

        this.loadApList();
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
    }

    @Override
    public void onRefresh() {
        this.loadApList();
    }


    public void loadApList() {
        Observable<List<WiFiApConfig>> wifiApObservable = App.getWifiNetworksManager().getSortedWifiApConfigsList2();
        wifiApObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<WiFiApConfig>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        recyclerView.showError();
                    }

                    @Override
                    public void onNext(List<WiFiApConfig> wiFiApConfigs) {
                        adapter.clear();
                        adapter.addAll(wiFiApConfigs);
                    }
                });
    }


}
