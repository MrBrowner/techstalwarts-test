package com.example.test.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.test.R
import com.example.test.Utils
import com.example.test.model.User
import com.squareup.picasso.Picasso

class HomeAdapter(private val context: Context) : BaseAdapter(), Filterable
{
    var items = mutableListOf<User>()
    var filteredItems = mutableListOf<User>()

    fun setUsers(users: List<User>)
    {
        items.clear()
        filteredItems.clear()
        items.addAll(users)
        filteredItems.addAll(users)
        notifyDataSetChanged()
    }

    override fun getCount(): Int
    {
        return filteredItems.size
    }

    override fun getItem(position: Int): User
    {
        return filteredItems[position]
    }

    override fun getItemId(position: Int): Long
    {
        return filteredItems[position].id!!.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        val view: View
        val holder: ViewHolder
        if (convertView == null)
        {
            view = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.row_user, null)
            holder = ViewHolder(view)
            view.tag = holder
        } else
        {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val item = filteredItems[position]
        holder.name.text = item.getFullName()
        holder.email.text = item.email
        Picasso.get().load(item.avatar).into(holder.avatar)

        return view
    }

    inner class ViewHolder(row: View)
    {
        val name: TextView = row.findViewById(R.id.tvName)
        val email: TextView = row.findViewById(R.id.tvEmail)
        val avatar: ImageView = row.findViewById(R.id.ivAvatar)
    }

    fun doSearch(search: String)
    {
        filter.filter(search)
    }

    override fun getFilter(): Filter
    {
        return object : Filter()
        {
            override fun performFiltering(constraint: CharSequence?): FilterResults
            {
                val filterResults = FilterResults()
                if (TextUtils.isEmpty(constraint))
                {
                    filterResults.count = items.size
                    filterResults.values = items
                } else
                {
                    val searchResults = mutableListOf<User>()
                    val searchStr = constraint.toString()
                    items.forEach { u ->
                        if (searchStr.contains(u.email, true) || searchStr.contains(u.getFullName(), true))
                        {
                            searchResults.add(u)
                        }
                    }
                    Utils.loge("results", searchResults.joinToString { it.getFullName() })
                    filterResults.count = searchResults.size
                    filterResults.values = searchResults
                }
                Utils.loge("count", filterResults.count.toString())
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults)
            {
                filteredItems = results.values as MutableList<User>
                notifyDataSetChanged()
            }
        }
    }
}