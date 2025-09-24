package ru.urfu.chucknorrisdemo.presentation.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.urfu.chucknorrisdemo.domain.model.FactEntity
import ru.urfu.chucknorrisdemo.domain.repository.FactsRepository

class GalleryViewModel : ViewModel() {
    private val repository = FactsRepository()

    var facts: List<FactEntity> by mutableStateOf(emptyList())
        private set

    var currentPage by mutableStateOf(0)

    init {
        loadFacts()
    }

    fun loadFacts() {
        facts = repository.getAllFacts()
    }
}