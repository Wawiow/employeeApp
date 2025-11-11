package com.example.konekin.network

import android.telecom.Call
import com.example.konekin.model.Employee
import com.example.konekin.model.EmployeeDetailResponse
import com.example.konekin.model.EmployeeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    //digunakan karena kita mau call API dengan url: BASE_URL + "/employee"
    @GET("employees")
    fun getAllEmployee(): retrofit2.Call<EmployeeResponse>

    //handle call api utk detail employee
    //url = BASE_URL + "employee/id"
    @GET("employe/{id}")
    fun getAllEmployeeDetail(
        @Path("id") id: Int
    ): retrofit2.Call<EmployeeDetailResponse>
}