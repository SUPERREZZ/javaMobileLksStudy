package com.example.lks2024;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lks2024.model.Delivery;

import java.util.List;

public class DeliveryAdapter extends ArrayAdapter<Delivery> {
    private List<Delivery> deliveryList;
    private Context context;

    public DeliveryAdapter(Context context, List<Delivery> deliveryList) {
        super(context, R.layout.delivery_item, deliveryList);
        this.context = context;
        this.deliveryList = deliveryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.delivery_item, parent, false);
        }

        Delivery delivery = deliveryList.get(position);

        TextView deliveryName = convertView.findViewById(R.id.deliverycompany);
        TextView deliveryDetails = convertView.findViewById(R.id.deldes);

        deliveryName.setText(delivery.getName());
        deliveryDetails.setText(delivery.getDuration() + " day(s), Rp " + delivery.getPrice());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent); // Gunakan tampilan yang sama untuk dropdown
    }
}
