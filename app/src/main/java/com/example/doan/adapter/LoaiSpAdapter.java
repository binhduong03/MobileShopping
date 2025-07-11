package com.example.doan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.doan.R;
import com.example.doan.model.LoaiSp;
import com.example.doan.utils.Utils;

import java.util.List;

public class LoaiSpAdapter extends BaseAdapter {
    List<LoaiSp> array;
    Context context;

    public LoaiSpAdapter(Context context, List<LoaiSp> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView texttensp;
        ImageView imghinhanh;

    }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder = new ViewHolder();
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.item_sanpham,null);
                viewHolder.texttensp = convertView.findViewById(R.id.item_tepsp);
                viewHolder.imghinhanh = convertView.findViewById(R.id.item_image);
                convertView.setTag(viewHolder);
            }else {
                    viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.texttensp.setText(array.get(position).getName());
            if(array.get(position).getImage().contains("http")){
                Glide.with(context).load(array.get(position).getImage()).into(viewHolder.imghinhanh);
            }else {
                String hinh = Utils.BASE_URL_HINHANH+"public/backend/assets/img/menu/"+array.get(position).getImage();
                Log.d("Xem đường dẫn", "getView: " + hinh + " vị trí " + position);
            }

            return convertView;
        }
}
