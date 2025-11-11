package com.example.konekin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.konekin.databinding.ActivityDetailEmployeeBinding
import com.example.konekin.model.Employee
import com.example.konekin.model.EmployeeDetailResponse
import com.example.konekin.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEmployeeBinding

    //api client
    private val apiClient = ApiClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailEmployeeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val employeeId = intent.getIntExtra("EXTRA_ID", -1)
        getEmployeeDetail(employeeId)

    }

    private fun getEmployeeDetail(id: Int){
        val response = apiClient.getAllEmployeeDetail(id)

        response.enqueue(object : Callback<EmployeeDetailResponse>{
            override fun onResponse(
                p0: Call<EmployeeDetailResponse?>,
                response: Response<EmployeeDetailResponse?>
            ) {
                if (response.isSuccessful){
                    Toast.makeText(
                        this@DetailEmployeeActivity,
                        "HTTP ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()

                    return

                }

                val body = response.body()
                val employee = body?.data

                //update ui
                binding.txtName.setText(employee?.name.toString())
                binding.txtAge.setText(employee?.age.toString())
                binding.txtSalary.setText(employee?.salary.toString())
            }

            override fun onFailure(p0: Call<EmployeeDetailResponse?>, p1: Throwable) {
                Toast.makeText(
                    this@DetailEmployeeActivity,
                    "Get Employee Data Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }
}