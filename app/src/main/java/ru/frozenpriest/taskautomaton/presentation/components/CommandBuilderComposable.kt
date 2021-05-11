package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.utils.Gravity
import java.util.*

@Composable
@Preview(showBackground = true)
fun CommandBuilderPreview() {
    CommandBuilderComposable(CommandBuilder.commandToInfo[8], {}, {})
}

@Composable
fun CommandBuilderComposable(
    info: CommandBuilder.CommandInfoShort,
    build: (params: Map<CommandBuilder.ParamWithType, Any>) -> Unit,
    cancel: () -> Unit
) {
    val preparedParams = remember {
        mutableMapOf<CommandBuilder.ParamWithType, Any>()
    }
    Column {
        info.params.forEach { param ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = param.name,
                    modifier = Modifier.weight(0.4f, false)
                )
                when (param.type) {
                    CommandBuilder.ParamType.String -> {
                        val (textField, setText) = remember { mutableStateOf(TextFieldValue()) }

                        TextField(
                            value = textField,
                            onValueChange = {
                                setText(it)
                                preparedParams[param] = it.text
                            },
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                                .weight(0.6f, true)
                                .padding(all = 8.dp),
                        )
                    }
                    CommandBuilder.ParamType.Gravity -> {
                        Selector(
                            possibleValues = Gravity.values().map { it.toString() },
                            modifier = Modifier.weight(0.6f, true),
                            onItemSelected = { itemValue -> preparedParams[param] = itemValue }
                        )
                    }
                    CommandBuilder.ParamType.Duration -> {
                        Selector(
                            possibleValues = listOf("Long", "Short"),
                            modifier = Modifier.weight(0.6f, true),
                            onItemSelected = { itemValue -> preparedParams[param] = itemValue }
                        )
                    }
                    CommandBuilder.ParamType.Function -> {
                        //TODO()
                    }
                    CommandBuilder.ParamType.Color -> {
                        Selector(
                            possibleValues = listOf(
                                "Red",
                                "Green",
                                "Blue"
                            ),
                            modifier = Modifier.weight(0.6f, true),
                            onItemSelected = { itemValue -> preparedParams[param] = itemValue }


                        )//todo fill more or add colorpicker
                    }
                    CommandBuilder.ParamType.Language -> {
                        Selector(
                            possibleValues = Locale.getAvailableLocales().map { it.language },
                            modifier = Modifier.weight(0.6f, true),
                            onItemSelected = { itemValue -> preparedParams[param] = itemValue }
                        )
                    }
                }

            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                if (preparedParams.size == info.params.size)
                    build(preparedParams)
            }) {
                Text("Add")
            }
            Button(onClick = { cancel() }) {
                Text("Cancel")
            }
        }
    }
}
