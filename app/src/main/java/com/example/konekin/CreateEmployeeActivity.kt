package com.example.konekin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.konekin.databinding.ActivityCreateEmployeeBinding
import com.example.konekin.model.EmployeeDetailResponse
import com.example.konekin.model.EmployeeRequest
import com.example.konekin.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEmployeeBinding
    private val apiClient = ApiClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreate.setOnClickListener {
            createEmployee()
        }
    }

    private fun createEmployee() {
        val name = binding.etName.text.toString()
        val salaryStr = binding.etSalary.text.toString()
        val ageStr = binding.etAge.text.toString()

        if (name.isEmpty() || salaryStr.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val salary = salaryStr.toInt()
        val age = ageStr.toInt()

        val request = EmployeeRequest(name, salary, age)

        apiClient.createEmployee(request)
            .enqueue(object : Callback<EmployeeDetailResponse> {
                override fun onResponse(
                    call: Call<EmployeeDetailResponse>,
                    response: Response<EmployeeDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@CreateEmployeeActivity, "Employee dibuat!", Toast.LENGTH_SHORT).show()

                        finish() // kembali ke halaman sebelumnya
                    } else {
                        Toast.makeText(this@CreateEmployeeActivity, "Gagal membuat employee", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<EmployeeDetailResponse>, t: Throwable) {
                    Toast.makeText(this@CreateEmployeeActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }

            })
    }
}