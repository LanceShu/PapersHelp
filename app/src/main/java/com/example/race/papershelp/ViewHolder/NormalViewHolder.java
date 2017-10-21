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

public class NormalViewHolder extends ApplyAbstractViewHolder {

    private TextView mDate, MSG;
    private ImageView Image;
    private LinearLayout Item_Up,Item_Down;

    public NormalViewHolder(View itemView) {
        super(itemView);
        /*设置控件的绑定，以及相应的属性设置*/
        mDate = (TextView) itemView.findViewById(R.id.item_date);
        MSG = (TextView) itemView.findViewById(R.id.item_message);
        Image = (ImageView) itemView.findViewById(R.id.item_image);
        Item_Up = (LinearLayout) itemView.findViewById(R.id.item_up);
        Item_Down = (LinearLayout) itemView.findViewById(R.id.item_down);
    }

    @Override
    public void binHolder(ApplyProgressData applyProgressData) {
        /*设置实际的内容*/
        mDate.setText(applyProgressData.getTime() + "\n" + applyProgressData.getDay());
        MSG.setText(applyProgressData.getProgressContent());
        Image.setImageBitmap(applyProgressData.getImage());
        if (applyProgressData.isProgressBarColor()) {
            Item_Up.setBackgroundResource(R.color.colorPrimary);
            Item_Down.setBackgroundResource(R.color.colorPrimary);
        }
        else{
            Item_Up.setBackgroundResource(R.color.Unprogress);
            Item_Down.setBackgroundResource(R.color.Unprogress);
        }

    }
}
