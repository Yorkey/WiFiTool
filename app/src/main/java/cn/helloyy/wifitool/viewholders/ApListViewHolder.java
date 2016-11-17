package cn.helloyy.wifitool.viewholders;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import be.shouldit.proxy.lib.WiFiApConfig;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.components.WifiSignal;

/**
 * Created by wangyu on 2016/11/17.
 */

public class ApListViewHolder extends BaseViewHolder<WiFiApConfig> {

    private WifiSignal signalView;

    private TextView ssidView;

    private TextView proxyInfoView;

    public ApListViewHolder(ViewGroup parent) {
        super(parent, R.layout.list_item_wifi_ap);
        signalView = $(R.id.wifi_ap_signal_icon);
        ssidView = $(R.id.ssid);
        proxyInfoView = $(R.id.proxyInfo);
    }

    @Override
    public void setData(WiFiApConfig wiFiApConfig) {
        super.setData(wiFiApConfig);

        signalView.setConfiguration(wiFiApConfig);
        ssidView.setText(wiFiApConfig.getSSID());
        proxyInfoView.setText(wiFiApConfig.getProxyStatusString());
    }
}
