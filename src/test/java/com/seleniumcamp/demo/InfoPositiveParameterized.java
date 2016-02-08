package com.seleniumcamp.demo;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Stories;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by evgeniyat on 07.01.16
 */
@RunWith(Parameterized.class)
public class InfoPositiveParameterized {
    private static final String endpoint = "http://somesite.com/info";
    @Parameter("Target")
    @Parameterized.Parameter
    public Target target;
    @Parameter("Category")
    @Parameterized.Parameter(1)
    public Category category;

    @Parameterized.Parameters(name = "{0} {1}")
    public static List<Object[]> data() {
        return Arrays.asList(Target.values()).stream().map(target ->
                Arrays.asList(Category.values()).stream().map(category ->
                        new Object[]{target, category}).collect(Collectors.toList()))
                .flatMap(Collection::stream).collect(Collectors.toList());

    }

    @Stories("DEMO")
    @Features("P")
    @Test
    public void get() {
        given().param("target", target)
                .param("category", category)
                .expect().statusCode(Matchers.equalTo(200))
                .when().get(endpoint);
    }
}
