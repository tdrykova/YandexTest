package com.tatry.yandextest.presentation.components

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.tatry.yandextest.R


private fun createCardView(context: Context): CardView {
    val cardView = CardView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        setContentPadding(16, 16, 16, 16)
        cardElevation = 8f
    }
    return cardView
}

private fun createLinearLayout(context: Context): LinearLayout {
    val linearLayout = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }
    return linearLayout
}

private fun createTextView(context: Context): TextView {
    val textView = TextView(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        text = "Label"
    }
    textView.isClickable = true
    textView.setOnClickListener {
        Toast.makeText(context, "txt", Toast.LENGTH_SHORT).show()
    }
    return textView
}

private fun dpToPx(context: Context, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

fun Context.createButton(container: ViewGroup, title: String, onClick: () -> Unit): View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this)

    val button = Button(this).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }
    button.id = View.generateViewId()
    button.text = title
    button.setTextColor(Color.WHITE)
    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark))
    button.setOnClickListener {
        onClick()
    }

    linearLayout.addView(textView)
    linearLayout.addView(button)
    cardView.addView(linearLayout)

    container.addView(cardView)
    return cardView
}

fun Context.createSwitch(
    container: ViewGroup,
    onClick: (Boolean) -> Unit
): View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this)

    val switch = SwitchCompat(this)
    Log.d("TAG", "switch id: ${switch.id}")
    switch.setOnCheckedChangeListener { buttonView, isChecked ->
        onClick(isChecked)
    }
    switch.isClickable = true

    val layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    layoutParams.gravity = Gravity.CENTER
    switch.layoutParams = layoutParams

    linearLayout.addView(textView)
    linearLayout.addView(switch)
    cardView.addView(linearLayout)

    container.addView(cardView)
    return cardView
}

fun Context.createEditTextWithButton(
    container: ViewGroup,
) : View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this)

    val editText = EditText(this).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        hint = "Enter text"
    }

    val button = Button(this).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        text = "Submit"
    }

    linearLayout.addView(textView)
    linearLayout.addView(editText)
    linearLayout.addView(button)
    cardView.addView(linearLayout)

    container.addView(cardView)
    return cardView
}

fun Context.createSeekbar(
    container: ViewGroup,
    maxValue: Int,
    currentState: Int,
    widthInDp: Int,
    onClick: (Int) -> Unit
): View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this)

    val seekbar = SeekBar(this)
    seekbar.id = View.generateViewId()
    seekbar.max = maxValue
    seekbar.progress = currentState

    val layoutParams = LinearLayout.LayoutParams(
        dpToPx(this, widthInDp),
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    seekbar.layoutParams = layoutParams

    seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            onClick(seekbar.progress)
        }
    })

    linearLayout.addView(textView)
    linearLayout.addView(seekbar)
    cardView.addView(linearLayout)

    container.addView(cardView)
    return cardView
}

fun Context.createCheckbox(
    container: ViewGroup,
    onClick: (Boolean) -> Unit
): View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this)

    val checkbox = CheckBox(this)
    checkbox.id = View.generateViewId()
    checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
        onClick(isChecked)
    }

    linearLayout.addView(textView)
    linearLayout.addView(checkbox)
    cardView.addView(linearLayout)

    container.addView(cardView)
    return cardView
}

fun Context.createColorPicker(
    container: ViewGroup,
    width: Int,
    height: Int
): View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this)

    val colorPicker = ImageView(this)
    colorPicker.id = View.generateViewId()

    colorPicker.setImageResource(R.drawable.color)
    val params = ViewGroup.LayoutParams(
        dpToPx(this, width),
        dpToPx(this, height)
    )
    colorPicker.layoutParams = params

    val view = View(this)
    val paramsView = ViewGroup.LayoutParams(
        dpToPx(this, 20),
        dpToPx(this, 20)
    )
    view.layoutParams = paramsView

    linearLayout.addView(textView)
    linearLayout.addView(view)
    linearLayout.addView(colorPicker)
    cardView.addView(linearLayout)

    container.addView(cardView)

    return cardView
}



