import kotlin.test.Test
import kotlin.test.assertEquals

internal class ExampleTest {
    @Test
    fun day01() {
        assertEquals(24000, Day01.partOneSolution(Day01.examplePath))
        assertEquals(45000, Day01.partTwoSolution(Day01.examplePath))
    }
}