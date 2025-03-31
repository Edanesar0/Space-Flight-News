package com.edwinespejo.flightnew.app.adapter.ktx

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Picasso.get()
        .load(url)
        .resize(300,300)
        .centerInside()
        .into(view)
}
