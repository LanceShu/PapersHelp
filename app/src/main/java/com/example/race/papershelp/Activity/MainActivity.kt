package com.example.race.papershelp.Activity

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.widget.LinearLayout
import com.example.race.papershelp.Content
import com.example.race.papershelp.Fragment.ConsultFragment

import com.example.race.papershelp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_layout.*
import kotlinx.android.synthetic.main.nav_menu.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Content.context = this
        //初始化控件;
        initWight()
    }

    fun initWight(){

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
        //设置
        papers_setting.setOnClickListener({
            /*清理缓存、意见反馈*/

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
}
