package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import ru.frozenpriest.taskautomaton.program.commands.Function
import ru.frozenpriest.taskautomaton.utils.GravityRestriction
import java.util.*

@Composable
@Preview(showBackground = true)
fun CommandBuilderPreview() {
    val preparedParams = remember {
        mutableMapOf<CommandBuilder.ParamWithType, Any>()
    }
    CommandBuilderComposable(CommandBuilder.commandToInfo[8], preparedParams, {}, {})
}

@Composable
fun CommandBuilderComposable(
    info: CommandBuilder.CommandInfoShort,
    preparedParams: MutableMap<CommandBuilder.ParamWithType, Any>,
    build: () -> Unit,
    cancel: () -> Unit
) {
    // val setFunction
    Column {
        CommandBuilderMain(
            info,
            preparedParams,
            buttons =  {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        if (preparedParams.size == info.params.size)
                            build()
                    }) {
                        Text("Add")
                    }
                    Button(onClick = { cancel() }) {
                        Text("Cancel")
                    }
                }
            }
        )
    }
}

@Composable
fun CommandBuilderMain(
    info: CommandBuilder.CommandInfoShort,
    preparedParams: MutableMap<CommandBuilder.ParamWithType, Any>,
    modifier: Modifier = Modifier,
    onReady: () -> Unit = {},
    buttons: @Composable () -> Unit = {}
) {
    val checkReady = remember {
        { if (preparedParams.size == info.params.size) onReady() }
    }
    LazyColumn(modifier = modifier) {
        items(items = info.params) { param ->
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
                        val (textField, setText) = remember {
                            mutableStateOf(
                                TextFieldValue(
                                    preparedParams.getOrDefault(param, "").toString()
                                )
                            )
                        }

                        TextField(
                            value = textField,
                            onValueChange = {
                                setText(it)
                                preparedParams[param] = it.text
                                checkReady()
                            },
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                                .weight(0.6f, true)
                                .padding(all = 8.dp),
                        )
                    }
                    CommandBuilder.ParamType.Gravity -> {
                        Selector(
                            currentValue = preparedParams.getOrDefault(param, "").toString(),
                            possibleValues = GravityRestriction.values().map { it.toString() },
                            showAsString = { it }, //TODO(change to work with <>)
                            modifier = Modifier.weight(0.6f, true),
                            onItemSelected = { itemValue ->
                                preparedParams[param] = itemValue
                                checkReady()
                            }
                        )
                    }
                    CommandBuilder.ParamType.DurationToast -> {
                        Selector(
                            currentValue = preparedParams.getOrDefault(param, "").toString(),
                            possibleValues = listOf("Long", "Short"),
                            showAsString = { it }, //TODO(change to work with <>)
                            modifier = Modifier.weight(0.6f, true),
                            onItemSelected = { itemValue ->
                                preparedParams[param] = itemValue
                                checkReady()
                            }
                        )
                    }
                    CommandBuilder.ParamType.DurationExpire -> {
                        val (textField, setText) = remember {
                            mutableStateOf(
                                TextFieldValue(
                                    preparedParams.getOrDefault(param, "").toString()
                                )
                            )
                        }

                        TextField(
                            value = textField,
                            onValueChange = {
                                if (it.text.toIntOrNull() != null) {
                                    setText(it)
                                    preparedParams[param] = it.text
                                    checkReady()
                                }
                            },
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                                .weight(0.6f, true)
                                .padding(all = 8.dp),
                        )
                    }
                    CommandBuilder.ParamType.Function -> {
                        FunctionSelector(
                            initialFunction = preparedParams[param] as Function?,
                            onReady = { itemValue ->
                                preparedParams[param] = itemValue
                                checkReady()
                            }
                        )
                    }
                    CommandBuilder.ParamType.Boolean -> {
                        Selector(
                            currentValue = preparedParams.getOrDefault(param, "").toString(),
                            possibleValues = listOf(
                                "True",
                                "False",
                            ),
                            modifier = Modifier.weight(0.6f, true),
                            showAsString = { it }, //TODO(change to work with <>)
                            onItemSelected = { itemValue ->
                                preparedParams[param] = itemValue
                                checkReady()
                            }
                        )
                    }
                    CommandBuilder.ParamType.Color -> {
                        val (textField, setText) = remember {
                            mutableStateOf(
                                TextFieldValue(
                                    preparedParams.getOrDefault(param, "").toString(),
                                )
                            )
                        }

                        TextField(
                            value = textField,
                            onValueChange = {
                                if (it.text.toIntOrNull() != null) {
                                    setText(it)
                                    preparedParams[param] = it.text
                                    checkReady()
                                }
                            },
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                                .weight(0.6f, true)
                                .padding(all = 8.dp),
                        )//todo fill more or add colorpicker
                    }
                    CommandBuilder.ParamType.Language -> {
                        Selector(
                            currentValue = preparedParams.getOrDefault(param, "").toString(),
                            possibleValues = Locale.getAvailableLocales().map { it.language },
                            showAsString = { it }, //TODO(change to work with <>)
                            modifier = Modifier.weight(0.6f, true),
                            onItemSelected = { itemValue ->
                                preparedParams[param] = itemValue
                                checkReady()
                            }
                        )
                    }
                }
            }
        }
        item {
            buttons()
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun FunctionSelector(
    initialFunction: Function?,
    onReady: (Function) -> Unit
) {
    val selectedFunction = remember {
        mutableStateOf(CommandBuilder.functionsOnly.find { it.className.toString() == initialFunction?.info?.commandClass?.toString() })
    }
    Column() {
        val possibleValues = remember {
            CommandBuilder.functionsOnly.map { it.className.toString() }
        }
        Selector(
            currentValue = initialFunction?.info?.commandClass?.toString() ?: "",
            possibleValues = possibleValues,
            showAsString = { it }, //TODO(change to work with <>)
            onItemSelected = { newString ->
                selectedFunction.value =
                    CommandBuilder.functionsOnly.find { it.className.toString() == newString }
            },
        )

        if (selectedFunction.value != null) {
            val newPreparedParams = remember {
                CommandBuilder.populateParams(initialFunction, selectedFunction.value!!)
            }


            CommandBuilderMain(
                info = selectedFunction.value!!,
                preparedParams = newPreparedParams,
                onReady = {
                    onReady(
                        CommandBuilder(
                            selectedFunction.value!!,
                            newPreparedParams
                        ).build()[0] as Function
                    )
                }
            )
        }
    }
}