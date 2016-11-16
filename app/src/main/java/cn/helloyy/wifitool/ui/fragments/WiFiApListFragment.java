package cn.helloyy.wifitool.ui.fragments;

import android.content.Intent;
import android.view.View;

import java.lang.reflect.Type;
import java.util.List;

import be.shouldit.proxy.lib.WiFiApConfig;
import cn.helloyy.infinite.base.BaseListAdapter;
import cn.helloyy.infinite.base.BaseListFragment;
import cn.helloyy.infinite.interf.RvItemClickListener;
import cn.helloyy.wifitool.App;
import cn.helloyy.wifitool.adapter.WiFiApListAdapter;
import cn.helloyy.wifitool.ui.activities.CommonContainerActivity;
import cn.helloyy.wifitool.ui.viewholders.WiFiApListViewHolder;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static cn.helloyy.wifitool.App.getWifiNetworksManager;

/**
 * Created by wangyu on 2016/9/21.
 */
public class WiFiApListFragment extends BaseListFragment<WiFiApListViewHolder, WiFiApConfig> {

    @Override
    public void initView(View view) {
        super.initView(view);

        setOnItemClickListener(new RvItemClickListener() {
            @Override
            public void OnItemClick(View itemView, int position) {
                WiFiApConfig wifiApConfig = recyclerAdapter.getItemData(position);
                Intent intent = new Intent(getContext(), CommonContainerActivity.class);
                intent.putExtra(CommonContainerActivity.CONTAINER_FRAGMENT_CLASS, "cn.helloyy.wifitool.ui.fragments.WifiApDetailFragment");
                intent.putExtra(CommonContainerActivity.CONTAINER_FRAGMENT_TITLE, "WifiApDetail");
                intent.putExtra("WIFI_AP_CONFIG", wifiApConfig);
                startActivity(intent);
            }
        });
    }

    @Override
    protected BaseListAdapter<WiFiApListViewHolder, WiFiApConfig> getListAdapter() {
        return new WiFiApListAdapter();
    }


    @Override
    public Observable<List<WiFiApConfig>> getDataObservable() {
        Observable<List<WiFiApConfig>> wifiApObservable = App.getWifiNetworksManager().getSortedWifiApConfigsList2();
        return  wifiApObservable.subscribeOn(Schedulers.io());
    }
}
