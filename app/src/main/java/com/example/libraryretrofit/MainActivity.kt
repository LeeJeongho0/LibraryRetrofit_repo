package com.example.libraryretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryretrofit.data.Library
import com.example.libraryretrofit.data.Row
import com.example.libraryretrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Member

class MainActivity : AppCompatActivity() {
    companion object{
        val DB_NAME = "libraryDB.db2"
        val VERSION = 1
    }
    lateinit var binding:ActivityMainBinding
    lateinit var libraryData: MutableList<LibraryData>
    lateinit var dbHelper:DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이타베이스 객체
        dbHelper = DBHelper(applicationContext, MainActivity.DB_NAME, MainActivity.VERSION)
        libraryData = mutableListOf()
        val libraryList = dbHelper.selectAll()
        if(libraryList == null){
            // 서울도서관 정보를 가져오는 로딩함수
            loadLibraries()
        }else{
            binding.recyclerView.adapter = LibraryAdapter(libraryList!!)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }

    }

    private fun loadLibraries() {
        val retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SeoulOpenService::class.java)

        service.getLibrarys(SeoulOpenApi.API_KEY, 30).enqueue(object:
            Callback<Library>{
            override fun onResponse(call: Call<Library>, response: Response<Library>) {
                val library = response.body()
                showLibrary(library)
                Toast.makeText(this@MainActivity,"서울도서관 공공데이터를 가져왔습니다",Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Library>, t: Throwable) {
                Log.e("MainActivity", "서울도서관 공공데이터를 가져올 수 없습니다. ${t.printStackTrace()}")
                Toast.makeText(this@MainActivity,"서울도서관 공공데이터를 가져올 수 없습니다",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showLibrary(library: Library?) {

        library?.let {
            for(data in it.SeoulPublicLibraryInfo.row){
                val code = data.GU_CODE
                val name = data.LBRRY_NAME
                val phone = data.TEL_NO
                val address = data.ADRES
                val latitude = data.XCNTS
                val logitude = data.YDNTS
                val libraryData= LibraryData(code, name, phone, address, latitude, logitude)

                if(dbHelper.insertTBL(libraryData)){
                    Log.e("MainActivity","입력성공 ${libraryData.toString()}")
                }else{
                    Log.e("MainActivity","입력실패 ${libraryData.toString()}")
                }
            }
        }
    }
}