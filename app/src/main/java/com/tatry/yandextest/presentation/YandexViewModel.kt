package com.tatry.yandextest.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tatry.yandextest.App
import com.tatry.yandextest.data.UserRepositoryImpl
import com.tatry.yandextest.data.local.entity.device.DeviceRelations
import com.tatry.yandextest.domain.model.devices.action.DeviceListModel
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.user_info.DeviceCapabilityModel
import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.model.devices.user_info.UserInfoModel
import com.tatry.yandextest.domain.model.local.CreateDeviceCapabilityModel
import com.tatry.yandextest.domain.usecase.CreateDeviceCapabilityListUseCase
import com.tatry.yandextest.domain.usecase.CashDeviceListUseCase
import com.tatry.yandextest.domain.usecase.GetAllDeviceListUseCase
import com.tatry.yandextest.domain.usecase.GetDeviceListUseCase
import com.tatry.yandextest.domain.usecase.GetDeviceStateUseCase
import com.tatry.yandextest.domain.usecase.InsertDeviceWithCapabilityListUseCase
import com.tatry.yandextest.domain.usecase.UploadUserInfoUseCase
import com.tatry.yandextest.domain.usecase.PostDevicesActionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MainFragment555"

class YandexViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YandexViewModel::class.java)) {
            val repo = UserRepositoryImpl(App.INSTANCE)
            val useCase1 = UploadUserInfoUseCase(repo)
            val useCase4 = CashDeviceListUseCase(repo)
            val useCase5 = GetDeviceListUseCase(repo)
            val createDeviceCapabilityList = CreateDeviceCapabilityListUseCase(repo)
            val useCase2 = GetDeviceStateUseCase(repo)
            val useCase3 = PostDevicesActionsUseCase(repo)
            val useCase7 = InsertDeviceWithCapabilityListUseCase(repo)
            val getAllDeviceList = GetAllDeviceListUseCase(repo)

            return YandexViewModel(
                useCase1,
                useCase4,
                createDeviceCapabilityList,
                useCase5,
                useCase2,
                useCase3,
                useCase7,
                getAllDeviceList

            ) as T
        }
        throw RuntimeException("Unknown class name")
    }
}

class YandexViewModel(
    private val uploadUserInfoUseCase: UploadUserInfoUseCase,
    private val cashDeviceListUseCase: CashDeviceListUseCase,
    private val createDeviceCapabilityListUseCase: CreateDeviceCapabilityListUseCase,
    private val getDeviceListUseCase: GetDeviceListUseCase,
    private val getDeviceStateUseCase: GetDeviceStateUseCase,
    private val postDevicesActionsUseCase: PostDevicesActionsUseCase,
    private val insertDeviceWithCapabilityListUseCase: InsertDeviceWithCapabilityListUseCase,
    private val getAllDeviceListUseCase: GetAllDeviceListUseCase,
) : ViewModel() {

    private var _userInfo = MutableStateFlow(UserInfoModel())
    var userInfo = _userInfo.asStateFlow()

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
            _devState.value = getDeviceStateUseCase.getDeviceStateUseCase(token, devId)
            Log.d(
                YandexFragment.TAG, " getDeviceState ${_devState.value.toString()}"
            )
        }
    }

    fun postAction(token: String, deviceList: DeviceListModel) {
        viewModelScope.launch {
            _devAction.value = postDevicesActionsUseCase.postDevicesActions(
                token = token,
                deviceList = deviceList)
        }
    }

    fun uploadUserInfo(token:String) {
        viewModelScope.launch(Dispatchers.IO) {
           _userInfo.value = uploadUserInfoUseCase(token)
            Log.d(TAG, "uploadUserInfo: ${_userInfo.value}")
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