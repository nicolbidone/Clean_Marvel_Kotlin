package com.puzzlebench.clean_marvel_kotlin.presentation.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.getImageByUrl(url: String) {
    Glide.with(context).load(url).apply(RequestOptions().centerCrop()).into(this)
}
