import anno.CCC;
import com.who.use.ProjectDirectoryClasses;

public class Main {
    public static void main(String[] args) {
        ProjectDirectoryClasses
                .getAllClassesUseSpecialAnnotation(CCC.class)
                .forEach(System.out::println);
    }
}
