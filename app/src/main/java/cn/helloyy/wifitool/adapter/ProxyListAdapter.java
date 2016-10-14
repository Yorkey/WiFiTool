package cn.helloyy.wifitool.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.helloyy.infinite.base.BaseListAdapter;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.model.WifiProxy;
import cn.helloyy.wifitool.ui.viewholders.ProxyListViewHolder;

/**
 * Created by wangyu on 2016/9/28.
 */

public class ProxyListAdapter extends BaseListAdapter<ProxyListViewHolder, WifiProxy> {

    @Override
    public ProxyListViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_proxy, parent, false);
        return new ProxyListViewHolder(view, null);
    }

    @Override
    public void onBindItemViewHolder(ProxyListViewHolder holder, int position) {

        WifiProxy wifiProxy = getItemData(position);
        holder.getProxyHost().setText(wifiProxy.getHost());
        holder.getProxyPort().setText(String.valueOf(wifiProxy.getPort()));
        holder.getProxyPassBy().setText("used: "+wifiProxy.getUsed());
    }
}
