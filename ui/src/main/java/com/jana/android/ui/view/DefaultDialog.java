/**
 *
 */
package com.jana.android.ui.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * @author isamak
 */
public class DefaultDialog implements
        DialogInterface.OnClickListener {

    protected AlertDialog dialog;
    protected AlertDialog.Builder builder;
    private OnClickListener onClickListener;

    /**
     * @param context
     */
    public DefaultDialog(Context context) {
        builder = new AlertDialog.Builder(context);
    }

    /**
     * @param context
     * @param theme
     */
    public DefaultDialog(Context context, int theme) {
        builder = new AlertDialog.Builder(context, theme);
    }

    public void enableNegativeButton(int lable) {
        builder.setNegativeButton(lable, this);
    }

    public void enableNegativeButton(String lable) {
        builder.setNegativeButton(lable, this);
    }

    public void enableNeutralButton(int lable) {
        builder.setNeutralButton(lable, this);
    }

    public void enableNeutralButton(String lable) {
        builder.setNeutralButton(lable, this);
    }

    public void enablePositiveButton(int lable) {
        builder.setPositiveButton(lable, this);
    }

    public void enablePositiveButton(String lable) {
        builder.setPositiveButton(lable, this);
    }

    public void setMessage(int message) {
        builder.setMessage(message);
    }

    public void setMessage(String message) {
        builder.setMessage(message);
    }

    public void setTitle(int title) {
        builder.setTitle(title);
    }

    public void setTitle(String title) {
        builder.setTitle(title);
    }

    public void setCustomTitle(View title) {
        builder.setCustomTitle(title);
    }

    public void setContentView(View view) {
        builder.setView(view);
    }

    public void setCancelable(boolean cancelable) {
        builder.setCancelable(cancelable);
    }

    public AlertDialog createDialog() {
        if (builder == null) {
            return null;
        }

        dialog = builder.create();

        return dialog;
    }

    public void show() {
        if (dialog == null) {
            return;
        }
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (onClickListener == null) {
            return;
        }

        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                onClickListener.onClickPositive(dialog);
                break;
            case Dialog.BUTTON_NEUTRAL:
                onClickListener.onClickNeutral(dialog);
                break;
            case Dialog.BUTTON_NEGATIVE:
                onClickListener.onClickNegative(dialog);
                break;
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public interface OnClickListener {

        void onClickPositive(DialogInterface dialog);

        void onClickNeutral(DialogInterface dialog);

        void onClickNegative(DialogInterface dialog);
    }

}
