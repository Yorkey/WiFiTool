package cn.helloyy.wifitool.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import be.shouldit.proxy.lib.WiFiApConfig;
import cn.helloyy.wifitool.App;

/**
 * Created by wangyu on 2016/9/19.
 */
public class ProxyConfigurationTaskLoader extends AsyncTaskLoader<List<WiFiApConfig>> {

    public ProxyConfigurationTaskLoader(Context context) {
        super(context);
    }

    @Override
    public List<WiFiApConfig> loadInBackground() {
        List<WiFiApConfig> result = App.getWifiNetworksManager().getSortedWifiApConfigsList();
        return result;
    }
}
