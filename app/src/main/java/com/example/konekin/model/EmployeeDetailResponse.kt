package com.example.konekin.model

import com.google.gson.annotations.SerializedName

data class EmployeeDetailResponse(
    @SerializedName("data")
    val data: Employee
)
