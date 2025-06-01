import com.google.gson.annotations.SerializedName

data class DepositResponse(
	@SerializedName("data")
	val data: DepositData,
	@SerializedName("message")
	val message: String,
	@SerializedName("status")
	val status: String
)

data class DepositData(
	@SerializedName("id")
	val id: String,
	@SerializedName("userId")
	val userId: String,
	@SerializedName("type")
	val type: String,
	@SerializedName("amount")
	val amount: Int,
	@SerializedName("status")
	val status: String,
	@SerializedName("detail")
	val detail: DepositDetail,
	@SerializedName("createdAt")
	val createdAt: String,
	@SerializedName("updatedAt")
	val updatedAt: String
)

data class DepositDetail(
	@SerializedName("VA")
	val va: String,
	@SerializedName("bank")
	val bank: String,
	@SerializedName("detail")
	val subDetail: DepositSubDetail
)

data class DepositSubDetail(
	@SerializedName("date")
	val date: Long,
	@SerializedName("amount")
	val amount: Int,
	@SerializedName("merchant_ref")
	val merchantRef: String,
	@SerializedName("code_pembayaran")
	val codePembayaran: String
)
