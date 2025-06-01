package com.mobile.pacificaagent.data.network.retrofit

import com.mobile.pacificaagent.data.request.LoginRequest
import com.mobile.pacificaagent.data.request.RegisterRequest
import com.mobile.pacificaagent.data.request.UpdateProfileRequest
import com.mobile.pacificaagent.data.response.DetailProdukPrabayarResponse
import com.mobile.pacificaagent.data.response.GetBalanceResponse
import com.mobile.pacificaagent.data.response.LoginResponse
import com.mobile.pacificaagent.data.response.ProdukPrabayarResponse
import com.mobile.pacificaagent.data.response.RegisterUpdateResponse
import com.mobile.pacificaagent.data.response.UserProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/api/users/register")
    suspend fun register(
        @Body requestBody: RegisterRequest
    ): Response<RegisterUpdateResponse>

    @POST("/api/users/login")
    suspend fun login(
        @Body requestBody: LoginRequest
    ): Response<LoginResponse>

    @GET("/api/users/profile")
    suspend fun getProfile(): Response<UserProfileResponse>

    @PATCH("api/users/profile")
    suspend fun updateProfile(
        @Body requestBody: UpdateProfileRequest
    ): Response<RegisterUpdateResponse>

    @GET("/api/users/balance")
    suspend fun getBalance(): Response<GetBalanceResponse>

    // PRODUK PRABAYAR
    @GET("/api/users/produk-prabayar/{category_id}")
    suspend fun produkPrabayar(
        @Path("category_id") categoryId: String,
        @Query("number") number: String
    ): Response<ProdukPrabayarResponse>

    @GET("/api/users/detail-produk-prabayar/{product_id}")
    suspend fun detailProdukPrabayar(
        @Path("product_id") productId: String,
    ): Response<DetailProdukPrabayarResponse>
}
