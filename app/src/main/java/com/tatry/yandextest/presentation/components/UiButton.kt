package com.tatry.yandextest.presentation.components

import android.content.Context
import android.view.ViewGroup
import android.widget.Button

//class CustomButton(context: Context) : androidx.appcompat.widget.AppCompatButton(context) {
//
//    init {
//        setTextColor(Color.WHITE)
//        setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))
//        text = "Custom Button"
//    }
//
//    fun customizeButton(text: String, textColor: Int, backgroundColor: Int) {
//        this.text = text
//        setTextColor(textColor)
//        setBackgroundColor(backgroundColor)
//    }
//
//}

fun Context.createButton(container: ViewGroup, text: String): Button {
    val button = Button(this)
    button.text = text
    container.addView(button)
    return button
}