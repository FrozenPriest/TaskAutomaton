package ru.frozenpriest.taskautomaton.presentation.ui.program

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.OnCommandRunListener
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.utils.ItemTouchHelperAdapter

class CommandItemAdapter(
    private val context: Context,
    var program: Program,
) :
    RecyclerView.Adapter<CommandItemAdapter.ViewHolder>(),
    ItemTouchHelperAdapter,
    OnCommandRunListener {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.view_command,
            parent,
            false
        )
        return ViewHolder(view).apply {
            ivIcon = view.findViewById(R.id.imageViewIcon)
            commandName = view.findViewById(R.id.textViewCommand)
            commandDesc = view.findViewById(R.id.textViewDescription)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = program.commands[position]

        val layoutParams = holder.ivIcon.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginStart = item.level * 32 + 8
        holder.ivIcon.layoutParams = layoutParams

        holder.ivIcon.setImageResource(item.iconId)

        holder.commandName.text = item.name
        holder.commandDesc.text = item.description

        if (program.commandPointer == position) holder.itemView.setBackgroundColor(Color.GREEN)
        else holder.itemView.setBackgroundColor(Color.WHITE)

    }

    override fun getItemCount(): Int {
        return program.commands.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun bind(program: Program) {
        this.program = program
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var ivIcon: ImageView
        lateinit var commandName: TextView
        lateinit var commandDesc: TextView
    }

    override fun onItemMove(fromPos: Int, toPos: Int) {
        val mutable = program.commands.toMutableList()
        val movingItem = program.commands[fromPos]

        notifyItemChanged(toPos)

        mutable.remove(movingItem)
        mutable.add(toPos, movingItem)

        program.commands = mutable.toList()

        program.setLevels()


        notifyItemMoved(fromPos, toPos)
        notifyItemChanged(toPos)

    }

    override fun onCommandRun(commandPointerFrom: Int, commandPointerTo: Int) {
        notifyItemChanged(commandPointerTo)
        notifyItemChanged(commandPointerFrom)
    }
}