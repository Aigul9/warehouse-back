package com.warehouse.roles;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {

    ADMIN("Администратор"),

    DEFAULT_USER("Пользователь по умолчанию"),

    SELLER("Продавец"),

    WAREHOUSEMAN("Кладовщик"),

    SUPERVISOR("Начальник");

    private final String title;
}
