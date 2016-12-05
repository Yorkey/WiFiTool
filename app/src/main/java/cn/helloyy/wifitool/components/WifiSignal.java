package cn.helloyy.wifitool.components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import be.shouldit.proxy.lib.WiFiApConfig;
import be.shouldit.proxy.lib.enums.SecurityType;
import cn.helloyy.wifitool.R;

/**
 * Created by wangyu on 2016/9/27.
 */

public class WifiSignal extends LinearLayout {

    private ImageView iconImageView;
    private ImageView iconLockView;
    private WiFiApConfig configuration;

    public WifiSignal(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.wifi_signal, this);

        if (inflater != null)
        {
            iconImageView = (ImageView) v.findViewById(R.id.wifi_ap_signal);
            iconLockView = (ImageView)v.findViewById(R.id.ic_wifi_lock);
        }
    }

    private void refreshUI()
    {
        if (configuration == null) {
            iconImageView.setImageResource(R.drawable.wifi0);
            iconLockView.setVisibility(View.INVISIBLE);
        } else {
            if (configuration.getLevel() == -1)
            {
                iconImageView.setImageResource(R.drawable.wifi0);
            }
            else
            {
                iconImageView.setImageLevel(configuration.getLevel());
                iconImageView.setImageResource(R.drawable.wifi_signal_icon);
            }
            iconLockView.setVisibility((configuration.getSecurityType() != SecurityType.SECURITY_NONE) ? View.VISIBLE : View.INVISIBLE);
        }

    }

    public void setConfiguration(WiFiApConfig configuration)
    {
        this.configuration = configuration;
        refreshUI();
    }
}
