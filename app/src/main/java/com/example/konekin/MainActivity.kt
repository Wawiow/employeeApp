package com.example.konekin

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.konekin.databinding.ActivityMainBinding
import com.example.konekin.model.EmployeeDetailResponse
import com.example.konekin.model.EmployeeRequest
import com.example.konekin.model.EmployeeResponse
import com.example.konekin.network.ApiClient
import okhttp3.Callback
import okhttp3.Response
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //api client
    //digunakan utk call api yg sudah dibuat
    private val apiClient = ApiClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getAllEmployee()


        with(binding){


            btnAddEmployee.setOnClickListener {
                val intent = Intent(this@MainActivity, CreateEmployeeActivity::class.java)
                startActivity(intent)
            }



//           createEmployee()

            //updateEmployee(1)

            //deleteEmployee(2)

        }
    }

    override fun onResume() {
        super.onResume()
        getAllEmployee()   // refresh list setiap kembali ke MainActivity
    }

    //fun utk call api get all employee
    private fun getAllEmployee(){
        //call get api yang tadi sudah dibuat
        val response = apiClient.getAllEmployee()

        response.enqueue(object : retrofit2.Callback<EmployeeResponse> {
            override fun onResponse(
                p0: Call<EmployeeResponse?>,
                response: retrofit2.Response<EmployeeResponse?>
            ) {
                if (!response.isSuccessful){
                    Toast.makeText(
                        this@MainActivity,
                        "HTTP ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()

                    return
                }

                val body = response.body()
                val employees = body?.data.orEmpty()

                if (employees.isEmpty()){
                    Toast.makeText(
                        this@MainActivity,
                        "data employee kosong",
                        Toast.LENGTH_SHORT
                    ).show()

                    return
                }

                val names = employees.map { it.name }

                val listAdapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    names
                )

                binding.lvEmployee.adapter = listAdapter

                //jika user klik item
                //open detail page

                binding.lvEmployee.onItemClickListener = AdapterView.OnItemClickListener{_,_,position,_ ->
                    val intent = Intent(this@MainActivity, DetailEmployeeActivity::class.java)

                    //kirim id ke detail page
                    val id = employees[position].id
                    intent.putExtra("EXTRA_ID", id)

                    //buka value
                    startActivity(intent)
                }

            }

            override fun onFailure(
                p0: Call<EmployeeResponse?>,
                p1: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Failed",
                    Toast.LENGTH_SHORT
                ).show()

            }

        })
    }

    private fun createEmployee() {

        val request = EmployeeRequest(
            name = "Karyawan Baru",
            salary = 2500000,
            age = 23
        )

        val call = apiClient.createEmployee(request)

        call.enqueue(object : retrofit2.Callback<EmployeeDetailResponse> {
            override fun onResponse(
                call: Call<EmployeeDetailResponse?>,
                response: retrofit2.Response<EmployeeDetailResponse?>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    Toast.makeText(
                        this@MainActivity,
                        "Berhasil tambah: ${data?.name}",
                        Toast.LENGTH_SHORT
                    ).show()

                    getAllEmployee() // refresh list
                } else {
                    Toast.makeText(this@MainActivity, "Gagal membuat employee", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<EmployeeDetailResponse?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateEmployee(id: Int) {

        val request = EmployeeRequest(
            name = "Nama Update",
            salary = 5000000,
            age = 27
        )

        val call = apiClient.updateEmployee(id, request)

        call.enqueue(object : retrofit2.Callback<EmployeeDetailResponse> {
            override fun onResponse(
                call: Call<EmployeeDetailResponse?>,
                response: retrofit2.Response<EmployeeDetailResponse?>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@MainActivity,
                        "Employee ID $id berhasil diupdate",
                        Toast.LENGTH_SHORT
                    ).show()

                    getAllEmployee() // refresh
                } else {
                    Toast.makeText(this@MainActivity, "Gagal update", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EmployeeDetailResponse?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteEmployee(id: Int) {

        val call = apiClient.deleteEmployee(id)

        call.enqueue(object : retrofit2.Callback<EmployeeResponse> {
            override fun onResponse(
                call: Call<EmployeeResponse?>,
                response: retrofit2.Response<EmployeeResponse?>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@MainActivity,
                        "Employee ID $id berhasil dihapus",
                        Toast.LENGTH_SHORT
                    ).show()

                    getAllEmployee() // refresh list
                } else {
                    Toast.makeText(this@MainActivity, "Gagal menghapus", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EmployeeResponse?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}