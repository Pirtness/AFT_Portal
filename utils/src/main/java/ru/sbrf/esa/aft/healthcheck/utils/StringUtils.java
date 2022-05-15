package ru.sbrf.esa.aft.healthcheck.utils;

import java.util.Map;

public class StringUtils {
    /**
     * Реализация подстановки в шаблонах, где параметры находятся в фигурных скобках
     * Выполняется замена всех найденных параметров из карты на значение
     * <br/>
     * Пример: есть строка "Привет {your_name}, меня зовут {my_name}. Пока {your_name}"
     * если передать map вида: your_name: Вася; my_name: Коля
     * <br/>
     * на выходе будет "Привет Вася, меня зовут Коля. Пока Вася"
     *
     * @param template входящий шаблон
     * @param params   карта параметров и значений
     * @return форматированная строка
     */
    public static String formatEx(final String template, Map<String, String> params) {
        String result = template.intern();
        for (Map.Entry<String, String> paramEntry : params.entrySet()) {
            result = result.replaceAll("\\{" + paramEntry.getKey() + "}", paramEntry.getValue());
        }
        return result;
    }

    /**
     * Реализация подстановки в шаблонах, где параметры находятся в фигурных скобках
     * Выполняется последовательная замена параметров в шаблоне обернутых в фигурные скобки
     * Ограничение: плохо будет если в тексте окажется что-то нужное с фигурными скобками
     * <br/>
     * Пример: есть строка "Привет {your_name}, меня зовут {my_name}. Пока {your_name}"
     * если передать параметры:  Вася, Коля
     * <br/>
     * на выходе будет "Привет Вася, меня зовут Коля. Пока {your_name}"
     * <br/>
     * т.е. пришло  два параметра их и меняем по порядку не зависимо от того что есть в этих
     * квадратных скобка (можно совсем ничего не писать)
     *
     * @param template входящий шаблон
     * @param params   карта параметров и значений
     * @return форматированная строка
     */
    public static String formatEx(final String template, String... params) {
        String result = template.intern();
        for (String param : params) {
            result = result.replaceFirst("\\{\\w*}", param);
        }
        return result;
    }

    public static String formatEx(final String template, Object... params) {
        String result = template.intern();
        for (Object param : params) {
            result = result.replaceFirst("\\{\\w*}", param.toString());
        }
        return result;
    }

}
