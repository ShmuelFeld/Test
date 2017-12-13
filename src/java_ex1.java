import javax.print.attribute.standard.MediaSize;
import java.util.function.Function;

public class java_ex1 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        NavigationProgram np = new NavigationProgram("./Test.txt");
        np.openInputFile();
        System.out.println(np.findPath());
    }
}
