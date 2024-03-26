package com.tatry.yandextest.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tatry.yandextest.data.network.UserRepositoryImpl
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.request.Action
import com.tatry.yandextest.domain.model.devices.request.Device
import com.tatry.yandextest.domain.model.devices.request.DeviceActionsModel
import com.tatry.yandextest.domain.model.devices.request.State
import com.tatry.yandextest.domain.model.user.UserInfoResponse
import com.tatry.yandextest.domain.usecase.GetDeviceStateUseCase
import com.tatry.yandextest.domain.usecase.GetUserInfoUseCase
import com.tatry.yandextest.domain.usecase.PostDevicesActionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MainFragment555"

class YandexViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YandexViewModel::class.java)) {
            val repo = UserRepositoryImpl
            val useCase1 = GetUserInfoUseCase(repo)
            val useCase2 = GetDeviceStateUseCase(repo)
            val useCase3 = PostDevicesActionsUseCase(repo)

            return YandexViewModel(useCase1, useCase2, useCase3) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}

class YandexViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getDeviceStateUseCase: GetDeviceStateUseCase,
    private val postDevicesActionsUseCase: PostDevicesActionsUseCase
) : ViewModel() {

    private val token = "Bearer y0_AgAEA7qkJBRwAAtNHQAAAAD7NOpOAABZXzInfHtFAoIVc4SUjPlw0bda8g"
    private var _userInfo = MutableStateFlow(UserInfoResponse())
    var userInfo = _userInfo.asStateFlow()

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

    init {
        viewModelScope.launch {
            _state.value = ProgressState.Loading
            try {
//                _userInfo.value = getUserInfoUseCase.getUserInfo(_userToken.value)
                _userInfo.value = getUserInfoUseCase.getUserInfo(token)
                Log.d(TAG, "user info: ${_userInfo.value}")
//                _characterList.value = getCharacterListUseCase.getCharacterList()
            } catch (t: Throwable) {
                Log.e(TAG, "${t.message}", t)
            }
            _state.value = ProgressState.Success
        }
    }

    fun getUserInfo() {

    }

    fun setToken(token: String) {
        viewModelScope.launch {
            _userToken.value = token
        }

    }

//    fun randomCharacter() {
////        _character.value = _characterList.value.random()
//        viewModelScope.launch {
//            _state.value = ProgressState.Loading
//            try {
//                val listSize = _characterList.value.size
//                _character.value = getCharacterUseCase.getCharacter((1..listSize).random())
//            } catch (t: Throwable) {
//                Log.e(TAG, "${t.message}", t)
//            }
//            _state.value = ProgressState.Success
//        }
//    }


    fun getDeviceState(token: String, devId: String) {
        viewModelScope.launch {
            _devState.value = getDeviceStateUseCase.getDeviceStateUseCase(token, devId)
            Log.d(
                YandexFragment.TAG, " getDeviceState ${_devState.value.toString()}"
            )
        }
    }

    fun postAction(token: String, devId: String, typeAction: String, state: State) {
        viewModelScope.launch {
            _devAction.value = postDevicesActionsUseCase.postDevicesActions(
                token, DeviceActionsModel(
                    devices = listOf(
                        Device(
                            id = devId,
                            actions = listOf(
                                Action(
                                    type = typeAction, //"devices.capabilities.on_off",
                                    state = state //State(instance = "on", value = false)
                                )
                            )
                        )
                    )
                )
            )
        }

    }
}