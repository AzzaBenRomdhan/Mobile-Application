package org.esprit.registrationform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView .Adapter<CustomAdapter.MyViewHolder>{
     Context context;
     ArrayList   _id, product_title, Description, price;
    CustomAdapter customAdapter;

    CustomAdapter(Context context, ArrayList _id, ArrayList product_title, ArrayList Description, ArrayList price){
        this._id= _id;
        this.product_title= product_title;
        this.Description= Description;
        this.price = price;
        this.context= context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_item, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder._id.setText(String.valueOf(_id.get(position)));
        holder.product_title.setText(String.valueOf(product_title.get(position)));
        holder.Description.setText(String.valueOf(Description.get(position)));
        holder.price.setText(String.valueOf(price.get(position)));

    }

    @Override
    public int getItemCount() {
        return _id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView _id, product_title, Description, price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            _id= itemView.findViewById(R.id._id);
            product_title= itemView.findViewById(R.id.product_title);
            Description= itemView.findViewById(R.id.Description);
            price= itemView.findViewById(R.id.price);
        }
    }
}
