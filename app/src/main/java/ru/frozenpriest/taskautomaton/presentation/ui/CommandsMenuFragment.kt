package ru.frozenpriest.taskautomaton.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.DialogFragment
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.presentation.components.CommandBuilderComposable
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType

class CommandsMenuFragment(private val listener: CommandSelectionListener) : DialogFragment() {
    interface CommandSelectionListener {
        fun onCommandSelected(commands: List<Command>)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                GridMenu(
                    dismiss = { dismiss() },
                    add = { info, params ->
                        val command = CommandBuilder(info, params).build()
                        listener.onCommandSelected(command)
                        dismiss()
                    }
                )
            }
        }
    }
}

/**
 * todo
 * remove commandType from command
 * class CommandContainer with commandType and fun(): Command
 * commandType enum moved here
 * also contains categorisedCommands hashmap
 */

val types =
    CommandType.values().filter { it != CommandType.Uncategorized && it != CommandType.Functions }
val categorizedCommands = CommandBuilder.commandToInfo.groupBy { it.commandType }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridMenu(
    dismiss: () -> Unit,
    add: (info: CommandBuilder.CommandInfoShort, params: Map<CommandBuilder.ParamWithType, Any>) -> Unit
) {

    val selectedCategory = remember { mutableStateOf<CommandType?>(null) }
    val commandInfoToAdd = remember { mutableStateOf<CommandBuilder.CommandInfoShort?>(null) }
    Surface() {

        if (commandInfoToAdd.value == null) {
            LazyVerticalGrid(cells = GridCells.Fixed(3)) {
                if (selectedCategory.value == null) {
                    items(types) {
                        IconButton(onClick = {
                            selectedCategory.value = it
                        }) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(imageVector = it.icon, contentDescription = "icon")
                                Text(text = it.name)
                            }
                        }
                    }
                } else {
                    items(categorizedCommands[selectedCategory.value] ?: emptyList()) {
                        IconButton(onClick = { commandInfoToAdd.value = it }) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = it.iconId),
                                    contentDescription = "icon"
                                )
                                Text(text = it.className.name)
                            }
                        }
                    }
                }
            }
        } else {
            commandInfoToAdd.value?.let { commandInfo ->
                val preparedParams = remember {
                    mutableMapOf<CommandBuilder.ParamWithType, Any>()
                }
                CommandBuilderComposable(
                    info = commandInfo,
                    preparedParams = preparedParams,
                    build = {
                        add(commandInfo, preparedParams)
                    },
                    cancel = { dismiss() },
                )
            }
        }
    }
}

