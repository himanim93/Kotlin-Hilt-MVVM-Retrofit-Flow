package com.hm.users.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hm.users.R
import com.hm.users.data.model.UserDetails
import com.hm.users.databinding.ItemUserDetailsBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class UserAdapter @Inject constructor(
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var arrayList = mutableListOf<UserDetails.User>()

    fun updateUser(arrayList: MutableList<UserDetails.User>) {
        this.arrayList = arrayList
        notifyItemRangeInserted(0, arrayList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class UserViewHolder(
        private val itemBinding: ItemUserDetailsBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: UserDetails.User) {

            itemBinding.txtUserName.text = data.name
            itemBinding.txtUserGitUrl.text = data.html_url

            Picasso.get()
                .load(data.photoUrlSmall)
                .placeholder(R.drawable.ic_user_placeholder_24)
                .error(R.drawable.ic_user_placeholder_24)
                .into(itemBinding.imgUser)
        }
    }
}