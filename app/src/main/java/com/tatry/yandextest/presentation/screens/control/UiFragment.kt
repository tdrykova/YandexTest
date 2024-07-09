package com.tatry.yandextest.presentation.screens.control

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.datastore.dataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.musfickjamil.snackify.Snackify
import com.tatry.yandextest.App
import com.tatry.yandextest.BuildConfig
import com.tatry.yandextest.data.cipher.CryptoManager
import com.tatry.yandextest.data.cipher.SecurityManger
import com.tatry.yandextest.data.cipher.UserSettingsSerializer
import com.tatry.yandextest.databinding.FragmentUiBinding
import com.tatry.yandextest.domain.model.devices.action.ActionObjectModel
import com.tatry.yandextest.domain.model.devices.action.DeviceActionModel
import com.tatry.yandextest.domain.model.devices.action.DeviceActionsRequestModel
import com.tatry.yandextest.domain.model.devices.action.StateObjectModel
import com.tatry.yandextest.presentation.BaseActivity
import com.tatry.yandextest.presentation.YandexViewModel
import com.tatry.yandextest.presentation.components.createCheckbox
import com.tatry.yandextest.presentation.components.createColorPicker
import com.tatry.yandextest.presentation.components.createJoystick
import com.tatry.yandextest.presentation.components.createSlider
import com.tatry.yandextest.presentation.components.createSwitch
import com.tatry.yandextest.presentation.enum.MethodsType
import com.tatry.yandextest.presentation.enum.TypeAction
import com.tatry.yandextest.presentation.enum.WidgetType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream

class UiFragment : Fragment() {

    private var _binding: FragmentUiBinding? = null
    private val binding get() = _binding!!

    private var red = 0xff
    private var green = 0xff
    private var blue = 0xff
    private var brightness = 255

    private val token =
        "Bearer y0_AgAEA7qkJBRwAAtNHQAAAAD7NOpOAABZXzInfHtFAoIVc4SUjPlw0bda8g"
    private lateinit var devId: String
    private val yandexViewModel: YandexViewModel by viewModels {
        App.INSTANCE.applicationComponent.yandexViewModelFactory()
    }

    private lateinit var file: File
    private var actionsCounter = 0
    private lateinit var inputStream: InputStream

    private val viewModel: UiSettingsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUiBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        requestPermission()

        binding.dragContainer.setupWithViewModel(viewModel)
        binding.btnPickFile.setOnClickListener { showFileChooser() }

        val securityManger = SecurityManger(requireContext())
        binding.btnEncrypt.setOnClickListener {
//            securityManger.encryptFile(
////                "${Environment.getExternalStorageDirectory()}/${file.absolutePath}",
////                "${file.absolutePath}",
////                "test.json",
//                file.name,
//                "encryptedTest.json"
//            )

//            Log.d(TAG, "encrypt: ${securityManger.encrypt("[{go: true}]")}")

            Snackify.success(binding.mainContainer, "Success message !", Snackify.LENGTH_LONG)
                .show()
            securityManger.encryptFile(inputStream, "encryptedTest.json")
        }

        binding.btnDecrypt.setOnClickListener {
            val decryptionFileToString = securityManger.decryptFile(
                "encryptedTest.json"
            )

            sendJson()
            Log.d("decryptionFileToString", decryptionFileToString)

            // AESEncyption
//            Log.d(TAG, "encrypt: ${AESEncyption.decrypt("I2q1v5Dbs3Vl5blX+Q/4aw==")}")
//            Log.d(TAG, "encrypt: ${securityManger.decrypt("xiiXWXWtNW3F2iO4JMVWCg==")}")
        }

        getUserDevice()
        getWidgetId()

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

                    TypeAction.MOVE.toString() -> {
                        when (el.methodsType) {
                            MethodsType.Yandex.toString() -> {
                                when (el.widgetType) {

                                }
                            }

                            MethodsType.Arduino.toString() -> {
                                when (el.widgetType) {
                                    WidgetType.JOYSTICK.toString() -> {
                                        val joystick = requireActivity().createJoystick(
                                            container = binding.dragContainer,
                                            tvLabel = "Joystick"
                                        ) { moveJoystick(it) }
                                        el.id = joystick.id
                                        binding.dragContainer.addDraggableChild(joystick)
                                        viewModel.addItem(el)
                                        Log.d(TAG, "el:$el ")
                                        binding.dragContainer.bringToFront()
                                    }
                                }
                            }

                            MethodsType.ROS.toString() -> {
                                when (el.widgetType) {

                                }
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
                                                val hsv = FloatArray(3)
                                                val currentColor = Color.rgb(
                                                    currentRed,
                                                    currentGreen,
                                                    currentBlue
                                                );
                                                Color.colorToHSV(currentColor, hsv)
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

    private fun getWidgetId() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.widgetId.collect { widgetId ->

                val viewToRemove = binding.dragContainer.findViewById<View>(widgetId.toInt())
                binding.dragContainer.removeDraggableChild(widgetId.toInt())
                if (viewToRemove != null) {
                    binding.dragContainer.removeView(viewToRemove)
                }
                binding.dragContainer.invalidate() // перерисовка dragContainer
                binding.dragContainer.requestLayout() // применение новых изменений
                Log.d("TAG", "addDraggableChild: ${widgetId}")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri ->
                inputStream = requireContext().contentResolver.openInputStream(uri)!!
                val path: String = uri.path.toString()
                file = File(path)
                binding.tvPath.text = "Путь: $path Файл: ${file.name}".trimIndent()
            }
        }
    }

    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
        }
        try {
            startActivityForResult(
                Intent.createChooser(intent, "Выберите json-файл"),
                100
            )
        } catch (exception: Exception) {
            Toast.makeText(requireContext(), "Установите файловый менеджер", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private companion object {
        private const val STORAGE_PERMISSION_CODE = 100
        private const val TAG = "PERMISSION_TAG"
    }

    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //Android is 11(R) or above
            Environment.isExternalStorageManager()
        } else {
            //Android is below 11(R)
            val write =
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            val read =
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun sendJson() {
//        val jsonObj = JSONObject("text")
        val file2 = File(requireContext().filesDir, "encryptedTest.json")
//        val uri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", file2)
        file = File(requireContext().filesDir, file.name)
        val uri = FileProvider.getUriForFile(
            requireContext(),
            BuildConfig.APPLICATION_ID + ".provider",
            file
        )
        Log.d(TAG, "File: ${File(requireContext().filesDir, "encryptedTest.json").toString()}")
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
//            type = "application/json"
            putExtra(Intent.EXTRA_EMAIL, "tatrykova2002@yandex.ru")
            putExtra(Intent.EXTRA_SUBJECT, "json")
            putExtra(Intent.EXTRA_TEXT, "File")
            putExtra(
                Intent.EXTRA_STREAM, uri
//                Uri.parse("file:///sdcard/file.json")
//                Uri.parse(File(requireContext().filesDir,"encryptedTest.json").toString())

            )
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(intent, "Отправить email"))
    }

    private fun passDataToTelegram(packageName: String) {
        val securityManger2 = SecurityManger(requireContext())
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.type = "text/plain"
        sendIntent.setPackage(packageName)
        try {
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
//                AESEncyption.encrypt()
                securityManger2.encryptFile(inputStream, "encryptedTest.json").toString()
            )
            startActivity(sendIntent)
        } catch (ex: ActivityNotFoundException) {
            if (packageName == "org.telegram.messenger")
                passDataToTelegram("org.telegram.messenger.web")
//            else DialogHelper().showTelegramNotFoundDialog(requireActivity().applicationContext)
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //Android is 11(R) or above
            try {
                Log.d(TAG, "requestPermission: try")
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", activity?.packageName, null)
                intent.data = uri
                storageActivityResultLauncher.launch(intent)
            } catch (e: Exception) {
                Log.e(TAG, "requestPermission: ", e)
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                storageActivityResultLauncher.launch(intent)
            }
        } else {
            //Android is below 11(R)
            ActivityCompat.requestPermissions(
                (activity as BaseActivity),
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    private val storageActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d(TAG, "storageActivityResultLauncher: ")
            //here we will handle the result of our intent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                //Android is 11(R) or above
                if (Environment.isExternalStorageManager()) {
                    //Manage External Storage Permission is granted
                    Log.d(
                        TAG,
                        "storageActivityResultLauncher: Manage External Storage Permission is granted"
                    )
//                    createFolder()
                } else {
                    //Manage External Storage Permission is denied....
                    Log.d(
                        TAG,
                        "storageActivityResultLauncher: Manage External Storage Permission is denied...."
                    )
//                    toast("Manage External Storage Permission is denied....")
                }
            } else {
                //Android is below 11(R)
            }
        }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty()) {
                //check each permission if granted or not
                val write = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val read = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (write && read) {
                    //External Storage Permission granted
                    Log.d(
                        "MY_TAG",
                        "onRequestPermissionsResult: External Storage Permission granted"
                    )
//                    createFolder()
                } else {
                    //External Storage Permission denied...
                    Log.d(
                        "MY_TAG",
                        "onRequestPermissionsResult: External Storage Permission denied..."
                    )
//                    toast("External Storage Permission denied...")
                }
            }
        }
    }


    private fun appendLogMessage(message: String) {
        actionsCounter += 1
        binding.tvLogger.append("№ ${actionsCounter}: \n ${message}\n")
        binding.tvLogger.movementMethod = ScrollingMovementMethod()

        val scrollAmount =
            binding.tvLogger.layout.getLineTop(binding.tvLogger.lineCount) - binding.tvLogger.height
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
                        Log.d("TAG", "devId: ${devId}")
                    }

                    Log.d("TAG", "dev: ${dev.externalId} devId: ${dev.id}")
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
                it.devices.forEach { device ->
                    if (device.id == devId && fl == 0) {
                        appendLogMessage(device.toString())
                        Log.d(TAG, "setTemperatureLight: ${device.toString()}")
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
            yandexViewModel.devAction.collect {
                it.devices.forEach { device ->
                    if (device.id == devId && fl == 0) {
                        appendLogMessage(device.toString())
                        Log.d(TAG, "setTemperatureLight: ${device.toString()}")
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
                deviceList = DeviceActionsRequestModel(
                    devices = listOf(
                        DeviceActionModel(
                            id = devId,
                            actions = listOf(
                                ActionObjectModel(
                                    type = "devices.capabilities.color_setting",
                                    state = StateObjectModel(
                                        instance = "temperature_k",
                                        value = temperature.toInt()
                                    )
                                )
                            )
                        )
                    )
                )

            ) else return@launch
            yandexViewModel.devAction.collect {
                it.devices.forEach { device ->
                    if (device.id == devId && fl == 0) {
                        appendLogMessage(device.toString())
                        Log.d(TAG, "setTemperatureLight: ${device.toString()}")
                        fl += 1
                    }
                }
            }
        }
    }

    private fun moveJoystick(angle: Float) {
        if (angle in -20.0..20.0) {
            Log.d(TAG, "moveJoystick: right")
        }

        if (angle in 70.0..110.0) {
            Log.d(TAG, "moveJoystick: back")
        }

        if (angle in -110.0..-70.0) {
            Log.d(TAG, "moveJoystick: forward")
        }

        if (angle in -180.0..-160.0 || angle in 160.0..180.0) {
            Log.d(TAG, "moveJoystick: left")
        }

    }

}

