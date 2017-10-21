package com.example.race.papershelp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.race.papershelp.Bean.ApplyProgressData;
import com.example.race.papershelp.R;

/**
 * Created by Kiboooo on 2017/10/20.
 *
 */

public class HeadViewHolder extends ApplyAbstractViewHolder {
    private TextView mDate, MSG;
    private ImageView Image;
    private LinearLayout Head_down;

    public HeadViewHolder(View itemView) {
        super(itemView);
        mDate = (TextView) itemView.findViewById(R.id.head_date);
        MSG = (TextView) itemView.findViewById(R.id.head_message);
        Image = (ImageView) itemView.findViewById(R.id.head_image);
        Head_down = (LinearLayout) itemView.findViewById(R.id.head_down);
    }

    @Override
    public void binHolder(ApplyProgressData applyProgressData) {
        mDate.setText(applyProgressData.getTime() + "\n" + applyProgressData.getDay());
        MSG.setText(applyProgressData.getProgressContent());
        Image.setImageBitmap(applyProgressData.getImage());
        if (applyProgressData.isProgressBarColor())
                Head_down.setBackgroundResource(R.color.colorPrimary);
        else
            Head_down.setBackgroundResource(R.color.Unprogress);
    }
}
