package com.example.race.papershelp.Activity

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.example.race.papershelp.Content
import com.example.race.papershelp.DataBase.MyDataBaseHelper
import com.example.race.papershelp.R
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.register_layout.*

/**
 * Created by Lance on 2017/10/15.
 */
class RegisterActivity : AppCompatActivity(){

    var isCanSee = false
    var isCanSeeAgagin = false

    var myDataBaseHelper : MyDataBaseHelper? = null
    var db : SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)
        //初始化数据库
        myDataBaseHelper = MyDataBaseHelper(this,"PapersDB.db",null,1)
        db = myDataBaseHelper!!.writableDatabase
        //初始化控件
        initWight()
    }

    fun initWight(){
        //返回按钮
        register_back.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                finish()
            }
        })
        //用户名清除按钮
        register_clear.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                register_num.text.clear()
            }
        })
        //密码是否可见按钮
        register_isCanSee.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                if(isCanSee){
                    register_isCanSee.setImageResource(R.mipmap.eye_cant_see)
                    isCanSee = false
                    register_pass.transformationMethod = PasswordTransformationMethod.getInstance()
                }else{
                    register_isCanSee.setImageResource(R.mipmap.eyes_can_see)
                    isCanSee = true
                    register_pass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
            }
        })

        register_isCanSee_again.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                if(isCanSeeAgagin){
                    register_isCanSee_again.setImageResource(R.mipmap.eye_cant_see)
                    isCanSee = false
                    register_pass_again.transformationMethod = PasswordTransformationMethod.getInstance()
                }else{
                    register_isCanSee_again.setImageResource(R.mipmap.eyes_can_see)
                    isCanSee = true
                    register_pass_again.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
            }
        })

        //注册按钮;
        register_enter.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(register_num.text.length == 0|| register_pass.text.length == 0 || register_pass_again.text.length == 0){
                    Toast.makeText(this@RegisterActivity,"账号或者密码不能为空!",Toast.LENGTH_SHORT).show()
                } else if(register_num.text.length < 11){
                    Toast.makeText(this@RegisterActivity,"请输入正确的手机号码!",Toast.LENGTH_SHORT).show()
                } else if(register_pass.text.toString() != register_pass_again.text.toString()){
                    Toast.makeText(this@RegisterActivity,"两次密码输入不正确!",Toast.LENGTH_SHORT).show()
                } else{
                    val cursor = db!!.query("User",null,"uPhone = "+register_num.text.toString(),null,null,null,null)
                    if(cursor.moveToFirst()){
                        Toast.makeText(this@RegisterActivity,"用户已存在!",Toast.LENGTH_SHORT).show()
                    }else{
                        val values = ContentValues()
                        values.put("uPhone",register_num.text.toString())
                        values.put("uPass",register_pass.text.toString())
                        db!!.insert("User",null,values)
                        Toast.makeText(this@RegisterActivity,"用户注册成功!",Toast.LENGTH_SHORT).show()
                        Content.handler.postDelayed(Runnable { finish() },1000)
                    }
                    cursor.close()
                }
            }
        })
    }
}