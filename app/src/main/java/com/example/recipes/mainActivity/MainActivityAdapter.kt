package com.example.recipes.mainActivity

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.recipes.R
import com.example.recipes.data.model.Card
import kotlinx.android.synthetic.main.card_view_recipe.view.*

class MainActivityAdapter(private val cardsList: List<Card>?,
                          private val context: Context?):
                        RecyclerView.Adapter<MainActivityAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MainActivityAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(R.layout.card_view_recipe, parent, false)
        return MainActivityAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(cardsList!![position], context)
    }

    override fun getItemCount(): Int {
        return cardsList!!.size
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
    override fun onViewAttachedToWindow(viewHolder: ViewHolder) {
        super.onViewAttachedToWindow(viewHolder)
        animateCircularReveal(viewHolder.itemView)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(card: Card, context: Context?){
            setNameAndPhoto(card, context)
            setSave(card, context)
            setCounts(card)
            setLike(card, context)
        }

        private fun setNameAndPhoto(card: Card, context: Context?){
            itemView.nameTextView.text = card.name
            if(context != null){
                Glide.with(context).load(card.recipePhoto)
                    .into(itemView.recipeImageView)
            }
        }

        private fun setSave(card: Card, context: Context?){
            if(card.isSaved){
                itemView.saveImageView.setImageDrawable(context?.resources?.getDrawable(R.drawable.baseline_save_black_18dp))
            }else{
                itemView.saveImageView.setImageDrawable(context?.resources?.getDrawable(R.drawable.outline_save_black_18dp))
            }
        }

        private fun setCounts(card: Card){
            itemView.commentCountTextView.text = card.commentCount.toString()
            itemView.likeCountTextView.text = card.likeCount.toString()
        }

        private fun setLike(card: Card, context: Context?){
            if(card.isLiked){
                itemView.likeImageView.setImageDrawable(context?.resources?.getDrawable(R.drawable.ic_thumb_up_black_18dp))
            }else{
                itemView.likeImageView.setImageDrawable(context?.resources?.getDrawable(R.drawable.outline_thumb_up_alt_black_18dp))
            }
        }
    }

}