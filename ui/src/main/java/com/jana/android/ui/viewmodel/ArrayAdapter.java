/**
 *
 */
package com.jana.android.ui.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jana.android.ui.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Islam Samak
 */
public abstract class ArrayAdapter<T, H extends ViewHolder> extends BaseAdapter {

    public static final int TAG_HOLDER = R.id.tag_holder;

    public static final int TAG_LAYOUT_ID = R.id.tag_layout_id;

    private final Object lock = new Object();
    protected Context context;
    protected int layoutId;
    protected List<T> itemsList;
    protected LayoutInflater inflater;
    private boolean notifyOnChange = true;

    public ArrayAdapter(Context context, int layoutId, List<T> items) {

        this.context = context;

        this.layoutId = layoutId;

        this.itemsList = items;

        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getItemLayoutId() {
        return layoutId;
    }

    public void setItemLayoutId(int layout) {
        this.layoutId = layout;
    }

    @Override
    public int getCount() {
        if (itemsList == null || itemsList.isEmpty()) {
            return 0;
        }

        return itemsList.size();
    }

    @Override
    public T getItem(int position) {
        if (itemsList == null || itemsList.isEmpty()) {
            return null;
        }

        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(T item) {
        synchronized (lock) {
            if (itemsList == null) {
                itemsList = new ArrayList<T>(20);
            }
            itemsList.add(item);
        }

        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void addAll(Collection<? extends T> collection) {
        synchronized (lock) {
            if (itemsList == null) {
                itemsList = new ArrayList<T>(collection.size());
            }
            itemsList.addAll(collection);
        }

        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void addAll(T... items) {
        synchronized (lock) {
            if (itemsList == null) {
                itemsList = new ArrayList<T>(items.length);
            }
            Collections.addAll(itemsList, items);
        }

        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void insert(T object, int index) {
        synchronized (lock) {
            if (itemsList == null) {
                itemsList = new ArrayList<T>(20);
            }
            itemsList.add(index, object);
        }

        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void remove(T object) {
        synchronized (lock) {
            if (itemsList == null) {
                return;
            }
            itemsList.remove(object);
        }

        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void clear() {
        synchronized (lock) {
            if (itemsList == null) {
                return;
            }
            itemsList.clear();
        }

        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        notifyOnChange = true;
    }

    public void setNotifyOnChange(boolean notifyOnChange) {
        this.notifyOnChange = notifyOnChange;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = createView(position, view, parent);
        }

        @SuppressWarnings("unchecked")
        H holder = (H) view.getTag(TAG_HOLDER);

        bindView(position, view, holder);

        return view;
    }

    protected View createView(int position, View view, ViewGroup parent) {

        view = inflater.inflate(layoutId, parent, false);

        ViewHolder holder = createHolder(view);

        view.setTag(TAG_HOLDER, holder);

        view.setTag(TAG_LAYOUT_ID, layoutId);

        return view;
    }

    protected abstract ViewHolder createHolder(View view);

    protected abstract void bindView(int position, View view, H holder);

}
