package cn.helloyy.wifitool.ui.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import be.shouldit.proxy.lib.WiFiApConfig;
import be.shouldit.proxy.lib.reflection.android.ProxySetting;
import butterknife.Bind;
import cn.helloyy.infinite.base.BaseFragment;
import cn.helloyy.wifitool.R;

/**
 * Created by wangyu on 2016/10/11.
 */

public class WifiApDetailFragment extends BaseFragment {

    @Bind(R.id.proxySwitch)
    Switch proxySwitch;

    @Bind(R.id.proxyDetail)
    View proxyDetail;

    @Bind(R.id.hostEdit)
    EditText hostEdit;

    @Bind(R.id.portEdit)
    EditText portEdit;

    @Bind(R.id.selectProxy)
    Button selectProxy;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wifi_ap_detail;
    }

    @Override
    public void initView(View view) {
        Intent intent = getActivity().getIntent();
        WiFiApConfig wifiApConfig = (WiFiApConfig)intent.getParcelableExtra("WIFI_AP_CONFIG");

        proxySwitch.setChecked(wifiApConfig.getProxySetting() == ProxySetting.STATIC);

        if (wifiApConfig.getProxySetting() == ProxySetting.STATIC) {
            proxyDetail.setVisibility(View.VISIBLE);
            hostEdit.setText(wifiApConfig.getProxyHost());
            portEdit.setText(String.valueOf(wifiApConfig.getProxyPort()));
        } else {
            proxyDetail.setVisibility(View.INVISIBLE);
        }

        selectProxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void initData() {

    }
}
