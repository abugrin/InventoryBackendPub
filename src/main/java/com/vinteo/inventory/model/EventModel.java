package com.vinteo.inventory.model;

public class EventModel {
    public static final int ACTION_ADD = 1;
    public static final int ACTION_CHANGE = 2;
    public static final int ACTION_MOVE = 3;

    public static final int OBJECT_GENERIC = 0;
    public static final int OBJECT_USER = 1;
    public static final int OBJECT_DEVICE = 2;
    public static final int OBJECT_PART = 3;
    public static final int OBJECT_LOCATION = 4;

    public static final String EVENT_MOVE = "Перемещено\n";
    public static final String EVENT_CHANGE = "Изменено\n";
    public static final String EVENT_ADD = "Добавлено\n";
}
