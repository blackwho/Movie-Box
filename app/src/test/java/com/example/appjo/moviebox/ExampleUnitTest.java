package com.example.appjo.moviebox;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;



import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void integer_isCorrect(){
        DashboardActivity activity = new DashboardActivity();
        Integer result = activity.getInteger();
        assertThat(result).isEqualTo(3);
    }
}