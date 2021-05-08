package ru.frozenpriest.taskautomaton.presentation.ui.program

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.service.MyService
import ru.frozenpriest.taskautomaton.utils.CustomItemTouchHelper
import ru.frozenpriest.taskautomaton.utils.ItemTouchHelperAdapter


class ProgramFragment : Fragment(R.layout.fragment_program) {

    private val viewModel: ProgramViewModel by viewModels()

    private lateinit var rvProgram: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getLong("programId")?.let { programId ->
            println("Got program id")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupToolbar()

        (rvProgram.adapter as CommandItemAdapter).bind(viewModel.program.value)
//       todo remake as viewmodel command
        viewModel.program.value.listener = rvProgram.adapter as CommandItemAdapter

        context?.let {
            val intent = Intent(it, MyService::class.java)
            intent.putExtra("ProgramId", "id") //todo when have database
            it.stopService(intent) //todo for debug purposes, remove later
            it.startService(intent)
        }
    }

    private fun setupToolbar() {
        activity?.apply {

            val buttonStart: ActionMenuItemView = findViewById(R.id.imageButtonPlay)
            buttonStart.setOnClickListener { onButtonStart() }

            val buttonStep: ActionMenuItemView = findViewById(R.id.imageButtonStep)
            buttonStep.setOnClickListener { onButtonStep() }

            val buttonReset: ActionMenuItemView = findViewById(R.id.imageButtonStop)
            buttonReset.setOnClickListener { onButtonReset() }
        }
    }

    private fun setupRecyclerView() {
        activity?.apply {
            rvProgram = findViewById(R.id.rvProgram)
            rvProgram.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = CommandItemAdapter(context = context, program = Program(1, "emptynaem", emptyList()))

                val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
                AppCompatResources.getDrawable(context, R.drawable.divider_drawable)?.let {
                    dividerItemDecoration.setDrawable(it)
                }

                addItemDecoration(dividerItemDecoration)

                val callback: ItemTouchHelper.Callback =
                    CustomItemTouchHelper(adapter as ItemTouchHelperAdapter)
                val touchHelper = ItemTouchHelper(callback)
                touchHelper.attachToRecyclerView(rvProgram)
            }

        }
    }

    private fun onButtonStart() {
        context?.let {
            if (viewModel.program.value.isSyntaxValid)
                viewModel.executeCommands(it)
            else
                showProgramErrorToast()

        }
    }

    private fun onButtonReset() {
        if (viewModel.isSyntaxValid())
            viewModel.stop()
        else
            showProgramErrorToast()
    }

    private fun onButtonStep() {
        context?.let {
            if (viewModel.isSyntaxValid())
                viewModel.nextCommand(it)
            else
                showProgramErrorToast()

        }
    }

    private fun showProgramErrorToast() {
        context?.let {
            Toast.makeText(it, "Syntax error", Toast.LENGTH_LONG).show()
        }
    }

}