package com.example.libraryretrofit

import com.example.libraryretrofit.data.Library
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

//   http://openapi.seoul.go.kr:8088/4d4151665961776634355953716572/json/SeoulPublicLibraryInfo/1/5/

class SeoulOpenApi {
    companion object{
        val DOMAIN = "http://openapi.seoul.go.kr:8088"
        val API_KEY = "4d4151665961776634355953716572"
        val LIST_TOTAL_COUNT = 203
    }
}

interface SeoulOpenService {
    @GET("{api_key}/json/SeoulPublicLibraryInfo/1/{end}/")
    fun getLibrarys(@Path("api_key") key:String, @Path("end") limit:Int) : Call<Library>
}