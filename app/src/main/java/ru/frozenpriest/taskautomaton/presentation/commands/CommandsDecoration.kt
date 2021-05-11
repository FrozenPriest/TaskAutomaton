package ru.frozenpriest.taskautomaton.presentation.commands

import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType
import ru.frozenpriest.taskautomaton.program.commands.functions.CheckVar

data class CommandInfo(
    val name: String,
    val description: String,
    val iconId: Int,
    val commandType: CommandType,
) {
    var level = 0
}

/*
    JsonSubTypes.Type(value = CheckVar::class, name = "CheckVar"),
    JsonSubTypes.Type(value = EqualVar::class, name = "EqualVar"),
    JsonSubTypes.Type(value = ExistVar::class, name = "ExistVar"),
    JsonSubTypes.Type(value = GreaterVar::class, name = "GreaterVar"),
    JsonSubTypes.Type(value = LowerVar::class, name = "LowerVar"),
    JsonSubTypes.Type(value = NotFunction::class, name = "NotFunction"),

    JsonSubTypes.Type(value = ElseCondition::class, name = "ElseCondition"),
    JsonSubTypes.Type(value = EndElse::class, name = "EndElse"),
    JsonSubTypes.Type(value = EndIf::class, name = "EndIf"),
    JsonSubTypes.Type(value = EndWhile::class, name = "EndWhile"),
    JsonSubTypes.Type(value = IfCondition::class, name = "IfCondition"),
    JsonSubTypes.Type(value = WhileCondition::class, name = "WhileCondition"),

    JsonSubTypes.Type(value = ShowHtml::class, name = "ShowHtml"),
    JsonSubTypes.Type(value = ShowToast::class, name = "ShowToast"),
    JsonSubTypes.Type(value = UseTts::class, name = "UseTts"),
    JsonSubTypes.Type(value = VibrateWithPattern::class, name = "VibrateWithPattern"),

    JsonSubTypes.Type(value = SumVar::class, name = "SumVar"),
    JsonSubTypes.Type(value = DivVar::class, name = "DivVar"),
    JsonSubTypes.Type(value = IncVar::class, name = "IncVar"),
    JsonSubTypes.Type(value = MulVar::class, name = "MulVar"),
    JsonSubTypes.Type(value = SetVar::class, name = "SetVar"),
    JsonSubTypes.Type(value = SubVar::class, name = "SubVar"),

    JsonSubTypes.Type(value = ExecuteProgram::class, name = "ExecuteProgram"),
 */

fun getCommands(category: CommandType): List<Command> {
    val list = mutableListOf<Command>()
    val i = CheckVar::class
    when (category) {
        CommandType.Output -> TODO()
        CommandType.Logic -> TODO()
        CommandType.Variables -> TODO()
        CommandType.Test1 -> TODO()
        CommandType.Test2 -> TODO()
        CommandType.Test3 -> TODO()
        CommandType.Test4 -> TODO()
        CommandType.Test5 -> TODO()
        CommandType.Test6 -> TODO()
        CommandType.Uncategorized -> TODO()
    }

    return list
}

//
//    when (command::class.simpleName) {
//        CheckVar::class.simpleName -> {
//        }
//        EqualVar::class.simpleName -> {
//        }
//        ExistVar::class.simpleName -> {
//        }
//        GreaterVar::class.simpleName -> {
//        }
//        LowerVar::class.simpleName -> {
//        }
//        NotFunction::class.simpleName -> {
//        }
//        ElseCondition::class.simpleName -> {
//        }
//        EndElse::class.simpleName -> {
//        }
//        EndIf::class.simpleName -> {
//        }
//        EndWhile::class.simpleName -> {
//        }
//        IfCondition::class.simpleName -> {
//        }
//        WhileCondition::class.simpleName -> {
//        }
//        ShowHtml::class.simpleName -> {
//        }
//        ShowToast::class.simpleName -> {
//        }
//        UseTts::class.simpleName -> {
//        }
//        VibrateWithPattern::class.simpleName -> {
//        }
//        SumVar::class.simpleName -> {
//        }
//        DivVar::class.simpleName -> {
//        }
//        IncVar::class.simpleName -> {
//        }
//        MulVar::class.simpleName -> {
//        }
//        SetVar::class.simpleName -> {
//        }
//        SubVar1::class.simpleName -> {
//        }
//        ExecuteProgram::class.simpleName -> {
//        }
//    }
//}

//@Preview
//@Composable
//fun CommandItemPreview() {
//    val res = SumVar::class
//    CommandItem(res) { cb: CommandBuilder -> SumVar("1", "1", "1") }
//}
//
//@Composable
//fun CommandItem(
//    commandInfoShort: CommandBuilder.CommandInfoShort,
//    addCommand: (CommandBuilder) -> Command
//) {
//
//    IconButton(onClick = {
//        //addCommand(commandBuilder)
//    }) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(painter = painterResource(commandInfoShort.iconId), contentDescription = "icon")
//            Text(text = command.name, style = MaterialTheme.typography.h6)
//        }
//    }
//}
