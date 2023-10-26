package net.felix.ratsmod.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum RatVariant {
    DEFAULT(0),

    ALBINO(1),

    GREY(2),

    HUSKY(3),

    LIGHT_BROWN(4);

    private static final RatVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(RatVariant::getId)).toArray(RatVariant[]::new);
    private final int id;

    RatVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static RatVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
