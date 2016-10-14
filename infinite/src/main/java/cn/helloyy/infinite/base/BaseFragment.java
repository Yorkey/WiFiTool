package cn.helloyy.infinite.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.helloyy.infinite.interf.BaseFragmentInterface;

/**
 * Created by wangyu on 2016/2/29.
 */
public abstract class BaseFragment extends Fragment implements BaseFragmentInterface {

    protected Context mContent;

    abstract protected int getLayoutId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContent = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getLayoutId() != 0) {
            View view = inflater.inflate(getLayoutId(), container, false);
            return view;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        initView(view);

        initData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
