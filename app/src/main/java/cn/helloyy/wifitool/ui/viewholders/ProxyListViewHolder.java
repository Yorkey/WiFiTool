package cn.helloyy.wifitool.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import cn.helloyy.infinite.base.BaseViewHolder;
import cn.helloyy.infinite.interf.RvItemClickListener;
import cn.helloyy.wifitool.R;

/**
 * Created by wangyu on 2016/9/28.
 */

public class ProxyListViewHolder extends BaseViewHolder {

    @Bind(R.id.proxyHost)
    TextView proxyHost;

    @Bind(R.id.proxyPort)
    TextView proxyPort;

    @Bind(R.id.proxyPassBy)
    TextView proxyPassBy;

    public ProxyListViewHolder(View itemView, RvItemClickListener listener) {
        super(itemView, listener);
    }

    public TextView getProxyHost() {
        return proxyHost;
    }

    public TextView getProxyPort() {
        return proxyPort;
    }

    public TextView getProxyPassBy() {
        return proxyPassBy;
    }
}
