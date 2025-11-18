package com.example.konekin.network

import android.telecom.Call
import com.example.konekin.model.Employee
import com.example.konekin.model.EmployeeDetailResponse
import com.example.konekin.model.EmployeeRequest
import com.example.konekin.model.EmployeeResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    //digunakan karena kita mau call API dengan url: BASE_URL + "/employee"
    @GET("employees")
    fun getAllEmployee(): retrofit2.Call<EmployeeResponse>

    //handle call api utk detail employee
    //url = BASE_URL + "employee/id"
    @GET("employee/{id}")
    fun getAllEmployeeDetail(
        @Path("id") id: Int
    ): retrofit2.Call<EmployeeDetailResponse>

    // CREATE EMPLOYEE
    @POST("create")
    fun createEmployee(
        @Body request: EmployeeRequest
    ): retrofit2.Call<EmployeeDetailResponse>

    // UPDATE EMPLOYEE
    @PATCH("update/{id}")
    fun updateEmployee(
        @Path("id") id: Int,
        @Body request: EmployeeRequest
    ): retrofit2.Call<EmployeeDetailResponse>

    // DELETE EMPLOYEE
    @DELETE("delete/{id}")
    fun deleteEmployee(
        @Path("id") id: Int
    ): retrofit2.Call<EmployeeResponse>
}