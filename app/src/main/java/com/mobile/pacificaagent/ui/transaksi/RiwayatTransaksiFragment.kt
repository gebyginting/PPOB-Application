package com.mobile.pacificaagent.ui.transaksi

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.databinding.FragmentRiwayatTransaksiBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.auth.UserViewModel
import com.mobile.pacificaagent.utils.Helper.formatRupiah
import com.mobile.pacificaagent.utils.ResultState
import com.mobile.pacificaagent.utils.TransactionDetail
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class RiwayatTransaksiFragment : Fragment() {

    private var _binding: FragmentRiwayatTransaksiBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRiwayatTransaksiBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDetailPembayaran()
        setupShareAction()
        setupBackButton()
    }

    private fun setupDetailPembayaran() {
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.historyDetailState.collectLatest { result ->
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        when (val detail = result.data) {
                            is TransactionDetail.Token -> {
                                // Tampilkan tokenData di UI
                                binding.tvTitle.text = "Pembelian ${detail.data.type}"
                                binding.layoutKodeToken.visibility = View.VISIBLE
                                binding.ivTransaksi.setImageResource(R.drawable.ic_pln)
                                binding.tvKodeTokenValue.text = detail.detail.hasil.tokenNumber
                                binding.tvJenisValue.text = detail.data.type
                                binding.tvStatusValue.text = detail.data.status
                                binding.tvHargaValue.text = formatRupiah(detail.detail.breakdown.total)
                                binding.tvTotalTagihanValue.text = formatRupiah(detail.detail.breakdown.total)
                            }

                            is TransactionDetail.EWallet -> {
                                // Tampilkan ewalletData di UI
                                binding.tvTitle.text = getString(R.string.pembelian_ewallet, detail.detail.nameApps)
                                binding.ivTransaksi.setImageResource(getEWalletLogo(detail.detail.nameApps))
                                binding.tvJenisValue.text = detail.detail.nameApps
                                binding.tvStatusValue.text = detail.data.status
                                binding.tvHargaValue.text = formatRupiah(detail.detail.harga)
                                binding.tvTotalTagihanValue.text = formatRupiah(detail.detail.harga)
                            }

                            is TransactionDetail.PulsaData -> {
                                // Tampilkan pulsaData di UI
                                binding.tvTitle.text = getString(R.string.pembelian_pulsa_paket_data)
                                binding.tvJenisValue.text = detail.data.type
                                binding.tvStatusValue.text = detail.data.status
                                binding.tvHargaValue.text = formatRupiah(detail.detail.price)
                                binding.tvTotalTagihanValue.text = formatRupiah(detail.detail.price)
                            }

                            is TransactionDetail.Deposit -> {
                                binding.tvTitle.text = getString(R.string.top_up_saldo_pacificapay)
                                binding.tvJenisValue.text = detail.data.type
                                binding.tvStatusValue.text = detail.data.status
                                binding.tvHargaValue.text = formatRupiah(detail.detail.detail.amount)
                                binding.tvTotalTagihanValue.text = formatRupiah(detail.detail.detail.amount)
                            }

                            is TransactionDetail.Unknown -> {
                                // Tampilkan fallback jika tipe tidak dikenal
                                binding.tvTitle.text = getString(R.string.transaksi_tidak_diketahui)
                                binding.tvJenisValue.text = "-"
                                binding.tvStatusValue.text = "-"
                                binding.tvHargaValue.text = "-"
                                binding.tvTotalTagihanValue.text = "-"
                            }
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupShareAction() {
        binding.shareBtn.setOnClickListener {
            shareLayoutAsImage()
        }
    }

    private fun shareLayoutAsImage() {
        val viewToShare = binding.shareItem

        // Tunggu hingga view sudah layout dan ukurannya valid
        viewToShare.post {
            // 1. Buat Bitmap dari view
            val bitmap = Bitmap.createBitmap(
                viewToShare.width,
                viewToShare.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            viewToShare.draw(canvas)

            // 2. Simpan ke file sementara
            val cachePath = File(requireContext().cacheDir, "images")
            cachePath.mkdirs()
            val file = File(cachePath, "transaksi.png")
            FileOutputStream(file).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            }

            // 3. Dapatkan URI file
            val fileUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                file
            )

            // 4. Intent share
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, fileUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(Intent.createChooser(shareIntent, "Bagikan transaksi melalui..."))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingOverlay.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun getEWalletLogo(type: String): Int {
        return when (type.uppercase()) {
            "DANA" -> R.drawable.logo_dana
            "OVO" -> R.drawable.logo_ovo
            "GPAY", "GOPAY" -> R.drawable.logo_gopay
            else -> R.drawable.e_wallet
        }
    }

    private fun setupBackButton() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}