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
@SuppressWarnings("unused")
public abstract class SimpleAdapter<T> extends RecyclerView.Adapter<SimpleViewHolder> {

    public static final int ITEM_VIEW_TYPE_NONE = -10;
    public static final int ITEM_VIEW_TYPE_HEADER = -1;
    public static final int ITEM_VIEW_TYPE_FOOTER = -2;

    private List<T> data;
    private int adapterLayoutRes;
    private OnItemClickListener<T> itemClickListener;

    private View headerView;
    private View footerView;

    public abstract void bindView(SimpleViewHolder viewHolder, T data, int position);

    public SimpleAdapter(@LayoutRes int layoutRes) {
        this(null, layoutRes);
    }

    public SimpleAdapter(@Nullable List<T> data, @LayoutRes int layoutRes) {
        this.data = data;
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.adapterLayoutRes = layoutRes;
    }

    ////////////////////////////////////public field for Data ////////////////////////////////////////////

    public void setData(List<T> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    public void addData(List<T> newDataList) {
        if (newDataList == null || newDataList.isEmpty()) {
            return;
        }
        int positionStart = getHeadersCount() + getDataSize();
        getData().addAll(newDataList);
        notifyItemRangeInserted(positionStart, newDataList.size());
    }

    public void addData(T newData) {
        if (newData == null) {
            return;
        }
        int positionStart = getHeadersCount() + getDataSize();
        getData().add(newData);
        notifyItemInserted(positionStart);
    }

    public void removeData(T data) {
        if (getData().contains(data)) {
            final int removePos = getHeadersCount() + getData().indexOf(data);
            getData().remove(data);
            notifyItemRemoved(removePos);
        }
    }

    public void removeData(int position) {
        if (position >= 0 && getDataSize() > position) {
            getData().remove(position);
            notifyItemRemoved(getHeadersCount() + position);
        }
    }

    public void updateData(T newData, int position) {
        if (position >= 0 && getDataSize() > position) {
            getData().set(position, newData);
            notifyItemChanged(getHeadersCount() + position);
        }
    }

    public void updateData(List<T> newData, int positionStart) {
        if (newData == null || newData.isEmpty()) {
            return;
        }
        if (newData.size() == 1) {
            updateData(newData.get(0), positionStart);
            return;
        }
        if (positionStart >= 0 && getDataSize() > positionStart) {
            for (int i = 0; i < newData.size(); i++) {
                getData().set(positionStart + i, newData.get(i));
            }
            notifyItemRangeChanged(getHeadersCount() + positionStart, newData.size());
        }
    }

    public void insertData(T newData, int position) {
        if (position >= 0 && getDataSize() > position) {
            getData().add(position, newData);
            notifyItemInserted(getHeadersCount() + position);
        }
    }

    public void insertData(List<T> newData, int positionStart) {
        if (newData == null || newData.isEmpty()) {
            return;
        }
        if (newData.size() == 1) {
            insertData(newData.get(0), positionStart);
            return;
        }
        if (positionStart >= 0 && getDataSize() > positionStart) {
            getData().addAll(positionStart, newData);
            notifyItemRangeInserted(getHeadersCount() + positionStart, newData.size());
        }
    }

    public List<T> getData() {
        if(data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public int getDataSize() {
        return getData().size();
    }

    ////////////////////////////////////public field for View ////////////////////////////////////////////

    public void addHeader(View headView) {
        if (headView != null) {
            this.headerView = headView;
            notifyItemInserted(0);
        }
    }

    public void removeHeader() {
        if(this.headerView != null) {
            this.headerView = null;
            notifyItemRemoved(0);
        }
    }

    public int getHeadersCount() {
        return headerView != null ? 1 : 0;
    }

    public void addFooter(View footView) {
        if (footView != null) {
            this.footerView = footView;
            notifyItemInserted(getItemCount());
        }
    }

    public void removeFooter() {
        if(this.footerView != null) {
            this.footerView = null;
            notifyItemRemoved(getItemCount());
        }
    }

    public int getFooterCount() {
        return footerView != null ? 1 : 0;
    }

    public boolean isHeader(int position) {
        return position < getHeadersCount();
    }

    public boolean isFooter(int position) {
        return getItemCount() - position <= getFooterCount();
    }

    public void setItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            if (headerView == null) {
                throw new IllegalArgumentException("headView is null ! Make sure you had really add HeadView");
            }
            return new SimpleViewHolder(headerView) {
            };
        } else if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            if (footerView == null) {
                throw new IllegalArgumentException("footView is null ! Make sure you had really add FootView");
            }
            return new SimpleViewHolder(footerView) {
            };
        } else {
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
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder viewHolder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_HEADER) {
            // do something
        } else if (getItemViewType(position) == ITEM_VIEW_TYPE_FOOTER) {
            // do something
        } else {
            int dataPos = position - getHeadersCount();
            bindView(viewHolder, getData().get(dataPos), dataPos);
        }
    }

    @Override
    public int getItemCount() {
        int headSize = getHeadersCount();
        int footSize = getFooterCount();
        return getDataSize() + headSize + footSize;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return ITEM_VIEW_TYPE_HEADER;
        }
        if (isFooter(position)) {
            return ITEM_VIEW_TYPE_FOOTER;
        }
        return super.getItemViewType(position);
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
            int clickedDataPos = viewHolder.getAdapterPosition() - getHeadersCount();
            itemClickListener.onItemClicked(viewHolder.itemView, getData().get(clickedDataPos), clickedDataPos);
        }
    }
}

