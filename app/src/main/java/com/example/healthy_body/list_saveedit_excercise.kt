package com.example.healthy_body

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.healthy_body.calculate.delectdata
import com.example.healthy_body.calculate.savetotalkcal
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_list_saveedit_food.*
import kotlinx.android.synthetic.main.activity_savedatafood_user.*
import kotlinx.android.synthetic.main.activity_savedatafood_user.amount
import kotlinx.android.synthetic.main.activity_savedatafood_user.tatal
import java.util.*

class list_saveedit_excercise : AppCompatActivity(), View.OnClickListener {

    private var doubleBackToExitPressedOnce = false
    var nameExcerciseShowB: String = ""
    var kcalExcerciseShowB: String = ""
    var sum = 0
    var resultBig: Int = 0
    var UID: String = ""
    var idfoodShow: String = ""
    var newsum: Int? = null
    var statusdoting: String = ""
    var date: String = ""
    var KEY :String=""
    var numberbackpage :String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_saveedit_excercise)
        var add = findViewById<Button>(R.id.add)
        var sub = findViewById<Button>(R.id.sub)
        if (intent.getStringExtra("numberbackpage")!=null) numberbackpage = intent.getStringExtra("numberbackpage")


        KEY = intent.getStringExtra("key")
        UID = intent.getStringExtra("UID")
        nameExcerciseShowB = intent.getStringExtra("nameExcerciseShowB")
        kcalExcerciseShowB = intent.getStringExtra("kcalExcerciseShowB")
        resultBig = intent.getIntExtra("resultBig", resultBig)
        sum = intent.getIntExtra("sum", sum)
        date = intent.getStringExtra("date")
        idfoodShow = intent.getStringExtra("id")
        val resultBigfoundation = resultBig

        val textdate = findViewById<TextView>(R.id.date)
        val textanmefood = findViewById<TextView>(R.id.nameexcercise)
        val textkcal = findViewById<TextView>(R.id.Kcal)
        val textamount = findViewById<TextView>(R.id.amount)
        val texttotal = findViewById<TextView>(R.id.tatal)

        add.setOnClickListener(this)
        sub.setOnClickListener(this)


        textdate.setText(date)
        textanmefood.setText(nameExcerciseShowB)
        textkcal.setText(kcalExcerciseShowB)
        textamount.setText("${sum}")
        texttotal.setText("${resultBig}")

        val arrow = findViewById<ImageView>(R.id.arrow)
        val delect = findViewById<ImageView>(R.id.dalect)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        delect.setOnClickListener {
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("คุณเเน่ใจ?")
                .setContentText("ว่าต้องการลบรายการนี้!")
                .setCancelText("ไม่ต้องการ!")
                .setConfirmText("ต้องการ!")
                .showCancelButton(true)
                .setCancelClickListener { sDialog -> sDialog.cancel() }
                .setConfirmClickListener {
                    val nametype: String = "EXCERCISE"
                    val nametypeStatus: String = "Remove"
                    val statusdoting = ""

                    if(resultBig  > resultBigfoundation || resultBig < resultBigfoundation) {
                        val resultB =  delectdata(UID,nameExcerciseShowB, kcalExcerciseShowB, resultBigfoundation, sum, date, idfoodShow,KEY,nametype).deelect()
                        Log.e("resultB","${resultB}")
                        savetotalkcal(resultBigfoundation, nametype, UID, statusdoting, nametypeStatus, date).savetotal()


                        val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
                        pDialog.titleText = "กำลังทำการลบรายการ"
                        pDialog.setCancelable(false)
                        if (resultB.equals(true)){
                            pDialog.setCancelable(true)
                            pDialog
                                .setTitleText("ลบเรียบร้อยเเล้ว!")
                                .setContentText("ข้อมูลนี้จะไม่มีอยู่รายการของคุณ!").setConfirmText("ตกลง")
                                .setConfirmClickListener{
                                    if(numberbackpage =="2"){
                                        val backtohome = "homeselectexcercise"
                                        val intent = Intent(this, Home_User::class.java)
                                        intent.putExtra("UID",UID)
                                        intent.putExtra("backtohome","$backtohome")
                                        startActivity(intent)
                                        finish()
                                    }else{
                                        val intent = Intent(this, list_edit_excercise::class.java)
                                        intent.putExtra("UID",UID)
                                        startActivity(intent)
                                        finish()
                                    }


                                }
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                        }
                        pDialog.show()
                    }else{

                        val resultB =  delectdata(UID,nameExcerciseShowB, kcalExcerciseShowB, resultBig, sum, date, idfoodShow,KEY,nametype).deelect()
                        Log.e("resultB","${resultB}")
                        savetotalkcal(resultBig, nametype, UID, statusdoting, nametypeStatus, date).savetotal()


                        val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
                        pDialog.titleText = "กำลังทำการลบรายการ"
                        pDialog.setCancelable(false)
                        if (resultB.equals(true)){
                            pDialog.setCancelable(true)
                            pDialog
                                .setTitleText("ลบเรียบร้อยเเล้ว!")
                                .setContentText("ข้อมูลนี้จะไม่มีอยู่รายการของคุณ!").setConfirmText("ตกลง")
                                .setConfirmClickListener{
                                    if(numberbackpage =="2"){
                                        val backtohome = "homeselectexcercise"
                                        val intent = Intent(this, Home_User::class.java)
                                        intent.putExtra("UID",UID)
                                        intent.putExtra("backtohome","$backtohome")
                                        startActivity(intent)
                                        finish()
                                    }else{
                                        val intent = Intent(this, list_edit_excercise::class.java)
                                        intent.putExtra("UID",UID)
                                        startActivity(intent)
                                        finish()
                                    }


                                }
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                        }
                        pDialog.show()
                    }



                }
                .show()
        }
        arrow.setOnClickListener {
            if(numberbackpage =="2"){
                val backtohome = "homeselectexcercise"
                val intent = Intent(this, Home_User::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("backtohome","$backtohome")
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, list_edit_excercise::class.java)
                intent.putExtra("UID",UID)
                startActivity(intent)
                finish()
            }
        }



        savelist.setOnClickListener {
            val nametype: String = "EXCERCISE"
            val nametypeStatus: String = "Edit"
            updatetodata(nameExcerciseShowB, kcalExcerciseShowB, resultBig, sum, date, idfoodShow,KEY)
        //    savetotalkcal(newsum!!, nametype, UID, statusdoting, nametypeStatus, date).savetotal()
            if(newsum == null) {
                Log.e("sub standas","$newsum")
                newsum = 0
                savetotalkcal(
                    newsum!!,
                    nametype,
                    UID,
                    statusdoting,
                    nametypeStatus,
                    date
                ).savetotal()

            }else{
                savetotalkcal(newsum!!,nametype,UID,statusdoting,nametypeStatus,date).savetotal()
            }




            if(numberbackpage =="2"){
                val backtohome = "homeselectexcercise"
                val intent = Intent(this, Home_User::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("backtohome","$backtohome")
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, list_edit_excercise::class.java)
                intent.putExtra("UID",UID)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun updatetodata(
        nameFoodShowB: String?,
        kcalfoodShowB: String?,
        resultBig: Int,
        sum: Int,
        currentDate: String,
        idfoodShow: String,
        KEY:String
    ) {

        val ref = FirebaseDatabase.getInstance().getReference("SELECTEXCERCISE").child("${UID}").child("$currentDate").child("$KEY")
        val childUpdates = HashMap<String, Any>()
        childUpdates.put("sum", sum)
        childUpdates.put("resultBig", resultBig)
        ref.updateChildren(childUpdates as Map<String, Any>)

    }

    override fun onClick(v: View?) {
        Log.d("kcalfoodShow", "${nameExcerciseShowB}")
        Log.d("kcalfoodShow", "${kcalExcerciseShowB}")
        var sumkcalsub = kcalExcerciseShowB.toInt()
        Log.d("kcalfoodShow", "${sumkcalsub}")
        var sumkcal = kcalExcerciseShowB.toInt()

        when (v?.id) {
            R.id.add -> {
                sum = sum + 1
                statusdoting = "Add"
               // newsum = newsum - sumkcal
                resultBig = sumkcal * sum
                if(newsum == null){
                    newsum=0
                    newsum = newsum?.minus(sumkcal)
                    Log.e("newsum Add (newsum == null)", "${newsum}")

                }else if(newsum != null) {
                    if(newsum != 0){
                        newsum = newsum?.minus(sumkcal)
                        Log.e("newsum Add (else)", "${newsum}")
                    } else{
                        newsum = newsum?.minus(sumkcal)
                        Log.e("newsum Add (else)", "${newsum}")
                    }
                }

                Log.d("sumkcal add =>", "${sum}")
                amount.setText("$sum")
                tatal.setText("$resultBig")
            }
            R.id.sub -> {
                sum = sum - 1
                statusdoting = "de"
                //newsum = newsum + sumkcal
                resultBig = resultBig - sumkcalsub

                if(sum <1 &&newsum == null){  //กรณีที่ จำนวนเป็น 1 ตั่งเเต่ต้น
                    sum = 1
                    this.resultBig=sumkcalsub
                    newsum=null
                    Log.e("newsum sub (sum<1)", "${newsum}")
                }else if (newsum == null){//กรณีที่ไม่ถูกกดเพิ่ม
                    newsum=0
                    newsum = newsum?.plus(sumkcal)
                    Log.e("newsum sub (newsum == null)", "${newsum}")
                    v.isEnabled = true
                }else if (newsum != null){
                    if(newsum!!>=0){


                        if (sum < 1 ){
                            sum = 1
                            this.resultBig=sumkcalsub
                            Log.e("newsum sub (else if)", "${newsum}")
                        }else{
                            newsum = newsum?.plus(sumkcal)
                            Log.e("newsum sub (else)", "${newsum}")
                        }

                    }
                    else{
                        newsum = newsum?.plus(sumkcal)
                        Log.e("newsum sub (else)", "${newsum}")
                    }

                }
                Log.d("sumkcal sub =>", "${resultBig}")
                Log.d("sum", "${sum}")
                amount.setText("$sum")
                tatal.setText("$resultBig")
            }
            else -> {

            }
        }
    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        if(numberbackpage =="2"){
            val backtohome = "homeselectexcercise"
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            intent.putExtra("backtohome","$backtohome")
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, list_edit_excercise::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            finish()
        }

    }
}
