package com.jewel.libx.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/4/24
 */
public class BitmapUtil {

    public static Bitmap rotate(Bitmap bitmapOrg) {
        Matrix matrix = new Matrix();
        matrix.postRotate(-90);//旋转的角度
        return Bitmap.createBitmap(bitmapOrg, 0, 0,
                bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
    }

    /**
     * 使用TransitionDrawable实现渐变效果（比使用AlphaAnimation效果要好，可避免出现闪烁问题）
     */
    private void setImageBitmap(ImageView imageView, Bitmap bitmap) {
        Context context = imageView.getContext();
        // Use TransitionDrawable to fade in.
        final TransitionDrawable td = new TransitionDrawable(
                new Drawable[]{
                        new ColorDrawable(context.getResources().getColor(android.R.color.transparent)),
                        new BitmapDrawable(context.getResources(), bitmap)});
        //noinspection deprecation
        imageView.setBackgroundDrawable(imageView.getDrawable());
        imageView.setImageDrawable(td);
        td.startTransition(200);
    }
}
