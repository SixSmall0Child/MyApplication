package com.example.taoying.testapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    private final Context mContext;
    private ArrayList<String> mDatas = new ArrayList<>();//Recyclerview数据

    public MyAdapter(Context context) {
        this.mContext = context;
        generateData();
    }

    private void generateData() {
        for (int i = 0; i < 50; i++) {
            mDatas.add(i + "");
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_test_eventmotion, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.mTv.setText(mDatas.get(position));
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView mTv;

        public VH(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

}