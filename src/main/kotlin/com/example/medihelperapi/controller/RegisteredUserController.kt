package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.*
import com.example.medihelperapi.service.*
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/registered-users")
class RegisteredUserController(
        private val registeredUserService: RegisteredUserService,
        private val medicineService: MedicineService,
        private val personService: PersonService,
        private val medicinePlanService: MedicinePlanService,
        private val plannedMedicineService: PlannedMedicineService
) {

    @PatchMapping("/password")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun changeUserPassword(@RequestBody newPassword: NewPasswordDto) {
        registeredUserService.changePassword(registeredUserService.getLoggedUser(), newPassword)
    }

    @PutMapping("/synchronization/medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun synchronizeMedicines(@RequestBody syncRequestDto: SyncRequestDto<MedicineDto>): List<MedicineDto> {
        println("synchronizeMedicines")
        println(syncRequestDto.toString())
        return medicineService.synchronizeMedicines(
                registeredUser = registeredUserService.getLoggedUser(),
                insertUpdateDtoList = syncRequestDto.insertUpdateDtoList,
                deleteRemoteIdList = syncRequestDto.deleteRemoteIdList
        )
    }
}