package com.tatry.yandextest.presentation

import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
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
import com.tatry.yandextest.domain.model.devices.request.State
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

    // var wifiManager:WifiManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYandexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val wifiP2pManager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager?

        val wifiManager =
            requireActivity().applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val wifiConfig = WifiConfiguration()
        wifiConfig.SSID = "Wive-NG-NT"
        wifiConfig.preSharedKey = "********"

        wifiManager.addNetwork(wifiConfig)
        wifiManager.enableNetwork(wifiConfig.networkId, true)
        Log.d(TAG, "onViewCreated: ${wifiManager.isP2pSupported}")

//        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
////            val socket = Socket("192.168.1.50", 80)
//            val socket = Socket("95.25.108.6", 8883)
//            // 95.25.108.6
//
//            val out = PrintWriter(socket.getOutputStream())
////            val out = DataOutputStream(socket.getOutputStream())
////            out.
//            out.println("AT+CWJAP=${wifiConfig.SSID},${wifiConfig.preSharedKey}")
//            Log.d(TAG, "onViewCreated: ${socket.inetAddress}")
////            out.close()
////            socket.close()
//        }

//        socket.connect()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userInfo.collect {
//                it.devices.forEach { dev -> dev.external_id }.toString()
                externalId = if (it.devices.isEmpty()) "empty" else it.devices[2].external_id
                devId = if (it.devices.isEmpty()) "empty" else it.devices[2].id
                with(binding) {
                    Log.d(TAG, "dev: $it")
                    tvDevices.text = devId
                }
            }
        }

        binding.btnDevState.setOnClickListener {
            if (devId != "empty") {
                viewModel.getDeviceState(token, devId).toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    ProgressState.Loading -> binding.progressBar.isVisible = true
                    ProgressState.Success -> binding.progressBar.isVisible = false
                    else -> {}
                }
            }
        }

        // on_off
        binding.switchLight.setOnCheckedChangeListener { buttonView, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                val res = if (devId != "empty") viewModel.postAction(
                    token = token,
                    devId = devId,
                    typeAction = "devices.capabilities.on_off",
                    state = State(instance = "on", value = isChecked, relative = false)
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
            viewLifecycleOwner.lifecycleScope.launch() {
                val res = if (devId != "empty") viewModel.postAction(
                    token = token,
                    devId = devId,
                    typeAction = "devices.capabilities.color_setting",
                    state = State(
                        instance = "temperature_k",
                        value = value.toInt(),
                        relative = false
                    )
                ) else return@launch
                viewModel.devAction.collect {
                    binding.tvDevices.text = it.devices.toString()
                    Log.d(TAG, " $it")
                }
            }
        }

        // color_setting, hsv
        binding.btnChangeColor.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch() {
                val res = if (devId != "empty") viewModel.postAction(
                    token = token,
                    devId = devId,
                    typeAction = "devices.capabilities.color_setting",
                    state = State(instance = "hsv", value = object {
                        val h = binding.etColorH.text.toString().toInt() // 125
                        val s = binding.etColorS.text.toString().toInt() // 25
                        val v = binding.etColorV.text.toString().toInt() // 100
                    }, relative = false)
                ) else return@launch
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