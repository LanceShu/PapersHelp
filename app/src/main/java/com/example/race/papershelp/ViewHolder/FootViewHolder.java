package com.example.race.papershelp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.race.papershelp.Bean.ApplyProgressData;
import com.example.race.papershelp.R;

/**
 * Created by Kiboooo on 2017/10/20.
 */

public class FootViewHolder extends ApplyAbstractViewHolder {

    private TextView mDate, MSG;
    private ImageView Image;
    private LinearLayout Foot_Up;

    public FootViewHolder(View itemView) {
        super(itemView);
        mDate = (TextView) itemView.findViewById(R.id.foot_date);
        MSG = (TextView) itemView.findViewById(R.id.foot_message);
        Image = (ImageView) itemView.findViewById(R.id.foot_image);
        Foot_Up = (LinearLayout) itemView.findViewById(R.id.foot_up);
    }

    @Override
    public void binHolder(ApplyProgressData applyProgressData) {
        mDate.setText(applyProgressData.getTime() + "\n" + applyProgressData.getDay());
        MSG.setText(applyProgressData.getProgressContent());
        Image.setImageBitmap(applyProgressData.getImage());
        if (applyProgressData.isProgressBarColor())
            Foot_Up.setBackgroundResource(R.color.progress);
        else
            Foot_Up.setBackgroundResource(R.color.Unprogress);
    }
}
