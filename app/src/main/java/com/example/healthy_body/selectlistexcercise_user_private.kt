package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.healthy_body.model.modellistexcercise
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_selectlistfood_user.*
import kotlinx.android.synthetic.main.list_food.view.*

class selectlistexcercise_user_private : AppCompatActivity() {

    //var UID :String="GRp37lrFluTK2OhZpUc5dTg0Ofa2"
    private var doubleBackToExitPressedOnce = false
    var UID :String=""
    var searchtext :String=""
var backtohome:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectlistexcercise_user_private)

        UID = intent.getStringExtra("UID")
        if(intent.getStringExtra("backtohome")!=null) backtohome =  intent.getStringExtra("backtohome")

        if (intent.getStringExtra("nametypeStatus") != null){
            baranimation.isVisible =true
            val slide_down = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_down)
            slide_down.setFillAfter(true)
            baranimation.startAnimation(slide_down);

            Handler().postDelayed({
                val slide_up = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up)
                baranimation.startAnimation(slide_up);
                baranimation.isVisible = false
            }, 3000)
        }


        val adapter = GroupAdapter<ViewHolder>()
        mRecycleVeiew.adapter = adapter

        loadexcercise(searchtext)

        var search = findViewById<EditText>(R.id.Searching)
        sreachtext.setOnClickListener {
            searchtext = search.text.toString()
            loadexcercise(searchtext)
        }


        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)

        arrow.setOnClickListener {


            if(backtohome !=""){
                val backtohome = "homeselectexcercise"
                val intent = Intent(this, selectlistexcercise_user::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("back_home_add",backtohome)
                startActivity(intent)
                finish()

            }else{

                val intent = Intent(this, selectlistexcercise_user::class.java)
                intent.putExtra("UID", UID)
                startActivity(intent)
                finish()

            }



        }

        val imageView = findViewById<ImageView>(R.id.addexcercise_private) as ImageView
        imageView.setOnClickListener {
            if(backtohome !=""){
                val backtohome = "homeselectexcercise"
                val intent = Intent(this, addexcerciser_user_private::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("backtohome",backtohome)
                startActivity(intent)
                finish()

            }else{

                val intent = Intent(this, addexcerciser_user_private::class.java)
                intent.putExtra("UID", UID)
                startActivity(intent)
                finish()

            }


        }
    }
    fun loadexcercise(s: String) {
        val ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_EXCERCISE").child("${UID}")
        if (s != null) {
            val firebaseSrarchQuery: Query =
                ref.orderByChild("name_excercise").startAt(s).endAt(s + "\uf8ff")
            Log.d("firebaseSrarchQuery", "${firebaseSrarchQuery}")
            val adapter = GroupAdapter<ViewHolder>()
            firebaseSrarchQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                   if (p0.exists()) {
                       p0.children.forEach {
                           Log.d("text", it.toString())
                           val excercise = it.getValue(modellistexcercise::class.java)
                           if (excercise != null) {
                               adapter.add(Excercise(excercise))
                           }
                       }
                       adapter.setOnItemClickListener { item, view ->
                           val excerciseitem = item as Excercise

                           if (backtohome != "") {
                               val backtohome = "homeselectexcercise"
                               val intent =
                                   Intent(view.context, savedataexcercise_user_private::class.java)
                               intent.putExtra("UID", UID)
                               intent.putExtra("backtohome", backtohome)
                               intent.putExtra(
                                   "nameexcercise",
                                   excerciseitem.excercise.name_excercise
                               )
                               intent.putExtra("kcalexcercise", excerciseitem.excercise.kcal)
                               intent.putExtra("id", excerciseitem.excercise.id_excercise)
                               startActivity(intent)
                               finish()

                           } else {

                               val intent =
                                   Intent(view.context, savedataexcercise_user_private::class.java)
                               intent.putExtra("UID", UID)
                               intent.putExtra(
                                   "nameexcercise",
                                   excerciseitem.excercise.name_excercise
                               )
                               intent.putExtra("kcalexcercise", excerciseitem.excercise.kcal)
                               intent.putExtra("id", excerciseitem.excercise.id_excercise)
                               startActivity(intent)
                               finish()

                           }

                       }
                       mRecycleVeiew.adapter = adapter

                   } else{
                        SweetAlertDialog(this@selectlistexcercise_user_private, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ไม่ค้นพบที่ค้นหา")
                            .setContentText("กรุณากรอกข้อมูลให้ถูกต้อง")
                            .setConfirmText("ต้องการ")
                            .showCancelButton(true)
                            .setCancelClickListener { sDialog -> sDialog.cancel() }
                            .show()
                    }
                    }



                override fun onCancelled(p0: DatabaseError) {

                }


            })
        } else {
            val adapter = GroupAdapter<ViewHolder>()
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        Log.d("text", it.toString())
                        val excercise = it.getValue(modellistexcercise::class.java)
                        if (excercise != null) {
                            adapter.add(Excercise(excercise))
                        }
                    }
                    adapter.setOnItemClickListener { item, view ->
                        val excerciseitem= item as Excercise

                        if(backtohome !=""){
                            val backtohome = "homeselectexcercise"
                            val intent = Intent(view.context, savedataexcercise_user_private::class.java)
                            intent.putExtra("UID",UID)
                            intent.putExtra("backtohome",backtohome)
                            intent.putExtra("nameexcercise", excerciseitem.excercise.name_excercise)
                            intent.putExtra("kcalexcercise", excerciseitem.excercise.kcal)
                            intent.putExtra("id", excerciseitem.excercise.id_excercise)
                            startActivity(intent)
                            finish()

                        }else{

                            val intent = Intent(view.context, savedataexcercise_user_private::class.java)
                            intent.putExtra("UID",UID)
                            intent.putExtra("nameexcercise", excerciseitem.excercise.name_excercise)
                            intent.putExtra("kcalexcercise", excerciseitem.excercise.kcal)
                            intent.putExtra("id", excerciseitem.excercise.id_excercise)
                            startActivity(intent)

                        }



                    }
                    mRecycleVeiew.adapter = adapter
                }

                override fun onCancelled(p0: DatabaseError) {

                }


            })



        }

    }
    inner class Excercise(val excercise: modellistexcercise) : Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            Log.d("name_food", excercise.name_excercise)
            Log.d("kcal_String", excercise.kcal)
            viewHolder.itemView.name.text = excercise.name_excercise
            viewHolder.itemView.kcal.text = excercise.kcal
            Log.d("viewHolder", "${viewHolder}")


        }

        override fun getLayout(): Int {
            return R.layout.list_food
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true

        if(backtohome !=""){
            val backtohome = "homeselectexcercise"
            val intent = Intent(this, selectlistexcercise_user::class.java)
            intent.putExtra("UID",UID)
            intent.putExtra("back_home_add",backtohome)
            startActivity(intent)
            finish()

        }else{

            val intent = Intent(this, selectlistexcercise_user::class.java)
            intent.putExtra("UID", UID)
            startActivity(intent)
            finish()

        }


    }
}
