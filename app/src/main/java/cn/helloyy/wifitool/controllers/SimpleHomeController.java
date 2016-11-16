package cn.helloyy.wifitool.controllers;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.base.BaseController;

/**
 * Created by wangyu on 2016/11/16.
 */

public class SimpleHomeController extends BaseController {

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.list_item_proxy, container, false);
        return view;
    }


    @Override
    protected String getTitle() {
        return "WiFiTool";
    }
}
