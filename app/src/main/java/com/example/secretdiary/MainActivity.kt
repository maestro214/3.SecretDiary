package com.example.secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val numberPicker1 : NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.numberPicker1).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker2 : NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.numberPicker2).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker3 : NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.numberPicker3).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val openButton : AppCompatButton by lazy{
        findViewById(R.id.openButton)
    }

    private val changePasswordButton : AppCompatButton by lazy{
        findViewById(R.id.changePasswordButton)
    }

    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener {

            if(changePasswordMode){
                Toast.makeText(this,"비밀번호 변경 중입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //내부저장소 쉐어드 프리퍼런스
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if(passwordPreferences.getString("password","000").equals(passwordFromUser)){
                //패스워드 성공

                //TODO 다이어리 페이지 작성 후에 남겨주어야함
                startActivity(Intent(this,DiaryActivity::class.java))
            }else{
                //실패
                showErrorAlertDialog()


            }
        }

        changePasswordButton.setOnClickListener {
            if(changePasswordMode){
                val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

                //ktx문법으로 edit사용한거임
                passwordPreferences.edit(true){

                        val passwordFromUser =
                            "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
                        putString("password", passwordFromUser)


                    }
                    changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)

                }

            else{
                //changePassWordMode 가 활성화 :: 비밀번호가 맞는지 체크

                val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
                val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

                if(passwordPreferences.getString("password","000").equals(passwordFromUser)){
                    //패스워드 성공

                    changePasswordMode = true

                    Toast.makeText(this,"변경할 패스워드를 입력해주세요",Toast.LENGTH_SHORT).show()

                    changePasswordButton.setBackgroundColor(Color.RED)


                }else{
                    //실패
                    showErrorAlertDialog()

                }

            }
       }


    }


    private fun showErrorAlertDialog(){
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호 오류")
            .setPositiveButton("확인"){ dialog,which ->}
            .create().show()


    }
}