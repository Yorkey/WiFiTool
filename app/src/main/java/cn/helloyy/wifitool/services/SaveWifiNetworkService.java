package cn.helloyy.wifitool.services;

import android.app.IntentService;
import android.content.Intent;
import android.provider.SyncStateContract;


import be.shouldit.proxy.lib.APL;
import be.shouldit.proxy.lib.WiFiApConfig;
import be.shouldit.proxy.lib.utils.SaveResult;
import cn.helloyy.wifitool.App;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.constants.Intents;
import timber.log.Timber;

/**
 * Created by Marco on 09/03/14.
 */
public class SaveWifiNetworkService extends IntentService
{
    public static final String CALLER_INTENT = "CallerIntent";
    public static String TAG = SaveWifiNetworkService.class.getSimpleName();
    private boolean isHandling = false;
    private static SaveWifiNetworkService instance;

    public SaveWifiNetworkService()
    {
        super("SaveWifiNetworkService");
    }

    public static SaveWifiNetworkService getInstance()
    {
        return instance;
    }

    public boolean isHandlingIntent()
    {
        return isHandling;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        instance = this;
        isHandling = true;

        SaveResult result = null;

        while(App.getWifiNetworksManager().savingOperationsCount() > 0)
        {
            WiFiApConfig wiFiApConfig = App.getWifiNetworksManager().getSavingOperation();

            if (wiFiApConfig != null)
            {
                try
                {
                    result = APL.writeWifiAPConfig(wiFiApConfig, 1000, 5000); // 1000 attempts, 5 seconds
                    if (result != null)
                        Timber.i("Configuration %s in %d attempts after %d ms", result.status.toString(), result.attempts, result.elapsedTime);
                }
                catch (Exception e)
                {
                    Timber.e(e, "Exception saving Wi-Fi network configuration to device");

                    Intent i = new Intent(Intents.SERVICE_COMUNICATION);
//                    i.putExtra(SyncStateContract.Constants.SERVICE_COMUNICATION_TITLE, getString(R.string.attention));
//                    i.putExtra(SyncStateContract.Constants.SERVICE_COMUNICATION_MESSAGE, getString(R.string.exception_apl_writeconfig_error_message));
//                    i.putExtra(SyncStateContract.Constants.SERVICE_COMUNICATION_CLOSE_ACTIVITY, WiFiApDetailActivity.class.getSimpleName());
                    sendBroadcast(i);
                }

                Intents.callIntent(getApplicationContext(), Intents.PROXY_REFRESH_UI);
            }
            else
            {
                Timber.w("Got null configuration to save from queue");
            }
        }

        isHandling = false;
    }

}
