
package com.sunay.moony.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sunay.moony.R;


/**
 * 
 * @author MentorMate
 *
 */

/**
 * Custom {@link TextView} with a Roboto type face. Default type face will be
 * Roboto_Regular if is not defined. To define custom type face use
 * robo_typeface="string" in your xml layout. All Roboto type face values are
 * defined in strings.xml under 'Roboto typefaces' tag.
 */
public class RobotoTextView extends TextView {

    public RobotoTextView(Context context) {
        super(context);
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            setTypeFace(context, attrs);
        }
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            setTypeFace(context, attrs);
        }
    }

    private void setTypeFace(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RobotoTextView);
        String robotoFont = a.getString(R.styleable.RobotoTextView_robo_typeface);
        if (TextUtils.isEmpty(robotoFont)) {
            robotoFont = context.getString(R.string.Roboto_Regular);
        }
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/" + robotoFont
                + ".ttf");
        setTypeface(font);
        a.recycle();
    }
}
