package com.boycoder.kotlinjetpackinaction

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.boycoder.kotlinjetpackinaction.databinding.ActivityMainBinding
import com.boycoder.kotlinjetpackinaction.entity.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private val TAG = "Main"
    private val requestQueue: RequestQueue by lazy(LazyThreadSafetyMode.NONE) {
        Volley.newRequestQueue(this)
    }
    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        display(CACHE_RESPONSE)
        requestOnlineInfo()
    }

    private fun requestOnlineInfo() {
        val url = "https://api.github.com/users/JakeWharton"
        val stringRequest = StringRequest(Request.Method.GET,
                url,
                Response.Listener { response ->
                    display(response)
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this@MainActivity, error?.message, Toast.LENGTH_SHORT).show()
                })
        stringRequest.tag = TAG
        requestQueue.add(stringRequest)
    }

    private fun display(response: String?) {
        if (response.isNullOrBlank()) {
            return
        }
        val gson = Gson()
        val user = gson.fromJson(response, User::class.java)
        user?.apply {
            Glide.with(this@MainActivity).load("file:///android_asset/bless.gif").into(binding.gif)
            Glide.with(this@MainActivity).load(user.avatar_url).apply(RequestOptions.circleCropTransform()).into(binding.image)
            binding.username.text = name
            binding.company.text = company
            binding.website.text = blog
            binding.image.setOnClickListener { gotoImagePreviewActivity(this) }
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
}