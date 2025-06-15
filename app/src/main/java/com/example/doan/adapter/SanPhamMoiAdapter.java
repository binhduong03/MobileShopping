package com.example.doan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan.Interface.ItemClickListener;
import com.example.doan.R;
import com.example.doan.activity.Pages.ChiTietActivity;
import com.example.doan.model.SanPhamMoi;
import com.example.doan.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.MyViewHolder> {
    Context context;
    List<SanPhamMoi> array;

    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public SanPhamMoiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_moi,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamMoiAdapter.MyViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = array.get(position);
        holder.txtten.setText(sanPhamMoi.getName());
        try {
            String giaStr = sanPhamMoi.getPrice();
            if (giaStr != null && !giaStr.isEmpty()) {
                double gia = Double.parseDouble(giaStr);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.txtgia.setText("Giá: " + decimalFormat.format(gia) + "đ");
            } else {
                holder.txtgia.setText("Giá: Đang cập nhật");
            }
        } catch (NumberFormatException e) {
            holder.txtgia.setText("Giá: Không hợp lệ");
            e.printStackTrace(); // log lỗi để bạn dễ debug
        }
        if (sanPhamMoi.getImage().contains("http")){
            Glide.with(context).load(sanPhamMoi.getImage()).into(holder.imghinhanh);
        }else {
            String hinh = Utils.BASE_URL+"Admin/images/"+sanPhamMoi.getImage();
            Glide.with(context).load(hinh).into(holder.imghinhanh);
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick){
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitiet", sanPhamMoi);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtgia, txtten;
        ImageView imghinhanh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgia = itemView.findViewById(R.id.itemsp_gia);
            txtten = itemView.findViewById(R.id.itemsp_ten);
            imghinhanh = itemView.findViewById(R.id.itemsp_image);
            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

    }
}