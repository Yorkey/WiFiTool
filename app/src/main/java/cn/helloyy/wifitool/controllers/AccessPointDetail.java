package cn.helloyy.wifitool.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import be.shouldit.proxy.lib.WiFiApConfig;
import be.shouldit.proxy.lib.reflection.android.ProxySetting;
import butterknife.Bind;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.base.BaseController;
import cn.helloyy.wifitool.base.ControllerWithToolbar;
import cn.helloyy.wifitool.model.WifiProxy;
import cn.helloyy.wifitool.util.BundleBuilder;

/**
 * Created by wangyu on 2016/11/17.
 */

public class AccessPointDetail extends ControllerWithToolbar {

    private static final String KEY_AP_DATA = "AccessPointDetail.ap-data";

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

        wifiApConfig = (WiFiApConfig)args.getParcelable(KEY_AP_DATA);
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_ap_detail, container, false);
    }

    @Override
    protected String getTitle() {
        return "APDetail";
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        this.showBackNavigator();

        Toolbar toolbar = this.getToolbar();
        toolbar.inflateMenu(R.menu.menu_proxy_detail);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_save) {

                    Toast.makeText(getActivity(), "Save", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });

        boolean enable = wifiApConfig.getProxySetting() == ProxySetting.STATIC;
        proxySwitch.setChecked(enable);
        proxySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                proxyDetail.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            }
        });

        selectProxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRouter().pushController(RouterTransaction.with(new ProxyListController())
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler()));
            }
        });

        if (enable) {
            proxyDetail.setVisibility(View.VISIBLE);
            hostEdit.setText(wifiApConfig.getProxyHost());
            portEdit.setText(wifiApConfig.getProxyPort().toString());
        } else {
            proxyDetail.setVisibility(View.INVISIBLE);
        }

    }


    protected void updateProxyView(String host, String port) {

        if (wifiApConfig.getProxySetting() == ProxySetting.STATIC) {
            proxyDetail.setVisibility(View.VISIBLE);
            hostEdit.setText(host);
            portEdit.setText(port);
        } else {
            proxyDetail.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onProxySelected(ProxySelectEvent proxySelectEvent) {
        WifiProxy wifiProxy = proxySelectEvent.getSelectWifiProxy();
        proxySwitch.setChecked(true);
        hostEdit.setText(wifiProxy.getHost());
        portEdit.setText(String.valueOf(wifiProxy.getPort()));
    }

    public static class ProxySelectEvent {
        private WifiProxy selectWifiProxy;

        public ProxySelectEvent(WifiProxy wifiProxy) {
            selectWifiProxy = wifiProxy;
        }

        public WifiProxy getSelectWifiProxy() {
            return selectWifiProxy;
        }
    }
}
