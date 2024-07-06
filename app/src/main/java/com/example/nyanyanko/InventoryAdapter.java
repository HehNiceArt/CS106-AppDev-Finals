package com.example.nyanyanko;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class InventoryAdapter extends ArrayAdapter<InventoryItem> {
    private final Context context;
    private final List<InventoryItem> items;
    public InventoryAdapter(Context context, List<InventoryItem> items) {
        super(context, R.layout.inventory_item, items);
        this.context = context;
        this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.inventory_item, parent, false);
        }
        InventoryItem item = items.get(position);
        ImageView icon = convertView.findViewById(R.id.item_icon);
        TextView name = convertView.findViewById(R.id.item_name);
        TextView quantity = convertView.findViewById(R.id.item_quantity);

        icon.setImageBitmap(item.getIcon());
        name.setText(item.getName());
        quantity.setText(" x " + item.getQuantity());
        return convertView;
    }
}
