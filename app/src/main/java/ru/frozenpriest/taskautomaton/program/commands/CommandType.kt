package ru.frozenpriest.taskautomaton.program.commands

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Outbox
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.ui.graphics.vector.ImageVector

enum class CommandType(val icon: ImageVector) {
    Output(Icons.Filled.Outbox),
    Logic(Icons.Filled.Mail),
    Variables(Icons.Filled.Map),
    Inner(Icons.Filled.Outbox),
    DateTime(Icons.Filled.Timer),
    Test2(Icons.Filled.Outbox),
    Test3(Icons.Filled.Outbox),
    Test4(Icons.Filled.Outbox),
    Test5(Icons.Filled.Outbox),
    Test6(Icons.Filled.Outbox),

    Uncategorized(Icons.Outlined.Stop),
    Functions(Icons.Outlined.Stop),
}

