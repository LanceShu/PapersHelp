package com.example.race.papershelp.Activity

import android.app.Fragment
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Toast
import com.example.race.papershelp.Content
import com.example.race.papershelp.Fragment.ConsultFragment

import com.example.race.papershelp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_layout.*
import kotlinx.android.synthetic.main.nav_menu.*

class MainActivity : AppCompatActivity() {

    var pref : SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        editor = pref!!.edit()
        Log.e("isNightMode:",Content.isNightMode.toString())
        if(Content.isNightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        setContentView(R.layout.activity_main)

        Content.context = this

        //初始化控件;
        initWight()
    }

    fun initWight(){

        papers_setting.isChecked = Content.isNightMode

        replaceFragment(ConsultFragment())

        //打开菜单按钮;
        main_func.setOnClickListener({
            drawer_layout.openDrawer(GravityCompat.START)
        })
        //办证咨询;
        papers_consult.setOnClickListener({
            /*各种证件的办理流程、所需资料;
            * 申请办理（姓名、电话、身份证、邮箱（可填）、照片资料（可填））；*/

        })
        //我的申请
        papers_me.setOnClickListener({
            /*我申请的证件、申请的进度*/

        })
        //夜间模式
        papers_setting.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, status: Boolean) {
                Content.isNightMode = status
                //重启Activity
                recreate()
            }
        })

        //关于
        papers_me.setOnClickListener({
            /*关于程序*/

        })
    }

    fun replaceFragment(fragment: android.support.v4.app.Fragment){
        Content.fragmentManager = supportFragmentManager
        val transaction = Content.fragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment,fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        editor!!.clear()
        editor!!.putBoolean("isNightMode",Content.isNightMode)
        editor!!.apply()
    }

}
