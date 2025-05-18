package com.mobile.pacificaagent.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.pacificaagent.data.model.Promo
import com.mobile.pacificaagent.databinding.ItemPromoBinding

class PromoAdapter(
    private val promoList: List<Promo> // Gunakan resource drawable ID atau URL
) : RecyclerView.Adapter<PromoAdapter.PromoViewHolder>() {

    inner class PromoViewHolder(val binding: ItemPromoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
        val binding = ItemPromoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PromoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        val promo = promoList[position]
        holder.binding.imgPromo.setImageResource(promo.imgPromo)
        // Kalau pakai Glide:
        // Glide.with(holder.itemView).load(promo).into(holder.binding.imgPromo)
    }

    override fun getItemCount(): Int = promoList.size
}
