package ru.netology.web.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLHelper;
import ru.netology.web.page.LoginPage;


import static ru.netology.web.data.SQLHelper.cleanAutoCodes;
import static ru.netology.web.data.SQLHelper.cleanDatabase;


public class BankLoginTests {
    LoginPage LoginPage;

    @AfterEach
    void tearDown() {
        cleanAutoCodes();
    }

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @BeforeEach
    void setUp() {
        LoginPage = Selenide.open("http://localhost:9999", LoginPage.class);
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationWithRandomUser() {
        var autoInfo = DataHelper.generateRandomUser();
        LoginPage.validLogin(autoInfo);
        LoginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("Should get error if login with exist in base and active user and random verification code")
    void shouldGetErrorMessage() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }

}
