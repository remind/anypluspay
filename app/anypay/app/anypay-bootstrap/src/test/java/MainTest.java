import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wxj
 * 2026/2/5
 */
public class MainTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println( next);
            if (next == 2) {
                break;
            }
            if (!iterator.hasNext()) {
                System.out.println("while end");
            }
        }
        System.out.println("end");
    }
}
