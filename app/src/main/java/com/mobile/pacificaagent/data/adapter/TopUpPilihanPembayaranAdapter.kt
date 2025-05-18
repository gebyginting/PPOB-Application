package com.mobile.pacificaagent.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.pacificaagent.data.model.PilihanItem
import com.mobile.pacificaagent.databinding.ItemTopupPilihanPembayaranBinding

class TopUpPilihanPembayaranAdapter(
    private val listPilihanPembayaran: List<PilihanItem>,
    private val onItemClick: (PilihanItem) -> Unit
) : RecyclerView.Adapter<TopUpPilihanPembayaranAdapter.TopUpPilihanPembayaranViewHolder>() {

    inner class TopUpPilihanPembayaranViewHolder(private val binding: ItemTopupPilihanPembayaranBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pilihan: PilihanItem) {
            binding.imgLogoBank.setImageResource(pilihan.imgUrl)
            binding.tvNamaBank.text = pilihan.nama

            binding.root.setOnClickListener {
                onItemClick(pilihan)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopUpPilihanPembayaranViewHolder {
        val binding = ItemTopupPilihanPembayaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopUpPilihanPembayaranViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopUpPilihanPembayaranAdapter.TopUpPilihanPembayaranViewHolder, position: Int) {
        holder.bind(listPilihanPembayaran[position])
    }

    override fun getItemCount(): Int = listPilihanPembayaran.size
}

