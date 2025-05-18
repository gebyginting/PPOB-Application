package com.mobile.pacificaagent.ui.transaksi

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.databinding.FragmentRiwayatTransaksiBinding
import java.io.File
import java.io.FileOutputStream

class RiwayatTransaksiFragment : Fragment() {

    private var _binding: FragmentRiwayatTransaksiBinding? = null
    private val binding get() = _binding!!

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
        arguments?.let {
            val jenisTransaksi = RiwayatTransaksiFragmentArgs.fromBundle(it).jenisTransaksi
            val statusTransaksi = RiwayatTransaksiFragmentArgs.fromBundle(it).statusTransaksi
            val namaTransaksi = RiwayatTransaksiFragmentArgs.fromBundle(it).namaTransaksi
            val nominalTransaksi = RiwayatTransaksiFragmentArgs.fromBundle(it).nominalTransaksi

            with(binding) {
                when (jenisTransaksi) {
                    "DANA" -> ivTransaksi.setImageResource(R.drawable.ic_dana)
                    "Listrik Token" -> ivTransaksi.setImageResource(R.drawable.ic_pln)
                }
                if (jenisTransaksi == "Listrik Token") {
                    layoutKodeToken.visibility = View.VISIBLE
                }
                tvJenisValue.text = jenisTransaksi
                tvTitle.text = "Pembelian $namaTransaksi"
                tvStatusLabel.text = statusTransaksi
                tvHargaValue.text = nominalTransaksi
                tvTotalTagihanValue.text = nominalTransaksi
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