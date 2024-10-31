package introduction.encapsulation.date;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class DateBasicTest {

    @Test
    public void getNumDayInYear() {
        DateBasic date1 = new DateBasic(1, 1, 2024);
        Assertions.assertThat(date1.getNumDayInYear()).isEqualTo(1);

        DateBasic date2 = new DateBasic(1, 2, 2024);
        Assertions.assertThat(date2.getNumDayInYear()).isEqualTo(32);

        DateBasic date3 = new DateBasic(15, 2, 2024);
        Assertions.assertThat(date3.getNumDayInYear()).isEqualTo(46);
    }

    @Test
    public void computeDurationInDays1() {
        DateBasic date1 = new DateBasic(1, 1, 2024);
        DateBasic date2 = new DateBasic(2, 1, 2024);
        Assertions.assertThat(date1.computeDurationInDays(date2)).isEqualTo(1);
    }

    @Test
    public void computeDurationInDays2() {
        DateBasic date1 = new DateBasic(1, 1, 2024);
        DateBasic date2 = new DateBasic(2, 2, 2024);
        Assertions.assertThat(date1.computeDurationInDays(date2)).isEqualTo(32);
    }

}