package com.tatry.yandextest.data.mapper


import com.tatry.yandextest.data.local.entity.device.DeviceEntity
import com.tatry.yandextest.data.network.dto.action.ActionObjectDTO
import com.tatry.yandextest.data.network.dto.action.DeviceActionDTO
import com.tatry.yandextest.data.network.dto.action.DeviceListDTO
import com.tatry.yandextest.data.network.dto.action.StateObjectDTO
import com.tatry.yandextest.data.network.dto.user_info.CapabilityGroupDTO
import com.tatry.yandextest.data.network.dto.user_info.DeviceCapabilityDTO
import com.tatry.yandextest.data.network.dto.user_info.DeviceDTO
import com.tatry.yandextest.data.network.dto.user_info.DevicePropertyDTO
import com.tatry.yandextest.data.network.dto.user_info.GroupDTO
import com.tatry.yandextest.data.network.dto.user_info.HouseholdDTO
import com.tatry.yandextest.data.network.dto.user_info.RoomDTO
import com.tatry.yandextest.data.network.dto.user_info.ScenarioDTO
import com.tatry.yandextest.data.network.dto.user_info.UserInfoDTO
import com.tatry.yandextest.domain.model.devices.action.ActionObjectModel
import com.tatry.yandextest.domain.model.devices.action.DeviceActionModel
import com.tatry.yandextest.domain.model.devices.action.DeviceListModel
import com.tatry.yandextest.domain.model.devices.action.StateObjectModel
import com.tatry.yandextest.domain.model.devices.user_info.CapabilityGroupModel
import com.tatry.yandextest.domain.model.devices.user_info.DeviceCapabilityModel
import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.model.devices.user_info.DevicePropertyModel
import com.tatry.yandextest.domain.model.devices.user_info.GroupModel
import com.tatry.yandextest.domain.model.devices.user_info.HouseholdModel
import com.tatry.yandextest.domain.model.devices.user_info.RoomModel
import com.tatry.yandextest.domain.model.devices.user_info.ScenarioModel
import com.tatry.yandextest.domain.model.devices.user_info.UserInfoModel

class DeviceMapper {

    // DeviceActionDTOToDeviceActionModel

    fun mapDeviceListDTOToDeviceListModel(deviceListDto: DeviceListDTO): DeviceListModel {
        return DeviceListModel(
            devices = deviceListDto.devices.map { mapDeviceActionDTOToDeviceActionModel(it) }
        )
    }

    private fun mapDeviceActionDTOToDeviceActionModel(deviceActionDto: DeviceActionDTO): DeviceActionModel {
        return DeviceActionModel(
            id = deviceActionDto.id,
            actions = deviceActionDto.actions.map { mapActionObjectDTOToActionObjectModel(it) }
        )
    }

    private fun mapActionObjectDTOToActionObjectModel(actionObjectDto: ActionObjectDTO): ActionObjectModel {
        return ActionObjectModel(
            type = actionObjectDto.type,
            state = mapStateObjectDTOToSateObjectModel(actionObjectDto.state)
        )
    }

    private fun mapStateObjectDTOToSateObjectModel(stateObjectDto: StateObjectDTO?): StateObjectModel {
        return StateObjectModel(
            instance = stateObjectDto?.instance,
            value = stateObjectDto?.value
        )
    }


    // DeviceListModelToDeviceListDTO

    fun mapDeviceListModelToDeviceListDTO(deviceListModel: DeviceListModel): DeviceListDTO {
        return DeviceListDTO(
            devices = deviceListModel.devices.map { mapDeviceActionModelToDeviceActionDTO(it) }
        )
    }

    private fun mapDeviceActionModelToDeviceActionDTO(deviceActionModel: DeviceActionModel): DeviceActionDTO {
        return DeviceActionDTO(
            id = deviceActionModel.id,
            actions = deviceActionModel.actions.map { mapActionObjectModelToActionObjectDto(it) }
        )
    }

    private fun mapActionObjectModelToActionObjectDto(actionObjectModel: ActionObjectModel): ActionObjectDTO {
        return ActionObjectDTO(
            type = actionObjectModel.type,
            state = mapStateObjectModelToSateObjectDTO(actionObjectModel.state)
        )
    }

    private fun mapStateObjectModelToSateObjectDTO(stateObjectModel: StateObjectModel?): StateObjectDTO {
        return StateObjectDTO(
            instance = stateObjectModel?.instance,
            value = stateObjectModel?.value
        )
    }


    // UserInfoModelToUserInfoDTO
    fun mapUserInfoModelToUserInfoDTO(userInfoModel: UserInfoModel): UserInfoDTO {
        return UserInfoDTO(
            status = userInfoModel.status,
            requestId = userInfoModel.requestId,
            roomList = userInfoModel.roomList.map { mapRoomModelToRoomDTO(it) },
            groupList = userInfoModel.groupList.map { mapGroupModelToGroupDTO(it) },
            deviceList = userInfoModel.deviceList.map { mapDeviceModelToDeviceDTO(it) },
            scenarioList = userInfoModel.scenarioList.map { mapScenarioModelToScenarioDTO(it) },
            householdList = userInfoModel.householdList.map { mapHouseholdModelToHouseholdDTO(it) }
        )
    }

    private fun mapRoomModelToRoomDTO(roomModel: RoomModel): RoomDTO {
        return RoomDTO(
            id = roomModel.id,
            name = roomModel.name,
            deviceIdList = roomModel.deviceIdList.map { it },
            householdId = roomModel.householdId
        )
    }

    private fun mapGroupModelToGroupDTO(groupModel: GroupModel): GroupDTO {
        return GroupDTO(
            id = groupModel.id,
            name = groupModel.name,
            aliasesList = groupModel.aliasesList.map { it },
            type = groupModel.type,
            capabilityList = groupModel.capabilityList.map {
                mapCapabilityGroupModelToCapabilityGroupDTO(
                    it
                )
            },
            deviceIdList = groupModel.deviceIdList.map { it },
            householdId = groupModel.householdId
        )
    }

    private fun mapCapabilityGroupModelToCapabilityGroupDTO(
        capabilityGroupModel: CapabilityGroupModel
    ): CapabilityGroupDTO {
        return CapabilityGroupDTO(
            type = capabilityGroupModel.type,
            retrievable = capabilityGroupModel.retrievable,
            parameters = capabilityGroupModel.parameters,
            state = mapStateObjectModelToSateObjectDTO(capabilityGroupModel.state)
        )
    }

    private fun mapDeviceModelToDeviceDTO(deviceModel: DeviceModel): DeviceDTO {
        return DeviceDTO(
            id = deviceModel.id,
            name = deviceModel.name,
            aliasesList = deviceModel.aliasesList.map { it },
            roomId = deviceModel.roomId,
            externalId = deviceModel.externalId,
            skillId = deviceModel.skillId,
            type = deviceModel.type,
            groupIdList = deviceModel.groupIdList.map { it },
            capabilityList = deviceModel.capabilityList.map {
                mapDeviceCapabilityModelToDeviceCapabilityDTO(
                    it
                )
            },
            propertyList = deviceModel.propertyList.map {
                mapDevicePropertyModelToDevicePropertyDTO(
                    it
                )
            },
            householdId = deviceModel.householdId
        )
    }

    private fun mapDeviceCapabilityModelToDeviceCapabilityDTO(
        deviceCapabilityModel: DeviceCapabilityModel
    ): DeviceCapabilityDTO {
        return DeviceCapabilityDTO(
            type = deviceCapabilityModel.type,
            reportable = deviceCapabilityModel.reportable,
            retrievable = deviceCapabilityModel.retrievable,
            parameters = deviceCapabilityModel.parameters,
            state = mapStateObjectModelToSateObjectDTO(deviceCapabilityModel.state),
            lastUpdated = deviceCapabilityModel.lastUpdated
        )
    }

    private fun mapDevicePropertyModelToDevicePropertyDTO(
        devicePropertyModel: DevicePropertyModel
    ): DevicePropertyDTO {
        return DevicePropertyDTO(
            type = devicePropertyModel.type,
            reportable = devicePropertyModel.reportable,
            retrievable = devicePropertyModel.retrievable,
            parameters = devicePropertyModel.parameters,
            state = mapStateObjectModelToSateObjectDTO(devicePropertyModel.state),
            lastUpdated = devicePropertyModel.lastUpdated
        )
    }

    private fun mapScenarioModelToScenarioDTO(scenarioModel: ScenarioModel): ScenarioDTO {
        return ScenarioDTO(
            id = scenarioModel.id,
            name = scenarioModel.name,
            isActive = scenarioModel.isActive
        )
    }

    private fun mapHouseholdModelToHouseholdDTO(householdModel: HouseholdModel): HouseholdDTO {
        return HouseholdDTO(
            id = householdModel.id,
            name = householdModel.name
        )
    }


    // UserInfoDTOToUserInfoModel
    fun mapUserInfoDTOToUserInfoModel(userInfoDto: UserInfoDTO): UserInfoModel {
        return UserInfoModel(
            status = userInfoDto.status,
            requestId = userInfoDto.requestId,
            roomList = userInfoDto.roomList.map { mapRoomDTOToRoomModel(it) },
            groupList = userInfoDto.groupList.map { mapGroupDTOToGroupModel(it) },
            deviceList = userInfoDto.deviceList.map { mapDeviceDTOToDeviceModel(it) },
            scenarioList = userInfoDto.scenarioList.map { mapScenarioDTOToScenarioModel(it) },
            householdList = userInfoDto.householdList.map { mapHouseholdDTOToHouseholdModel(it) }
        )
    }

    private fun mapRoomDTOToRoomModel(roomDto: RoomDTO): RoomModel {
        return RoomModel(
            id = roomDto.id,
            name = roomDto.name,
            deviceIdList = roomDto.deviceIdList.map { it },
            householdId = roomDto.householdId
        )
    }

    private fun mapGroupDTOToGroupModel(groupDto: GroupDTO): GroupModel {
        return GroupModel(
            id = groupDto.id,
            name = groupDto.name,
            aliasesList = groupDto.aliasesList.map { it },
            type = groupDto.type,
            capabilityList = groupDto.capabilityList.map {
                mapCapabilityGroupDTOToCapabilityGroupModel(
                    it
                )
            },
            deviceIdList = groupDto.deviceIdList.map { it },
            householdId = groupDto.householdId
        )
    }

    private fun mapCapabilityGroupDTOToCapabilityGroupModel(
        capabilityGroupDto: CapabilityGroupDTO
    ): CapabilityGroupModel {
        return CapabilityGroupModel(
            type = capabilityGroupDto.type,
            retrievable = capabilityGroupDto.retrievable,
            parameters = capabilityGroupDto.parameters,
            state = mapStateObjectDTOToSateObjectModel(capabilityGroupDto.state)
        )
    }

    private fun mapDeviceDTOToDeviceModel(deviceDto: DeviceDTO): DeviceModel {
        return DeviceModel(
            id = deviceDto.id,
            name = deviceDto.name,
            aliasesList = deviceDto.aliasesList.map { it },
            roomId = deviceDto.roomId,
            externalId = deviceDto.externalId,
            skillId = deviceDto.skillId,
            type = deviceDto.type,
            groupIdList = deviceDto.groupIdList.map { it },
            capabilityList = deviceDto.capabilityList.map {
                mapDeviceCapabilityDTOToDeviceCapabilityModel(
                    it
                )
            },
            propertyList = deviceDto.propertyList.map { mapDevicePropertyDTOToDevicePropertyModel(it) },
            householdId = deviceDto.householdId
        )
    }

    private fun mapDeviceCapabilityDTOToDeviceCapabilityModel(
        deviceCapabilityDto: DeviceCapabilityDTO
    ): DeviceCapabilityModel {
        return DeviceCapabilityModel(
            type = deviceCapabilityDto.type,
            reportable = deviceCapabilityDto.reportable,
            retrievable = deviceCapabilityDto.retrievable,
            parameters = deviceCapabilityDto.parameters,
            state = mapStateObjectDTOToSateObjectModel(deviceCapabilityDto.state),
            lastUpdated = deviceCapabilityDto.lastUpdated
        )
    }

    private fun mapDevicePropertyDTOToDevicePropertyModel(
        devicePropertyDto: DevicePropertyDTO
    ): DevicePropertyModel {
        return DevicePropertyModel(
            type = devicePropertyDto.type,
            reportable = devicePropertyDto.reportable,
            retrievable = devicePropertyDto.retrievable,
            parameters = devicePropertyDto.parameters,
            state = mapStateObjectDTOToSateObjectModel(devicePropertyDto.state),
            lastUpdated = devicePropertyDto.lastUpdated
        )
    }

    private fun mapScenarioDTOToScenarioModel(scenarioDto: ScenarioDTO): ScenarioModel {
        return ScenarioModel(
            id = scenarioDto.id,
            name = scenarioDto.name,
            isActive = scenarioDto.isActive
        )
    }

    private fun mapHouseholdDTOToHouseholdModel(householdDto: HouseholdDTO): HouseholdModel {
        return HouseholdModel(
            id = householdDto.id,
            name = householdDto.name
        )
    }


    // DeviceModelToDeviceEntity
    fun mapDeviceModelToDeviceEntity(deviceModel: DeviceModel): DeviceEntity {
        return DeviceEntity(
            id = deviceModel.id,
            name = deviceModel.name,
            externalId = deviceModel.externalId,
            type = deviceModel.type
        )
    }

    fun mapDeviceModelListToDeviceEntityList(deviceModelList: List<DeviceModel>) =
        deviceModelList.map { mapDeviceModelToDeviceEntity(it) }

    // DeviceMEntityToDeviceModel
    fun mapDeviceEntityToDeviceModel(deviceEntity: DeviceEntity): DeviceModel {
        return DeviceModel(
            id = deviceEntity.id,
            name = deviceEntity.name,
            aliasesList = listOf(),
            roomId = null,
            externalId = deviceEntity.externalId,
            type = deviceEntity.type,
            skillId = "",
            groupIdList = listOf(),
            capabilityList = listOf(),
            propertyList = listOf(),
            householdId = ""
        )
    }

    fun mapDeviceEntityListToDeviceModelList(deviceEntityList: List<DeviceEntity>) =
        deviceEntityList.map { mapDeviceEntityToDeviceModel(it) }

}