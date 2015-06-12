/**
 *
 */
package com.jana.android.ui.view;

import android.app.AlertDialog;
import android.content.Context;

import com.jana.android.ui.view.DefaultDialog.OnClickListener;

/**
 * @author isamak
 */
public class ConfirmationDialog {

    public static AlertDialog createDialog(Context context, int title,
                                           int message, OnClickListener onClickListener, int positive,
                                           int neutral, int negative) {

        DefaultDialog dialog = new DefaultDialog(context);

        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(true);

        if (positive > 0) {
            dialog.enablePositiveButton(positive);
        }

        if (neutral > 0) {
            dialog.enableNeutralButton(neutral);
        }

        if (negative > 0) {
            dialog.enableNegativeButton(negative);
        }

        dialog.setOnClickListener(onClickListener);

        return dialog.createDialog();
    }

}
