package com.example.sharonapp.utility

// 필요한 import 추가

import android.os.Parcelable
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Retrofit 인터페이스 및 데이터 클래스 정의
data class Checkconnection(val connect: Boolean, val NeedToUpdate: Boolean)
// 1. JSON 구조에 맞는 데이터 클래스 정의
@Parcelize
data class ServerResponse(
    @SerializedName("data") var data: List<List<String>>
) : Parcelable {
    var pCount = 1
        get() = data.size
}

interface ApiService {
    @GET("/join/{nickname}")
    suspend fun submitNickname(@Path("nickname") nickname: String): String

    @GET("/check/{nickname}")
    suspend fun connectionCheck(@Path("nickname") nickname: String): Checkconnection
    @GET("/playerData/{nickname}")
    suspend fun getPlayerData(@Path("nickname") nickname: String): ServerResponse

    @GET("/ready/{nickname}")
    suspend fun sendReady(@Path("nickname") nickname: String): String
    @GET("/notReady/{nickname}")
    suspend fun sendNotReady(@Path("nickname") nickname: String): String

    @GET("/isRunning")
    suspend fun isRunning(): Boolean

    @GET("/inputNumber/{nickname}")
    suspend fun getInputNumber(
        @Path("nickname") nickname: String,           // 경로 변수
        @Query("number") number: Int     // 쿼리 파라미터
    ): Int
}
// 내부적으로 싱글톤을 관리하기 위한 객체
private object RetrofitHolder {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://sharonproject.ddns.net:5522") // 베이스 URL
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create())) // JSON 파싱용
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
fun createApiService(): ApiService {
    return RetrofitHolder.apiService
}
fun SecondApiService(): ApiService {
    return Retrofit.Builder()
        .baseUrl("http://sharonproject.ddns.net:5522")
        .addConverterFactory(ScalarsConverterFactory.create()) // 단순 문자열 처리용
        .build()
        .create(ApiService::class.java)
}