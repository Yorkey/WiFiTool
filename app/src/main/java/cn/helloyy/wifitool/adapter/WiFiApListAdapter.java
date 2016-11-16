package cn.helloyy.wifitool.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.shouldit.proxy.lib.WiFiApConfig;
import be.shouldit.proxy.lib.enums.SecurityType;
import cn.helloyy.infinite.base.BaseListAdapter;
import cn.helloyy.infinite.interf.RvItemClickListener;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.ui.viewholders.WiFiApListViewHolder;

/**
 * Created by wangyu on 2016/9/21.
 */
public class WiFiApListAdapter extends BaseListAdapter<WiFiApListViewHolder, WiFiApConfig> {

    @Override
    public WiFiApListViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wifi_ap, parent, false);
        return new WiFiApListViewHolder(view, rvItemClickListener);
    }

    @Override
    public void onBindItemViewHolder(WiFiApListViewHolder holder, int position) {
        WiFiApConfig wiFiApConfig = getItemData(position);


        holder.getSignalView().setConfiguration(wiFiApConfig);
        holder.getSsidView().setText(wiFiApConfig.getSSID());
        holder.getProxyInfoView().setText(wiFiApConfig.getProxyStatusString());
    }
}
