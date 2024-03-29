package ru.frozenpriest.taskautomaton.program.commands

import android.content.Context
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.functions.*
import ru.frozenpriest.taskautomaton.program.commands.logic.*
import ru.frozenpriest.taskautomaton.program.commands.output.*
import ru.frozenpriest.taskautomaton.program.commands.system.DateToVarText
import ru.frozenpriest.taskautomaton.program.commands.system.TimeToVar
import ru.frozenpriest.taskautomaton.program.commands.system.TimeToVarText
import ru.frozenpriest.taskautomaton.program.commands.variables.*

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
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

    JsonSubTypes.Type(value = TimeToVar::class, name = "TimeToVar"),
    JsonSubTypes.Type(value = TimeToVarText::class, name = "TimeToVarText"),
    JsonSubTypes.Type(value = DateToVarText::class, name = "DateToVarText"),

    JsonSubTypes.Type(value = ShowAllVariables::class, name = "ShowAllVariables"),
    JsonSubTypes.Type(value = PlayMusic::class, name = "PlayMusic"),
    JsonSubTypes.Type(value = StopMusic::class, name = "StopMusic"),

    )
abstract class Command(
    name: String,
    description: String,
    iconId: Int,
    commandType: CommandType,
    commandClass: CommandBuilder.CommandClass
) : ICommand {

    @JsonIgnore
    val info: CommandInfo = CommandInfo(name, description, iconId, commandType, commandClass)
}


interface ICommand {
    fun perform(program: Program, context: Context)
}

data class CommandInfo(
    val name: String,
    val description: String,
    val iconId: Int,
    val commandType: CommandType,
    val commandClass: CommandBuilder.CommandClass
) {
    var level = 0
}

