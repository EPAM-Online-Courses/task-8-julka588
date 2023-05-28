package efs.task.unittests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class FitCalculatorTest {

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        //given
        double weight = 89.2;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertTrue(recommended);
    }

    @Test
    void shouldReturnFalse_whenDietRecommended() {
        //given
        double weight = 69.5;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertFalse(recommended);
    }

    @Test
    void shouldThrowAnException_whenHeightIsEqualToZero() {
        //given
        double weight = 70.0;
        double height = 0.0;

        //then
        assertThrows(IllegalArgumentException.class,
                () -> {
                    //when
                    FitCalculator.isBMICorrect(weight, height);
                });
    }

    @ParameterizedTest(name = "{index} => weight={0}")
    @ValueSource(doubles = {84.5, 65.0, 60.5})
    void shouldReturnTrue_whenDietRecommendedForGivenParameter(double weight) {
        //given
        double height = 1.55;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertTrue(recommended);
    }

    @ParameterizedTest(name = "{index} => weight={0}, height={1}, expectedRecommended={2}")
    @CsvSource({"54.0,1.72,false", "65.5, 1.80, false", "60.0,1.55,false"})
    void shouldReturnFalse_whenDietRecommendedFromCsvSource(double weight, double height, boolean expectedRecommended) {
        //when
        boolean actualRecommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertEquals(expectedRecommended, actualRecommended);
    }

    @ParameterizedTest(name = "{index} => weight={0}, height={1}")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void shouldReturnFalse_whenDietRecommendedFromCsvFile(double weight, double height) {
        //when
        boolean actualRecommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertFalse(actualRecommended);
    }

    @Test
    void shouldReturnTheWorstBMI_forTestUsersList() {
        //given
        double weight = 97.3;
        double height = 1.79;

        //when
        var user = FitCalculator.findUserWithTheWorstBMI(TestConstants.TEST_USERS_LIST);

        //then
        assertAll("Should return the user with the worst BMI",
                () -> assertEquals(weight, user.getWeight()),
                () -> assertEquals(height, user.getHeight())
        );
    }

    @Test
    void shouldReturnNull_whenListOfUsersBlank() {
        //when
        var user = FitCalculator.findUserWithTheWorstBMI(Collections.emptyList());

        //then
        assertNull(user);
    }

    @Test
    void shouldReturnTestUsersBMIScore_whenTestUsersListGiven() {
        //given
        double[] bmiScore;

        //when
        bmiScore = FitCalculator.calculateBMIScore(TestConstants.TEST_USERS_LIST);

        //then
        assertArrayEquals(TestConstants.TEST_USERS_BMI_SCORE, bmiScore);
    }


}