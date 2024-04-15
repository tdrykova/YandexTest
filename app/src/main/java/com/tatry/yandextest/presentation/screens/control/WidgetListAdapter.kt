package com.tatry.yandextest.presentation.screens.control

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tatry.yandextest.databinding.WidgetItemBinding
import com.tatry.yandextest.domain.model.widget.WidgetModel

interface WidgetActionListener {
    fun onChooseWidget(widget: WidgetModel)

    fun getWidgetId(id: String)
}

class WidgetListAdapter(
    private val actionListener: WidgetActionListener,
):
    ListAdapter<WidgetModel, WidgetListAdapter.WidgetListViewHolder>(callback),
    View.OnClickListener
{

    class WidgetListViewHolder(val binding: WidgetItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetListViewHolder {
        val binding = WidgetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
//        binding.imageButton.setOnClickListener(this)
        return WidgetListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WidgetListViewHolder, position: Int) {
        val widgetItem = getItem(position)
//        holder.binding.imageView.load(placeItem.imageUrl)
        holder.binding.tvWidgetType.text = widgetItem.widgetType
        holder.binding.tvWidgetId.text = widgetItem.id.toString()
        holder.itemView.tag = widgetItem
//        holder.binding.imageButton.tag = placeItem
//        holder.itemView.setOnClickListener {
//            Log.d("MY_TAG", "onClick: item")
//        }
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("key" to widgetItem.id)
            actionListener.getWidgetId(widgetItem.id.toString())
//                getPlace.getPlaceId(placeItem.id)
//                val intent = Intent(context, PlaceDetailFragment::class.java)
//
//                intent.putExtra("key", bundle)
//                context.startActivity(intent)
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

//    override fun onClick(v: View) {
//        val widget = v.tag as WidgetModel
//        when (v.id) {
//            R.id.item -> {
//                actionListener.onChoosePlace(place)
//            }
//        }
//    }
}

val callback = object: DiffUtil.ItemCallback<WidgetModel>() {
    override fun areItemsTheSame(oldItem: WidgetModel, newItem: WidgetModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WidgetModel, newItem: WidgetModel): Boolean {
        return oldItem == newItem
    }
}