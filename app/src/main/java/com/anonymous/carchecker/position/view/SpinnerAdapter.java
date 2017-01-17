package com.anonymous.carchecker.position.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.anonymous.carchecker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 1/17/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {
    List<String> items = new ArrayList<>();
    private Context mContext;
    private int mSelection;

    public SpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.items = items;
        mContext = context;
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.spinner_item, parent, false);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txt);
        String str = items.get(position);
        if (!TextUtils.isEmpty(str)) {
            txtTitle.setText(str);
        }
        if (mSelection == position) {
            txtTitle.setTextColor(Color.RED);
        }
        return convertView;
    }

    public int getSelection() {
        return mSelection;
    }

    public void setSelection(int mSelection) {
        this.mSelection = mSelection;
    }
}
