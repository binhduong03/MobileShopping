package com.example.doan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.doan.R;
import com.example.doan.model.Item;
import com.example.doan.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class ChiTietAdapter extends RecyclerView.Adapter<ChiTietAdapter.MyViewHolder> {

    Context context;
    List<Item> itemList;

    public ChiTietAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public ChiTietAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtTenspchitiet.setText(item.getName() + "");
        holder.txtSoluongchitiet.setText("Số lượng: " + item.getQuantity());
        Log.d("ChiTietAdapter", "Tên sản phẩm: " + itemList.get(position).getName());
        if (item.getImage().contains("http")){
            Glide.with(context).load(item.getImage()).into(holder.imageChitiet);
        } else {
            String hinh = Utils.BASE_URL+"Admin/images/"+item.getImage();
            Glide.with(context).load(hinh).into(holder.imageChitiet);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageChitiet;
        TextView txtTenspchitiet, txtSoluongchitiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageChitiet = itemView.findViewById(R.id.item_Imgchitiet);
            txtTenspchitiet = itemView.findViewById(R.id.item_Tenspchitiet);
            txtSoluongchitiet = itemView.findViewById(R.id.item_Soluongchitiet);
        }
    }
}

