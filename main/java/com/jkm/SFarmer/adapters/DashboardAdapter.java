package com.jkm.SFarmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkm.SFarmer.R;  // هذا هو الـ import الصحيح

public class DashboardAdapter extends BaseAdapter {
    private Context context;
    private String[] sections;
    private int[] icons;
    private LayoutInflater inflater;

    public DashboardAdapter(Context context, String[] sections, int[] icons) {
        this.context = context;
        this.sections = sections;
        this.icons = icons;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sections.length;
    }

    @Override
    public Object getItem(int position) {
        return sections[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dashboard_item, parent, false);
            holder = new ViewHolder();
            holder.icon = convertView.findViewById(R.id.sectionIcon);
            holder.title = convertView.findViewById(R.id.sectionTitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setImageResource(icons[position]);
        holder.title.setText(sections[position]);

        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView title;
    }
}