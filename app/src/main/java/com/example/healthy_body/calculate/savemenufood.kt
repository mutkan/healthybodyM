package com.example.healthy_body.calculate

import android.util.Log
import com.example.healthy_body.model.modelsavefood
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class savemenufood(var namefood:String="",val kcal:String="",val unit:String="",val unittype:String="",val amount :Int){

    val ref = FirebaseDatabase.getInstance().getReference("FOOD")
    val namefoodS = namefood
    val kcalS = kcal
    val unitS = unit
    val unittypeS = unittype
    val amountS = amount
    fun save(){

        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for(list in p0.children){
                        val s = list!!.getValue(data::class.java)
                        val key = s!!.id_food
                        val namefood =s!!.namefood
                        val amount =s!!.amount
                        val kcal = s!!.kcal
                        val util =s!!.unit
                        val unittype = s!!.unittype

                        if(namefoodS == namefood){
                            val ref = FirebaseDatabase.getInstance().getReference("FOOD").child(key)
                            val amount = amountS.toString()
                            val childUpdates = HashMap<String, String>()
                            childUpdates.put("namefood", "${namefoodS}")
                            childUpdates.put("amount", "${amount}")
                            childUpdates.put("kcal", "${kcalS}")
                            childUpdates.put("utit", "${unitS}")
                            childUpdates.put("unittype", "${unittypeS}")
                            ref.updateChildren(childUpdates as Map<String, Any>)
                        }else{
                            val amount = amountS.toString()
                            val key = list!!.key
                            val intkey = key!!.toInt()
                            val newkey = intkey + 1
                            val id_food = newkey.toString()
                            val model = modelsavefood(id_food,namefoodS,kcalS,unitS,unittypeS,amount)
                            ref.child("${id_food}").setValue(model)
                        }

                    }
                }else {
                    val amount = amount.toString()
                    val key: Int = 0
                    val newkey = key + 1
                    val id_food = newkey.toString()
                    val model = modelsavefood(id_food,namefood,kcal,unit,unittype,amount)
                    ref.child("${id_food}").setValue(model)
                }
            }
        })
    }


}
class data (val id_food:String ="",val namefood: String="",val amount: String="",val kcal :String="" ,val unit: String ="",val unittype: String="")