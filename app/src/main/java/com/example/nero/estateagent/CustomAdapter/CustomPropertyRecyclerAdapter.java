package com.example.nero.estateagent.CustomAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nero.estateagent.Property;
import com.example.nero.estateagent.R;
import com.example.nero.estateagent.interfaces.CustomPropertyInteractor;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.R.attr.x;

/**
 * Created by Nero on 18/05/2017.
 */

public class CustomPropertyRecyclerAdapter extends RecyclerView.Adapter<CustomPropertyRecyclerAdapter.PropertyViewHolder> {

    ArrayList<Property> properties;
    Context context;
    CustomPropertyInteractor interactor;

    public CustomPropertyRecyclerAdapter(Context context, ArrayList<Property> properties){
        this.context = context;
        this.properties = properties;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards, parent, false);
        return new PropertyViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder holder, int position) {
        final Property prop = properties.get(position);
        prop.setPosition(position);

        if(prop.isImageNull()){
            Picasso.with(context).load(R.drawable.no_image).into(holder.image);
        }else{
            Picasso.with(context).load(prop.getLargeImageURL()).into(holder.image);
        }
        holder.price.setText("£" + getFormatedAmount(Integer.valueOf(prop.getPrice())));
        holder.address.setText(prop.getAddress());
        holder.bedrooms.setText(prop.getBedrooms() + " bedrooms");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interactor.OpenPropertyDescription(prop);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                interactor.onLongClick(prop);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public void setInteractor(CustomPropertyInteractor interactor){
        this.interactor = interactor;
    }

    class PropertyViewHolder extends RecyclerView.ViewHolder{
        TextView price, address, bedrooms;
        ImageView image;
        public PropertyViewHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.property_price);
            address = (TextView) itemView.findViewById(R.id.property_address);
            bedrooms = (TextView) itemView.findViewById(R.id.property_bedrooms);
            image = (ImageView) itemView.findViewById(R.id.property_image);
        }
    }

    private String getFormatedAmount(int amount){
        return NumberFormat.getNumberInstance(Locale.UK).format(amount);
    }

}
