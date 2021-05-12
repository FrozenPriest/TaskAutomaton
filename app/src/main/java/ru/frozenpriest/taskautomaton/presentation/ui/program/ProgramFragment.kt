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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.ui.CommandsMenuFragment
import ru.frozenpriest.taskautomaton.program.commands.Command
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

        setupToolbar()
        setupRecyclerView()
        val toolbar: MaterialToolbar = view.findViewById(R.id.toolbar)
        val floatingButton: FloatingActionButton = view.findViewById(R.id.floatingActionButton)

        viewModel.program.observe(viewLifecycleOwner, {
            toolbar.title = it.name
            if (rvProgram.adapter == null) {
                rvProgram.adapter = context?.let { context ->
                    CommandItemAdapter(
                        context = context,
                        viewModel = viewModel
                    )
                }
                val callback: ItemTouchHelper.Callback =
                    CustomItemTouchHelper(rvProgram.adapter as ItemTouchHelperAdapter)
                val touchHelper = ItemTouchHelper(callback)
                touchHelper.attachToRecyclerView(rvProgram)
            }
            (rvProgram.adapter as CommandItemAdapter).bind(it)

            floatingButton.setOnClickListener { showAddCommandDialog() }
        })

    }

    private fun showAddCommandDialog() {
        context?.let {
            val listener = object :
                CommandsMenuFragment.CommandSelectionListener {
                override fun onCommandSelected(commands: List<Command>) {
                    viewModel.addCommands(commands)
                }
            }
            CommandsMenuFragment(listener).show(parentFragmentManager, "menuTag")
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

                val dividerItemDecoration =
                    DividerItemDecoration(context, RecyclerView.VERTICAL)
                AppCompatResources.getDrawable(context, R.drawable.divider_drawable)?.let {
                    dividerItemDecoration.setDrawable(it)
                }

                addItemDecoration(dividerItemDecoration)


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