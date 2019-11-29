package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import kotlinx.android.synthetic.main.activity_register__infirmation.*
import kotlinx.android.synthetic.main.activity_totalkcal__user.*
import java.util.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class Register_Infirmation : AppCompatActivity() {

    internal var SPINNERLST = arrayOf("low workout","normal workout to 1-3 time a week","normal workout to 4-5 time a week",
        "heavy workout to 6-7 time a week","heaviest workout over to 2 time a day")

    //  val BMR = 0
    //  val TDEE = 0
    //  var age = inputage.text.toString()
    //  var i = Integer.parseInt(inputage.getText())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register__infirmation)


        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLST)
        val betterSpinner = findViewById(R.id.android_material_desgn_spinner) as MaterialBetterSpinner

        // val BMI =

        betterSpinner.setAdapter(arrayAdapter)
        registerbutton.setOnClickListener {
            val resultSPIN = betterSpinner.text.toString()

            //  Log.d("age4","$aget")
            //  if (resultSPIN == "low workout"){

            //  }
            val intent = Intent(this, Totalkcal_User::class.java)
            startActivity(intent)

        }
    }



}
