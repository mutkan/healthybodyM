package com.example.healthy_body

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import com.example.healthy_body.calculate.data
import com.example.healthy_body.calculate.savemenufood_private
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import kotlinx.android.synthetic.main.activity_addfood_user.*
import kotlinx.android.synthetic.main.activity_savedatafood_user.*

class addfood_user_private : AppCompatActivity(), View.OnClickListener {
    //val UID="GRp37lrFluTK2OhZpUc5dTg0Ofa2"
    private var doubleBackToExitPressedOnce = false
    val ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_FOOD")
    var sum  = 1
    var amount :Int=1
    var UID :String=""
var backtohome:String=""
    internal var SPINNERLST = arrayOf("อาหารจานเดี่ยว/กับข้าว","เครื่องดื่ม","ขนม/ของหวาน",
        "ผลไม้")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addfood_user_private)

        UID = intent.getStringExtra("UID")
        if(intent.getStringExtra("backtohome")!=null) backtohome = intent.getStringExtra("backtohome")


        val amount = findViewById<TextView>(R.id.textView6)
        amount.setText("${sum}")

        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLST)
        val betterSpinner = findViewById(R.id.inputtypefood) as MaterialBetterSpinner
        betterSpinner.setAdapter(arrayAdapter)

        val inputname = findViewById<EditText>(R.id.inputname)
        val inputkcal = findViewById<EditText>(R.id.inputkcal)
        val inputunit = findViewById<EditText>(R.id.inputunit)
        val inputtypeunit = findViewById<EditText>(R.id.inputtypeunit)
        var add =findViewById<Button>(R.id.add)
        var sub  = findViewById<Button>(R.id.sub)

        add.setOnClickListener(this)
        sub.setOnClickListener(this)
        inputkcal.setTransformationMethod(null)

        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            if(backtohome !=""){
                val backtohome = "homeselectfood"
                val intent = Intent(this, selectelistfood_user_private::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("backtohome",backtohome)
                startActivity(intent)
                finish()

            }else{
                val intent = Intent(this, selectelistfood_user_private::class.java)
                intent.putExtra("UID",UID)
                startActivity(intent)
                finish()

            }
        }



        buttonaddfood.setOnClickListener {
            val nametypeStatus :String = "SAVE"
            val namefood = inputname.text.toString()
            val kcal = inputkcal.text.toString()
            val unit = inputunit.text.toString()
            val typefood = betterSpinner.text.toString()
            if(namefood!= ""&&kcal!=""&&unit!=""&&typefood!=""&&this.amount!=null){
                savemenufood_private(UID,namefood,kcal,unit,typefood,this.amount).save()
                val progest  = ProgressDialog(this@addfood_user_private,R.style.MyTheme)
                progest.setCancelable(false)
                progest.show()
                Handler().postDelayed({
                    progest.cancel()

                if(backtohome !=""){
                    val backtohome = "homeselectfood"
                    val intent = Intent(this, selectelistfood_user_private::class.java)
                    intent.putExtra("UID",UID)
                    intent.putExtra("nametypeStatus",nametypeStatus)
                    intent.putExtra("backtohome",backtohome)
                    startActivity(intent)
                    finish()

                }else{
                    val intent = Intent(this, selectelistfood_user_private::class.java)
                    intent.putExtra("UID",UID)
                    intent.putExtra("nametypeStatus",nametypeStatus)
                    startActivity(intent)
                    finish()

                }
            }, 1500)
            }else{
                Toast.makeText(this, "Please in put Information Food", Toast.LENGTH_SHORT).show()
            }



        }



    }
    override fun onClick(v: View?) {
        //val sumInt= sum.toInt()

        var subsum = 1
        when (v?.id) {
            R.id.add -> {
                amount = amount + 1
                Log.d("resultBig","$amount")
                // val textsum = reesult.toString()
                textView6.setText("${amount}")

            }
            R.id.sub -> {
                amount = amount - subsum

                if(amount<=1){
                    amount = 1
                }
                Log.d("resultBig","$amount")
                // val textsum = reesult.toString()
                textView6.setText("${amount}")
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
            val intent = Intent(this, selectelistfood_user_private::class.java)
            intent.putExtra("UID",UID)
            intent.putExtra("back_home_add",backtohome)
            startActivity(intent)
            finish()

        }else{
            val intent = Intent(this, selectelistfood_user_private::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            finish()

        }

    }
}
