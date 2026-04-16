package com.example.unscramble.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert
    suspend fun  insertWord(word: Word)

    @Query("SELECT * FROM words")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT COUNT(*) FROM words WHERE word = :word")
    suspend fun isWordExists(word: String): Int

    @Query("DELETE FROM words")
    suspend fun deleteAllWords()

}