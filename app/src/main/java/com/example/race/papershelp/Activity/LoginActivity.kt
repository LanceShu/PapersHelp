package com.example.race.papershelp.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.race.papershelp.Content
import com.example.race.papershelp.DataBase.MyDataBaseHelper
import com.example.race.papershelp.R
import kotlinx.android.synthetic.main.login_activity.*

/**
 * Created by Lance on 2017/10/15.
 */
class LoginActivity : AppCompatActivity(){

    var isCanSee = false

    var myDataBaseHelper : MyDataBaseHelper? = null
    var db : SQLiteDatabase? = null

    var pref : SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        editor = pref!!.edit()
        Content.isNightMode = pref!!.getBoolean("isNightMode",false)
        Content.isRememberPass = pref!!.getBoolean("remember",false)
        Log.e("checkBox:",pref!!.getBoolean("remember",false).toString())
        setContentView(R.layout.login_activity)

        if(Content.isNightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        //初始化SQLite数据库
        myDataBaseHelper = MyDataBaseHelper(this,"PapersDB.db",null,1)
        db = myDataBaseHelper!!.writableDatabase

        //初始化控件;
        initWight()
    }

    fun initWight(){

        if(Content.isRememberPass){
            login_name.setText(pref!!.getString("name",""))
            login_pass.setText(pref!!.getString("pass",""))
            checkBox.isChecked = true
        }

        //登录账号清除按钮;
        login_clear.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                login_name.text.clear()
            }
        })

        //密码是否可见按钮
        login_isCanSee.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                if(isCanSee){
                    login_isCanSee.setImageResource(R.mipmap.eye_cant_see)
                    isCanSee = false
                    login_pass.transformationMethod = PasswordTransformationMethod.getInstance()
                }else{
                    login_isCanSee.setImageResource(R.mipmap.eyes_can_see)
                    isCanSee = true
                    login_pass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
            }
        })

        //登录按钮
        login_enter.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                if(login_name.text.toString().length == 0 || login_pass.text.toString().length == 0){
                    Toast.makeText(this@LoginActivity,"账号密码不能为空!",Toast.LENGTH_SHORT).show()
                }else{
                    val cursor = db!!.query("User",null,"uPhone = " + login_name.text.toString(),null,null,null,null)
                    if(cursor.moveToFirst()){
                        do{
                            if(cursor.getString(cursor.getColumnIndex("uPass")) == login_pass.text.toString()){
                                Content.uName = login_name.text.toString()
                                Content.uPass = login_pass.text.toString()
                                rememberNamePass(login_name.text.toString(),login_pass.text.toString(),checkBox.isChecked)
                                //记住账户密码；
                                val loginToMain = Intent(this@LoginActivity,MainActivity::class.java)
                                startActivity(loginToMain)
                                finish()
                            }else{
                                Toast.makeText(this@LoginActivity,"账号密码不正确，请重新输入!",Toast.LENGTH_SHORT).show()
                            }
                        }while(cursor.moveToNext())
                        cursor.close()
                    }else{
                        Toast.makeText(this@LoginActivity,"用户不存在!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        //注册按钮
        login_register.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val loginToRegisterIntent = Intent(this@LoginActivity,RegisterActivity::class.java)
                startActivity(loginToRegisterIntent)
            }
        })


    }

    private fun rememberNamePass(name: String, pass: String, checked: Boolean) {
        Log.e("checked:",checked.toString())
        if(checked){
            editor!!.putBoolean("remember",true)
            editor!!.putString("name",name)
            editor!!.putString("pass",pass)
            Log.e("remember:","success")
        }else{
            editor!!.putBoolean("remember",false)
            editor!!.putString("name","")
            editor!!.putString("pass","")
            Log.e("remember:","failure")
        }
        editor!!.apply()
    }

}