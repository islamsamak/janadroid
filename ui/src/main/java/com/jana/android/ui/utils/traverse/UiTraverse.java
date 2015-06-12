/**
 *
 */
package com.jana.android.ui.utils.traverse;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * @author islamsamak
 */
public class UiTraverse {

    private OnTraversedViewListener onTraversedViewListener;

    public UiTraverse(OnTraversedViewListener listener) {
        onTraversedViewListener = listener;
    }

    public void traverseActivity(Activity activity) {

        if (activity == null) {
            return;
        }

        View view = activity.getWindow().getDecorView().getRootView();

        traverseRootView(view);
    }

    public void traverseFragmentTypeface(Fragment fragment) {

        if (fragment == null) {
            return;
        }

        traverseRootView(fragment.getView());
    }

    public void traverseRootView(View view) {

        if (view instanceof ViewGroup) {
            traverseViewGroup((ViewGroup) view);
        } else {
            onTraversedViewListener.onTraversedView(view);
        }
    }

    public void traverseViewGroup(ViewGroup group) {

        if (group == null) {
            return;
        }

        int childCount = group.getChildCount();

        for (int i = 0; i < childCount; i++) {

            Object adapterObj = null;

            View child = group.getChildAt(i);

            if (child instanceof AdapterView<?>) {

                adapterObj = ((AdapterView<?>) child).getAdapter();

            } else if (child instanceof ViewPager) {

                adapterObj = ((ViewPager) child).getAdapter();
            }

            if (adapterObj instanceof TraverseViewListener) {
                TraverseViewListener listener = (TraverseViewListener) adapterObj;
                listener.traverse(onTraversedViewListener);
            }

            if (child instanceof ViewGroup) {
                traverseViewGroup((ViewGroup) child);
            } else {
                onTraversedViewListener.onTraversedView(child);
            }
        }
    }

}
