import anno.Test;
import com.who.use.ProjectDirectoryClasses;

public class Main {
    public static void main(String[] args) {
        ProjectDirectoryClasses.getAllClassesUseSpecialAnnotation(Test.class).forEach(System.out::println);
    }
}
