package com.boycoder.kotlinjetpackinaction

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.boycoder.kotlinjetpackinaction.entity.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var stringRequest: StringRequest
    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        display(User.CACHE_RESPONSE)
        requestOnlineInfo()
    }

    private fun requestOnlineInfo() {
        requestQueue = Volley.newRequestQueue(this)
        val url = "https://api.github.com/users/JakeWharton"
        stringRequest = StringRequest(Request.Method.GET, url,
                { response -> display(response) }) { error -> Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show() }
        stringRequest.tag = TAG
        requestQueue.add(stringRequest)
    }

    private fun display(response: String?) {
        if (TextUtils.isEmpty(response)) {
            return
        }
        val gson = Gson()
        val user = gson.fromJson(response, User::class.java)
        if (user != null) {
            Glide.with(this).load("file:///android_asset/bless.gif").into(gif!!)
            Glide.with(this).load(user.avatar_url).apply(RequestOptions.circleCropTransform()).into(image!!)
            username.text = user.name
            company.text = user.company
            website.text = user.blog
            image.setOnClickListener { gotoImagePreviewActivity(user) }
        }
    }

    private fun gotoImagePreviewActivity(user: User) {
        val intent = Intent(this, ImagePreviewActivity::class.java)
        intent.putExtra(EXTRA_PHOTO, user.avatar_url)
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        requestQueue.cancelAll(TAG)
    }

    companion object {
        const val TAG = "Main"
        const val EXTRA_PHOTO = "photo"
    }
}