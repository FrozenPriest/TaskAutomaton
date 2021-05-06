package ru.frozenpriest.taskautomaton.data.local.entities

data class CommandEntity(
    val commandType: CommandType,
    val params: String
)


enum class CommandType {
    CheckVar,
    EqualVar,
    ExistVar,
    GreaterVar,
    LoverVar,
    NotFunction,
    ElseCondition,
    EndElse,
    EndIf,
    EndWhile,
    IfCondition,
    WhileCondition,
    ShowHtml,
    UseTls,
    VibrateWithPattern,
    AddVar,
    DivVar,
    IncVar,
    MulVar,
    SetVar,
    SubVar
}