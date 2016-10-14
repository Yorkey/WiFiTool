package cn.helloyy.infinite.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import cn.helloyy.infinite.interf.RvItemClickListener;

/**
 * Created by wangyu on 2016/3/14.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    RvItemClickListener rvItemClickListener;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);
    }

    public BaseViewHolder(View itemView, RvItemClickListener listener) {
        this(itemView);

        rvItemClickListener = listener;
    }

    public void setRvItemClickListener(RvItemClickListener listener) {
        rvItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (v == itemView && rvItemClickListener != null) {
            rvItemClickListener.OnItemClick(v, getLayoutPosition());
        }
    }
}
