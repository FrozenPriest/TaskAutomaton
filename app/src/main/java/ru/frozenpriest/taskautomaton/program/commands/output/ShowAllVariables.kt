package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType

@JsonTypeName("ShowAllVariables")
class ShowAllVariables() : Command(
    name = "Show table with variables",
    description = "Pop-up",
    iconId = R.drawable.icon_sample,
    commandType = CommandType.Output,
    commandClass = CommandBuilder.CommandClass.ShowVariables
) {
    override fun perform(program: Program, context: Context) {
        var html = "<html><body><table>"
        for (variable in program.variables) {
            html+="${variable.key}:\t\t   ${variable.value} <br/> "
        }
        html+="</table></body></html>"



        ShowHtml(
            stringToShow = html,
            args = emptyList(),
            duration = 30000
        ).perform(program, context)
    }

}