package com.example.race.papershelp.Fragment;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private List<String> ApplyName;

    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    private View view;
    private TextView ApplyProgressEmail,ApplyProgressPhone, ApplyProgressIDCard;
    private Spinner ApplyProgressContent,ApplyProgressName;
    private RecyclerView recyclerView;

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
        ApplyProgressName = (Spinner) view.findViewById(R.id.apply_progress_name);
        ApplyProgressIDCard = (TextView) view.findViewById(R.id.apply_progress_idcar);
        ApplyProgressPhone = (TextView) view.findViewById(R.id.apply_progress_phone);
        ApplyProgressEmail = (TextView) view.findViewById(R.id.apply_progress_email);
        ApplyProgressContent = (Spinner) view.findViewById(R.id.apply_progress_spinner);
        recyclerView = (RecyclerView) view.findViewById(R.id.apply_progress_recyclerview);
    }

    private void initApplyMessage() {
        /*根据登陆的账户搜索出他的申请项目，以及展示他的个人信息 更新*/
        cursor = db.query("Apply", null, "aUser = " + Content.uName, null, null, null, null);
        ApplyName = new ArrayList<>();
        ApplyName.add("");
        if (cursor.moveToFirst()) {
            do {
                ApplyName.add(cursor.getString(cursor.getColumnIndex("aName")));
            } while (cursor.moveToNext());
            ArrayAdapter<String> applyNameAdapter = new ArrayAdapter<>(Content.context, android.R.layout.simple_spinner_item, ApplyName);
            applyNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ApplyProgressName.setAdapter(applyNameAdapter);

            ApplyProgressName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    adapterView.setVisibility(View.VISIBLE);
                    initApplyItem(ApplyName.get(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    adapterView.setVisibility(View.GONE);
                }
            });
        }
        cursor.close();
    }

    private void initApplyItem(final String ApplyPersonName) {
        if (!ApplyPersonName.equals("")) {
            try {
                Cursor cursor = db.rawQuery("select * from Apply where aUser = " + Content.uName
                        + " and aName = ?" , new String[]{ApplyPersonName});

                if (cursor.moveToFirst()) {
                    ApplyItem = new ArrayList<>();
                    ApplyItem.add("");

                    ApplyProgressEmail.setText(cursor.getString(cursor.getColumnIndex("aMail")));
                    ApplyProgressPhone.setText(cursor.getString(cursor.getColumnIndex("aPhone")));
                    ApplyProgressIDCard.setText(cursor.getString(cursor.getColumnIndex("aIdentity")));

                    do {
                        ApplyItem.add(cursor.getString(cursor.getColumnIndex("aApply")));
                    } while (cursor.moveToNext());

                    ArrayAdapter<String> applyItemAdapter = new ArrayAdapter<>(Content.context, android.R.layout.simple_spinner_item, ApplyItem);
                        applyItemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ApplyProgressContent.setAdapter(applyItemAdapter);

                        ApplyProgressContent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            adapterView.setVisibility(View.VISIBLE);
                            initApplyRecyclerVIew(ApplyPersonName, ApplyItem.get(i));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            adapterView.setVisibility(View.GONE);
                        }
                    });
                }
                cursor.close();
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "用户不存在，请核实！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void initApplyRecyclerVIew(String Name, String ApplyType) {
        /*根据申请的类型从数据库中获取相应的申请进度,选择实现不同的的Adapter*/
    }


}
