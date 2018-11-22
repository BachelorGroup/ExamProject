package no.kristiania.soj.groupexam.db

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class UserTest {

    @Before
    fun setUp() {
        val user: User? =null

        user!!.username = "test"
        user.password = "test123"
        user.email = "test@test.no"
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getUsername() {
    }

    @Test
    fun setUsername() {
    }

    @Test
    fun getPassword() {
    }

    @Test
    fun setPassword() {
    }

    @Test
    fun getEmail() {
    }

    @Test
    fun setEmail() {
    }

    @Test
    fun getId() {
    }

    @Test
    fun setId() {
    }
}