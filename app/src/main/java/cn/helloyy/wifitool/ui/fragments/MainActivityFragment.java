package cn.helloyy.wifitool.ui.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.shouldit.proxy.lib.WiFiApConfig;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.helloyy.wifitool.R;
import cn.helloyy.wifitool.loaders.ProxyConfigurationTaskLoader;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<WiFiApConfig>> {

    private static final int LOADER_PROXYCONFIGURATIONS = 1;

    private Loader<List<WiFiApConfig>> loader;

    @Bind(R.id.mainText)TextView mainTextView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();

        loader = getLoaderManager().initLoader(LOADER_PROXYCONFIGURATIONS, new Bundle(), this);
        loader.forceLoad();
    }

    public void refreshLoaderResults(List<WiFiApConfig> wiFiApConfigs) {
        mainTextView.setText(wiFiApConfigs.get(0).toString());
    }

    @Override
    public Loader<List<WiFiApConfig>> onCreateLoader(int id, Bundle args) {
        ProxyConfigurationTaskLoader proxyConfigurationTaskLoader = new ProxyConfigurationTaskLoader(getActivity());
        return proxyConfigurationTaskLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<WiFiApConfig>> loader, List<WiFiApConfig> data) {
        refreshLoaderResults(data);
    }

    @Override
    public void onLoaderReset(Loader<List<WiFiApConfig>> loader) {
        Timber.d("onLoaderReset");
    }
}
