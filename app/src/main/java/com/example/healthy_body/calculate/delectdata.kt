package com.example.healthy_body.calculate

import com.google.firebase.database.FirebaseDatabase
import java.nio.file.Files.delete

class delectdata(val UID:String,val nameExcerciseShowB:String,val kcalExcerciseShowB:String,val resultBig:Int,val sum:Int,val date:String,val idfoodShow:String,val KEY :String,val nametype :String){

    fun deelect():Boolean{
if (nametype.equals("EXCERCISE")){
    val myRef = FirebaseDatabase.getInstance().getReference("SELECTEXCERCISE").child("${UID}").child("${date}").child("${KEY}")


    myRef.removeValue()


}else{
    val myRef = FirebaseDatabase.getInstance().getReference("SELECTFOOD").child("${UID}").child("${date}").child("${KEY}")

    myRef.removeValue()
}
        return true
    }
}