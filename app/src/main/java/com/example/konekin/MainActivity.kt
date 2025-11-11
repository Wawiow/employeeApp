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



        }
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
}