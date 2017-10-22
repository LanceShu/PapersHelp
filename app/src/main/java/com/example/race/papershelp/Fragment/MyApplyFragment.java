package com.example.race.papershelp.Fragment;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.race.papershelp.Adapter.ApplyProgressAdapter;
import com.example.race.papershelp.Bean.ApplyProgressData;
import com.example.race.papershelp.Content;
import com.example.race.papershelp.DataBase.FindPapers;
import com.example.race.papershelp.DataBase.MyDataBaseHelper;
import com.example.race.papershelp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kiboooo on 2017/10/18.
 */

public class MyApplyFragment extends Fragment {

    /*申请项目数组*/
    private List<String> ApplyItem;
    private List<String> ApplyName;

    private SQLiteDatabase db;

    private View view;
    private TextView ApplyProgressEmail,ApplyProgressPhone, ApplyProgressIDCard;
    private Spinner ApplyProgressContent,ApplyProgressName;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(getContext(), "PapersDB.db", null, 1);
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
        /*绑定布局*/
        ApplyProgressName = (Spinner) view.findViewById(R.id.apply_progress_name);
        ApplyProgressIDCard = (TextView) view.findViewById(R.id.apply_progress_idcar);
        ApplyProgressPhone = (TextView) view.findViewById(R.id.apply_progress_phone);
        ApplyProgressEmail = (TextView) view.findViewById(R.id.apply_progress_email);
        ApplyProgressContent = (Spinner) view.findViewById(R.id.apply_progress_spinner);
        recyclerView = (RecyclerView) view.findViewById(R.id.apply_progress_recyclerview);
    }

    private void initApplyMessage() {
        /*根据登陆的账户搜索出他的申请项目，以及展示他的个人信息 更新*/
        Cursor cursor = db.query("Apply", null, "aUser = " + Content.uName, null, null, null, null);
        ApplyName = new ArrayList<>();
        ApplyName.add("");
        if (cursor.moveToFirst()) {
            do {
                String base = cursor.getString(cursor.getColumnIndex("aName"));
                if(ApplyName.indexOf(base)< 0)
                    ApplyName.add(base);
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
        /*获取申请人所申请的所有表的数据*/
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
        try {
            Cursor cursor = db.rawQuery("select * from Apply where aUser = " + Content.uName
                    + " and aName = ?" + " and aApply = ?", new String[]{Name, ApplyType});
            List<ApplyProgressData> DataShow = new ArrayList<>();
            String[] MSG = FindPapers.INSTANCE.findProgressMessage(ApplyType);

            if (cursor.moveToFirst()) {
                /*根据进度和申请类型获取相应的 DataList */
                int all = cursor.getInt(cursor.getColumnIndex("aAllProgress"));
                int now = cursor.getInt(cursor.getColumnIndex("aNowProgress"));
                for (int i = 0; i < all; i++) {
                    ApplyProgressData Data = new ApplyProgressData();
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat simpleDateFormat =
                            new SimpleDateFormat("yyyy-MM-dd HH:MM", Locale.CHINA);
                    String[] TimeAndDay = simpleDateFormat.format(date).split(" ");
                    Data.setDay(TimeAndDay[0]);
                    Data.setTime(TimeAndDay[1]);
                    Data.setProgressBarColor(i <= now);
                    /*根据 申请类型选择相应的提示信息*/
                    Data.setProgressContent(MSG != null ? MSG[i] : "");
                    /*根据 申请类型选择相应的图标文件*/
                    Data.setImage(GetApplyBitmap(ApplyType));
                    DataShow.add(Data);
                }
            }
            cursor.close();

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false));
            ApplyProgressAdapter adapter = new ApplyProgressAdapter(DataShow, getContext());
            recyclerView.setAdapter(adapter);

        } catch (SQLException e) {
            Toast.makeText(getContext(), "申请完毕", Toast.LENGTH_SHORT).show();
        }
    }

    /*获取相应的显示图片*/
    private Bitmap GetApplyBitmap(String ApplyName) {
        if (ApplyName.equals("身份证"))
            return BitmapFactory.decodeStream(getResources().openRawResource(+ R.drawable.idcar));//找出为什么

        if (ApplyName.equals("居住证"))
            return BitmapFactory.decodeStream(getResources().openRawResource(+ R.drawable.residence));

        if (ApplyName.equals("护照"))
            return BitmapFactory.decodeStream(getResources().openRawResource(+ R.drawable.passport));

        if (ApplyName.equals("港澳通行证"))
            return BitmapFactory.decodeStream(getResources().openRawResource(+ R.drawable.exit_permit));

        if (ApplyName.equals("营运证"))
            return BitmapFactory.decodeStream(getResources().openRawResource(+ R.drawable.charter));

        if (ApplyName.equals("营业执照"))
            return BitmapFactory.decodeStream(getResources().openRawResource(+ R.drawable.trading_card));

        if (ApplyName.equals("卫生许可证"))
            return BitmapFactory.decodeStream(getResources().openRawResource(+ R.drawable.health_license));

        if (ApplyName.equals("国有土地使用证"))
            return BitmapFactory.decodeStream(getResources().openRawResource(+ R.drawable.landcard));

        if (ApplyName.equals("结婚证"))
            return BitmapFactory.decodeStream(getResources().openRawResource(+ R.drawable.marriage_license));

        if (ApplyName.equals("离婚证"))
            return BitmapFactory.decodeStream(getResources().openRawResource(+ R.drawable.divorce_certificate));

        if (ApplyName.equals("单身证明"))
            return BitmapFactory.decodeStream(getResources().openRawResource(+ R.drawable.dog));

        return null;
    }

}
