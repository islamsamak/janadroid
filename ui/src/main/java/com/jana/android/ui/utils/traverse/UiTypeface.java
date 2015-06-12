/**
 *
 */
package com.jana.android.ui.utils.traverse;

import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.jana.android.utils.Logger;

/**
 * @author islamsamak
 */
public class UiTypeface implements OnTraversedViewListener {

    protected SparseArray<Typeface> typefaceMap;

    protected int style;

    public UiTypeface() {
        typefaceMap = new SparseArray<Typeface>(3);
    }

    /**
     * Sets the typeface and style in which the text should be displayed, and
     * turns on the fake bold and italic bits in the Paint if the Typeface that
     * you provided does not have all the bits in the style that you specified.
     *
     * @attr ref android.R.styleable#TextView_textStyle
     * @attr ref android.R.styleable#TextView_typeface
     */
    public void setTypeface(int style, Typeface typeface) {
        this.style = style;
        typefaceMap.put(style, typeface);
    }

    public Typeface getTypeface(int style) {
        return typefaceMap.get(style);
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    @Override
    public void onTraversedView(View view) {
        if (view instanceof TextView) {

            TextView textView = (TextView) view;

            int style = this.style;
            if (textView.getTypeface() != null) {
                style = textView.getTypeface().getStyle();
            }

            Typeface typeface = typefaceMap.get(style);
            if (typeface == null) {
                Logger.w("WARNING: Selected typeface style is not defined");
                return;
            }

            textView.setTypeface(typeface, style);
        }
    }

}
