package com.example.nyanyanko;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InventoryAdapter extends ArrayAdapter<InventoryItem> {
    private final Context context;
    private final List<InventoryItem> items;
    private final OnItemInteractionListener listener;
    public InventoryAdapter(Context context, List<InventoryItem> items, OnItemInteractionListener listener) {
        super(context, R.layout.inventory_item, items);
        this.context = context;
        this.items = items;
        this.listener = listener;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.inventory_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = convertView.findViewById(R.id.item_icon);
            viewHolder.name = convertView.findViewById(R.id.item_name);
            viewHolder.quantity = convertView.findViewById(R.id.item_quantity);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        InventoryItem item = items.get(position);

        viewHolder.icon.setImageBitmap(item.getIcon());
        viewHolder.name.setText(item.getName());
        viewHolder.quantity.setText(" x " + item.getQuantity());

        convertView.setOnClickListener(v -> listener.onItemClick(item));

        return convertView;
    }
    static class ViewHolder{
        ImageView icon;
        TextView name;
        TextView quantity;
    }
    public interface OnItemInteractionListener{
        void onItemClick(InventoryItem item);
    }
}
