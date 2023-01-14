package com.vasundhara.vipl24.firebase

import android.net.Uri
import android.provider.ContactsContract

data class Employee(
    var empName:String?="",
    val empMobile:String?="",
    var empAddress:String?="",
    val empdepartment:String="",
    val empEmail:String?="",
    val empBloodGroup:String?="",
    val imageUrl:String?=null
)
