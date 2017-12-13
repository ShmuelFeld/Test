import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Sharon on 08 דצמבר 2017.
 */
public class AStarAlgorithm implements IAlgorithm {
    private Matrix mat;
    private PriorityQueue<Node> openList;
    private Node root;

    public AStarAlgorithm(Matrix mat) {
        System.out.println("AStarAlgorithm");
        this.mat = mat;
        this.openList = new PriorityQueue<>();
    }

    @Override
    public String findPath(Node root, Node goal, Matrix matrix) {
        this.root = root;
        root.setG_score(0);
        this.openList.add(root);
        root.calculateEstimatedValue(goal);
        while (!this.openList.isEmpty()) {
            Node current = this.openList.poll();
            if(current.equals(goal)) {
                return this.toPath(current);
            }
            for (Node node: matrix.descendant(current)) {
                int cos = current.getG_score() + node.getCost();
//                if (cos >= node.getG_score()) {
//                    continue;
//                }
                if ((!this.openList.contains(node)) || cos < node.getG_score()) {
                    node.setFather(current);
                    node.setG_score(cos);
                    node.setF_score(node.getG_score() + node.heuristic(goal));
                    if (!this.openList.contains(node)) {
                        this.openList.add(node);
                    }
                }
            }


        }
        return null;
    }

    @Override
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
}
