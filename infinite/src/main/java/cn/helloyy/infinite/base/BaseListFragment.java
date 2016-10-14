package cn.helloyy.infinite.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.List;

import cn.helloyy.infinite.R;
import cn.helloyy.infinite.animations.LceAnimator;
import cn.helloyy.infinite.interf.OnReachBottomListener;
import cn.helloyy.infinite.interf.OnScrollToBottomListener;
import cn.helloyy.infinite.interf.RvItemClickListener;
import cn.helloyy.infinite.views.CommonDecoration;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by wangyu on 2016/4/17.
 */
public abstract class BaseListFragment<VH extends RecyclerView.ViewHolder, T>
        extends BaseFragment implements  SwipeRefreshLayout.OnRefreshListener, OnReachBottomListener {

    public static final int LOADING_NONE = 0;
    public static final int LOADING_FIRST = 1;
    public static final int LOADING_REFRESH = 2;
    public static final int LOADING_MORE = 3;

    protected View loadingView;

    protected SwipeRefreshLayout contentView;

    protected TextView errorView;

    protected RecyclerView recyclerView;

    protected BaseListAdapter<VH, T> recyclerAdapter;

    protected Observable<List<T>> dataObservable;

    protected boolean autoLoadmore;

    protected int currentPage = 1;

    protected int loadingState;

    private boolean pullToRefresh = true;
    private boolean loadmore = true;

    private int totalCount;


    public void setPullToRefresh(boolean pullToRefresh) {
        this.pullToRefresh = pullToRefresh;
    }

    public void setLoadmore(boolean loadmore) {
        this.loadmore = loadmore;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_list_fagment;
    }

    @Override
    public void initView(View view) {

        loadingView = view.findViewById(R.id.loadingView);
        contentView = (SwipeRefreshLayout)view.findViewById(R.id.contentView);
        errorView = (TextView)view.findViewById(R.id.errorView);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        autoLoadmore = getResources().getBoolean(R.bool.list_auto_load_more);

        if (pullToRefresh) {
            contentView.setOnRefreshListener(this);
        } else {
            contentView.setEnabled(false);
        }

        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onErrorViewClicked();
            }
        });

        dataObservable = getDataObservable();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new CommonDecoration(getContext(), LinearLayoutManager.HORIZONTAL, R.drawable.default_list_divider));
        recyclerView.setHasFixedSize(true);
        if (recyclerAdapter == null) {
            recyclerAdapter = getListAdapter();
            loadData(LOADING_FIRST);
        } else {
            showContent();
        }
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addOnScrollListener(new OnScrollToBottomListener(this));

    }

    @Override
    public void initData() {

    }

    protected abstract BaseListAdapter<VH, T> getListAdapter();

    protected Type getListResultType() {
        return null;
    }

    public void showLoading() {
        switch (loadingState) {
            case LOADING_FIRST:
                LceAnimator.showLoading(loadingView, contentView, errorView);
                break;
            case LOADING_REFRESH:
                showRefreshLoading();
                break;
            case LOADING_MORE:
                showLoadingMore(BaseListAdapter.BOTTOM_LOADING);
                break;
            default:
                ;
        }

    }

    public void showContent() {
        LceAnimator.showContent(loadingView, contentView, errorView);
    }


    public void showError(String error, int loadingState) {

        switch (loadingState) {
            case LOADING_FIRST:
                errorView.setText(error);
                LceAnimator.showErrorView(loadingView, contentView, errorView);
                break;
            case LOADING_REFRESH:
                showLightError(error);
                break;
            case LOADING_MORE:
                showLoadingMore(BaseListAdapter.BOTTOM_ERROR);
                break;
            default:
                ;
        }
    }


    protected void showLightError(String msg) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void loadData(int loadingState) {
        if (this.loadingState != LOADING_NONE) {
            return;
        }
        this.loadingState = loadingState;
        showLoading();

        if (loadingState == LOADING_MORE) {
            currentPage++;
        }
        loadData();
    }

    abstract public Observable<List<T>> getDataObservable();

    public void loadData() {
        dataObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<T>>() {
                    @Override
                    public void onCompleted() {
                        showContent();
                        onLoadFinish(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (loadingState == LOADING_MORE) {
                            currentPage--;
                        }
                        showError(e.getMessage(), loadingState);
                        onLoadFinish(false);
                    }

                    @Override
                    public void onNext(List<T> ts) {
                        setData(ts);
                    }
                });
    }


    public void setData(List<T> data) {
        recyclerAdapter.setData(data);
    }


    public void setOnItemClickListener(RvItemClickListener listener) {
        recyclerAdapter.setItemClickListener(listener);
    }


    public void onReachBottom() {
        if (!loadmore) {
            return;
        }

        if (recyclerAdapter.dataList.size() >= totalCount) {
            return;
        }

        if (!recyclerAdapter.isBottomShowing()) {
            if (autoLoadmore) {
                loadData(LOADING_MORE);
            } else {
                showLoadingMore(BaseListAdapter.BOTTOM_WAIT_LOAD);
            }
        }
    }

    public void onRefresh() {
        loadData(LOADING_REFRESH);
    }

    protected void onErrorViewClicked() {
        loadData(LOADING_FIRST);
    }

    public void appendData(List<T> data) {
        recyclerAdapter.hideLoadingMore();
        recyclerAdapter.addData(data);
    }


    public void hideLoadingMore() {
        recyclerAdapter.hideLoadingMore();
    }

    public void showLoadingMore(int state) {
        recyclerAdapter.showLoadingMore(state);
    }


    public void showRefreshLoading() {
        if (pullToRefresh) {
            contentView.setRefreshing(true);
            // 防止多次重复刷新
            contentView.setEnabled(false);
        }
    }

    public void onLoadFinish(boolean succeed) {
        loadingState = LOADING_NONE;

        if (pullToRefresh) {
            contentView.setRefreshing(false);
            contentView.setEnabled(true);
        }
    }

//    public class ListCallback implements DidEatCallback {
//
//        @Override
//        public void onSuccess(HttpResult httpResult) {
//
//            ListResult<T> listResult = httpResult.getList( getListResultType() );
//            switch (loadingState) {
//                case LOADING_FIRST:
//                case LOADING_REFRESH:
//                    currentPage = 1;
//                    totalCount = listResult.getTotalCount();
//                    setData(listResult.getList());
//                    showContent();
//                    onLoadData(listResult, loadingState);
//                    break;
//                case LOADING_MORE:
//                    totalCount = listResult.getTotalCount();
//                    appendData(listResult.getList());
//                    onLoadData(listResult, loadingState);
//                    break;
//                default:
//                    ;
//            }
//
//            onLoadFinish();
//        }
//
//        @Override
//        public void onFailure(String error) {
//            if (loadingState == LOADING_MORE) {
//                currentPage--;
//            }
//            showError(error, loadingState);
//            onLoadFinish();
//        }
//    }
//
//    protected void onLoadData(ListResult<T> listResult, int loadingState) {
//
//    }
}
