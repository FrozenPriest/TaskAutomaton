package ru.frozenpriest.taskautomaton

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.gui.ShowToast
import ru.frozenpriest.taskautomaton.program.commands.variables.AddVar
import ru.frozenpriest.taskautomaton.program.commands.variables.SetVar

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var tv: TextView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.textView)

        val list = listOf<Command>(
            SetVar("funkyVar1", true),
            SetVar("funkyVar2", 5),
            SetVar("funkyVar3", 3.0),
            SetVar("funkyVar4", 2),
            SetVar("funkyVar5", -6),
            AddVar("funkyVar888","funkyVar2", "funkyVar3"),
            AddVar("funkyVar999","funkyVar2", "funkyVar5"),
            ShowToast("New text %s, to go %s", arrayOf("funkyVar1", "funkyVar2"), Toast.LENGTH_LONG)

        )

        val program = Program(list)

        program.executeCommands(applicationContext)
    }

}