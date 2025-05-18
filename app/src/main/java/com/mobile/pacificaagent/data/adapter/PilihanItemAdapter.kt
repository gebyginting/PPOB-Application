package com.mobile.pacificaagent.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.pacificaagent.data.model.PilihanItem
import com.mobile.pacificaagent.databinding.ItemWifiEwalletBinding

class PilihanItemAdapter(
    private val listPilihanProduk: List<PilihanItem>,
    private val onItemClick: (PilihanItem) -> Unit
) : RecyclerView.Adapter<PilihanItemAdapter.PilihanProdukViewHolder>() {

    inner class PilihanProdukViewHolder(private val binding: ItemWifiEwalletBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(produk: PilihanItem) {
            binding.ivPilihanItem.setImageResource(produk.imgUrl)
            binding.tvPilihanItem.text = produk.nama
            binding.root.setOnClickListener {
                onItemClick(produk)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PilihanProdukViewHolder {
        val binding = ItemWifiEwalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PilihanProdukViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PilihanProdukViewHolder, position: Int) {
        holder.bind(listPilihanProduk[position])
    }

    override fun getItemCount(): Int = listPilihanProduk.size
}