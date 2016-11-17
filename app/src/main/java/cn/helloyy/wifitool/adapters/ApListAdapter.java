package cn.helloyy.wifitool.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import be.shouldit.proxy.lib.WiFiApConfig;

/**
 * Created by wangyu on 2016/11/17.
 */

public class ApListAdapter extends RecyclerArrayAdapter<WiFiApConfig> {

    public ApListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
