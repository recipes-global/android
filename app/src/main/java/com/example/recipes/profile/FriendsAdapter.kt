package com.example.recipes.profile

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.recipes.R
import com.example.recipes.data.model.Friend
import kotlinx.android.synthetic.main.friend_one_card.view.*

class FriendsAdapter (private val context: Context?):
                    androidx.recyclerview.widget.RecyclerView.Adapter<FriendsAdapter.ViewHolder>(){
    private var friendsList: List<Friend>? = null

    fun setFriendsList(friendsList: List<Friend>?){
        this.friendsList = friendsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): FriendsAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(R.layout.friend_one_card, parent, false)
        return FriendsAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(friendsList!![position], context)
    }

    override fun getItemCount(): Int {
        return friendsList!!.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun animateCircularReveal(view: View?){
        val centerX = 0
        val centerY = 0
        val startRadius = 0
        val endRadius = Math.max(view!!.width, view.height)

        val animation = ViewAnimationUtils
            .createCircularReveal(view, centerX, centerY, startRadius.toFloat(), endRadius.toFloat())

        view.visibility = View.VISIBLE
        animation.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewAttachedToWindow(viewHolder: FriendsAdapter.ViewHolder) {
        super.onViewAttachedToWindow(viewHolder)
        animateCircularReveal(viewHolder.itemView)
    }

    class ViewHolder(view: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view){
        fun bind(friend: Friend, context: Context?){
            itemView.friendTextView.text = friend.name
            if(context != null){
                Glide.with(context).load(friend.avatar)
                    .into(itemView.friendImageView)
            }
        }
    }
}