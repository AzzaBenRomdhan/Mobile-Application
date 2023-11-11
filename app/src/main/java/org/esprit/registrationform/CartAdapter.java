package org.esprit.registrationform;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItemList;
    private int count = 1;
    Context context;

    // Constructeur
    public CartAdapter(List<CartItem> cartItemList, Context context) {
        this.cartItemList = cartItemList;
        this.context= context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        CartItem cartItem = cartItemList.get(position);
        // Mettez à jour les éléments de l'interface utilisateur avec les données du produit du panier
        holder.productTitleTextView.setText(cartItem.getProductTitle());
        holder.priceTextView.setText(String.valueOf(cartItem.getPrice()));
        holder.quantityTextView.setText(String.valueOf(cartItem.getQuantity()));
        holder.decrement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                    notifyDataSetChanged();
                }
            }
        });
        holder.increment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItem.setPrice(cartItem.getQuantity() * cartItem.getPrice()); // Update price based on quantity
                notifyDataSetChanged();
            }
        });

    }




    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productTitleTextView, priceTextView, quantityTextView;
        Button increment_btn, decrement2;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productTitleTextView = itemView.findViewById(R.id.productTitleTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            increment_btn = itemView.findViewById(R.id.increment_btn);
            decrement2 = itemView.findViewById(R.id.decrement2);
        }
    }

}
