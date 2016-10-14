package cn.helloyy.wifitool;

import android.app.Application;

import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;

import be.shouldit.proxy.lib.APL;
import be.shouldit.proxy.lib.logging.TraceUtils;
import cn.helloyy.wifitool.model.DaoMaster;
import cn.helloyy.wifitool.model.DaoSession;
import cn.helloyy.wifitool.util.CrashReportingTree;
import timber.log.Timber;

import static timber.log.Timber.DebugTree;

/**
 * Created by wangyu on 2016/9/19.
 */
public class App extends Application {

    private static final String DATABASE_NAME = "wifi-tool.db";

    private static App mInstance;

    private WifiNetworksManager wifiNetworksManager;

    private DaoSession daoSession;

    private TraceUtils traceUtils;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        traceUtils = new TraceUtils();
        wifiNetworksManager = new WifiNetworksManager(App.this);

        initDatabase();

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        APL.setup(this);
    }

    private void initDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DATABASE_NAME);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static App getInstance() {
        return mInstance;
    }

    public static TraceUtils getTraceUtils() {
        return getInstance().traceUtils;
    }

    public static WifiNetworksManager getWifiNetworksManager() {
        return getInstance().wifiNetworksManager;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
