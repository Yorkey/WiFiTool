package cn.helloyy.infinite.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.helloyy.infinite.R;
import cn.helloyy.infinite.interf.RvItemClickListener;

/**
 * Created by wangyu on 2016/3/14.
 */
public abstract class BaseListAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter {


    public static final int BOTTOM_LOADING = 0;
    public static final int BOTTOM_ERROR = 1;
    public static final int BOTTOM_NO_DATA = 2;
    public static final int BOTTOM_WAIT_LOAD = 3;

    private final int VIEW_FOOT = 0;
    private final int VIEW_ITEM = 1;

    protected List<T> dataList;

    protected int bottomState;

    protected RvItemClickListener rvItemClickListener;

    protected View.OnClickListener onBottomClickListener;

    public BaseListAdapter(List<T> dataList) {
        this(dataList, null);
    }


    public BaseListAdapter(List<T> dataList, View.OnClickListener onBottomClickListener) {
        this.dataList = dataList;
        if (this.dataList == null) {
            this.dataList = new ArrayList<T>();
        }
        this.onBottomClickListener = onBottomClickListener;
    }

    public BaseListAdapter() {
        this.dataList = new ArrayList<T>();
    }


    public void setItemClickListener(RvItemClickListener listener) {
        rvItemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position) != null ? VIEW_ITEM : VIEW_FOOT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_FOOT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loadmore_view, parent, false);
            return new DefaultFooterViewHolder(view);
        } else {
            return onCreateItemViewHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseListAdapter.DefaultFooterViewHolder) {
            DefaultFooterViewHolder defaultFooterViewHolder = (DefaultFooterViewHolder) holder;
            ((DefaultFooterViewHolder) holder).initView();
        } else {
            onBindItemViewHolder((VH)holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<T> getDataList() {
        return dataList;
    }

    public T getItemData(int position) {
        if (position >=0 && position < dataList.size()) {
            return dataList.get(position);
        }

        return null;
    }


    public void showLoadingMore(int state) {
        if (!isBottomShowing()) {
            dataList.add(null);
        }
        if (bottomState != state) {
            bottomState = state;
            notifyItemChanged(dataList.size() - 1);
        }
    }

    public boolean isBottomShowing() {
        if (dataList.size() > 0) {
            return (dataList.get(dataList.size()-1) == null);
        }
        return false;
    }

    public void hideLoadingMore() {
        dataList.remove(dataList.size()-1);
        notifyItemChanged(dataList.size()-1);
    }


    public void setData(List<T> newList) {
        dataList = newList;
        notifyDataSetChanged();
    }

    public void addData(List<T> moreList) {
        if (moreList != null && moreList.size() > 0) {
            int currentSize = dataList.size();
            dataList.addAll(moreList);
            notifyItemRangeInserted(currentSize - 1, moreList.size() - 1);
        }
    }


    public abstract VH onCreateItemViewHolder(ViewGroup parent);

    public abstract void onBindItemViewHolder(VH holder, int position);


    class DefaultFooterViewHolder extends BaseViewHolder {

        protected ProgressBar progressBar;

        protected TextView textView;

        public DefaultFooterViewHolder(View itemView) {
            super(itemView);

            progressBar = (ProgressBar)itemView.findViewById(R.id.loadmoreProgress);
            textView = (TextView)itemView.findViewById(R.id.loadmoreInfo);

        }

        public void initView() {
            switch (bottomState) {
                case BOTTOM_LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setText("加载中...");
                    itemView.setOnClickListener(null);
                    break;
                case BOTTOM_ERROR:
                    progressBar.setVisibility(View.GONE);
                    textView.setText("加载失败,点击重试");
                    itemView.setOnClickListener(onBottomClickListener);
                    break;
                case BOTTOM_WAIT_LOAD:
                    progressBar.setVisibility(View.GONE);
                    textView.setText("点击加载更多");
                    itemView.setOnClickListener(onBottomClickListener);
                    break;
                case BOTTOM_NO_DATA:
                    progressBar.setVisibility(View.GONE);
                    textView.setText("所有数据已加载完成");
                    itemView.setOnClickListener(null);
                    break;
            }
        }

    }
}
