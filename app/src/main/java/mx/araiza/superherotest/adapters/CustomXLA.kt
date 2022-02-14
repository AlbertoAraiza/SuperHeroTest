package mx.araiza.superherotest.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import mx.araiza.superherotest.R
import mx.araiza.superherotest.model.SuperheroModel

class CustomXLA(var superhero :SuperheroModel) : BaseExpandableListAdapter() {
    private var xListTitles :List<String>
    private var xListDetails :HashMap<String, List<String>> = hashMapOf(
        "Descripción" to listOf(superhero.description),
        "Comics" to superhero.comics,
        "Historias" to superhero.stories,
        "Series" to superhero.series,
        "Eventos" to superhero.events)

    init {
        this.xListTitles = this.xListDetails.keys.sorted()
    }

    override fun getGroupCount(): Int = xListTitles.size

    override fun getChildrenCount(listPosition: Int): Int {
        val title = xListTitles.elementAt(listPosition)
        return xListDetails[title]!!.size
    }

    override fun getGroup(listPosition: Int): Any = xListTitles.elementAt(listPosition)

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any{
        val title = xListTitles.elementAt(listPosition)
        return xListDetails[title]!![expandedListPosition]
    }

    override fun getGroupId(listPosition: Int): Long = listPosition.toLong()

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long = expandedListPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val listTitle = getGroup(listPosition) as String
        val currentView :View = convertView ?: LayoutInflater.from(parent!!.context).inflate(R.layout.list_group, parent, false)
        val titleTextView = currentView.findViewById<TextView>(R.id.tvTitle)
        titleTextView.typeface = Typeface.DEFAULT_BOLD
        titleTextView.text = listTitle
        return currentView
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        val currentView :View = convertView ?: LayoutInflater.from(parent!!.context).inflate(R.layout.list_item, parent, false)
        val itemTextView = currentView.findViewById<TextView>(R.id.tvItem)
        itemTextView.text = expandedListText
        return currentView
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean = true
}