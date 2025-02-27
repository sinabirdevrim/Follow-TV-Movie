package com.ugurbuga.followtvmovie.bindings

import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.ugurbuga.followtvmovie.R
import com.ugurbuga.followtvmovie.common.ImageManager

@BindingAdapter("imageUrl")
fun AppCompatImageView.setImageUrl(url: String?) {
    if (url.isNullOrBlank()) {
        setImageResource(R.drawable.ic_tv)
        background = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background)
        scaleType = ImageView.ScaleType.CENTER
    } else {
        ImageManager.setImageUrl(url, this)
    }
}

@BindingAdapter("android:src")
fun AppCompatImageView.setImageFromResource(resource: Int) {
    setImageResource(resource)
}

@BindingAdapter("tintColor")
fun AppCompatImageView.setTintColor(color: String) {
    setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP)
}
