package dev.fabled.domain.use_cases

import dev.fabled.domain.use_cases.authorization.ValidateEmail
import org.junit.Assert.assertFalse
import org.junit.Test

class EmailValidationTests {

    private val validateEmailCase = ValidateEmail()

    @Test
    fun `Empty and blank email, returns unsuccessful result`() {
        val emptyEmail = ""
        val blankEmail = "     "

        assertFalse(validateEmailCase(emptyEmail).isSuccessFull)
        assertFalse(validateEmailCase(blankEmail).isSuccessFull)
    }

    @Test
    fun `Wrong email formats, returns unsuccessful result`() {
        
    }

}