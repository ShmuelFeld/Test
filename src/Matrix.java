import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sharon on 10 דצמבר 2017.
 */
public class Matrix {
    private String state;
    private int size;
    private char[][] mat;
    private Node root;
    private Node goal;
    private int creationTime;

    public int getSize() {
        return this.size;
    }

    public Node getRoot() {
        return this.root;
    }

    public Node getGoal() {
        return this.goal;
    }

    public Matrix(String state, int size) {
        this.state = state;
        this.size = size;
        this.creationTime = 0;
        this.setBoard(this.state.toCharArray(), this.size);
        this.printBoard();
        this.setRootAndGoal();
    }

    private void printBoard() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                System.out.print(this.valueAt(i,j));
            }
            System.out.print("\n");
        }
    }

    public void setBoard(char[] state, int size) {
        this.mat = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.mat[i][j] = state[(size * i) + j];
            }
        }
    }
    public void setRootAndGoal() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.mat[i][j] == 'S') {
                    this.root = new Node(new Point(i,j), this, 'S', this.creationTime);
                    this.creationTime++;
                    this.root.setFather(null);
                }else if (this.mat[i][j] == 'G') {
                    this.goal = new Node(new Point(i,j), this, 'G', this.creationTime);
                    this.creationTime++;
                }
            }

        }
    }
    private char valueAt(Node n) {
        return this.mat[n.getLocation().getX()][n.getLocation().getY()];
    }
    private char valueAt(Point p) {
        return this.mat[p.getY()][p.getX()];
    }
    private char valueAt(int x, int y) {
        return this.mat[x][y];
    }


    private Node RSon(Node ancestor) {
        int x = ancestor.getLocation().getX() + 1;
        int y = ancestor.getLocation().getY();
        if (validNode(x, y, ancestor)) {
            return nodeCreator(x, y);
        }else
            return null;
    }
    private Node RDSon(Node ancestor) {
        int x = ancestor.getLocation().getX() + 1;
        int y = ancestor.getLocation().getY() + 1;
        if (validNode(x, y, ancestor) && this.siblingIsWater(x, y - 1)
                && this.siblingIsWater(x - 1, y)) {
            return nodeCreator(x, y);
        }else
            return null;
    }
    private Node DSon(Node ancestor) {
        int x = ancestor.getLocation().getX();
        int y = ancestor.getLocation().getY() + 1;
        if (validNode(x, y, ancestor)) {
            return nodeCreator(x, y);
        }else
            return null;
    }
    private Node LDSon(Node ancestor) {
        int x = ancestor.getLocation().getX() - 1;
        int y = ancestor.getLocation().getY() + 1;
        if (validNode(x, y, ancestor) && this.siblingIsWater(x, y - 1)
                && this.siblingIsWater(x + 1, y)) {
            return nodeCreator(x, y);
        }else
            return null;
    }
    private Node LSon(Node ancestor) {
        int x = ancestor.getLocation().getX() - 1;
        int y = ancestor.getLocation().getY();
//        Point p = new Point(ancestor.getLocation().getX() - 1, ancestor.getLocation().getY());
        if (validNode(x, y, ancestor)) {
            return nodeCreator(x, y);
        }else
            return null;
    }
    private Node LUSon(Node ancestor) {
        int x = ancestor.getLocation().getX() - 1;
        int y = ancestor.getLocation().getY() - 1;
//        Point p = new Point(ancestor.getLocation().getX() - 1, ancestor.getLocation().getY() - 1);
        if (validNode(x, y, ancestor) && this.siblingIsWater(x, y + 1)
                && this.siblingIsWater(x + 1, y)) {
            return nodeCreator(x, y);
        }else
            return null;
    }
    private Node USon(Node ancestor) {
        int x = ancestor.getLocation().getX();
        int y = ancestor.getLocation().getY() - 1;
//        Point p = new Point(ancestor.getLocation().getX(), ancestor.getLocation().getY() - 1);
        if (validNode(x, y, ancestor)) {
            return nodeCreator(x, y);
        }else
            return null;
    }
    private Node RUSon(Node ancestor) {
        int x = ancestor.getLocation().getX() + 1;
        int y = ancestor.getLocation().getY() - 1;
//        Point p = new Point(ancestor.getLocation().getX() + 1, ancestor.getLocation().getY() - 1);
        if (validNode(x, y, ancestor) && this.siblingIsWater(x, y + 1)
                && this.siblingIsWater(x - 1, y)) {
            return nodeCreator(x, y);
        }else
            return null;
    }
    private Node nodeCreator(int x, int y) {
        Node n = new Node(new Point(x, y), this, this.valueAt(x, y), this.creationTime);
        this.creationTime++;
        return n;
    }
//    private boolean validNode(Point p) {
//        if (p.getX() < 0 || p.getX() >= this.size || p.getY() < 0 || p.getY() >= this.size || p.equals(this.root.getLocation())) {
//            return false;
//        }
//        else if (valueAt(p) == 'W') {
//            return false;
//        }
//        return true;
//    }
    private boolean validNode(int x, int y, Node father) {
        if (x < 0 || x >= this.size || y < 0 || y >= this.size
                || (this.root.getLocation().getX() == x && this.getRoot().getLocation().getY() == y)) {
            return false;
        }
        else if (valueAt(x,y) == 'W') {
            return false;
        }
        else if (father.getLocation().getY() == y && father.getLocation().getX() == x) {
            return false;
        }
        return true;
    }
    private boolean siblingIsWater(int x, int y) {
        return (this.valueAt(y, x) != 'W');
    }
    public List<Node> descendant(Node n){
        ArrayList<Node> descendant = new ArrayList<Node>();
        Node r = this.RSon(n);
        if (r != null) {
            descendant.add(r);
            r.setFather(n);
        }
        Node rd = this.RDSon(n);
        if (rd != null) {
            descendant.add(rd);
            rd.setFather(n);
        }
        Node d = this.DSon(n);
        if (d != null) {
            descendant.add(d);
            d.setFather(n);
        }
        Node ld = this.LDSon(n);
        if (ld != null) {
            descendant.add(ld);
            ld.setFather(n);
        }
        Node l = this.LSon(n);
        if (l != null) {
            descendant.add(l);
            l.setFather(n);
        }
        Node lu = this.LUSon(n);
        if (lu != null) {
            descendant.add(lu);
            lu.setFather(n);
        }
        Node u = this.USon(n);
        if (u != null) {
            descendant.add(u);
            u.setFather(n);
        }
        Node ru = this.RUSon(n);
        if (ru != null) {
            descendant.add(ru);
            ru.setFather(n);
        }
        return descendant;
    }

}
