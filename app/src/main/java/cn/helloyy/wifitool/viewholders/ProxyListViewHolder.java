package cn.helloyy.wifitool.viewholders;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.model.WifiProxy;

/**
 * Created by wangyu on 2016/11/27.
 */

public class ProxyListViewHolder extends BaseViewHolder<WifiProxy> {

    private TextView proxyHost;

    private TextView proxyPort;

    private TextView proxyPassBy;

    public ProxyListViewHolder(ViewGroup parent) {
        super(parent, R.layout.list_item_proxy);

        proxyHost = $(R.id.proxyHost);
        proxyPort = $(R.id.proxyPort);
        proxyPassBy = $(R.id.proxyPassBy);
    }

    @Override
    public void setData(WifiProxy wifiProxy) {
        super.setData(wifiProxy);

        proxyHost.setText(wifiProxy.getHost());
        proxyPort.setText(String.valueOf(wifiProxy.getPort()));
    }
}
