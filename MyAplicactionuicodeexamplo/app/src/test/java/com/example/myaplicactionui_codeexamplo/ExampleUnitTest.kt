package com.example.myaplicactionui_codeexamplo

import com.example.myaplicactionui_codeexamplo.data.Calculadora
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        //lista de pruebas
        //2,4 + 5 = 7,4
        //2.5 + 5 = "error no se puede ingresar ."
        val cal = Calculadora("2.1", "3,9")
        assertEquals("Error", cal.suma())
        //assertEquals(4, 2 + 3)
    }
}