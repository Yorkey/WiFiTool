package cn.helloyy.infinite.interf;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by wangyu on 2016/3/11.
 */
public class OnScrollToBottomListener extends RecyclerView.OnScrollListener {

    OnReachBottomListener onReachBottomListener;

    private boolean mIsEnd = false;

    public OnScrollToBottomListener(OnReachBottomListener onReachBottomListener) {
        this.onReachBottomListener = onReachBottomListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int lastVisiableItemPos = -1;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {

            lastVisiableItemPos = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

        } else if (layoutManager instanceof GridLayoutManager) {

            lastVisiableItemPos = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;

            int[] lastPosArray = new int[staggeredGridLayoutManager.getSpanCount()];

            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPosArray);
            lastVisiableItemPos = findMax(lastPosArray);
        } else {
            throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
        }

        int totalItemCount = layoutManager.getItemCount();

        if (lastVisiableItemPos >= totalItemCount-1) {
            mIsEnd = true;
        } else {
            mIsEnd = false;
        }
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == RecyclerView.SCROLL_STATE_IDLE && mIsEnd) {

            if (onReachBottomListener != null) {
                onReachBottomListener.onReachBottom();
            }
        }
    }


    private int findMax(int[] lastPosArray) {

        int maxPos = lastPosArray[0];
        for (int pos : lastPosArray) {
            if (pos > maxPos) {
                maxPos = pos;
            }
        }

        return maxPos;
    }
}
