package com.vasundhara.vipl24.firebase

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DisplayInfoActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter
    lateinit var dbref:DatabaseReference
     lateinit var empArrayList:ArrayList<Employee>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_info)


        recyclerView=findViewById(R.id.recykal)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        empArrayList= arrayListOf<Employee>()





        EmployeeInfo()



    }


    fun EmployeeInfo(){

        val progressdialog= ProgressDialog(this)
        progressdialog.setMessage("Loading Please Wait...")
        progressdialog.setCancelable(false)
        progressdialog.show()
        dbref=FirebaseDatabase.getInstance().getReference("Employee")

//        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val children=snapshot.children
//
//                for (emp in children){
//                    children.forEach {
//                        val e=Employee()
//                        e.empName=emp.child("Employee/empName").getValue().toString()
//                        e.empAddress=emp.getValue().toString()
//
//                        empArrayList.addAll(listOf(e))
//
//                    }
//                    recyclerView.adapter=MyAdapter(applicationContext, empArrayList)
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })


        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empArrayList= arrayListOf()
                val children=snapshot.children
                if (snapshot.exists()){

                    for (emp in children){

                        val data=emp.getValue(Employee::class.java)
                        empArrayList.add(data!!)
                    }
                    recyclerView.adapter=MyAdapter(applicationContext, empArrayList)

                }
                if (progressdialog.isShowing)progressdialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DisplayInfoActivity, "Failed", Toast.LENGTH_SHORT).show()
                if (progressdialog.isShowing)progressdialog.dismiss()

            }
        })


   }
}