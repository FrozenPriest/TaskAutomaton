package ru.frozenpriest.taskautomaton.presentation.ui.program

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
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.utils.CustomItemTouchHelper
import ru.frozenpriest.taskautomaton.utils.ItemTouchHelperAdapter

@AndroidEntryPoint
class ProgramFragment : Fragment(R.layout.fragment_program) {

    private val viewModel: ProgramViewModel by viewModels()

    private lateinit var rvProgram: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getLong("programId")?.let { programId ->
            viewModel.loadProgram(programId)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupToolbar()
        viewModel.program.observe(viewLifecycleOwner, {
            (rvProgram.adapter as CommandItemAdapter).bind(it)
            it.listener = rvProgram.adapter as CommandItemAdapter

            val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
            toolbar.title = it.name
        })

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
                adapter = CommandItemAdapter(
                    context = context,
                    program = Program(1, "emptynaem", emptyList())
                )

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
            if (viewModel.isSyntaxValid())
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