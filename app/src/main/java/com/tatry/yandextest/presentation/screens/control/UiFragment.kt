package com.tatry.yandextest.presentation.screens.control

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tatry.yandextest.databinding.FragmentUiBinding
import com.tatry.yandextest.domain.model.devices.action.ActionObjectModel
import com.tatry.yandextest.domain.model.devices.action.DeviceActionModel
import com.tatry.yandextest.domain.model.devices.action.DeviceActionsRequestModel
import com.tatry.yandextest.domain.model.devices.action.StateObjectModel
import com.tatry.yandextest.presentation.YandexFragment
import com.tatry.yandextest.presentation.YandexViewModel
import com.tatry.yandextest.presentation.YandexViewModelFactory
import com.tatry.yandextest.presentation.components.createCheckbox
import com.tatry.yandextest.presentation.components.createColorPicker
import com.tatry.yandextest.presentation.components.createSlider
import com.tatry.yandextest.presentation.components.createSwitch
import com.tatry.yandextest.presentation.enum.MethodsType
import com.tatry.yandextest.presentation.enum.TypeAction
import com.tatry.yandextest.presentation.enum.WidgetType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UiFragment : Fragment() {

    private var _binding: FragmentUiBinding? = null
    private val binding get() = _binding!!

    private var red = 0xff
    private var green = 0xff
    private var blue = 0xff
    private var brightness = 255

    private val token = "Bearer y0_AgAEA7qkJBRwAAtNHQAAAAD7NOpOAABZXzInfHtFAoIVc4SUjPlw0bda8g"
    private lateinit var devId: String
    private val yandexViewModel: YandexViewModel by viewModels {
        YandexViewModelFactory()
    }

    private val MAX_LOG_LINES = 10
    private var actionsCounter = 0

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

        getUserDevice()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.element.collect { el ->
                when (el.capabilityType) {
                    TypeAction.ON_OFF.toString() -> {
                        when (el.methodsType) {
                            MethodsType.Yandex.toString() -> {
                                when (el.widgetType) {
                                    WidgetType.SWITCH.toString() -> {
                                        val switch = requireActivity().createSwitch(
                                            container = binding.dragContainer,
                                            tvLabel = TypeAction.ON_OFF.toString()
                                        ) { turnOnOffLight(it) }
                                        el.id = switch.id
                                        binding.dragContainer.addDraggableChild(switch)
                                        viewModel.addItem(el)
                                    }

                                    WidgetType.CHECK_BOX.toString() -> {
                                        val checkbox = requireActivity().createCheckbox(
                                            container = binding.dragContainer,
                                            tvLabel = TypeAction.ON_OFF.toString()
                                        ) { turnOnOffLight(it) }
                                        el.id = checkbox.id
                                        binding.dragContainer.addDraggableChild(checkbox)
                                        viewModel.addItem(el)
                                    }
                                }
                            }

                            MethodsType.Arduino.toString() -> {

                            }

                            MethodsType.ROS.toString() -> {

                            }
                        }
                    }

                    TypeAction.COLOR_SETTING.toString() -> {
                        when (el.methodsType) {
                            MethodsType.Yandex.toString() -> {
                                when (el.widgetType) {
                                    WidgetType.COLOR_PICKER.toString() -> {
                                        val picker = requireActivity().createColorPicker(
                                            container = binding.dragContainer,
                                            width = el.width,
                                            height = el.height,
                                            tvLabel = TypeAction.COLOR_SETTING.toString()
                                        )
                                        el.id = picker.id
                                        binding.dragContainer.addDraggableChild(picker)
                                        viewModel.addItem(el)

                                        picker.setOnTouchListener { v, event ->
                                            if (event?.action == MotionEvent.ACTION_DOWN) {
                                                val bitmap = Bitmap.createBitmap(
                                                    picker.width,
                                                    picker.height,
                                                    Bitmap.Config.ARGB_8888
                                                )
                                                val canvas = Canvas(bitmap)
                                                picker.draw(canvas)
                                                val x =
                                                    event.x.toInt().coerceIn(0, bitmap.width - 1)
                                                val y =
                                                    event.y.toInt().coerceIn(0, bitmap.height - 1)

                                                val pixel = bitmap.getPixel(x, y)

                                                red = Color.red(pixel)
                                                green = Color.green(pixel)
                                                blue = Color.blue(pixel)
                                                val currentRed = red * brightness / 255
                                                val currentGreen = green * brightness / 255
                                                val currentBlue = blue * brightness / 255
//                                                val currentColor =
//                                                    (currentGreen shl 16) + (currentRed shl 8) + currentBlue
//                                                val hex = "#%06X".format(currentColor)

                                                val hsv = FloatArray(3)
                                                val currentColor = Color.rgb(currentRed, currentGreen, currentBlue);
                                                Color.colorToHSV(currentColor, hsv)
//                                                binding.dragContainer.setBackgroundColor(
//                                                    Color.rgb(
//                                                        currentRed,
//                                                        currentGreen,
//                                                        currentBlue,
//                                                    )
//                                                )
                                                setColorHsv(
                                                    h = hsv[0].toInt(),
                                                    s = hsv[1].toInt(),
                                                    v = hsv[2].toInt()
                                                )
                                                v?.performClick()
                                            }
                                            true
                                        }
                                    }
                                    WidgetType.SLIDER.toString() -> {
                                        val slider = requireActivity().createSlider(
                                            container = binding.dragContainer,
                                            step = 10,
                                            valueFrom = 2700,
                                            valueTo = 6500,
                                            widthInDp = 300,
                                            tvLabel = "Temperature"
                                        ) { setTemperatureLight(it.toFloat()) }
                                        el.id = slider.id
                                        binding.dragContainer.addDraggableChild(slider)
                                        viewModel.addItem(el)
                                    }
                                }
                            }

                            MethodsType.Arduino.toString() -> {

                            }

                            MethodsType.ROS.toString() -> {

                            }
                        }
                    }
                }

            }
        }

//                when (el.widgetType) {
//                    WidgetType.BUTTON.toString() -> {
//                        val btn = requireActivity().createButton(
//                            container = binding.dragContainer,
//                            title = el.title,
//                            onClick = { customToast(el.id.toString()) }
//                        )
//                        el.id = btn.id
//                        binding.dragContainer.addDraggableChild(btn)
//                        viewModel.addItem(el)
//                    }

//
//                    WidgetType.EDIT_TEXT_VIEW.toString() -> {
//                        val editText = requireActivity().createEditTextWithButton(
//                            container = binding.dragContainer
//                        )
//                        el.id = editText.id
//                        binding.dragContainer.addDraggableChild(editText)
//                        viewModel.addItem(el)
//                    }
//
//                }


        binding.mode.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.dragContainer.enableDrag(isChecked)
        }


    }

    private fun appendLogMessage(message: String) {
        actionsCounter += 1
        binding.tvLogger.append("№ ${actionsCounter}: \n ${message}\n")
        binding.tvLogger.movementMethod = ScrollingMovementMethod()

        val scrollAmount = binding.tvLogger.layout.getLineTop(binding.tvLogger.lineCount) - binding.tvLogger.height
        if (scrollAmount > 0)
            binding.tvLogger.scrollTo(0, scrollAmount)
        else
            binding.tvLogger.scrollTo(0, 0)
    }

    private fun getUserDevice() {
        yandexViewModel.uploadUserInfo(token)
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            yandexViewModel.userInfo.collect {
                it.deviceList.forEachIndexed { ind, dev ->
                    if (ind == 2) {
                        devId = dev.id
                        Log.d(YandexFragment.TAG, "devId: ${devId}")
                    }

                    Log.d(YandexFragment.TAG, "dev: ${dev.externalId} devId: ${dev.id}")
                }
            }
        }
    }

    private fun turnOnOffLight(isChecked: Boolean) {
        viewLifecycleOwner.lifecycleScope.launch {
            var fl = 0
            val res = if (devId != "empty") yandexViewModel.postAction(
                token = token,
                deviceList = DeviceActionsRequestModel(
                    devices = listOf(
                        DeviceActionModel(
                            id = devId,
                            actions = listOf(
                                ActionObjectModel(
                                    type = "devices.capabilities.on_off",
                                    state = StateObjectModel(instance = "on", value = isChecked)
                                )
                            )
                        )
                    )
                )

            ) else return@launch
            yandexViewModel.devAction.collectLatest {
                it.devices.forEach{device ->
                    if (device.id == devId && fl ==0) {
                        appendLogMessage(device.toString())
                        fl += 1
                    }
                }
            }
        }
    }

    private fun setColorHsv(h: Int, s: Int, v: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            var fl = 0
            val res = if (devId != "empty") yandexViewModel.postAction(
                token = token,
                deviceList = DeviceActionsRequestModel(
                    devices = listOf(
                        DeviceActionModel(
                            id = devId,
                            actions = listOf(
                                ActionObjectModel(
                                    type = "devices.capabilities.color_setting",
                                    state = StateObjectModel(instance = "hsv",
                                        value = object {
                                            val h = h
                                            val s = s
                                            val v = v
                                        })
                                )
                            )
                        )
                    )
                )

            ) else return@launch
            yandexViewModel.devAction.collectLatest {
                it.devices.forEach{device ->
                    if (device.id == devId && fl ==0) {
                        appendLogMessage(device.toString())
                        fl += 1
                    }
                }
            }
        }
    }

    private fun setTemperatureLight(temperature: Float) {
        viewLifecycleOwner.lifecycleScope.launch {
            var fl = 0
            val res = if (devId != "empty") yandexViewModel.postAction(
                token = token,
                deviceList = DeviceActionsRequestModel( devices = listOf(
                    DeviceActionModel(
                        id = devId,
                        actions = listOf(
                            ActionObjectModel(
                                type = "devices.capabilities.color_setting",
                                state = StateObjectModel(instance = "temperature_k", value = temperature.toInt())
                            ))
                    )
                ))

            ) else return@launch
            yandexViewModel.devAction.collect {
                it.devices.forEach{device ->
                    if (device.id == devId && fl ==0) {
                        appendLogMessage(device.toString())
                        fl += 1
                    }
                }
            }
        }
    }
}

