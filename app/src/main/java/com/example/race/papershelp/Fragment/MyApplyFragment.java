package com.example.race.papershelp.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.race.papershelp.Content;
import com.example.race.papershelp.DataBase.MyDataBaseHelper;
import com.example.race.papershelp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kiboooo on 2017/10/18.
 */

public class MyApplyFragment extends Fragment {

    /*申请项目数组*/
    private List<String> ApplyItem;

    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    private View view;
    private TextView ApplyProgressName,ApplyProgressEmail,ApplyProgressPhone, ApplyProgressIDCard;
    private Spinner ApplyProgressContent;
    private RecyclerView recyclerView;

    //已申请的类型的适配器
    private ArrayAdapter<String> ApplyItemAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDataBaseHelper = new MyDataBaseHelper(getContext(), "PapersDB.db", null, 1);
        db = myDataBaseHelper.getReadableDatabase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.apply_fragment, container, false);
        initWight();
        initApplyMessage();
        return view;
    }

    private void  initWight() {
        ApplyProgressName = (TextView) view.findViewById(R.id.apply_progress_name);
        ApplyProgressIDCard = (TextView) view.findViewById(R.id.apply_progress_idcar);
        ApplyProgressPhone = (TextView) view.findViewById(R.id.apply_progress_phone);
        ApplyProgressEmail = (TextView) view.findViewById(R.id.apply_progress_email);
        ApplyProgressContent = (Spinner) view.findViewById(R.id.apply_progress_spinner);
        recyclerView = (RecyclerView) view.findViewById(R.id.apply_progress_recyclerview);
    }

    private void initApplyMessage() {
        /*根据登陆的账户搜索出他的申请项目，以及展示他的个人信息 更新*/
        cursor = db.query("Apply", null, "aUser = " + Content.uName, null, null, null, null);
        ApplyItem = new ArrayList<>();
        ApplyItem.add("");
        Log.e("initApplyMessage", "applyItem" + ApplyItem.size());
        if (cursor.moveToFirst()) {
            ApplyProgressName.setText(cursor.getString(cursor.getColumnIndex("aName"))
                    ==null? "":cursor.getString(cursor.getColumnIndex("aName")));
            ApplyProgressEmail.setText(cursor.getString(cursor.getColumnIndex("aMail"))
                    ==null?"":cursor.getString(cursor.getColumnIndex("aMail")));
            ApplyProgressPhone.setText(cursor.getString(cursor.getColumnIndex("aPhone"))
                    ==null?"":cursor.getString(cursor.getColumnIndex("aPhone")));
            ApplyProgressIDCard.setText(cursor.getString(cursor.getColumnIndex("aIdentity"))
                    ==null?"":cursor.getString(cursor.getColumnIndex("aIdentity")));
            Log.e("initApplyMessage", "applyItem" + ApplyItem.size());
//            do {
////                ApplyItem.add(cursor.getString(cursor.getColumnIndex("aApply")));
//            } while (cursor.moveToFirst());
            cursor.close();

            ApplyItemAdapter = new ArrayAdapter<>(Content.context, android.R.layout.simple_spinner_item, ApplyItem);
            ApplyItemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ApplyProgressContent.setAdapter(ApplyItemAdapter);

            ApplyProgressContent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    adapterView.setVisibility(View.VISIBLE);
                    initApplyRecyclerVIew(ApplyItem.get(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    adapterView.setVisibility(View.GONE);
                }
            });
        }
    }

    private void initApplyRecyclerVIew(String ApplyType) {
        /*根据申请的类型从数据库中获取相应的申请进度,选择实现不同的的Adapter*/
    }


}
