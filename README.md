# WhoUseMyAnnotation

So, who ~~steal~~ use my annotation?

## Use

`Define a annotation`

```java

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // RUNTIME / CLASS ,don't use SOURCE!
public @interface Scala {
    boolean better() default true;
}

```

and use it in any class

```java

@Scala(better = true)
public class Lang {
    /*code*/
}


```

now ,you can use `this` to get all class which use your annotation `Scala`

```java

public class Main {
    public static void main(String[] args) {
        ProjectDirectoryClasses.getAllClassesUseSpecialAnnotation(Scala.class).forEach(System.out::println);
        // print xxx.xx.Lang
    }
}

```