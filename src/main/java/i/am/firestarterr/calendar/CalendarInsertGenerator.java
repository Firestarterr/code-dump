package i.am.firestarterr.calendar;


import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class CalendarInsertGenerator {

    public static void main(String[] args) {

        UUID calendarId = UUID.randomUUID();
        String month = "2021-06-";

        System.out.println("insert into calendar (id, created_date, updated_date, created_by, updated_by, country, name, description) VALUES('" +
                calendarId + "', '" + month + "01 00:00:00.000000', null, 'd_mcsayilgan', null, 'tr', 'test', 'test desc');");

        for (int i = 1; i < 31; i++) {
            if (i % 2 == 0)
                System.out.println("insert into calendar_entry(id, created_date, updated_date, created_by, updated_by, enddate, startdate, calendar_id)  VALUES('" +
                        UUID.randomUUID() + "', '" + month + "01 00:00:00.000000', null, 'd_mcsayilgan', null, " +
                        "'" + month + StringUtils.leftPad(String.valueOf(i), 2, "0") + " 20:00:00.000000', " +
                        "'" + month + StringUtils.leftPad(String.valueOf(i), 2, "0") + " 10:00:00.000000', " +
                        "'" + calendarId + "');");
        }
        System.out.println("UPDATE team SET calendar = '4442781c-d696-43a5-bdb0-d7e1846565ab' WHERE id = 'eab1133d-c17c-4fa9-838d-8bc98478a111';");
        System.out.println();
    }
}