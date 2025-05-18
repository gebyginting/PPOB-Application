package com.mobile.pacificaagent.data.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.data.model.Item
import com.mobile.pacificaagent.databinding.ItemPulsaPaketDataBinding

class ItemAdapter(
    private val listProduk: List<Item>,
    private val showHarga: Boolean = true,
    private val enableSelection: Boolean = false, // default: tidak aktif
    private val onItemClick: ((Item) -> Unit)? = null // bikin opsional
) : RecyclerView.Adapter<ItemAdapter.TransaksiViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class TransaksiViewHolder(private val binding: ItemPulsaPaketDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(produk: Item, isSelected: Boolean) {
            binding.namaProduk.text = produk.nama
            if (showHarga) {
                binding.hargaProduk.text = produk.harga
                binding.hargaProduk.visibility = View.VISIBLE
            } else {
                binding.hargaProduk.visibility = View.GONE
                binding.namaProduk.layoutParams = (binding.namaProduk.layoutParams as LinearLayout.LayoutParams).apply {
                    gravity = Gravity.CENTER
                }
            }


            // Handle background jika fitur seleksi diaktifkan
            if (enableSelection) {
                if (isSelected) {
                    binding.root.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.grey)
                    )
                } else {
                    binding.root.setBackgroundResource(R.drawable.item_card)
                }
            } else {
                // Jika tidak enableSelection, gunakan background default ripple biasa
                binding.root.setBackgroundResource(R.drawable.item_card)
            }

            binding.root.setOnClickListener {
                if (enableSelection) {
                    val previousPosition = selectedPosition
                    selectedPosition = adapterPosition

                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                }
                onItemClick?.invoke(produk)
            }
//            if (onItemClick != null) {
//                binding.root.setOnClickListener {
//                    onItemClick.invoke(produk)
//                }
//            } else {
//                binding.root.setOnClickListener(null) // no click
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        val binding = ItemPulsaPaketDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransaksiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
//        holder.bind(listProduk[position])
        val item = listProduk[position]
        val isSelected = position == selectedPosition
        holder.bind(item, isSelected)
    }

    override fun getItemCount(): Int = listProduk.size
}