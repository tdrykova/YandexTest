package com.tatry.yandextest.presentation.control

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.tatry.yandextest.databinding.FragmentUiSettingsBinding
import com.tatry.yandextest.presentation.enum.CapabilitySupType
import com.tatry.yandextest.presentation.enum.TypeAction
import com.tatry.yandextest.presentation.enum.WidgetType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UiSettingsFragment : Fragment() {

    private var _binding: FragmentUiSettingsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: UiSettingsViewModel by activityViewModels()

    private var devName = ""
    private var capabilityType = ""
    private var capabilitySubType = ""
    private var widgetType = ""
    private var posLeft = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUiSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deviceCapabilityType = ArrayList<String>()
        TypeAction.values().forEach { deviceCapabilityType.add(it.toString()) }
        val adapterDeviceCapabilityType =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                deviceCapabilityType
            )
        adapterDeviceCapabilityType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerCapabilityType.adapter = adapterDeviceCapabilityType
        binding.spinnerCapabilityType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    capabilityType = parent.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        val deviceCapabilitySubType = ArrayList<String>()
        CapabilitySupType.values().forEach { deviceCapabilitySubType.add(it.toString()) }
        val adapterDeviceCapabilitySubType =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                deviceCapabilitySubType
            )
        adapterDeviceCapabilitySubType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerCapabilitySubType.adapter = adapterDeviceCapabilitySubType
        binding.spinnerCapabilitySubType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    capabilitySubType = parent.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        val widgetTypeList = ArrayList<String>()
        WidgetType.values().forEach { widgetTypeList.add(it.toString()) }
        val widgetTypeAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                widgetTypeList
            )
        widgetTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerWidgetType.adapter = widgetTypeAdapter
        binding.spinnerWidgetType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    widgetType = parent.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        binding.etPosX.setText("0")
        binding.etPosY.setText("0")
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
            viewModel.posLeft.collect {
                withContext(Dispatchers.Main) {
                    binding.etPosX.setText(it.toString())
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
            viewModel.posTop.collect {
                withContext(Dispatchers.Main) {
                    binding.etPosY.setText(it.toString())
                }
            }
        }

        binding.etWidgetHeight.setText("10")
        binding.etWidgetWidth.setText("10")

        binding.btnCreateWidget.setOnClickListener {
            viewModel.setElement(
                WidgetModel(
                    title = "title",
                    capabilityType = capabilityType,
                    capabilitySubType = capabilitySubType,
                    widgetType = widgetType,
//                    posX =  binding.etPosX.text.toString().toInt(),
//                    posY = binding.etPosY.text.toString().toInt(),
                    posX = 0,
                    posY = 0,
                    width = binding.etWidgetWidth.text.toString().toInt(),
                    height = binding.etWidgetHeight.text.toString().toInt()
                )
            )
        }
    }
}