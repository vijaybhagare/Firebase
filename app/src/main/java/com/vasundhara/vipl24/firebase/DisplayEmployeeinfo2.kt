package com.vasundhara.vipl24.firebase

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DisplayEmployeeinfo2 : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter
    lateinit var dbref: DatabaseReference
    lateinit var empArrayList:ArrayList<Employee>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_employeeinfo2)

        recyclerView=findViewById(R.id.recykal1)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        empArrayList= arrayListOf<Employee>()





        EmployeeInfo()



    }


    fun EmployeeInfo(){

        val progressdialog=ProgressDialog(this)
        progressdialog.setMessage("Loading Please Wait...")
        progressdialog.setCancelable(false)
        progressdialog.show()

        dbref= FirebaseDatabase.getInstance().getReference("Employee")



        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empArrayList= arrayListOf()
                val children=snapshot.children
                if (snapshot.exists()){

                    for (emp in children){

                        val data=emp.getValue(Employee::class.java)
                        empArrayList.add(data!!)
                    }
                    recyclerView.adapter=HorizontalAdapter(applicationContext, empArrayList)
                }
              if (progressdialog.isShowing) progressdialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DisplayEmployeeinfo2, "Failed", Toast.LENGTH_SHORT).show()
                if (progressdialog.isShowing) progressdialog.dismiss()
            }
        })


    }
}