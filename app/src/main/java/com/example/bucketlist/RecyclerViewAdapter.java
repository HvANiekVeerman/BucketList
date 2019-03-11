package com.example.bucketlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<BucketItem> mData;
    private LayoutInflater mInflater;
    private Context mContext;

    RecyclerViewAdapter(Context context, ArrayList<BucketItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final BucketItem bucketItem = mData.get(position);
        holder.tvTitle.setText((bucketItem.getTitle()));
        holder.tvDescription.setText((bucketItem.getDescription()));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tvDescription.setPaintFlags(holder.tvDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tvDescription.setPaintFlags(holder.tvDescription.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        }
        );
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDescription;
        ConstraintLayout rvLayout;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvBucketDescription);
            tvDescription = itemView.findViewById(R.id.tvBucketTitle);
            rvLayout = itemView.findViewById(R.id.clRecyclerView);
            rvLayout.setClickable(true);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

}