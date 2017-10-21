package com.example.race.papershelp.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.race.papershelp.R;

/**
 * Created by Lance on 2017/10/20.
 */

public class AboutFragment extends Fragment implements View.OnClickListener{

    private View view;
    //功能介绍;
    private LinearLayout function;
    //意见反馈；
    private LinearLayout suggestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.about_layout,container,false);
        initWight();
        return view;
    }

    private void initWight() {

        function = (LinearLayout) view.findViewById(R.id.about_function);
        suggestion = (LinearLayout) view.findViewById(R.id.about_suggestion);

        function.setOnClickListener(this);
        suggestion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.about_function:
                Dialog dialog = new Dialog(getContext(),R.style.DialogTheme);
                dialog.setContentView(R.layout.about_function);
                TextView aboutFunc = (TextView) dialog.findViewById(R.id.function_about);
                aboutFunc.setText("\n该APP用于高新园区的证件办理，用户可通过该应用进行一些日常证件的办理申请，办理申请通过相关人员审核后" +
                        "，届时将会有相关专员联系您到线下业务厅进行相关的证件办理。\n在此，非常感谢您使用本应用进行高新园区证件办理！");
                dialog.show();
                break;
            case R.id.about_suggestion:
                Toast.makeText(getContext(),"该功能还未开放~敬请期待~",Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }
}
