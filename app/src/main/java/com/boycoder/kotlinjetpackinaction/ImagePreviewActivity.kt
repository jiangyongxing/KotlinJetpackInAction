package com.boycoder.kotlinjetpackinaction

import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ImagePreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)
        val intent = intent
        val url = intent.getStringExtra(MainActivity.EXTRA_PHOTO)
        if (!TextUtils.isEmpty(url)) {
            val imageView = findViewById<ImageView>(R.id.imagePreview)
            Glide.with(this).load(url).into(imageView)
        }
    }
}