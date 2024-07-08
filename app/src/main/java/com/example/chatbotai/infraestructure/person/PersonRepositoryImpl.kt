package com.example.chatbotai.infraestructure.person

import com.example.chatbotai.R
import com.example.chatbotai.domain.person.PersonEntity
import com.example.chatbotai.domain.person.PersonRepository

class PersonRepositoryImpl : PersonRepository {
    override fun getPersons(): List<PersonEntity> {
        return listOf(
            PersonEntity(
                name = "Sara",
                image = R.drawable.sara,
                prompt = R.string.sara_prompt,
                description = R.string.sara_description,
            ),
        )
    }
}