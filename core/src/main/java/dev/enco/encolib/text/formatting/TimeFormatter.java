package dev.enco.encolib.text.formatting;

import lombok.Builder;

import java.util.Objects;

public class TimeFormatter {
    private final UnitForms[] forms;

    private static final long[] UNITS_IN_SECONDS = {
            31_536_000L,
            2_592_000L,
            86_400L,
            3600L,
            60L,
            1L
    };

    private static final UnitForms DEFAULT_YEARS = new UnitForms("год", "года", "лет");
    private static final UnitForms DEFAULT_MONTHS = new UnitForms("месяц", "месяца", "месяцев");
    private static final UnitForms DEFAULT_DAYS = new UnitForms("день", "дня", "дней");
    private static final UnitForms DEFAULT_HOURS = new UnitForms("час", "часа", "часов");
    private static final UnitForms DEFAULT_MINUTES = new UnitForms("минута", "минуты", "минут");
    private static final UnitForms DEFAULT_SECONDS = new UnitForms("секунда", "секунды", "секунд");

    @Builder
    public TimeFormatter(UnitForms yearForms, UnitForms monthForms, UnitForms dayForms,
                         UnitForms hourForms, UnitForms minuteForms, UnitForms secondForms) {
        this.forms = new UnitForms[]{
                Objects.requireNonNullElse(yearForms, DEFAULT_YEARS),
                Objects.requireNonNullElse(monthForms, DEFAULT_MONTHS),
                Objects.requireNonNullElse(dayForms, DEFAULT_DAYS),
                Objects.requireNonNullElse(hourForms, DEFAULT_HOURS),
                Objects.requireNonNullElse(minuteForms, DEFAULT_MINUTES),
                Objects.requireNonNullElse(secondForms, DEFAULT_SECONDS)
        };
    }

    public String format(long seconds, int maxUnits) {
        if (seconds <= 0) {
            return "0 " + getCorrectForm(0, forms[5]);
        }

        StringBuilder sb = new StringBuilder();
        int unitsCount = 0;
        long remaining = seconds;

        for (int i = 0; i < UNITS_IN_SECONDS.length; i++) {
            if (unitsCount >= maxUnits) break;

            long unitValue = remaining / UNITS_IN_SECONDS[i];
            if (unitValue > 0) {
                if (unitsCount > 0) sb.append(' ');

                sb.append(unitValue).append(' ').append(getCorrectForm(unitValue, forms[i]));

                remaining %= UNITS_IN_SECONDS[i];
                unitsCount++;
            }
        }

        return sb.toString();
    }

    private String getCorrectForm(long value, UnitForms forms) {
        if (forms == null) return "";

        long abs = value < 0 ? -value : value;
        long r10 = abs % 10;
        long r100 = abs % 100;

        if (r10 == 1 && r100 != 11) {
            return forms.form1();
        } else if (r10 >= 2 && r10 <= 4 && (r100 < 10 || r100 >= 20)) {
            return forms.form2();
        } else {
            return forms.form5();
        }
    }
}
