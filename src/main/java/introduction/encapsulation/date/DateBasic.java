package introduction.encapsulation.date;

public class DateBasic implements Date {
    private int day;
    private int month;
    private int year;

    private int[] nbJoursByMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public DateBasic(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getNumDayInYear() {
        // mois et février
        int resultat = day;
        for (int iMonth = 1; iMonth < month; iMonth++) {
            resultat += nbJoursByMonth[iMonth - 1];
        }
        return resultat;
    }

    @Override
    public int computeDurationInDays(Date date2) {
        // FIXME a corriger
        int nb = 0;
        if (this.year < date2.getYear()) {
            for (int iYear = year; iYear < date2.getYear(); iYear++) {
                nb += isBissextile(iYear) ? 366 : 365;
            }
        }
        return
                Math.abs(((this.getYear() - date2.getYear()) * 365) + (getNumDayInYear() - date2.getNumDayInYear()))
                ;
    }

    private static boolean isBissextile(int year) {
        boolean divisiblePar4 = year % 4 == 0;
        boolean divisiblePar100 = year % 100 == 0;
        boolean divisiblePar400 = year % 400 == 0;

        return (divisiblePar4 && !divisiblePar100) || divisiblePar400;
    }
}
