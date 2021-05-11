package ru.frozenpriest.taskautomaton.presentation.commands


import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType
import ru.frozenpriest.taskautomaton.program.commands.ExecuteProgram
import ru.frozenpriest.taskautomaton.program.commands.functions.*
import ru.frozenpriest.taskautomaton.program.commands.logic.IfCondition
import ru.frozenpriest.taskautomaton.program.commands.logic.WhileCondition
import ru.frozenpriest.taskautomaton.program.commands.output.ShowHtml
import ru.frozenpriest.taskautomaton.program.commands.output.ShowToast
import ru.frozenpriest.taskautomaton.program.commands.output.UseTts
import ru.frozenpriest.taskautomaton.program.commands.output.VibrateWithPattern
import ru.frozenpriest.taskautomaton.program.commands.variables.*
import kotlin.reflect.KClass

class CommandBuilder(val commandInfo: CommandInfoShort, val params: Map<ParamWithType, Any>) {

    fun build(): List<Command> {
        TODO()
    }


    data class CommandInfoShort(
        val className: KClass<out Command>,
        val name: String,
        val commandType: CommandType,
        val iconId: Int,
        val params: List<ParamWithType>
    )

    data class ParamWithType(
        val name: String,
        val type: ParamType
    )

    enum class ParamType {
        String,
        Gravity,
        Duration,
        Function,
        Color,
        Language
    }


    companion object {
        //todo move to some non static maybe
        val commandToInfo: List<CommandInfoShort> = listOf(
            CommandInfoShort(
                CheckVar::class,
                "CheckVar",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(ParamWithType("Variable", ParamType.String))
            ),
            CommandInfoShort(
                EqualVar::class,
                "EqualVar",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                ExistVar::class,
                "ExistVar",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(ParamWithType("Variable", ParamType.String))
            ),
            CommandInfoShort(
                GreaterVar::class,
                "GreaterVar",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                LowerVar::class,
                "LowerVar",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                NotFunction::class,
                "NotFunction",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(ParamWithType("Function", ParamType.Function)) //todo can be inner function
            ),
            CommandInfoShort(
                IfCondition::class,
                "If",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(ParamWithType("Function", ParamType.Function))
            ),
            CommandInfoShort(
                WhileCondition::class,
                "While",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(ParamWithType("Function", ParamType.Function))
            ),
            CommandInfoShort(//name, args, backColor, textColor, gravity, duration
                ShowHtml::class,
                "ShowHtml",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Text", ParamType.String),
                    ParamWithType("Args", ParamType.String),
                    ParamWithType("Background", ParamType.Color),
                    ParamWithType("TextColor", ParamType.Color),
                    ParamWithType("Gravity", ParamType.Gravity),
                    ParamWithType("Duration", ParamType.Duration)
                )
            ),
            CommandInfoShort(
                ShowToast::class,
                "ShowToast",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Text", ParamType.String),
                    ParamWithType("Args", ParamType.String),
                    ParamWithType("Duration", ParamType.Duration)
                )
            ),
            CommandInfoShort(
                UseTts::class,
                "UseTts",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Text", ParamType.String),
                    ParamWithType("Args", ParamType.String),
                    ParamWithType("Language", ParamType.Language)
                )
            ),
            CommandInfoShort(
                VibrateWithPattern::class,
                "Vibrate",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Vibration", ParamType.String),
                )
            ),
            CommandInfoShort(
                SumVar::class,
                "SumVar",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("VarResult", ParamType.String),
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                DivVar::class,
                "DivVar",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("VarResult", ParamType.String),
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                IncVar::class,
                "IncVar",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Variable", ParamType.String),
                )
            ),
            CommandInfoShort(
                MulVar::class,
                "MulVar",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("VarResult", ParamType.String),
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                SetVar::class,
                "SetVar",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Variable", ParamType.String),
                )
            ),
            CommandInfoShort(
                SubVar::class,
                "SubVar",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("VarResult", ParamType.String),
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                ExecuteProgram::class,
                "Execute",
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Var1", ParamType.String),//TODO doesnt work yet anyway
                    ParamWithType("Var2", ParamType.String)
                )
            ),
        )
    }
}

