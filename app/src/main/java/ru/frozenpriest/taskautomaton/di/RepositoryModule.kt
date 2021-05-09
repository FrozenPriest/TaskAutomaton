package ru.frozenpriest.taskautomaton.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.frozenpriest.taskautomaton.data.local.RoomRepository
import ru.frozenpriest.taskautomaton.data.local.TaskRoomDatabase
import ru.frozenpriest.taskautomaton.data.local.dao.ProgramDao
import ru.frozenpriest.taskautomaton.data.local.dao.TriggerDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        programDao: ProgramDao,
        triggerDao: TriggerDao,
    ): RoomRepository {
        return RoomRepository(
            programDao = programDao,
            triggerDao = triggerDao
        )
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            app,
            TaskRoomDatabase::class.java,
            "task_database"
        ).build()

    @Singleton
    @Provides
    fun provideTriggerDao(database: TaskRoomDatabase): TriggerDao = database.triggerDao()

    @Singleton
    @Provides
    fun provideProgramDao(database: TaskRoomDatabase): ProgramDao = database.programDao()
}
