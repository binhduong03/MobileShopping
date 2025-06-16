package com.example.doan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.model.DonHang;
import com.example.doan.R;
import com.example.doan.retrofit.ApiBanHang;
import com.example.doan.retrofit.RetrofitClient;
import com.example.doan.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {

    ApiBanHang apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> listDonHang;

    public DonHangAdapter(Context context, List<DonHang> listDonHang) {
        this.context = context;
        this.listDonHang = listDonHang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang = listDonHang.get(position);
        holder.txtDonhang.setText("Đơn hàng: " + donHang.getOrder_id());
        holder.txtTrangthai.setText("Trạng thái: " + getStatusText(donHang.getStatus()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.reChitiet.getContext(), LinearLayoutManager.VERTICAL, false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());

        //adapter chitiet
        ChiTietAdapter chitietAdapter = new ChiTietAdapter(context, donHang.getItem());
        holder.reChitiet.setLayoutManager(layoutManager);
        holder.reChitiet.setAdapter(chitietAdapter);
        holder.reChitiet.setRecycledViewPool(viewPool);

        if (donHang.getStatus() == 4) {
            holder.btnHuyDon.setText("Đặt lại");
            holder.btnHuyDon.setBackgroundTintList(context.getResources().getColorStateList(android.R.color.holo_green_light));
            holder.btnHuyDon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datLaiDonHang(donHang.getOrder_id());
                }
            });
        } else if(donHang.getStatus() == 0){
            holder.btnHuyDon.setText("Huỷ đơn");
            holder.btnHuyDon.setBackgroundTintList(context.getResources().getColorStateList(android.R.color.holo_red_light));
            holder.btnHuyDon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    huyDonHang(donHang.getOrder_id());
                }
            });
        }else{
            holder.btnHuyDon.setText("Chờ giao");
            holder.btnHuyDon.setBackgroundTintList(context.getResources().getColorStateList(android.R.color.holo_blue_light));
        }

        Log.d("DonHangAdapter", "Đơn hàng ID: " + donHang.getOrder_id() + " có " + donHang.getItem().size() + " sản phẩm");

    }

    private String getStatusText(int status) {
        switch (status) {
            case 0:
                return "Chờ duyệt";
            case 1:
                return "Đang vận chuyển";
            case 2:
                return "Đang giao hàng";
            case 3:
                return "Đã giao";
            case 4:
                return "Đã huỷ";
            default:
                return "Không xác định";
        }
    }

    private void huyDonHang(int orderId) {
        compositeDisposable.add(apiBanHang
                .huyDon(orderId, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response.isSuccess()) {
                                Toast.makeText(context, "Huỷ đơn hàng thành công", Toast.LENGTH_SHORT).show();

                                // Tìm và update trạng thái đơn hàng trong list
                                for (DonHang donHang : listDonHang) {
                                    if (donHang.getOrder_id() == orderId) {
                                        donHang.setStatus(4);  // cập nhật trạng thái local
                                        break;
                                    }
                                }

                                notifyDataSetChanged();  // refresh lại RecyclerView
                            } else {
                                Toast.makeText(context, "Lỗi huỷ đơn: " + response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(context, "Lỗi kết nối server: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void datLaiDonHang(int orderId) {
        compositeDisposable.add(apiBanHang
                .huyDon(orderId, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response.isSuccess()) {
                                Toast.makeText(context, "Đặt lại đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                // Cập nhật trạng thái đơn hàng trong list
                                for (DonHang donHang : listDonHang) {
                                    if (donHang.getOrder_id() == orderId) {
                                        donHang.setStatus(0);  // Trở về trạng thái chờ duyệt
                                        break;
                                    }
                                }
                                notifyDataSetChanged();  // Refresh lại RecyclerView
                            } else {
                                Toast.makeText(context, "Lỗi đặt lại đơn: " + response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(context, "Lỗi kết nối server: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    @Override
    public int getItemCount() {
        return listDonHang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtDonhang, txtTrangthai;
        RecyclerView reChitiet;
        Button btnHuyDon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDonhang = itemView.findViewById(R.id.id_Donhang);
            txtTrangthai = itemView.findViewById(R.id.id_Trangthai);
            reChitiet = itemView.findViewById(R.id.recycleview_Chitiet);
            btnHuyDon = itemView.findViewById(R.id.btnHuyDon);
        }
    }

}
