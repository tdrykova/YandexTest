package com.tatry.yandextest.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tatry.yandextest.App
import com.tatry.yandextest.data.YandexRepositoryImpl
import com.tatry.yandextest.data.local.entity.device.DeviceRelations
import com.tatry.yandextest.domain.model.devices.action.DeviceActionsRequestModel
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.user_info.DeviceCapabilityModel
import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.model.devices.user_info.UserInfoModel
import com.tatry.yandextest.domain.usecase.local.CreateDeviceCapabilityListUseCase
import com.tatry.yandextest.domain.usecase.local.CashDeviceListUseCase
import com.tatry.yandextest.domain.usecase.local.GetAllDeviceListUseCase
import com.tatry.yandextest.domain.usecase.local.GetDeviceListUseCase
import com.tatry.yandextest.domain.usecase.network.UploadDeviceStateUseCase
import com.tatry.yandextest.domain.usecase.local.InsertDeviceWithCapabilityListUseCase
import com.tatry.yandextest.domain.usecase.network.UploadUserInfoUseCase
import com.tatry.yandextest.domain.usecase.network.PostDevicesActionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MainFragment555"

class YandexViewModelFactory(
    private val yandexViewModel: YandexViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YandexViewModel::class.java)) {
            return yandexViewModel as T
        }
        throw RuntimeException("Unknown class name")
    }
}

class YandexViewModel(
    private val uploadUserInfoUseCase: UploadUserInfoUseCase,
    private val cashDeviceListUseCase: CashDeviceListUseCase,
    private val createDeviceCapabilityListUseCase: CreateDeviceCapabilityListUseCase,
    private val getDeviceListUseCase: GetDeviceListUseCase,
    private val uploadDeviceStateUseCase: UploadDeviceStateUseCase,
    private val postDevicesActionsUseCase: PostDevicesActionsUseCase,
    private val insertDeviceWithCapabilityListUseCase: InsertDeviceWithCapabilityListUseCase,
    private val getAllDeviceListUseCase: GetAllDeviceListUseCase,
) : ViewModel() {

    private var _userInfo = MutableStateFlow(UserInfoModel())
    var userInfo = _userInfo.asStateFlow()

    fun uploadUserInfo(token:String) {
        viewModelScope.launch(Dispatchers.IO) {
            _userInfo.value = uploadUserInfoUseCase(token)
        }
    }

    private var _devList = MutableSharedFlow<List<DeviceRelations>>()
    var devList = _devList.asSharedFlow()

    private var _deviceList = MutableStateFlow<MutableList<DeviceModel>>(mutableListOf())
    var deviceList = _deviceList.asStateFlow()

    private var _userToken = MutableStateFlow("token")
    var userToken = _userToken.asStateFlow()

    private var _devState = MutableStateFlow(GetDeviceStateResponse())
    var devState = _devState.asStateFlow()

    private var _devAction = MutableStateFlow(DeviceActionsAnswerModel())
    var devAction = _devAction.asStateFlow()

//    private var _characterList = MutableStateFlow<List<CharacterModel>>(mutableListOf())
//    var characterList = _characterList.asStateFlow()

    private var _state = MutableStateFlow<ProgressState>(ProgressState.Success)
    var state = _state.asStateFlow()

    fun getUserInfo() {

    }

    fun setToken(token: String) {
        viewModelScope.launch {
            _userToken.value = token
        }

    }

    fun getDeviceList() {
        viewModelScope.launch {
            _devList.emit(getAllDeviceListUseCase())
        }
    }


    fun getDeviceState(token: String, devId: String) {
        viewModelScope.launch {
            _devState.value = uploadDeviceStateUseCase.getDeviceStateUseCase(token, devId)
            Log.d(
                "TAG", " getDeviceState ${_devState.value}"
            )
        }
    }

    fun postAction(token: String, deviceList: DeviceActionsRequestModel) {
        viewModelScope.launch {
            _devAction.value = postDevicesActionsUseCase.postDevicesActions(
                token = token,
                deviceList = deviceList)
        }
    }



    fun getDeviceList(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                _state.value = ProgressState.Loading
                cashDeviceListUseCase(uploadUserInfoUseCase(token).deviceList)
                getDeviceListUseCase().toMutableList()
            }.fold(
                onSuccess = {_deviceList.value = it},
                onFailure = {
                    Log.e(TAG, "${it.message}", it)
                }
            )
            _state.value = ProgressState.Success
        }
    }

//    fun createDeviceCapabilityList(deviceCapabilityList: List<CreateDeviceCapabilityModel>) {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching {
//                _state.value = ProgressState.Loading
//                createDeviceCapabilityListUseCase(deviceCapabilityList)
//            }.fold(
//                onSuccess = {  },
//                onFailure = {
//                    Log.e(TAG, "${it.message}", it)
//                }
//            )
//            _state.value = ProgressState.Success
//        }
//    }

    fun insertDeviceWithCapabilityList(device: DeviceModel,deviceCapabilityList: List<DeviceCapabilityModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                _state.value = ProgressState.Loading
                insertDeviceWithCapabilityListUseCase(device, deviceCapabilityList)
            }.fold(
                onSuccess = {  },
                onFailure = {
                    Log.e(TAG, "${it.message}", it)
                }
            )
            _state.value = ProgressState.Success
        }
    }
}