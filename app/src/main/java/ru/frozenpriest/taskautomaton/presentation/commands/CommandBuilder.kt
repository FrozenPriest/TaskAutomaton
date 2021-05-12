package ru.frozenpriest.taskautomaton.presentation.commands


import android.widget.Toast
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType
import ru.frozenpriest.taskautomaton.program.commands.Function
import ru.frozenpriest.taskautomaton.program.commands.functions.*
import ru.frozenpriest.taskautomaton.program.commands.logic.*
import ru.frozenpriest.taskautomaton.program.commands.output.ShowHtml
import ru.frozenpriest.taskautomaton.program.commands.output.ShowToast
import ru.frozenpriest.taskautomaton.program.commands.output.UseTts
import ru.frozenpriest.taskautomaton.program.commands.output.VibrateWithPattern
import ru.frozenpriest.taskautomaton.program.commands.variables.*
import ru.frozenpriest.taskautomaton.utils.GravityRestriction
import java.util.*

class CommandBuilder(private val commandInfo: CommandInfoShort, private val params: Map<ParamWithType, Any>) {

    fun build(): List<Command> {
        val list = mutableListOf<Command>()
        val params = params.mapValues{param ->
            param.key.getParametrized(param.value)
        }
        when (commandInfo.className) {
            CommandClass.CheckVar -> {
                list.add(CheckVar(params[commandInfo.params[0]] as String))
            }
            CommandClass.EqualVar -> {
                list.add(
                    EqualVar(
                        params[commandInfo.params[0]] as String,
                        params[commandInfo.params[1]] as String
                    )
                )
            }
            CommandClass.ExistVar -> {
                list.add(
                    ExistVar(
                        params[commandInfo.params[0]] as String,
                    )
                )
            }
            CommandClass.GreaterVar -> {
                list.add(
                    GreaterVar(
                        params[commandInfo.params[0]] as String,
                        params[commandInfo.params[1]] as String
                    )
                )
            }
            CommandClass.LowerVar -> {
                list.add(
                    LowerVar(
                        params[commandInfo.params[0]] as String,
                        params[commandInfo.params[1]] as String
                    )
                )
            }
            CommandClass.NotFunction -> {
                list.add(
                    NotFunction(
                        params[commandInfo.params[0]] as Function,
                    )
                )
            }
            CommandClass.IfCondition -> {
                list.addAll(
                    listOf(
                        IfCondition(
                            params[commandInfo.params[0]] as Function,
                        ),
                        EndIf(),
                    )
                )
                if (params[commandInfo.params[1]] as Boolean) {
                    list.addAll(
                        listOf(
                            ElseCondition(),
                            EndElse()
                        )
                    )
                }
            }
            CommandClass.WhileCondition -> {
                list.addAll(
                    listOf(
                        WhileCondition(
                            params[commandInfo.params[0]] as Function,
                        ),
                        EndWhile(),
                    )
                )
            }
            CommandClass.ShowHtml -> {
                list.add(
                    ShowHtml(
                        params[commandInfo.params[0]] as String,
                        (params[commandInfo.params[1]] as String).split(",").map { it.trim() },
                        (params[commandInfo.params[2]] as Int),
                        (params[commandInfo.params[3]] as Int),
                        (params[commandInfo.params[4]] as Int),
                        (params[commandInfo.params[5]] as Long),
                    )
                )
            }
            CommandClass.ShowToast -> {
                list.add(
                    ShowToast(
                        params[commandInfo.params[0]] as String,
                        (params[commandInfo.params[1]] as String).split(",").map { it.trim() },
                        (params[commandInfo.params[2]] as Int),
                    )
                )
            }
            CommandClass.UseTts -> {
                list.add(
                    UseTts(
                        params[commandInfo.params[0]] as String,
                        (params[commandInfo.params[1]] as String).split(",").map { it.trim() },
                        (params[commandInfo.params[2]] as Locale)
                    )
                )
            }
            CommandClass.VibrateWithPattern -> {
                list.add(
                    VibrateWithPattern(
                        (params[commandInfo.params[1]] as String).split(",").map { it.toLong()},
                    )
                )
            }
            CommandClass.DivVar -> {
                list.add(
                    DivVar(
                        params[commandInfo.params[0]] as String,
                        params[commandInfo.params[1]] as String,
                        params[commandInfo.params[2]] as String
                    )
                )
            }
            CommandClass.IncVar -> {
                list.add(
                    IncVar(
                        params[commandInfo.params[0]] as String
                    )
                )
            }
            CommandClass.MulVar -> {
                list.add(
                    MulVar(
                        params[commandInfo.params[0]] as String,
                        params[commandInfo.params[1]] as String,
                        params[commandInfo.params[2]] as String
                    )
                )
            }
            CommandClass.SetVar -> {
                list.add(
                    SetVar(
                        params[commandInfo.params[0]] as String,
                        params[commandInfo.params[1]] as String
                    )
                )
            }
            CommandClass.SubVar -> {
                list.add(
                    SubVar(
                        params[commandInfo.params[0]] as String,
                        params[commandInfo.params[1]] as String,
                        params[commandInfo.params[2]] as String
                    )
                )
            }
            CommandClass.SumVar -> {
                list.add(
                    SumVar(
                        params[commandInfo.params[0]] as String,
                        params[commandInfo.params[1]] as String,
                        params[commandInfo.params[2]] as String
                    )
                )
            }
            CommandClass.ExecuteProgram -> {
                TODO()
            }
            //else -> throw NotImplementedError("Command not supported")
        }
        return list
    }

    enum class CommandClass {
        CheckVar,
        EqualVar,
        ExistVar,
        GreaterVar,
        LowerVar,
        NotFunction,
        ElseCondition,
        EndElse,
        EndIf,
        EndWhile,
        IfCondition,
        WhileCondition,
        ShowHtml,
        ShowToast,
        UseTts,
        VibrateWithPattern,
        DivVar,
        IncVar,
        MulVar,
        SetVar,
        SubVar,
        SumVar,
        ExecuteProgram
    }

    data class CommandInfoShort(
        val className: CommandClass,
        val commandType: CommandType,
        val iconId: Int,
        val params: List<ParamWithType>
    )

    data class ParamWithType(
        val name: String,
        val type: ParamType
    ) {
        fun getParametrized(value: Any): Any {
            return type.getParametrized(value)
        }
    }

    enum class ParamType {
        String,
        Gravity,
        DurationToast,
        DurationExpire,
        Function,
        Color,
        Language,
        Boolean;

        /**
         * returns casted value. Still need to use as later
         */
        fun getParametrized(value: Any): Any {
            return when (this) {
                String -> return value
                Gravity -> {
                    when (GravityRestriction.valueOf(value as kotlin.String)) {
                        GravityRestriction.Center -> android.view.Gravity.CENTER
                        GravityRestriction.Top -> android.view.Gravity.TOP
                        GravityRestriction.Bottom -> android.view.Gravity.BOTTOM
                        GravityRestriction.Start -> android.view.Gravity.START
                        GravityRestriction.End -> android.view.Gravity.END
                    }
                }
                DurationToast -> if (value == "Long") Toast.LENGTH_LONG else Toast.LENGTH_SHORT
                DurationExpire -> (value as kotlin.String).toLong()
                Function -> TODO()
                Color -> android.graphics.Color.valueOf((value as kotlin.String).toInt())
                Language -> Locale(value as kotlin.String)
                Boolean -> (value as kotlin.String).toBoolean()
            }
        }
    }


    companion object {
        //todo move to some non static maybe
        val commandToInfo: List<CommandInfoShort> = listOf(
            CommandInfoShort(
                CommandClass.CheckVar,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(ParamWithType("Variable", ParamType.String))
            ),
            CommandInfoShort(
                CommandClass.EqualVar,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                CommandClass.ExistVar,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(ParamWithType("Variable", ParamType.String))
            ),
            CommandInfoShort(
                CommandClass.GreaterVar,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                CommandClass.LowerVar,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                CommandClass.NotFunction,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(ParamWithType("Function", ParamType.Function))
            ),
            CommandInfoShort(
                CommandClass.IfCondition,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Function", ParamType.Function),
                    ParamWithType("Else", ParamType.Boolean)
                )
            ),
            CommandInfoShort(
                CommandClass.WhileCondition,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(ParamWithType("Function", ParamType.Function))
            ),
            CommandInfoShort(//name, args, backColor, textColor, gravity, duration
                CommandClass.ShowHtml,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Text", ParamType.String),
                    ParamWithType("Args", ParamType.String),
                    ParamWithType("Background", ParamType.Color),
                    ParamWithType("TextColor", ParamType.Color),
                    ParamWithType("Gravity", ParamType.Gravity),
                    ParamWithType("Duration", ParamType.DurationExpire)
                )
            ),
            CommandInfoShort(
                CommandClass.ShowToast,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Text", ParamType.String),
                    ParamWithType("Args", ParamType.String),
                    ParamWithType("Duration", ParamType.DurationToast)
                )
            ),
            CommandInfoShort(
                CommandClass.UseTts,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Text", ParamType.String),
                    ParamWithType("Args", ParamType.String),
                    ParamWithType("Language", ParamType.Language)
                )
            ),
            CommandInfoShort(
                CommandClass.VibrateWithPattern,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Vibration", ParamType.String),
                )
            ),
            CommandInfoShort(
                CommandClass.SumVar,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("VarResult", ParamType.String),
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                CommandClass.DivVar,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("VarResult", ParamType.String),
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                CommandClass.IncVar,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Variable", ParamType.String),
                )
            ),
            CommandInfoShort(
                CommandClass.MulVar,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("VarResult", ParamType.String),
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                CommandClass.SetVar,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Variable", ParamType.String),
                    ParamWithType("Value", ParamType.String),
                )
            ),
            CommandInfoShort(
                CommandClass.SubVar,
                CommandType.Variables,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("VarResult", ParamType.String),
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                CommandClass.ExecuteProgram,
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

