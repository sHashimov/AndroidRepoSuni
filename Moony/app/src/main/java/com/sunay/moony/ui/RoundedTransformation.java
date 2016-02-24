package com.sunay.moony.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

/**
 * Implementation of com.squareup.picasso.Transformation used to get the user avatar in the shape of a circle
 *
 * Created by krasimir.karamazov on 5/13/2014.
 */
public class RoundedTransformation implements com.squareup.picasso.Transformation {
    private final int margin = 2;  // dp
    private final int lengthSide;

    // radius is corner radii in dp
    // margin is the border in dp
    public RoundedTransformation(final int lengthSide) {
        this.lengthSide = lengthSide;
    }

    /**
     * Transform a bitmap into a circle
     * @param source The bitmap that needs to be transformed
     * @return
     */
    @Override
    public Bitmap transform(final Bitmap source) {

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(source, lengthSide, lengthSide, true);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
//        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setShader(new BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
//        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap output = Bitmap.createBitmap(lengthSide, lengthSide, Bitmap.Config.ARGB_8888);
//        Bitmap output = Bitmap.createScaledBitmap(source, lengthSide, lengthSide, false);
        Canvas canvas = new Canvas(output);
//        canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin, source.getHeight() - margin), source.getWidth() / 2, source.getHeight() / 2, paint);
        canvas.drawRoundRect(new RectF(margin, margin, lengthSide - margin, lengthSide - margin), (lengthSide ) / 2, (lengthSide ) / 2, paint);

        if (source != output) {
            source.recycle();
        }

        return output;
    }

    @Override
    public String key() {
        return "rounded";
    }
}
