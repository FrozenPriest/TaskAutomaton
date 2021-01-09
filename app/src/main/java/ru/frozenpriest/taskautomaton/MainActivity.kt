package ru.frozenpriest.taskautomaton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.frozenpriest.taskautomaton.data.TaskViewModel
import ru.frozenpriest.taskautomaton.data.TaskViewModelFactory
import ru.frozenpriest.taskautomaton.data.base.App

class MainActivity : AppCompatActivity() {

    private val tasksViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as App).repository)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tasksViewModel.tasks.observe(this) {
            adapter.submitList(it)
        }
    }
}