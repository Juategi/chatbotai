package com.example.chatbotai.infraestructure.person

import com.example.chatbotai.R
import com.example.chatbotai.domain.person.PersonEntity
import com.example.chatbotai.domain.person.PersonRepository

class PersonRepositoryImpl : PersonRepository {
    private var persons: List<PersonEntity> = listOf(
        PersonEntity(
            name = "Sara",
            image = R.drawable.sara,
            prompt = R.string.sara_prompt,
            description = R.string.sara_description,
        ),
        PersonEntity(
            name = "John",
            image = R.drawable.john,
            prompt = R.string.john_prompt,
            description = R.string.john_description,
        ),
    )
    override fun getPersons(): List<PersonEntity> {
        return persons
    }

    override fun getPerson(name: String): PersonEntity {
        return persons.find { it.name == name } ?: persons[0]
    }
}