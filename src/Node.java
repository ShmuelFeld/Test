import java.util.Comparator;
import java.util.List;

/**
 * Created by Sharon on 08 דצמבר 2017.
 */
public class Node implements Comparator, Comparable {
    private Node father;
    private int cost;
    private char kind;
    private Point location;
    private List<Node> descendant;
    private Matrix matrix;
    private int creationTime;
    private int g_score;
    private int f_score;

    public int getF_score() {
        return f_score;
    }
    public void setF_score(int f_score) {
       this.f_score = f_score;
    }
    public int calculateEstimatedValue(Node goal) {
        this.f_score = this.heuristic(goal) + this.getG_score();
        return this.f_score;
    }

    public int heuristic(Node goal) {
        return Math.max(Math.abs(this.getLocation().getX() - goal.getLocation().getX())
                , Math.abs(this.getLocation().getY() - goal.getLocation().getY()));
    }
    public int getG_score() {
        return this.g_score;
    }
    public void setCostToMe() {
        this.g_score = this.getFather().g_score + this.getCost();
    }
    public void setG_score(int cost) {
        this.g_score = cost;
    }

    public void setCreationTime(int creationTime) {
        this.creationTime = creationTime;
    }

    public int getCreationTime() {

        return creationTime;
    }



    public Node(Point location, Matrix matrix, char kind, int creationTime) {
        this.location = location;
        this.matrix = matrix;
        this.kind = kind;
        this.creationTime = creationTime;
    }

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public int getCost() {
        switch (this.getKind()) {
            case 'R':
                return 1;
            case 'H':
                return 10;
            case 'D':
                return 3;
            default:
                return 0;

        }
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public char getKind() {
        return kind;
    }

    public void setKind(char kind) {
        this.kind = kind;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public int compare(Object o1, Object o2) {
        Node n1 = (Node)o1;
        Node n2 = (Node)o2;
        int f = n1.getF_score() - n2.getF_score();
        if (f != 0) {
            return f;
        } else {
            return n2.getCreationTime() - n1.getCreationTime();
        }
    }

    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(obj instanceof Node)) {
            return false;
        }
        Node n = (Node) obj;
        return this.location.equals(n.getLocation());
    }
    public List<Node> myDescendants() {
        return this.matrix.descendant(this);
    }
    public String fromFatherToMe() {
        if (this.getLocation().getX() - 1 == this.getFather().getLocation().getX()
                && this.getLocation().getY() == this.getFather().getLocation().getY()) {
            return "D";
        }
        if (this.getLocation().getX() - 1 == this.getFather().getLocation().getX()
                && this.getLocation().getY() - 1 == this.getFather().getLocation().getY()) {
            return "DR";
        }
        if (this.getLocation().getX() == this.getFather().getLocation().getX()
                && this.getLocation().getY() - 1 == this.getFather().getLocation().getY()) {
            return "R";
        }
        if (this.getLocation().getX() + 1 == this.getFather().getLocation().getX()
                && this.getLocation().getY() - 1 == this.getFather().getLocation().getY()) {
            return "DL";
        }
        if (this.getLocation().getX() + 1 == this.getFather().getLocation().getX()
                && this.getLocation().getY() == this.getFather().getLocation().getY()) {
            return "L";
        }
        if (this.getLocation().getX() + 1 == this.getFather().getLocation().getX()
                && this.getLocation().getY() + 1 == this.getFather().getLocation().getY()) {
            return "UL";
        }
        if (this.getLocation().getX() == this.getFather().getLocation().getX()
                && this.getLocation().getY() + 1 == this.getFather().getLocation().getY()) {
            return "U";
        }
        if (this.getLocation().getX() - 1 == this.getFather().getLocation().getX()
                && this.getLocation().getY() + 1 == this.getFather().getLocation().getY()) {
            return "UR";
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(this.location.getX());
        sb.append(",");
        sb.append(this.location.getY());
        sb.append(") = ");
        sb.append(this.getKind());
        sb.append("My hurisuc dist is-");
        sb.append(this.getF_score());
        sb.append(" and my creation time is - ");
        sb.append(this.getCreationTime());
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        return this.compare(this, o);
    }
}
