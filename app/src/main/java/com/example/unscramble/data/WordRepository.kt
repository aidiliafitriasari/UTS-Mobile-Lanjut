package com.example.unscramble.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WordRepository(
    private val wordDao: WordDao
) {
    fun getAllWordsFlow(): Flow<Set<String>> {
        return wordDao.getAllWords().map { dbWords ->
            val dbWordsSet = dbWords.map { it.word }.toSet()
            allWords + dbWordsSet
        }
    }

    suspend fun insertWord(word: String): Boolean {
        if (word.isBlank() || word.length < 3) {
            return false
        }

        val lowerCaseWord = word.lowercase().trim()
        val existsInDb = wordDao.isWordExists(lowerCaseWord) > 0
        val existsInStatic = allWords.contains(lowerCaseWord)

        return if (!existsInDb && !existsInStatic) {
            wordDao.insertWord(Word(word = lowerCaseWord))
            true
        } else {
            false
        }
    }

    suspend fun getTotalWordCount(): Int {
        var dbWordCount = 0
        wordDao.getAllWords().collect { dbWords ->
            dbWordCount = dbWords.size
        }
        return allWords.size + dbWordCount
    }
}