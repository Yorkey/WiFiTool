package cn.helloyy.wifitool.ui.fragments;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import butterknife.Bind;
import cn.helloyy.infinite.base.BaseFragment;
import cn.helloyy.wifitool.App;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.model.WifiProxy;
import cn.helloyy.wifitool.ui.activities.ProxyAndPacListActivity;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangyu on 2016/9/30.
 */

public class ProxyDetailFragment extends BaseFragment {

    @Bind(R.id.hostEdit)
    EditText hostEdit;

    @Bind(R.id.portEdit)
    EditText portEdit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_proxy_detail;
    }

    @Override
    public void initView(View view) {
        setHasOptionsMenu(true);

        ActionBar toolbar = ((AppCompatActivity)getActivity()).getSupportActionBar();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_proxy_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            String host = hostEdit.getText().toString();
            int port = Integer.parseInt(portEdit.getText().toString());
            if (port <= 0) {
                Toast.makeText(getContext(), "请输入正确端口号", Toast.LENGTH_SHORT).show();
                return true;
            }
            Date nowDate = new Date();
            WifiProxy wifiProxy = new WifiProxy(null, host, port, 0, nowDate, nowDate);
            App.getInstance().getDaoSession().getWifiProxyDao().rx()
                    .insert(wifiProxy)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WifiProxy>() {
                        @Override
                        public void onCompleted() {
                            getActivity().finish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(WifiProxy wifiProxy) {

                        }
                    });
        }

        return true;
    }
}
