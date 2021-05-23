package ru.frozenpriest.taskautomaton.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.presentation.components.CommandBuilderComposable
import ru.frozenpriest.taskautomaton.program.commands.Command

class CommandsEditDialogFragment(
    private val listener: CommandEditListener,
    private val command: Command
) : DialogFragment() {
    interface CommandEditListener {
        fun onCommandEdit(command: Command)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val commandInfo = remember {
                    CommandBuilder.commandToInfo.find { it.className == command.info.commandClass }
                }
                if (commandInfo != null) {
                    val preparedParams = remember {
                        CommandBuilder.populateParams(command, commandInfo)
                    }
                    CommandBuilderComposable(
                        info = commandInfo,
                        preparedParams = preparedParams,
                        build = {
                            val newCommand = CommandBuilder(commandInfo, preparedParams).build()[0]
                            listener.onCommandEdit(newCommand)
                            dismiss()
                        },
                        cancel = { dismiss() },
                    )
                } else {
                    dismiss()
                }
            }
        }
    }
}