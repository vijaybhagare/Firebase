package com.vasundhara.vipl24.firebase


import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.print.PrintManager
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_horizontal_recycleview.view.*
import kotlinx.android.synthetic.main.item_recycleview.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class HorizontalAdapter(var context: Context, val employeeList:ArrayList<Employee>) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {
    private lateinit var main: View
    private lateinit var imageView: ImageView
    lateinit var dialogInterface: DialogInterface
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_horizontal_recycleview, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.name.text = ( employeeList[position].empName)
        holder.mobolino.text = ("MOBILE NO : " + employeeList[position].empMobile)
        holder.address.text = ("ADDRESS : " + employeeList[position].empAddress)
        holder.bloodgroup.text = ("BLOODGROUP : " + employeeList[position].empBloodGroup)
        holder.department.text = employeeList[position].empdepartment
        holder.email.text = ("EMAIL : " + employeeList[position].empEmail)
        // Glide.with(context).load(employeeList[position].imageUrl).into(holder.image)
        Glide.with(context).load(employeeList[position].imageUrl).into(holder.image)

        holder.card.setOnClickListener {

//            Toast.makeText(
//                context,
//                "${employeeList[position].empName}+${employeeList[position].empdepartment}",
//                Toast.LENGTH_SHORT
//            ).show()


            val bitmap = getScreenShotFromView(holder.card)
            if (bitmap != null) {
                saveMediaToStorage(bitmap)
            }


        }


    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val image: ImageView
        val name: TextView
        val department: TextView
        val email: TextView
        val address: TextView
        val mobolino: TextView
        val bloodgroup: TextView
        val card: CardView


        init {
            image = itemView.img_profile2
            name = itemView.tv_hname
            department = itemView.tv_hdepartment
            email = itemView.tv_hemail
            address = itemView.tv_haddress
            mobolino = itemView.tv_hmobile
            bloodgroup = itemView.tv_hbloodgroup
            card = itemView.horizontalcardview


        }
    }


    private fun getScreenShotFromView(v: View): Bitmap? {
        // create a bitmap object
        var screenshot: Bitmap? = null
        try {

            screenshot =
                Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)

            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }

        return screenshot
    }


    private fun saveMediaToStorage(bitmap: Bitmap) {

        val filename = "${System.currentTimeMillis()}.jpg"

        var fos: OutputStream? = null


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            context.contentResolver?.also { resolver ->


                val contentValues = ContentValues().apply {


                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)


                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(context, "Captured Image and saved to Gallery", Toast.LENGTH_SHORT).show()


        }
    }

}
