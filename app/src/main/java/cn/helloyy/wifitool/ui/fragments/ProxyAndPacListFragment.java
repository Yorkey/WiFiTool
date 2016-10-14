package cn.helloyy.wifitool.ui.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.helloyy.infinite.base.BaseViewPagerFragment;

/**
 * Created by wangyu on 2016/9/28.
 */

public class ProxyAndPacListFragment extends BaseViewPagerFragment {


    @Override
    protected FragmentStatePagerAdapter getPagerAdapter() {
        return new ProxyAndPacAdapter(getChildFragmentManager());
    }


    public class ProxyAndPacAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();;
        private String[] titles = new String[]{"Proxy", "Pacs"};

        public ProxyAndPacAdapter(FragmentManager fm) {
            super(fm);

            fragmentList.add(new ProxyListFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
