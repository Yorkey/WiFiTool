package cn.helloyy.wifitool.ui.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import cn.helloyy.infinite.base.BaseViewHolder;
import cn.helloyy.infinite.interf.RvItemClickListener;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.ui.components.WifiSignal;

/**
 * Created by wangyu on 2016/9/21.
 */
public class WiFiApListViewHolder extends BaseViewHolder {

    @Bind(R.id.wifi_ap_signal_icon)
    WifiSignal signalView;

    @Bind(R.id.ssid)
    TextView ssidView;

    @Bind(R.id.proxyInfo)
    TextView proxyInfoView;

    public WiFiApListViewHolder(View itemView, RvItemClickListener listener) {
        super(itemView, listener);
    }

    public TextView getProxyInfoView() {
        return proxyInfoView;
    }

    public void setProxyInfoView(TextView proxyInfoView) {
        this.proxyInfoView = proxyInfoView;
    }

    public WifiSignal getSignalView() {
        return signalView;
    }

    public void setSignalView(WifiSignal signalView) {
        this.signalView = signalView;
    }

    public TextView getSsidView() {
        return ssidView;
    }

    public void setSsidView(TextView ssidView) {
        this.ssidView = ssidView;
    }
}
