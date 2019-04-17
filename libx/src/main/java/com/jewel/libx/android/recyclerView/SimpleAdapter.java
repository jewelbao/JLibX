package com.jewel.libx.android.recyclerView;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/2/19
 */
public abstract class SimpleAdapter<T> extends RecyclerView.Adapter<SimpleViewHolder> {

    private List<T> data = new ArrayList<>();
    private int adapterLayoutRes;
    private OnItemClickListener<T> itemClickListener;

    public abstract void bindView(SimpleViewHolder viewHolder, T data, int position);

    public SimpleAdapter(@LayoutRes int layoutRes) {
        this(null, layoutRes);
    }

    public SimpleAdapter(@Nullable List<T> data, @LayoutRes int layoutRes) {
        this.data = data;
        this.adapterLayoutRes = layoutRes;
    }

    ////////////////////////////////////public field////////////////////////////////////////////

    public void setData(List<T> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    public void addData(List<T> newDataList) {
        if (newDataList == null || newDataList.isEmpty()) {
            return;
        }
        if (data == null) {
            data = new ArrayList<>();
        }

        data.addAll(newDataList);
        notifyItemRangeInserted(getData().size(), data.size());
    }

    public void addData(T newData) {
        if (newData == null) {
            return;
        }
        if (data == null) {
            data = new ArrayList<>();
        }
        data.add(newData);
        notifyItemInserted(getItemCount());
    }

    public List<T> getData() {
        if(data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void setItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View adapterView = View.inflate(viewGroup.getContext(), adapterLayoutRes, null);
        final SimpleViewHolder viewHolder = new SimpleViewHolder(adapterView) {
        };
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemViewClickListener(viewHolder);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder viewHolder, int position) {
        bindView(viewHolder, getData().get(position), position);
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    /**
     * item clicked listener
     *
     * @param <T>
     */
    public interface OnItemClickListener<T> {
        void onItemClicked(View view, T data, int position);
    }

    ////////////////////////////////////private field////////////////////////////////////////////

    private void onItemViewClickListener(RecyclerView.ViewHolder viewHolder) {
        if (itemClickListener != null) {
            int clickedPos = viewHolder.getLayoutPosition() % getData().size();
            itemClickListener.onItemClicked(viewHolder.itemView, getData().get(clickedPos), clickedPos);
        }
    }
}

