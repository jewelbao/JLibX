package com.jewel.libx.android.recyclerView;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/4/17
 */
public class SimpleViewHolder extends RecyclerView.ViewHolder {

    public SimpleViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public final <T extends View> T findViewById(int id) {
        return itemView.findViewById(id);
    }

    public final SimpleViewHolder setText(int id, int stringRes) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(itemView.getContext().getResources().getText(stringRes));
        }
        return this;
    }

    public final SimpleViewHolder setText(int id, String text) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
        return this;
    }

    public final SimpleViewHolder setImageResource(int id, int drawableRes) {
        if(drawableRes == -1) {
            return this;
        }
        View view = findViewById(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(drawableRes);
        }
        return this;
    }

    public final SimpleViewHolder setImageDrawable(int id, Drawable drawableRes) {
        if(drawableRes == null) {
            return this;
        }
        View view = findViewById(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(drawableRes);
        }
        return this;
    }
}
