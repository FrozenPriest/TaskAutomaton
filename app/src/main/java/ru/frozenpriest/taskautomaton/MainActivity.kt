package ru.frozenpriest.taskautomaton

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.SetVariableCommand

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var tv: TextView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.textView)

        val list = listOf<Command>(
            SetVariableCommand("funkyVar1", true),
            SetVariableCommand("funkyVar2", 5),
            SetVariableCommand("funkyVar3", 3.0),
            SetVariableCommand("funkyVar4", "WWWW"),
            SetVariableCommand("funkyVar5", true)
        )

        val program = Program(list)

        program.executeCommands()
    }

}