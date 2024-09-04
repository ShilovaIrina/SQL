package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static final Faker FAKER = new Faker(new Locale("en"));

    private DataHelper() {
    }


    public static AutoInfo getAuthInfo() {
        return new AutoInfo("vasya", "qwerty123");
    }

    private static String generateRandomLogin() {
        return FAKER.name().username();
    }

    private static String generateRandomPassword() {
        return FAKER.internet().password();
    }

    public static AutoInfo generateRandomUser() {
        return new AutoInfo(generateRandomLogin(), generateRandomPassword());
    }

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(FAKER.numerify("######"));
    }

    @Value
    public static class AutoInfo {
        String login;
        String password;
    }

    @Data // аннотация для создания сеттеров
    @NoArgsConstructor //аннотация для метода "Конструктор по умолчанию"
    @AllArgsConstructor // аннотация для работы метода  User с его конструкторами
    public static class VerificationCode {
        String code;
    }
}
