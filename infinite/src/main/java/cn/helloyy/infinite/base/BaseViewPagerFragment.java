package cn.helloyy.infinite.base;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import butterknife.Bind;
import cn.helloyy.infinite.R;

/**
 * Created by wangyu on 2016/3/21.
 */
public abstract class BaseViewPagerFragment extends BaseFragment {


    protected PagerSlidingTabStrip pagerSlidingTabStrip;
    protected ViewPager viewPager;

    private FragmentStatePagerAdapter viewPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.base_viewpager_fragment;
    }

    @Override
    public void initView(View view) {

        pagerSlidingTabStrip = (PagerSlidingTabStrip)view.findViewById(R.id.pagerStrip);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        if (viewPagerAdapter == null) {
            viewPagerAdapter = getPagerAdapter();
        }
        viewPager.setAdapter(viewPagerAdapter);
        pagerSlidingTabStrip.setViewPager(viewPager);
    }

    @Override
    public void initData() {

    }

    abstract protected FragmentStatePagerAdapter getPagerAdapter();
}
