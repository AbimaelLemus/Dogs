package com.dessoft.dogs

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    /**
     * Para el nombre de los metodos
     * primero lo que se va a hacer
     * Y luego el resultado de lo que debemos de obtener
     * y arriba del nombre agregar la anotación de @Test
     *
     * el assert y sus derivados
     *
     * testImplementation 'junit:junit:4.13.2'
     * androidTestImplementation 'androidx.test.ext:junit:1.1.5'
     * androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
     */

    @Test
    fun additionIsCorrect() {
        assertEquals(4, 2 + 1)
    }


}