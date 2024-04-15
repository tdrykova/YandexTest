package com.tatry.yandextest.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tatry.yandextest.databinding.FragmentYandexBinding
import com.tatry.yandextest.domain.model.devices.action.ActionObjectModel
import com.tatry.yandextest.domain.model.devices.action.DeviceActionModel
import com.tatry.yandextest.domain.model.devices.action.DeviceActionsRequestModel
import com.tatry.yandextest.domain.model.devices.action.StateObjectModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class YandexFragment : Fragment() {
    private val token = "Bearer y0_AgAEA7qkJBRwAAtNHQAAAAD7NOpOAABZXzInfHtFAoIVc4SUjPlw0bda8g"
    private var _binding: FragmentYandexBinding? = null
    private lateinit var externalId: String
    private lateinit var devId: String

    private val binding get() = _binding!!

    private val viewModel: YandexViewModel by viewModels {
        YandexViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYandexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.uploadUserInfo(token)
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.userInfo.collect {

                it.deviceList.forEachIndexed { ind, dev->
                    if (ind == 1) devId = dev.id
                    Log.d(TAG, "dev: ${dev.externalId}")
                }

//                devId = it.deviceList[1].id
//                binding.tvDevices.text = it.deviceList[1].externalId
            }
        }

//        viewModel.createDeviceCapabilityList(listOf(
//            CreateDeviceCapabilityModel(type = "on_off"),
//            CreateDeviceCapabilityModel(type = "color_settings")
//        ))

//        viewModel.getDeviceList(token)
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.deviceList.collect {
//                Log.d(TAG, "deviceList: $it")
//            }
//        }

//        viewModel.uploadUserInfo(token)
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.userInfo.collect {
//                if (it.status == "ok") {
//                    Log.d(TAG, "onViewCreated: ${it.deviceList[1]}")
//                }
//                viewModel.insertDeviceWithCapabilityList(
//                    DeviceModel()
//                    it.deviceList[1], it.deviceList[1].capabilityList)
//            }
//        }

        viewModel.getDeviceList()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.devList.collect {
                Log.d(TAG, "devList: ${it.map {dev -> dev }}")
            }
        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.insertDeviceWithCapabilityList(
//                device = DeviceModel(
//                    generatedId=4,
//                    id="51e797a4-93cf-4bc4-832e-698b6703467c",
//                    name="Лампа",
//                    aliasesList= listOf(),
//                    roomId="ca82a680-0317-4bec-b92e-5c3dd27c61eb",
//                    externalId="bf9159632e4fb1987bi7am",
//                    type="devices.types.light",
//                    groupIdList=listOf(),
//                    capabilityList= listOf(
//                        DeviceCapabilityModel(generatedId =1, devId ="id", type ="devices.capabilities.color_setting", reportable =true, retrievable =true, lastUpdated =0.0),
//                        DeviceCapabilityModel(generatedId =1, devId ="id", type ="devices.capabilities.on_off", reportable =true, retrievable =true, lastUpdated =0.0),
//                        DeviceCapabilityModel(generatedId =1, devId ="id", type ="devices.capabilities.range", reportable =true, retrievable =true, lastUpdated =0.0)),
//                    propertyList= listOf(),
//                    householdId="c9a8269c-9939-429b-bb56-05f5abae2937",
//                    skillId = "35e2897a-c583-495a-9e33-f5d6f0f4cb49"
//                ),
//
//                listOf(
//                    DeviceCapabilityModel(generatedId =1, devId ="id", type ="devices.capabilities.color_setting", reportable =true, retrievable =true, lastUpdated =0.0),
//                    DeviceCapabilityModel(generatedId =1, devId ="id", type ="devices.capabilities.on_off", reportable =true, retrievable =true, lastUpdated =0.0),
//                    DeviceCapabilityModel(generatedId =1, devId ="id", type ="devices.capabilities.range", reportable =true, retrievable =true, lastUpdated =0.0))
//            )
//        }





//        viewModel.insertDeviceWithCapabilityList(
//            device =
//            deviceCapabilityList =
//        )

        binding.btnDevState.setOnClickListener {
            if (devId != "empty") {
                viewModel.getDeviceState(token, devId).toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                binding.progressBar.isVisible = it is ProgressState.Loading
            }
        }

        // on_off
        binding.switchLight.setOnCheckedChangeListener { buttonView, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                val res = if (devId != "empty") viewModel.postAction(
                    token = token,
                    deviceList = DeviceActionsRequestModel( devices = listOf(
                        DeviceActionModel(
                            id = devId,
                            actions = listOf(
                                ActionObjectModel(
                                type = "devices.capabilities.on_off",
                                state = StateObjectModel(instance = "on", value = isChecked)
                            ))
                            )
                    ))

                ) else return@launch
                Log.d(TAG, "answer post: $res")
                viewModel.devAction.collect {
                    binding.tvDevices.text = it.devices.toString()
                    Log.d(TAG, " $it")
                }
            }
        }

        // color_setting, temperature_k
        binding.sliderBrightness.addOnChangeListener { slider, value, fromUser ->
            viewLifecycleOwner.lifecycleScope.launch {
                val res = if (devId != "empty") viewModel.postAction(
                    token = token,
                    deviceList = DeviceActionsRequestModel( devices = listOf(
                        DeviceActionModel(
                            id = devId,
                            actions = listOf(
                                ActionObjectModel(
                                    type = "devices.capabilities.color_setting",
                                    state = StateObjectModel(instance = "temperature_k", value = value.toInt())
                                ))
                        )
                    ))

                ) else return@launch
                Log.d(TAG, "answer post: $res")
                viewModel.devAction.collect {
                    binding.tvDevices.text = it.devices.toString()
                    Log.d(TAG, " $it")
                }
            }
        }

        // color_setting, hsv
        binding.btnChangeColor.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val res = if (devId != "empty") viewModel.postAction(
                    token = token,
                    deviceList = DeviceActionsRequestModel( devices = listOf(
                        DeviceActionModel(
                            id = devId,
                            actions = listOf(
                                ActionObjectModel(
                                    type = "devices.capabilities.color_setting",
                                    state = StateObjectModel(instance = "hsv",
                                        value = object {
                        val h = binding.etColorH.text.toString().toInt() // 125
                        val s = binding.etColorS.text.toString().toInt() // 25
                        val v = binding.etColorV.text.toString().toInt() // 100
                    })
                                ))
                        )
                    ))

                ) else return@launch
                Log.d(TAG, "answer post: $res")
                viewModel.devAction.collect {
                    binding.tvDevices.text = it.devices.toString()
                    Log.d(TAG, " $it")
                }
            }
        }
    }

    fun controlAction(typeAction: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG: String = YandexFragment::class.java.simpleName
        fun newInstance(): YandexFragment {
            return YandexFragment()
        }
    }
}