package ru.frozenpriest.taskautomaton

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.frozenpriest.taskautomaton.program.MyService
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.functions.CheckVar
import ru.frozenpriest.taskautomaton.program.commands.functions.LowerVar
import ru.frozenpriest.taskautomaton.program.commands.functions.NotFunction
import ru.frozenpriest.taskautomaton.program.commands.logic.*
import ru.frozenpriest.taskautomaton.program.commands.output.ShowHtml
import ru.frozenpriest.taskautomaton.program.commands.output.ShowToast
import ru.frozenpriest.taskautomaton.program.commands.output.UseTts
import ru.frozenpriest.taskautomaton.program.commands.output.VibrateWithPattern
import ru.frozenpriest.taskautomaton.program.commands.variables.IncVar
import ru.frozenpriest.taskautomaton.program.commands.variables.SetVar
import ru.frozenpriest.taskautomaton.utils.CustomItemTouchHelper
import ru.frozenpriest.taskautomaton.utils.ItemTouchHelperAdapter
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object {
        const val OVERLAY_PERMISSION_REQ_CODE = 1

    }

    private lateinit var rvProgram: RecyclerView
    private lateinit var program: Program

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissionOverlay()

        setupRecyclerView()
        setupButtons()


        val list = listOf(
            SetVar("funkyVar1", true),
            SetVar("funkyVar2", true),
            SetVar("f3", 0),
            SetVar("f4", 9),
            SetVar("f10", 10),
            VibrateWithPattern(arrayOf(200, 100, 200, 100, 400, 200, 500)),
            WhileCondition(NotFunction(LowerVar("f10", "f3"))),
            UseTts("Test is %s", arrayOf("f3"), Locale.ENGLISH),
            IncVar("f3"),
            EndWhile(),

            IfCondition(CheckVar("funkyVar1")),
            IfCondition(CheckVar("funkyVar2")),
            ShowToast(
                "Test test test",
                arrayOf(),
                Toast.LENGTH_LONG
            ),
            EndIf(),
            ElseCondition(),
            ShowToast(
                "NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                arrayOf(),
                Toast.LENGTH_LONG
            ),
            EndElse(),
            EndIf(),
            IfCondition(CheckVar("funkyVar1")),
            ShowHtml(
                "<html>\n" +
                        "<body>\n" +
                        "<h1 style=\"font-size:300%%;\">This is a heading</h1>\n" +
                        "<p>Do not (%s, %s) forget to buy <mark>milk</mark> today.</p>\n" +
                        "</body>\n" +
                        "</html>\n",
                duration = 15000,
                args = arrayOf("funkyVar1", "funkyVar4"),
                textColor = Color.WHITE,
                backgroundColor = Color.BLACK,
                gravity = Gravity.START or Gravity.CENTER_VERTICAL
            ),
            EndIf(),
            ElseCondition(),
            ShowToast(
                "New text %s, to go %s",
                arrayOf("funkyVar1", "funkyVar2"),
                Toast.LENGTH_LONG
            ),
            EndElse()
        )
        Log.e("0", "Prog size is ${list.size}")
        program = Program(list)

        (rvProgram.adapter as CommandItemAdapter).bind(program)
        program.listener = rvProgram.adapter as CommandItemAdapter

        val intent = Intent(this, MyService::class.java)
        intent.putExtra("ProgramId", "id") //todo when have database
        stopService(intent) //todo for debug purposes, remove later
        startService(intent)


    }

    private fun setupButtons() {
        val buttonStart: ImageButton = findViewById(R.id.imageButtonPlay)
        buttonStart.setOnClickListener { onButtonStart() }

        val buttonStep: ImageButton = findViewById(R.id.imageButtonStep)
        buttonStep.setOnClickListener { onButtonStep() }

        val buttonReset: ImageButton = findViewById(R.id.imageButtonStop)
        buttonReset.setOnClickListener { onButtonReset() }
    }

    private fun setupRecyclerView() {
        rvProgram = findViewById(R.id.rvProgram)
        rvProgram.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = CommandItemAdapter(context = context, program = Program(emptyList()))

            val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            AppCompatResources.getDrawable(this@MainActivity, R.drawable.divider_drawable)?.let {
                dividerItemDecoration.setDrawable(it)
            }

            addItemDecoration(dividerItemDecoration)

            val callback: ItemTouchHelper.Callback =
                CustomItemTouchHelper(adapter as ItemTouchHelperAdapter)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(rvProgram)
        }
    }

    private fun onButtonStart() {
        if (program.isSyntaxValid)
            program.executeCommands(this)
        else
            showProgramErrorToast()
    }

    private fun onButtonReset() {
        if (program.isSyntaxValid)
            program.stop()
        else
            showProgramErrorToast()
    }

    private fun onButtonStep() {
        if (program.isSyntaxValid)
            program.nextCommand(this)
        else
            showProgramErrorToast()
    }

    private fun showProgramErrorToast() {
        Toast.makeText(this, "Syntax error", Toast.LENGTH_LONG).show()
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun checkPermissionOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            val intentSettings = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivityForResult(intentSettings, OVERLAY_PERMISSION_REQ_CODE)
        }
    }
}