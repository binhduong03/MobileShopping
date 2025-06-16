package com.example.doan.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan.Interface.ImageClickListener;
import com.example.doan.model.EventBus.TinhTongEvent;
import com.example.doan.model.GioHang;
import com.example.doan.R;
import com.example.doan.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;
    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang giohang = gioHangList.get(position);
        holder.item_Giohang_tensp.setText(giohang.getName());
        holder.item_Giohang_soluong.setText(giohang.getQuantity() + " ");
        Glide.with(context).load(giohang.getImage()).into(holder.item_Giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_Giohang_gia.setText(decimalFormat.format((giohang.getPrice())));
        long gia = giohang.getQuantity() * giohang.getPrice();
        holder.item_Giohang_gia2.setText(decimalFormat.format(gia));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Utils.mangmuahang.add(giohang);
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }else{
                    for(int i = 0; i < Utils.mangmuahang.size(); i++){
                        if(Utils.mangmuahang.get(i).getProduct_id() == giohang.getProduct_id()){
                            Utils.mangmuahang.remove(i);
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }
                    }
                }
            }
        });
        holder.setListener(new ImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if(giatri == 1){
                    if(gioHangList.get(pos).getQuantity() > 1){
                        int soLuongmoi = gioHangList.get(pos).getQuantity() - 1;
                        gioHangList.get(pos).setQuantity(soLuongmoi);

                        holder.item_Giohang_soluong.setText(gioHangList.get(pos).getQuantity() + " ");
                        long gia = gioHangList.get(pos).getQuantity() * gioHangList.get(pos).getPrice();
                        holder.item_Giohang_gia2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }else if(gioHangList.get(pos).getQuantity() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xoá sản phẩm này khỏi giỏ hàng không?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();

                    }
                }else if(giatri == 2){
                    if(gioHangList.get(pos).getQuantity() < 11){
                        int soLuongmoi = gioHangList.get(pos).getQuantity() + 1;
                        gioHangList.get(pos).setQuantity(soLuongmoi);
                    }
                    holder.item_Giohang_soluong.setText(gioHangList.get(pos).getQuantity() + " ");
                    long gia = gioHangList.get(pos).getQuantity() * gioHangList.get(pos).getPrice();
                    holder.item_Giohang_gia2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_Giohang_image, item_Giohang_tru, item_Giohang_cong, item_Giohang_handle;
        TextView item_Giohang_tensp, item_Giohang_gia, item_Giohang_soluong, item_Giohang_gia2;

        ImageClickListener listener;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_Giohang_image = itemView.findViewById(R.id.item_Giohang_image);
            item_Giohang_tensp = itemView.findViewById(R.id.item_Giohang_tensp);
            item_Giohang_gia = itemView.findViewById(R.id.item_Giohang_gia);
            item_Giohang_soluong = itemView.findViewById(R.id.item_Giohang_soluong);
            item_Giohang_gia2 = itemView.findViewById(R.id.item_Giohang_gia2);
            item_Giohang_tru = itemView.findViewById(R.id.item_Giohang_tru);
            item_Giohang_cong = itemView.findViewById(R.id.item_Giohang_cong);
            checkBox = itemView.findViewById(R.id.item_Giohang_checkbox);

            //event click
            item_Giohang_cong.setOnClickListener(this);
            item_Giohang_tru.setOnClickListener(this);
        }

        public void setListener(ImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if(view == item_Giohang_tru){
                listener.onImageClick(view, getAdapterPosition(), 1);
                //1 tru
            }else if(view == item_Giohang_cong){
                //2 cong
                listener.onImageClick(view, getAdapterPosition(), 2);
            }
        }
    }
}
