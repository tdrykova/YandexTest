package com.tatry.yandextest.presentation.screens.control

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatry.yandextest.databinding.FragmentUiBinding
import com.tatry.yandextest.presentation.components.createButton
import com.tatry.yandextest.presentation.components.createCheckbox
import com.tatry.yandextest.presentation.components.createColorPicker
import com.tatry.yandextest.presentation.components.createEditTextWithButton
import com.tatry.yandextest.presentation.components.createSeekbar
import com.tatry.yandextest.presentation.components.createSwitch
import com.tatry.yandextest.presentation.enum.WidgetType
import kotlinx.coroutines.launch

class UiFragment : Fragment() {

    private var _binding: FragmentUiBinding? = null
    private val binding get() = _binding!!

    private var red = 0xff
    private var green = 0xff
    private var blue = 0xff
    private var brightness = 255

    private val viewModel: UiSettingsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUiBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dragContainer.setupWithViewModel(viewModel)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.element.collect { el ->
//                when (el.capabilityType) {
//                    TypeAction.ON_OFF.toString() -> {
                when (el.widgetType) {
                    WidgetType.BUTTON.toString() -> {
                        val btn = requireActivity().createButton(
                            container = binding.dragContainer,
                            title = el.title,
                            onClick = { customToast(el.id.toString()) }
                        )
                        el.id = btn.id
                        binding.dragContainer.addDraggableChild(btn)
                        viewModel.addItem(el)
                    }

                    WidgetType.SWITCH.toString() -> {
                        val switch = requireActivity().createSwitch(
                            container = binding.dragContainer,
                        ) { customToast2(it) }
                        el.id = switch.id
                        binding.dragContainer.addDraggableChild(switch)
                        viewModel.addItem(el)
                    }

                    WidgetType.CHECK_BOX.toString() -> {
                        val checkbox = requireActivity().createCheckbox(
                            container = binding.dragContainer,
                        ) { customToast2(it) }
                        el.id = checkbox.id
                        binding.dragContainer.addDraggableChild(checkbox)
                        viewModel.addItem(el)
                    }

                    WidgetType.SEEKBAR.toString() -> {
                        val seekbar = requireActivity().createSeekbar(
                            container = binding.dragContainer,
                            maxValue = 100,
                            currentState = 50,
                            widthInDp = 300
                        ) { customToast(it.toString()) }
                        el.id = seekbar.id
                        binding.dragContainer.addDraggableChild(seekbar)
                        viewModel.addItem(el)
                    }

                    WidgetType.EDIT_TEXT_VIEW.toString() -> {
                        val editText = requireActivity().createEditTextWithButton(
                            container = binding.dragContainer
                        )
                        el.id = editText.id
                        binding.dragContainer.addDraggableChild(editText)
                        viewModel.addItem(el)
                    }

                    WidgetType.COLOR_PICKER.toString() -> {
                        val picker = requireActivity().createColorPicker(
                            container = binding.dragContainer,
                            width = el.width,
                            height = el.height
                        )
                        el.id = picker.id
                        binding.dragContainer.addDraggableChild(picker)
                        viewModel.addItem(el)

                        picker.setOnTouchListener { v, event ->
                            if (event?.action == MotionEvent.ACTION_DOWN || event?.action == MotionEvent.ACTION_MOVE) {
                                val bitmap = Bitmap.createBitmap(picker.width, picker.height, Bitmap.Config.ARGB_8888)
                                val canvas = Canvas(bitmap)
                                picker.draw(canvas)
                                val x = event.x.toInt().coerceIn(0, bitmap.width - 1)
                                val y = event.y.toInt().coerceIn(0, bitmap.height - 1)

                                val pixel = bitmap.getPixel(x, y)

                                red = Color.red(pixel)
                                green = Color.green(pixel)
                                blue = Color.blue(pixel)
                                val currentRed = red * brightness / 255
                                val currentGreen = green * brightness / 255
                                val currentBlue = blue * brightness / 255
                                val currentColor =
                                    (currentGreen shl 16) + (currentRed shl 8) + currentBlue
                                val hex = "#%06X".format(currentColor)
                                binding.dragContainer.setBackgroundColor(
                                    Color.rgb(
                                        currentRed,
                                        currentGreen,
                                        currentBlue
                                    )
                                )
                                v?.performClick()
                            }
                            true
                        }
                    }
                }
//                    }
//                }
            }
        }

        binding.mode.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.dragContainer.enableDrag(isChecked)
        }

    }


    private fun customToast2(fl: Boolean) {
        Toast.makeText(activity?.applicationContext, fl.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun customToast(str: String) {
        Toast.makeText(activity?.applicationContext, str, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()

    }
}

