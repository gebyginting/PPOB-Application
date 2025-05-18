package com.mobile.pacificaagent.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.pacificaagent.data.model.Transaksi
import com.mobile.pacificaagent.databinding.ItemTransaksiBinding

class TransaksiAdapter (
    private val listTransaksi: List<Transaksi>,
    private val onItemClick: (Transaksi) -> Unit// bikin opsional
) : RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>() {

    inner class TransaksiViewHolder(private val binding: ItemTransaksiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaksi: Transaksi) {
            binding.iconTransaksi.setImageResource(transaksi.imgTransaksi)
            binding.namaTransaksi.text = transaksi.namaTransaksi
            binding.tanggalTransaksi.text = transaksi.waktuTransaksi
            binding.root.setOnClickListener {
                onItemClick(transaksi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        val binding = ItemTransaksiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransaksiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        holder.bind(listTransaksi[position])
    }

    override fun getItemCount(): Int = listTransaksi.size
}