package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetween;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

//        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        for (UserMealWithExceed usermeal : list) {
//            System.out.println(usermeal);
//        }

        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)
                .forEach(System.out::println);

        //       .toLocalDate();
        //        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> mealWithExceeds = new ArrayList<>();

        Map<LocalDate, Integer> dayCalories = new HashMap<>();

        for (UserMeal userMeal : mealList) {

            if (dayCalories.containsKey(userMeal.getDateTime().toLocalDate())) {
                dayCalories.put(userMeal.getDateTime().toLocalDate(),
                        dayCalories.get(userMeal.getDateTime().toLocalDate()) + userMeal.getCalories());
            } else {
                dayCalories.put(userMeal.getDateTime().toLocalDate(), userMeal.getCalories());
            }
        }

//        for (UserMeal userMeal: mealList) {
//            if (isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
//                mealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(),
//                        userMeal.getDescription(), userMeal.getCalories(), caloriesPerDay>=dayCalories.get(userMeal.getDateTime().toLocalDate())));
//        }
////          }

        mealWithExceeds.addAll(mealList.stream().filter(userMeal -> isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(),
                userMeal.getDescription(), userMeal.getCalories(),
                        caloriesPerDay >= dayCalories.get(userMeal.getDateTime().toLocalDate())))
                .collect(Collectors.toList()));


        return mealWithExceeds;
    }

}