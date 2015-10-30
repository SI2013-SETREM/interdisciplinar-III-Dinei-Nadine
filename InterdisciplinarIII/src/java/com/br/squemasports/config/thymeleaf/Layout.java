
package com.br.squemasports.config.thymeleaf;

import java.lang.annotation.*;

/**
 * From http://blog.codeleak.pl/2013/11/thymeleaf-template-layouts-in-spring.html
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Layout {
    String value() default "";
}
