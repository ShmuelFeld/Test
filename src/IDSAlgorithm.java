import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sharon on 08 דצמבר 2017.
 */
public class IDSAlgorithm implements IAlgorithm {
    private String found;
    private Node root;
    private Node goal;
    private Node wanted;
    private Matrix matrix;
    private HashSet<Node> set;
    IDSAlgorithm(){
    }
    @Override
    public String findPath(Node root, Node goal, Matrix matrix) {
        this.matrix = matrix;
        this.goal = goal;
        this.root = root;

        int lim = (int)Math.pow(this.matrix.getSize(), 2);
        for (int i = 0; i < lim; i++) {
            this.set = new HashSet<>();
            System.out.println(i);
            this.set.add(this.root);
            Node n = ids(this.root, i);
            found = toPath(n);
            if (found != null)
                return found;
            this.set.clear();
        }
        return "No Path!";
    }
    public String toPath(Node node) {
        if (node != null) {
            List<Node> linkedList = new LinkedList<Node>();
            linkedList.add(node);
            node = node.getFather();
            while (!node.equals(this.root)) {
                linkedList.add(node);
                node = node.getFather();
            }
            linkedList.add(node);
            StringBuilder sb = new StringBuilder();
            int cost = 0;
            for (Node n: linkedList) {
                cost += n.getCost();
                try {
                    sb.append(n.fromFatherToMe());
                    sb.append("-");
                }catch (Exception e) {
                    //DoNothing
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
            sb = sb.reverse();
            sb.append(" ");
            sb.append(cost);
            return sb.toString();
        }
        return null;
    }

    private Node ids(Node node, int depth) {
        if (depth == 0 && node.equals(this.goal)) {
            return node;
        }
        else if (depth > 0) {
            List<Node> li = node.myDescendants();
            for (Node n : li) {
                if(!this.set.contains(n)) {
                    this.wanted = this.ids(n, --depth);
                    this.set.add(n);
                    depth++;
                }
                if (this.wanted != null) {
                    return this.wanted;
                }
            }
        }
        else
            return null;
        return null;
    }
}
