package ru.frozenpriest.taskautomaton.presentation.commands


import android.widget.Toast
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType
import ru.frozenpriest.taskautomaton.program.commands.ExecuteProgram
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

class CommandBuilder(
    private val commandInfo: CommandInfoShort,
    private val params: Map<ParamWithType, Any>
) {

    fun build(): List<Command> {
        val list = mutableListOf<Command>()
        val params = params.mapValues { param ->
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
                        (params[commandInfo.params[0]] as String).split(",").map { it.toLong() },
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
                list.add(
                    ExecuteProgram(
                        params[commandInfo.params[0]] as String
                    )
                )
            }
            else -> throw NotImplementedError("Command not supported")
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
                String -> value
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
                Function -> value
                Color -> android.graphics.Color.valueOf((value as kotlin.String).toInt())
                Language -> Locale(value as kotlin.String)
                Boolean -> (value as kotlin.String).toBoolean()
            }
        }
    }


    companion object {
        fun populateParams(
            command: Command?,
            info: CommandInfoShort
        ): MutableMap<ParamWithType, Any> {
            val map = mutableMapOf<ParamWithType, Any>()
            if (command == null) return map
            when (command.info.commandClass) {
                CommandClass.CheckVar -> {
                    map[info.params[0]] = (command as CheckVar).varName
                }
                CommandClass.EqualVar -> {
                    map[info.params[0]] = (command as EqualVar).var1
                    map[info.params[1]] = command.var2
                }
                CommandClass.ExistVar -> {
                    map[info.params[0]] = (command as ExistVar).varName
                }
                CommandClass.GreaterVar -> {
                    map[info.params[0]] = (command as GreaterVar).var1
                    map[info.params[1]] = command.var2
                }
                CommandClass.LowerVar -> {
                    map[info.params[0]] = (command as LowerVar).var1
                    map[info.params[1]] = command.var2
                }
                CommandClass.NotFunction -> {
                    map[info.params[0]] = (command as NotFunction).function
                }

                CommandClass.IfCondition -> {
                    map[info.params[0]] = (command as IfCondition).condition
                }
                CommandClass.WhileCondition -> {
                    map[info.params[0]] = (command as WhileCondition).condition
                }
                CommandClass.ShowHtml -> {
                    map[info.params[0]] = (command as ShowHtml).stringToShow
                    map[info.params[1]] = command.args.joinToString(", ")
                    map[info.params[2]] = command.backgroundColor
                    map[info.params[3]] = command.textColor
                    map[info.params[4]] = when (command.gravity) {
                        android.view.Gravity.CENTER -> GravityRestriction.Center
                        android.view.Gravity.TOP -> GravityRestriction.Top
                        android.view.Gravity.BOTTOM -> GravityRestriction.Bottom
                        android.view.Gravity.START -> GravityRestriction.Start
                        android.view.Gravity.END -> GravityRestriction.End
                        else -> GravityRestriction.Center
                    }
                    map[info.params[5]] = command.duration
                }
                CommandClass.ShowToast -> {
                    map[info.params[0]] = (command as ShowToast).stringToShow
                    map[info.params[1]] = command.args.joinToString(", ")
                    map[info.params[2]] =
                        if (command.duration == Toast.LENGTH_LONG) "Long" else "Short"

                }
                CommandClass.UseTts -> {
                    map[info.params[0]] = (command as UseTts).stringToShow
                    map[info.params[1]] = command.args.joinToString(", ")
                    map[info.params[2]] = command.language.language
                }
                CommandClass.VibrateWithPattern -> {
                    map[info.params[0]] = (command as VibrateWithPattern).delays.joinToString(", ")
                }
                CommandClass.DivVar -> {
                    map[info.params[0]] = (command as DivVar).varRes
                    map[info.params[1]] = command.varName1
                    map[info.params[2]] = command.varName2
                }
                CommandClass.IncVar -> {
                    map[info.params[0]] = (command as IncVar).varName
                }
                CommandClass.MulVar -> {
                    map[info.params[0]] = (command as MulVar).varRes
                    map[info.params[1]] = command.varName1
                    map[info.params[2]] = command.varName2
                }
                CommandClass.SetVar -> {
                    map[info.params[0]] = (command as SetVar).varName
                    map[info.params[1]] = command.value
                }
                CommandClass.SubVar -> {
                    map[info.params[0]] = (command as SubVar).varRes
                    map[info.params[1]] = command.varName1
                    map[info.params[2]] = command.varName2
                }
                CommandClass.SumVar -> {
                    map[info.params[0]] = (command as SumVar).varRes
                    map[info.params[1]] = command.varName1
                    map[info.params[2]] = command.varName2
                }
                CommandClass.ExecuteProgram -> {
                    TODO()
                }
                else -> {
                    throw IllegalArgumentException("Not supported")
                }
            }
            return map
        }

        //todo move to some non static maybe
        val commandToInfo: List<CommandInfoShort> = listOf(
            CommandInfoShort(
                CommandClass.IfCondition,
                CommandType.Logic,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Function", ParamType.Function),
                    ParamWithType("Else", ParamType.Boolean)
                )
            ),
            CommandInfoShort(
                CommandClass.WhileCondition,
                CommandType.Logic,
                R.drawable.icon_sample,
                listOf(ParamWithType("Function", ParamType.Function))
            ),
            CommandInfoShort(//name, args, backColor, textColor, gravity, duration
                CommandClass.ShowHtml,
                CommandType.Output,
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
                CommandType.Output,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Text", ParamType.String),
                    ParamWithType("Args", ParamType.String),
                    ParamWithType("Duration", ParamType.DurationToast)
                )
            ),
            CommandInfoShort(
                CommandClass.UseTts,
                CommandType.Output,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Text", ParamType.String),
                    ParamWithType("Args", ParamType.String),
                    ParamWithType("Language", ParamType.Language)
                )
            ),
            CommandInfoShort(
                CommandClass.VibrateWithPattern,
                CommandType.Output,
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
                CommandType.Inner,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Name", ParamType.String)
                )
            ),
        )
        val functionsOnly = listOf(
            CommandInfoShort(
                CommandClass.CheckVar,
                CommandType.Functions,
                R.drawable.icon_sample,
                listOf(ParamWithType("Variable", ParamType.String))
            ),
            CommandInfoShort(
                CommandClass.EqualVar,
                CommandType.Functions,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                CommandClass.ExistVar,
                CommandType.Functions,
                R.drawable.icon_sample,
                listOf(ParamWithType("Variable", ParamType.String))
            ),
            CommandInfoShort(
                CommandClass.GreaterVar,
                CommandType.Functions,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                CommandClass.LowerVar,
                CommandType.Functions,
                R.drawable.icon_sample,
                listOf(
                    ParamWithType("Var1", ParamType.String),
                    ParamWithType("Var2", ParamType.String)
                )
            ),
            CommandInfoShort(
                CommandClass.NotFunction,
                CommandType.Functions,
                R.drawable.icon_sample,
                listOf(ParamWithType("Function", ParamType.Function))
            ),
        )
    }
}

