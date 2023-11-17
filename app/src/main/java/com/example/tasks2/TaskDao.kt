package com.example.tasks2

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    fun getTasks(query: String, sortType: SortType, hideCompleted: Boolean): Flow<List<Task>> =
        when(sortType) {
            SortType.BY_DATE -> getTasksSortedByDateCreated(query, hideCompleted)
            SortType.BY_NAME -> getTasksSortedByName(query, hideCompleted)
        }

    @Query("SELECT * FROM tasks_table WHERE (done != :hideCompleted OR done = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, name")
    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM tasks_table WHERE (done != :hideCompleted OR done = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, date")
    fun getTasksSortedByDateCreated(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Upsert
    suspend fun upsert(task: Task)

    @Delete
    suspend fun delete(task: Task):Integer

    @Query("DELETE FROM tasks_table WHERE done = 1")
    suspend fun deleteCompletedTasks()
}