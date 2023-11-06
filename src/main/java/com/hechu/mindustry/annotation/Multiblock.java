package com.hechu.mindustry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Multiblock {
    int sizeX() default 1;
    int sizeY() default 1;
    int sizeZ() default 1;

    boolean useSingleTexture() default false;
}
