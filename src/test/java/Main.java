import com.who.use.ProjectDirectoryClasses;
import anno.Test;

@Test
public class Main {
    public static void main(String[] args) {
        ProjectDirectoryClasses.getAllClassesUseSpecialAnnotation(Test.class).forEach(System.out::println);
    }


}
