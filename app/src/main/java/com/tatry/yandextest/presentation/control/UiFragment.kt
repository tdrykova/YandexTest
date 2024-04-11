package com.tatry.yandextest.presentation.control

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.tatry.yandextest.databinding.FragmentUiBinding
import com.tatry.yandextest.presentation.components.createButton
import com.tatry.yandextest.presentation.components.createColorPicker
import com.tatry.yandextest.presentation.components.createSwitch
import com.tatry.yandextest.presentation.components.createTextView
import com.tatry.yandextest.presentation.enum.CapabilitySupType
import com.tatry.yandextest.presentation.enum.TypeAction
import com.tatry.yandextest.presentation.enum.WidgetType
import kotlinx.coroutines.launch

class UiFragment : Fragment() {

    private var _binding: FragmentUiBinding? = null
    private val binding get() = _binding!!

    private lateinit var bitmap: Bitmap
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
                            text = el.title,
                            onClick = { customToast(el.title) }
                        )
                        el.id = btn.id
                        binding.dragContainer.addDraggableChild(btn)
                    }

                    WidgetType.SWITCH.toString() -> {
                        val switch = requireActivity().createSwitch(
                            container = binding.dragContainer,
//                            widthInDp = el.width,
//                            heightInDp = el.height
                        ) { customToast2(it) }
                        el.id = switch.id
                        binding.dragContainer.addDraggableChild(switch)
                    }

                    WidgetType.TEXT_VIEW.toString() -> {
                        val textView = requireActivity().createTextView(
                            container = binding.dragContainer,
                            text = el.title
                        )
                        el.id = textView.id
                        binding.dragContainer.addDraggableChild(textView)
                    }

                    WidgetType.COLOR_PICKER.toString() -> {
                        val picker = requireActivity().createColorPicker(
                            container = binding.dragContainer,
                            width = el.width,
                            height = el.height
                        )
                        el.id = picker.id
                        binding.dragContainer.addDraggableChild(picker)
                        picker.setOnTouchListener { v, event ->
                            if (event?.action == MotionEvent.ACTION_DOWN || event?.action == MotionEvent.ACTION_MOVE) {
                                bitmap = picker.drawingCache
                                val x = event.x.toInt().coerceIn(0, bitmap.width - 1)
                                val y = event.y.toInt().coerceIn(0, bitmap.height - 1)

                                val pixel = bitmap.getPixel(x, y)

                                bitmap = picker.drawingCache
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

}

