package com.mobile.pacificaagent.data.network.retrofit

import DepositResponse
import com.mobile.pacificaagent.data.request.DepositRequest
import com.mobile.pacificaagent.data.request.LoginRequest
import com.mobile.pacificaagent.data.request.RegisterRequest
import com.mobile.pacificaagent.data.request.UpdateProfileRequest
import com.mobile.pacificaagent.data.request.prabayar.TopUpDataRequest
import com.mobile.pacificaagent.data.request.prabayar.TopUpEWalletRequest
import com.mobile.pacificaagent.data.request.prabayar.TopUpPulsaRequest
import com.mobile.pacificaagent.data.request.prabayar.TopUpTokenRequest
import com.mobile.pacificaagent.data.response.DetailProdukPrabayarResponse
import com.mobile.pacificaagent.data.response.GetBalanceResponse
import com.mobile.pacificaagent.data.response.HistoryResponse
import com.mobile.pacificaagent.data.response.LoginResponse
import com.mobile.pacificaagent.data.response.ProdukPrabayarResponse
import com.mobile.pacificaagent.data.response.RegisterUpdateResponse
import com.mobile.pacificaagent.data.response.UserProfileResponse
import com.mobile.pacificaagent.data.response.prabayar.TopUpEWalletResponse
import com.mobile.pacificaagent.data.response.prabayar.TopUpPulsaResponse
import com.mobile.pacificaagent.data.response.prabayar.TopUpTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // USER
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

    @GET("/api/users/history")
    suspend fun getHistory(): Response<HistoryResponse>

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

    // DEPOSIT
    @POST("/api/users/deposit")
    suspend fun deposit(
        @Body requestBody: DepositRequest
    ): Response<DepositResponse>

    // TOP-UP PRABAYAR
    @POST("/api/users/topup/pulsa")
    suspend fun topupPulsa(
        @Body requestBody: TopUpPulsaRequest
    ): Response<TopUpPulsaResponse>

    @POST("/api/users/topup/data")
    suspend fun topupData(
        @Body requestBody: TopUpDataRequest
    ): Response<TopUpPulsaResponse>

    @POST("/api/users/topup/token")
    suspend fun topupToken(
        @Body requestBody: TopUpTokenRequest
    ): Response<TopUpTokenResponse>

    @POST("/api/users/topup/ewallet")
    suspend fun topupEWallet(
        @Body requestBody: TopUpEWalletRequest
    ): Response<TopUpEWalletResponse>
}
