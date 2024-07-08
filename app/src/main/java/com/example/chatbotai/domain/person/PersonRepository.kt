package com.example.chatbotai.domain.person

interface PersonRepository {
    fun getPersons(): List<PersonEntity>
}