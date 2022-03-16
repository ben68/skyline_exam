package com.skyline.practice

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.JPEG
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

fun AppCompatActivity.getLifecycleOwner(): LifecycleOwner = this

fun<T> AppCompatActivity.replaceWith(activity: Class<T>, bundle: Bundle? = null) {
    show(activity, bundle)
    finish()
}

fun<T> AppCompatActivity.show(activity: Class<T>, bundle: Bundle? = null) =
        Intent(this, activity).apply {
            bundle?.apply {
                putExtras(this)
            }
            startActivity(this)
        }

fun ImageView.load(url: String, placeholder: Int) =
        Glide.with(this.context)
                .load(url)
                .placeholder(placeholder)
                .into(this)

fun ImageView.load(uri: Uri?) =
    Glide.with(this.context)
        .load(uri)
        .into(this)

@Throws(IOException::class)
fun Context.createImageFile(): File {
    val timeStamp = Calendar.getInstance().timeInMillis.toString()
    val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}",
        ".jpg",
        dir
    )
}

fun Bitmap.saveTo(context: Context, file: File) {
    try {
        FileOutputStream(file).let {
            compress(JPEG, 90, it)
            it.close()
        }
        Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Error while saving image!", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }
}
