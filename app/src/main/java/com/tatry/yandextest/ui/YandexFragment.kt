package com.tatry.yandextest.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tatry.yandextest.databinding.FragmentYandexBinding
import com.tatry.yandextest.domain.model.devices.request.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class YandexFragment : Fragment() {
    private val token = "Bearer y0_AgAEA7qkJBRwAAtNHQAAAAD7NOpOAABZXzInfHtFAoIVc4SUjPlw0bda8g"
    private var _binding: FragmentYandexBinding? = null
    private lateinit var externalId: String
    private lateinit var devId: String

    // This property is only valid between onCreateView and
    // onDestroyView.
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

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.setToken(token)
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userInfo.collect {
//                it.devices.forEach { dev -> dev.external_id }.toString()
                externalId = if (it.devices.isEmpty()) "empty" else it.devices[0].external_id
                devId = if (it.devices.isEmpty()) "empty" else it.devices[0].id
                with(binding) {
                    Log.d(TAG, "dev: $it")
                    tvRequest.text = it.status

                    tvDevices.text = externalId
//                    imageCharacter.load(it.imageUrl)
                }

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
        
//        viewLifecycleOwner.lifecycleScope.launch {
//            val res = if (devId != "empty") viewModel.postAction(
//                token = viewModel.userToken.value,
//                devId = devId,
//                typeAction = "devices.capabilities.on_off",
//                state = State(instance = "on", value = true)
//            ) else return@launch
//            Log.d(TAG, "answer post: $res")
//            viewModel.devAction.collect{
//                binding.tvDevices.text = it.devices.toString()
//                Log.d(TAG, " $it")
//            }
//
//        }

        Log.d(TAG, "dev state: extid: $externalId, id: ${devId}, token: $token")
        binding.switchLight.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                viewLifecycleOwner.lifecycleScope.launch {
                    val res = if (devId != "empty") viewModel.postAction(
//                        token = viewModel.userToken.value,
                        token = token,
                        devId = devId,
                        typeAction = "devices.capabilities.on_off",
                        state = State(instance = "on", value = true)
                    ) else return@launch
                    Log.d(TAG, "answer post: $res")
                    viewModel.devAction.collect{
                        binding.tvDevices.text = it.devices.toString()
                        Log.d(TAG, " $it")
                    }


                }
            }else{
                viewLifecycleOwner.lifecycleScope.launch {
                    val res = if (devId != "empty") viewModel.postAction(
                        token = token,
                        devId = devId,
                        typeAction = "devices.capabilities.on_off",
                        state = State(instance = "on", value = false)
                    ) else return@launch
                    Log.d(TAG, "answer post: $res")
                    viewModel.devAction.collect{
                        binding.tvDevices.text = it.devices.toString()
                        Log.d(TAG, " $it")
                    }

                }
            }
        }

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