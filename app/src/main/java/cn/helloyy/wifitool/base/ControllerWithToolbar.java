package cn.helloyy.wifitool.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import cn.helloyy.wifitool.R;

/**
 * Created by wangyu on 2016/11/27.
 */

public abstract class ControllerWithToolbar extends BaseController {

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;


    public ControllerWithToolbar() {

    }

    public ControllerWithToolbar(Bundle args) {
        super(args);
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    protected abstract String getTitle();

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        setTitle();
    }

    protected void setTitle() {
        if (toolbar != null) {
            toolbar.setTitle(getTitle());
        }
    }

    protected void showBackNavigator() {
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getRouter().popCurrentController();
                }
            });
        }
    }
}
