package com.example.race.papershelp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.race.papershelp.Bean.ApplyProgressData;

/**
 * Created by Kiboooo on 2017/10/20.
 */

public abstract class ApplyAbstractViewHolder extends RecyclerView.ViewHolder {

    public ApplyAbstractViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void binHolder(ApplyProgressData applyProgressData);
}
