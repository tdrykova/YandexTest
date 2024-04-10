package com.tatry.yandextest.presentation.components

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import com.tatry.yandextest.R

fun Context.createButton(container: ViewGroup, text: String, onClick: () -> Unit): View {
    val button = Button(this)
    button.id = View.generateViewId()
    button.text = text
    button.setTextColor(Color.WHITE)
    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark))
    button.setOnClickListener {
        onClick()
    }
    container.addView(button)
    return button
}

fun Context.createSwitch(
    container: ViewGroup,
//    widthInDp: Int,
//    heightInDp: Int,
    onClick: (Boolean) -> Unit
): SwitchCompat {
    val switch = SwitchCompat(this)
    switch.id = View.generateViewId()
    switch.setOnCheckedChangeListener { buttonView, isChecked ->
        onClick(isChecked)
    }
    switch.isClickable = true
//    val widthInPx = TypedValue.applyDimension(
//        TypedValue.COMPLEX_UNIT_DIP, widthInDp.toFloat(), resources.displayMetrics
//    ).toInt()
//    val heightInPx = TypedValue.applyDimension(
//        TypedValue.COMPLEX_UNIT_DIP, heightInDp.toFloat(), resources.displayMetrics
//    ).toInt()
//    val switchParams = ViewGroup.LayoutParams(widthInPx, heightInPx)
//    switch.layoutParams = switchParams
    container.addView(switch)
    return switch
}

fun Context.createEditText(
    container: ViewGroup,
): EditText {
    val editText = EditText(this)
    editText.id = View.generateViewId()
    container.addView(editText)
    return editText
}

fun Context.createTextView(container: ViewGroup, text: String): View {
    val textView = TextView(this)
    textView.id = View.generateViewId()
    textView.text = text
    textView.isClickable = true
    container.addView(textView)
    return textView
}

fun Context.createSeekbar(
    container: ViewGroup,
): SeekBar {
    val seekbar = SeekBar(this)
    seekbar.id = View.generateViewId()
    container.addView(seekbar)
    return seekbar
}

fun Context.createCheckbox(
    container: ViewGroup,
): CheckBox {
    val checkbox = CheckBox(this)
    checkbox.id = View.generateViewId()
    container.addView(checkbox)
    return checkbox
}

fun Context.createColorPicker(
    container: ViewGroup,
    width: Int,
    height: Int
    ): View {
    val colorPicker = ImageView(this)
    colorPicker.id = View.generateViewId()

    colorPicker.setImageResource(R.drawable.color)
    val params = ViewGroup.LayoutParams(
        dpToPx(width),
        dpToPx(height)
    )
    colorPicker.layoutParams = params

    container.addView(colorPicker)
    colorPicker.post {
        colorPicker.isDrawingCacheEnabled = true
        colorPicker.buildDrawingCache(true)
    }
    return colorPicker
}

private fun Context.dpToPx(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        this.resources.displayMetrics
    ).toInt()
}

//fun Context.createView(container: ViewGroup): View {
//    val colorView = View(this)
//    container.addView(colorView)
//    return colorView
//}