package com.bambang.githubsearch.view.search

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bambang.githubsearch.data.entity.User
import com.bambang.githubsearch.databinding.ListItemUserBinding
import com.bumptech.glide.Glide

/**
 * Recycler adapter for User List in [SearchFragment]
 */
class UserListAdapter(private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private val items = ArrayList<User>()
    lateinit var binding: ListItemUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListItemUserBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.apply {
            val user = items[position]
            val view = holder.itemView
            view.setOnClickListener { onItemClick(user.serverId) }
            textLogin.text = user.login
            Glide.with(view.context).load(user.avatarUrl).into(imageAvatar)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(users: List<User>) {
        items.clear()
        items.addAll(users)
        notifyDataSetChanged()
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}