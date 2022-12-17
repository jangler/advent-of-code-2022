import kotlin.test.Test
import kotlin.test.assertEquals

internal class ExampleTest {
    @Test fun day01() {
        val path = exampleDir + Day01.filename
        assertEquals(24000, Day01.partOneSolution(path))
        assertEquals(45000, Day01.partTwoSolution(path))
    }

    @Test fun day02() {
        val path = exampleDir + Day02.filename
        assertEquals(15, Day02.partOneSolution(path))
        assertEquals(12, Day02.partTwoSolution(path))
    }

    @Test fun day03() {
        val path = exampleDir + Day03.filename
        assertEquals(157, Day03.partOneSolution(path))
        assertEquals(70, Day03.partTwoSolution(path))
    }

    @Test fun day04() {
        val path = exampleDir + Day04.filename
        assertEquals(2, Day04.partOneSolution(path))
        assertEquals(4, Day04.partTwoSolution(path))
    }

    @Test fun day05() {
        val path = exampleDir + Day05.filename
        assertEquals("CMZ", Day05.partOneSolution(path))
        assertEquals("MCD", Day05.partTwoSolution(path))
    }
}