package cn.helloyy.wifitool.controllers;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.greendao.rx.RxDao;

import java.util.Date;

import butterknife.Bind;
import cn.helloyy.wifitool.App;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.base.ControllerWithToolbar;
import cn.helloyy.wifitool.model.WifiProxy;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangyu on 2016/11/29.
 */

public class ProxyEditController extends ControllerWithToolbar {

    private WifiProxy wifiProxy = null;
    private String title;

    @Bind(R.id.hostEdit)
    EditText hostEdit;

    @Bind(R.id.portEdit)
    EditText portEdit;

    public ProxyEditController() {
        title = "Add New";
    }

    public ProxyEditController(WifiProxy wifiProxy) {
        this.wifiProxy = wifiProxy;
        title = "Edit";
    }

    @Override
    protected String getTitle() {
        return title;
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_proxy_edit, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        this.showBackNavigator();
        Toolbar toolbar = getToolbar();
        toolbar.inflateMenu(R.menu.menu_proxy_detail);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_save) {
                    String host = hostEdit.getText().toString();
                    Integer port = new Integer(portEdit.getText().toString());
                    if (wifiProxy != null &&
                            host.equals(wifiProxy.getHost()) &&
                            port.equals(wifiProxy.getPort())) {
                        return true;
                    } else {
                        Date now = new Date();
                        RxDao<WifiProxy, Long> rxDao = App.getInstance().getDaoSession().getWifiProxyDao().rx();
                        Observable<WifiProxy> wifiProxyObservable = null;
                        if (wifiProxy == null) {
                            wifiProxy = new WifiProxy(null, host, port.intValue(), 0, now, now);
                            wifiProxyObservable = rxDao.insert(wifiProxy);
                        } else {
                            wifiProxy.setHost(host);
                            wifiProxy.setPort(port.intValue());
                            wifiProxy.setModifyDate(now);
                            wifiProxyObservable = rxDao.update(wifiProxy);
                        }
                        wifiProxyObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<WifiProxy>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNext(WifiProxy wifiProxy) {
                                        getRouter().popCurrentController();
                                    }
                                });
                    }
                    Toast.makeText(getActivity(), "Save", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });

        if (wifiProxy != null) {
            hostEdit.setText(wifiProxy.getHost());
            portEdit.setText(String.valueOf(wifiProxy.getPort()));
        }
    }
}
