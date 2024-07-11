package com.example.nyanyanko.ShopAct;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nyanyanko.ImageDialog;
import com.example.nyanyanko.Inventory.InventoryItem;
import com.example.nyanyanko.R;

import java.util.List;

public class ShopItemAdapter extends ArrayAdapter<ShopItem> {

    private Context context;
    private List<ShopItem> items;
    private final OnItemInteractionListener listener;

    public ShopItemAdapter(Context context, List<ShopItem> items, OnItemInteractionListener listener){
        super(context, R.layout.shop_item, items);
        this.items = items;
        this.context = context;
        this.listener = listener;
    }
    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.shop_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = view.findViewById(R.id.item_icon);
            viewHolder.name = view.findViewById(R.id.item_name);
            viewHolder.cost  = view.findViewById(R.id.item_cost);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        ShopItem currentItem = (ShopItem) getItem(pos);

        viewHolder.icon.setImageBitmap(currentItem.getIcon());
        viewHolder.name.setText(currentItem.getName());
        viewHolder.cost.setText(String.valueOf(currentItem.getCost()));

        view.setOnClickListener(v -> listener.onItemClick(currentItem));

        return view;
    }
    static class ViewHolder{
        ImageView icon;
        TextView name;
        TextView cost;
    }
    public interface OnItemInteractionListener{
        void onItemClick(ShopItem item);
    }
}
