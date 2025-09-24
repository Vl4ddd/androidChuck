package ru.urfu.chucknorrisdemo.domain.repository

import ru.urfu.chucknorrisdemo.domain.model.FactEntity
import ru.urfu.chucknorrisdemo.data.mock.MockData

class FactsRepository {
    fun getAllFacts(): List<FactEntity> = MockData.facts

    fun getRandomFact(): FactEntity = MockData.facts.random()

}