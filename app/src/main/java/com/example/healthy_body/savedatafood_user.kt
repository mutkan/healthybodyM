package com.example.healthy_body

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.healthy_body.calculate.dataex
import com.example.healthy_body.calculate.savetotalkcal
import com.example.healthy_body.model.modelSelectExcercise
import com.example.healthy_body.model.modelSelectFood
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_savedatafood_user.*
import kotlinx.android.synthetic.main.activity_selectlistfood_user.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class savedatafood_user : AppCompatActivity(), View.OnClickListener {
    private var doubleBackToExitPressedOnce = false
    var nameFoodShowB :String=""
    var kcalfoodShowB :String=""
    var sum = 1
    var resultBig :Int=0
    var UID :String=""
    var backtohome :String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savedatafood_user)

        if(intent.getStringExtra("backtohome")!=null) backtohome = intent.getStringExtra("backtohome")


        Log.e("backtohome","$backtohome")
        supportActionBar?.title ="Show Select Food"
        UID = intent.getStringExtra("UID")
        var nameFoodShow: String = intent.getStringExtra("namefood")
        var kcalfoodShow: String = intent.getStringExtra("kcalfood")
        var idfoodShow: String = intent.getStringExtra("id")

        nameFoodShowB = nameFoodShow
        kcalfoodShowB = kcalfoodShow
        val calendar = Calendar.getInstance()
        var currentDate = DateFormat.getDateInstance().format(calendar.time)
        var datetextview =  findViewById<TextView>(R.id.date)
        datetextview.setText(currentDate)
        var add =findViewById<Button>(R.id.add)
        var sub  = findViewById<Button>(R.id.sub)
        val amount = findViewById<TextView>(R.id.amount)
        val tatal = findViewById<TextView>(R.id.tatal)

        var sumkcal = kcalfoodShowB.toInt()
        resultBig = sumkcal
        amount.setText("${sum}")
        tatal.setText("$sumkcal")
        add.setOnClickListener(this)
        sub.setOnClickListener(this)
        namefood.setText(nameFoodShow)
        Kcal.setText(kcalfoodShow)


        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)

        arrow.setOnClickListener {
            if(backtohome != ""){
                val intent = Intent(this, selectlistfood_user::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("back_home_add",backtohome)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, selectlistfood_user::class.java)
                intent.putExtra("UID",UID)
                startActivity(intent)
                finish()
            }

        }

        savelist.setOnClickListener {

            var numberadd :Int = 1
            val nametype :String= "FOOD"
            val nametypeStatus :String = "SAVE"
            val statusdoting :String = ""
            val date =""
            savetodata(nameFoodShowB,kcalfoodShowB,resultBig,sum,currentDate,idfoodShow)
            savetotalkcal(resultBig,nametype,UID,statusdoting,nametypeStatus,date).savetotal()
            val progest  = ProgressDialog(this@savedatafood_user,R.style.MyTheme)
            progest.setCancelable(false)
            progest.show()
            Handler().postDelayed({
                progest.cancel()
                if(backtohome !=""){
                    val backtohome = "homeselectfood"
                    val intent = Intent(this, selectlistfood_user::class.java)
                    intent.putExtra("UID",UID)
                    intent.putExtra("back_home_add",backtohome)
                    intent.putExtra("nametypeStatus",nametypeStatus)
                    startActivity(intent)
                    finish()

                }else{
                    val intent = Intent(this,selectlistfood_user::class.java)
                    intent.putExtra("number",numberadd.toString())
                    intent.putExtra("UID",UID)
                    intent.putExtra("nametypeStatus",nametypeStatus)
                    startActivity(intent)
                    finish()

                }



            }, 1500)


        }

    }
    fun savetodata(nameFoodShowB: String, kcalfoodShowB: String, resultBig: Int, sum: Int,currentDate:String,id:String) {
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDater = sdf.format(Date())
        val date = SimpleDateFormat("dd-M-yyyy").parse("${currentDater}")
        val testtime = date.time
        val timeLong = date.time
        val timeString = timeLong.toString()
        Log.d("output","${currentDate}")
        val ref = FirebaseDatabase.getInstance().getReference("SELECTFOOD").child("${UID}").child("$currentDater")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()) {
                    val q = ref.orderByKey().limitToLast(1)
                    q.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            for (list in p0.children){

                                val d = list.getKey()
                                val intkey = d!!.toInt()
                                val newkey = intkey + 1
                                val id_list = newkey.toString()
                                val settext = modelSelectFood(id_list,nameFoodShowB,kcalfoodShowB,resultBig,sum,currentDater,id)
                                ref.child("${id_list}").setValue(settext)
                            }



                        }
                    })
                }else{

                    val key: Int = 0
                    val newkey = key + 1
                    val id_list = newkey.toString()
                    val settext = modelSelectFood(id_list,nameFoodShowB,kcalfoodShowB,resultBig,sum,currentDater,id)
                    ref.child("${id_list}").setValue(settext)
                    ref.child("${id_list}").setValue(settext)

                }
            }
        })


    }
    override fun onClick(v: View?) {
        var sumkcalsub = kcalfoodShowB.toInt()
        var sumkcal = kcalfoodShowB.toInt()

        when (v?.id) {
            R.id.add -> {
                sum = sum + 1
                resultBig = sumkcal*sum
                amount.setText("$sum")
                tatal.setText("$resultBig")
            }
            R.id.sub -> {
                sum = sum - 1
                resultBig = resultBig - sumkcalsub
                if(sum<=1){
                    sum = 1
                    resultBig=sumkcalsub
                }
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

        if(backtohome !=""){
            val backtohome = "homeselectfood"
            val intent = Intent(this, selectlistfood_user::class.java)
            intent.putExtra("UID",UID)
            intent.putExtra("back_home_add",backtohome)
            startActivity(intent)
            finish()

        }else{
            val intent = Intent(this, selectlistfood_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            finish()

        }


    }
}
class datafood (val id:String ="",val nameFoodShowB: String="",val kcalfoodShowB :String="",val sum :Int, val resultBig : Int ,val date :String ,val id_list :String )