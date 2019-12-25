import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Generator {


    private Generator() {
    }

    private static Faker faker = new Faker(new Locale("ru"));
    private static String name = faker.name().fullName();
    private static String city = faker.address().cityName();
    private static String phone = faker.phoneNumber().phoneNumber();

    static String getCity() {
        return city;
    }

    static String getName() {
        return name;
    }

    static String getPhone() {
        return phone;
    }

    static String getNewDate() {
        final String FORMAT_DATE = "dd.MM.yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(FORMAT_DATE);
        LocalDate localDate = LocalDate.now();
        LocalDate newDate = localDate.plusDays(3);
        return dateFormatter.format(newDate);
    }
}
