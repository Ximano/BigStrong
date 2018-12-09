package com.mili.base;

import android.app.Activity;
import android.content.Context;
import com.mili.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by TeeMo111 on 2018/8/21.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Activity activity;
    protected Context context;
    protected List<T> list = new ArrayList<>();

    public BaseRecyclerAdapter() {
    }

    public BaseRecyclerAdapter(Activity activity) {
        this.activity = activity;
        this.context = activity;
    }

    /**
     * 刷新列表
     *
     * @param list
     */
    public void updateList(List<T> list) {
        this.list.clear();
        loadList(list);
    }

    /**
     * 加载更多
     *
     * @param list
     */
    public void loadList(List<T> list) {
        if (list != null && list.size() > 0) {
            this.list.addAll(list);
        }
        LogUtils.d("adapter list.size()==" + this.list.size());
        notifyDataSetChanged();
    }
}
