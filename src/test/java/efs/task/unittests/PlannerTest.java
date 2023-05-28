package efs.task.unittests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class PlannerTest {

    private Planner planer;

    @BeforeEach
    void init() {
        planer = new Planner();
    }

    @ParameterizedTest(name = "{index} => activityLevel={0}")
    @EnumSource(ActivityLevel.class)
    void shouldCalculateAccurateDailyCaloriesDemand(ActivityLevel activityLevel) {
        //given
        var user = TestConstants.TEST_USER;
        int caloriesDemand = TestConstants.CALORIES_ON_ACTIVITY_LEVEL.get(activityLevel);
        //when
        int calculatedCaloriesDemand = planer.calculateDailyCaloriesDemand(user, activityLevel);

        //then
        assertEquals(caloriesDemand, calculatedCaloriesDemand);
    }

    @Test
    void shouldCalculateAccurateDailyIntake() {
        //given
        var user = TestConstants.TEST_USER;
        DailyIntake dailyIntake = TestConstants.TEST_USER_DAILY_INTAKE;

        //when
        DailyIntake calculatedDailyIntake = planer.calculateDailyIntake(user);

        //then
        assertAll("Should return daily intake for the user",
                () -> assertEquals(dailyIntake.getCalories(), calculatedDailyIntake.getCalories()),
                () -> assertEquals(dailyIntake.getCarbohydrate(), calculatedDailyIntake.getCarbohydrate()),
                () -> assertEquals(dailyIntake.getFat(), calculatedDailyIntake.getFat()),
                () -> assertEquals(dailyIntake.getProtein(), calculatedDailyIntake.getProtein()));
    }

}
