package com.example.race.papershelp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.race.papershelp.Activity.ApplyActivity;
import com.example.race.papershelp.Content;
import com.example.race.papershelp.DataBase.FindPapers;
import com.example.race.papershelp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lance on 2017/10/16.
 */

public class ConsultFragment extends Fragment implements View.OnClickListener{

    private String searchName;

    //证件类型
    private List<String> typeList;
    //身份证件
    private List<String> identityList;
    //资格证件
    private List<String> qualificationList;
    //关系证件
    private List<String> relationList;
    //证件类型适配器
    private ArrayAdapter<String> typerAdapter;
    //证件适配器
    private ArrayAdapter<String> paperAdapter;

    private View view;

    private Spinner typesSpinner;
    private Spinner papersSpinner;
    private Button search;
    private TextView paperText;
    private LinearLayout searchLayout;
    private Button applyPapers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化数据;
        initData();
    }

    private void initData() {
        typeList = new ArrayList<>();
        identityList = new ArrayList<>();
        qualificationList = new ArrayList<>();
        relationList = new ArrayList<>();

        typeList.add("身份证件");
        typeList.add("资格证件");
        typeList.add("关系证件");

        identityList.add("身份证");
        identityList.add("居住证");
        identityList.add("护照");
        identityList.add("港澳通行证");

        qualificationList.add("营运证");
        qualificationList.add("营业执照");
        qualificationList.add("卫生许可证");
        qualificationList.add("国有土地使用证");

        relationList.add("结婚证");
        relationList.add("离婚证");
        relationList.add("单身证明");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.papers_consult_layout,container,false);
        //初始化控件;
        initWight();
        return view;
    }

    private void initWight() {

        typesSpinner = (Spinner) view.findViewById(R.id.papersTypes);
        papersSpinner = (Spinner) view.findViewById(R.id.papers);
        search = (Button) view.findViewById(R.id.papers_search);
        paperText = (TextView) view.findViewById(R.id.papers_material);
        searchLayout = (LinearLayout) view.findViewById(R.id.search_layout);
        applyPapers = (Button) view.findViewById(R.id.papers_apply_btn);

        search.setOnClickListener(this);
        applyPapers.setOnClickListener(this);

        typerAdapter = new ArrayAdapter<String>(Content.context,android.R.layout.simple_spinner_item,typeList);
        typerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typesSpinner.setAdapter(typerAdapter);
        typesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.setVisibility(View.VISIBLE);
                showTypePapers(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setVisibility(View.GONE);
            }
        });
    }

    private void showTypePapers(int i) {
        switch (i){
            case 0:
                paperAdapter = new ArrayAdapter<String>(Content.context,android.R.layout.simple_spinner_item,identityList);
                break;
            case 1:
                paperAdapter = new ArrayAdapter<String>(Content.context,android.R.layout.simple_spinner_item,qualificationList);
                break;
            case 2:
                paperAdapter = new ArrayAdapter<String>(Content.context,android.R.layout.simple_spinner_item,relationList);
                break;
            default:
                break;
        }
        paperAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        papersSpinner.setAdapter(paperAdapter);
        papersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                searchName = paperAdapter.getItem(i);
                //Toast.makeText(Content.context,paperAdapter.getItem(i)+"",Toast.LENGTH_SHORT).show();
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.papers_search:
                searchLayout.setVisibility(View.VISIBLE);
                paperText.setText(FindPapers.INSTANCE.findPapers(searchName));
                break;
            case R.id.papers_apply_btn:
                Intent mainToApply = new Intent(Content.context, ApplyActivity.class);
                startActivity(mainToApply);
                break;
        }
    }
}
