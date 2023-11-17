package com.zzt.zztapt.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zzt.zztapt.R;
import com.zzt.zztapt.adapter.BaseRecyclerViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zeting
 * @date: 2023/11/9
 */
public class RvAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    List<String> mList = new ArrayList<>();

    private
    RvItemOnClickListener itemClick;

    public RvAdapter(List<String> mList, RvItemOnClickListener itemClick) {
        this.mList = mList;
        this.itemClick = itemClick;
    }


    @NonNull
    @NotNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View rootView = View.inflate(parent.getContext(), R.layout.item_layout, null);
        return new BaseRecyclerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BaseRecyclerViewHolder holder, int position) {
        TextView tv = holder.get(R.id.textView2);
        tv.setText(mList.get(position));
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (itemClick != null) {
//                    int pos = holder.getAbsoluteAdapterPosition();
//                    itemClick.onClickZ(pos, mList.get(pos) +" java");
//                }
//            }
//        });
        holder.itemView.setOnClickListener(v -> {
            if (itemClick != null) {
                int pos = holder.getAbsoluteAdapterPosition();
                itemClick.onClickZ(pos, mList.get(pos) + " Lambda");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface RvItemOnClickListener {
        void onClickZ(int pos, String objects);
    }
}
