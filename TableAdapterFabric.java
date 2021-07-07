import java.util.Vector;
import javax.swing.*;

public class TableAdapterFabric {

    public static void addTableUpdate(Vector<Vector<Integer>> my_vec, JTextArea my_jta) {
        my_jta.setText(null);
        for(int i = 0; i < my_vec.size(); i++) {
            for(int j = 0; j < my_vec.size(); j++) {
                if((my_vec.get(i).get(j) == 100000000)||(my_vec.get(i).get(j) == -1))
                    my_jta.append("N ");
                else
                    my_jta.append(my_vec.get(i).get(j).toString() + " ");
            }
            my_jta.append("\n");
        }
    }
}