package com.example.race.papershelp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.race.papershelp.Bean.ApplyProgressData;
import com.example.race.papershelp.R;
import com.example.race.papershelp.ViewHolder.ApplyAbstractViewHolder;
import com.example.race.papershelp.ViewHolder.FootViewHolder;
import com.example.race.papershelp.ViewHolder.HeadViewHolder;
import com.example.race.papershelp.ViewHolder.NormalViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kiboooo on 2017/10/20.
 */

public class ApplyProgressAdapter extends RecyclerView.Adapter {


    private final int TYPE_HEAD = 1023;
    private final int TYPE_NORMOL = 1024;
    private final int TYPE_FOOT = 1025;

    private Context mContext;

    private LayoutInflater aLayoutInflater;

    private List<ApplyProgressData> DataShow = new ArrayList<>();

    public ApplyProgressAdapter(List<ApplyProgressData> Show, Context context) {
        DataShow = Show;
        mContext = context;
        aLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEAD:
                return new HeadViewHolder(aLayoutInflater.inflate(R.layout.apply_progress_head, parent, false));
            case TYPE_FOOT:
                return new FootViewHolder(aLayoutInflater.inflate(R.layout.apply_progress_foot, parent, false));
            case TYPE_NORMOL:
                return new NormalViewHolder(aLayoutInflater.inflate(R.layout.apply_progress_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //调用外部实现的ViewHolder
        ((ApplyAbstractViewHolder)holder).binHolder(DataShow.get(position));
    }

    @Override
    public int getItemCount() {
        return DataShow.size() ;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEAD;
        if (position == getItemCount()-1)
            return TYPE_FOOT;
        return TYPE_NORMOL;
    }
}
