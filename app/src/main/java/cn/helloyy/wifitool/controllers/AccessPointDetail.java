package cn.helloyy.wifitool.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import be.shouldit.proxy.lib.WiFiApConfig;
import be.shouldit.proxy.lib.reflection.android.ProxySetting;
import butterknife.Bind;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.base.BaseController;
import cn.helloyy.wifitool.util.BundleBuilder;

/**
 * Created by wangyu on 2016/11/17.
 */

public class AccessPointDetail extends BaseController {

    private static final String KEY_AP_DATA = "AccessPointDetail.ap-data";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

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

    private WiFiApConfig wifiApConfig;


    public AccessPointDetail(WiFiApConfig wifiApConfig) {
        this(new BundleBuilder()
                .putParcelable(KEY_AP_DATA, wifiApConfig)
                .build());

    }

    public AccessPointDetail(Bundle args) {
        super(args);

        this.wifiApConfig = (WiFiApConfig)args.getParcelable(KEY_AP_DATA);
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_ap_detail, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        toolbar.setTitle("APDetail");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRouter().popCurrentController();
            }
        });

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
}
