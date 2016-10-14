package cn.helloyy.wifitool.ui.components;

import android.content.Context;
import android.util.AttributeSet;
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
    private WiFiApConfig configuration;

    public WifiSignal(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.wifi_signal, this);

        if (inflater != null)
        {
            iconImageView = (ImageView) v.findViewById(R.id.wifi_ap_signal);
        }
    }

    private void refreshUI()
    {
        if (configuration == null || configuration.getLevel() == -1)
        {
            iconImageView.setImageResource(R.drawable.ic_action_nowifi);
        }
        else
        {
            iconImageView.setImageLevel(configuration.getLevel());
            iconImageView.setImageResource(R.drawable.wifi_signal);
            iconImageView.setImageState((configuration.getSecurityType() != SecurityType.SECURITY_NONE) ? WiFiApConfig.STATE_SECURED : WiFiApConfig.STATE_NONE, true);
        }
    }

    public void setConfiguration(WiFiApConfig configuration)
    {
        this.configuration = configuration;
        refreshUI();
    }
}
