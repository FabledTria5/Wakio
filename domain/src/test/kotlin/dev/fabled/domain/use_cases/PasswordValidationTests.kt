package dev.fabled.domain.use_cases

import dev.fabled.domain.use_cases.authorization.ValidatePassword
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PasswordValidationTests {

    private val validatePasswordUseCase = ValidatePassword()

    @Test
    fun `Short password, returns unsuccessful result`() {
        val password = "short"
        
        assert(!validatePasswordUseCase(password).isSuccessFull)
    }
    
    @Test
    fun `Blank and empty password, returns unsuccessful result`() {
        val emptyPassword = ""
        val blankPassword = "           "

        assertFalse(validatePasswordUseCase(emptyPassword).isSuccessFull)
        assertFalse(validatePasswordUseCase(blankPassword).isSuccessFull)
    }

    @Test
    fun `All letters lowercase, returns unsuccessful result`() {
        val lowercasePassword = "lowercase_password"

        assertFalse(validatePasswordUseCase(lowercasePassword).isSuccessFull)
    }

    @Test
    fun `Not containing digits, returns unsuccessful result`() {
        val onlyLettersPassword = "onlyLettersPassword"

        assertFalse(validatePasswordUseCase(onlyLettersPassword).isSuccessFull)
    }

    @Test
    fun `Correct password, returns successful result`() {
        val correctPassword1 = "CorrectPassword13234"
        val correctPassword2 = "CorrectPassword_2_version"
        val correctPasswordMinSymbolsCount = "Pass4023"

        assertTrue(validatePasswordUseCase(correctPassword1).isSuccessFull)
        assertTrue(validatePasswordUseCase(correctPassword2).isSuccessFull)
        assertTrue(validatePasswordUseCase(correctPasswordMinSymbolsCount).isSuccessFull)
    }

}