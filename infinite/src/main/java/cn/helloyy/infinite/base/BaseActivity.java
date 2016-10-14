package cn.helloyy.infinite.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import cn.helloyy.infinite.interf.BaseViewInterface;

/**
 * Created by wangyu on 2016/2/19.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseViewInterface {

    abstract protected int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onBeforeSetContentLayout();

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }

        init(savedInstanceState);

        initView();

        initData();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        ButterKnife.bind(this);
    }

    protected void onBeforeSetContentLayout() {

    }

    protected void init(Bundle savedInstanceState) {

    }

}
