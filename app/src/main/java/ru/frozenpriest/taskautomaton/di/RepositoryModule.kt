package ru.frozenpriest.taskautomaton.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.frozenpriest.taskautomaton.data.local.RoomRepository
import ru.frozenpriest.taskautomaton.data.local.TaskRoomDatabase
import ru.frozenpriest.taskautomaton.data.local.dao.ProgramDao
import ru.frozenpriest.taskautomaton.data.local.dao.TriggerDao
import ru.frozenpriest.taskautomaton.utils.DataGenerator
import ru.frozenpriest.taskautomaton.utils.toEntity
import javax.inject.Provider
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
    fun provideDatabase(
        @ApplicationContext app: Context,
        programDao: Provider<ProgramDao>,
        triggerDao: Provider<TriggerDao>
    ) =
        Room.databaseBuilder(
            app,
            TaskRoomDatabase::class.java,
            "task_database"
        ).addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        programDao.get()
                            .insertAll(DataGenerator.getPrograms().map { it.toEntity() })
                        triggerDao.get()
                            .insertAll(DataGenerator.getTriggers())
                    }
                }
            }
        ).build()

    @Singleton
    @Provides
    fun provideTriggerDao(database: TaskRoomDatabase): TriggerDao = database.triggerDao()

    @Singleton
    @Provides
    fun provideProgramDao(database: TaskRoomDatabase): ProgramDao = database.programDao()
}
