import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sharon on 08 דצמבר 2017.
 */
public class NavigationProgram {
    private IAlgorithm theAlgorithm;
    private String path;
    private FileReader file;
    private BufferedReader reader;
    private int boardSize;
    private List<String> list;
    private String boardState;
    private String algoKind;
    private InputStream targetStream;
    private Matrix matrix;
    private int size;
    private Node root;
    private Node goal;

    NavigationProgram(String pathToinputFile) {
        this.path = pathToinputFile;
    }
    public boolean openInputFile() {
        try {
            this.file = new FileReader(this.path);
        }catch (Exception e) {
            System.out.println("file not found");
            return false;
        }
        return true;
    }
    private void readFromFile() throws IOException {
        this.list = new ArrayList<String>();
        this.reader = new BufferedReader(this.file);
        String st = this.reader.readLine();
        while (st != null) {
            this.list.add(st);
            st = this.reader.readLine();
        }
    }
    public String findPath() {
        try {
            this.readFromFile();
        } catch (Exception e) {
            System.out.println("file not found");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < this.list.size(); i++) {
            sb.append(this.list.get(i));
        }
        System.out.println(sb.toString());
        this.algorithmChooser(this.list.get(0));
        this.size = Integer.parseInt(this.list.get(1));
        this.matrix = new Matrix(sb.toString(), this.size);
        return this.theAlgorithm.findPath(this.matrix.getRoot(), this.matrix.getGoal(), this.matrix);
    }
    private void algorithmChooser(String algoKind) {
        if (algoKind.contentEquals("IDS")) {
            this.theAlgorithm = new IDSAlgorithm();
        } else {
            this.theAlgorithm = new AStarAlgorithm(this.matrix);
        }
    }
}
