import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import java.util.Vector;
import javax.swing.*;

public class TableAdapterFabric {
    public static Vector<String> getVecNames(mxGraph my_g) {
        Vector<String> out_vec = new Vector<String>();
        mxCell timeCell;
        for (Object c : my_g.getChildVertices(my_g.getDefaultParent())) {
            timeCell = (mxCell) c;
            String time_string = (String) my_g.getModel().getValue(timeCell);
            //System.out.println(time_string);
            out_vec.addElement(time_string);
        }
        System.out.println(out_vec);
        return out_vec;
    }

    public static int getMaxLengthElement(Vector<Vector<Integer>> my_vec) {
        Integer max = 0;
        for(int i = 0; i < my_vec.size(); i++) {
            for (int j = 0; j < my_vec.size(); j++) {
                if((my_vec.get(i).get(j) < 100000000)&&(my_vec.get(i).get(j) > max))
                    max = my_vec.get(i).get(j);
            }
        }
        String time_string = max.toString();
        return time_string.length();
    }

    public static int getMaxLengthElementString(Vector<String> my_str_vec, int bool_int) {
        int out_int = bool_int;
        for(int i = 0; i < my_str_vec.size(); i++) {
            if(my_str_vec.get(i).length()>bool_int) {
                out_int = my_str_vec.get(i).length();
            }
        }
        return out_int;
    }

    public static int getMaxLengthElementAlgorithm(Vector<Vector<Integer>> my_vec, Vector<String> my_str_vec, int bool_int, int i, int j, int k) {
        int out_int = bool_int;
        String time_string = my_vec.get(i).get(j).toString();
        String time_string2 = my_str_vec.get(k);
        int time_int = time_string.length() + time_string2.length() + 1;
        if(out_int < time_int)
            out_int = time_int;
        return out_int;
    }

    public static void addTableElement(JTextArea my_jta, String my_str, int l) {
        int insert_l = my_str.length();
        my_jta.append(my_str);
        for(; insert_l<l; insert_l++) {
            my_jta.append("  ");
        }
        my_jta.append("  ");
    }

    public static void addTableUpdate(Vector<Vector<Integer>> my_vec, JTextArea my_jta, mxGraph my_g) {
        my_jta.setText(null);
        int l_loc = getMaxLengthElement(my_vec);
        Vector<String> str_vec = getVecNames(my_g);
        l_loc = getMaxLengthElementString(str_vec, l_loc);
        addTableElement(my_jta, "", l_loc);
        for(int i = 0; i < my_vec.size(); i++) {
            addTableElement(my_jta, str_vec.get(i), l_loc);
        }
        my_jta.append("\n");
        for(int i = 0; i < my_vec.size(); i++) {
            addTableElement(my_jta, str_vec.get(i), l_loc);
            for(int j = 0; j < my_vec.size(); j++) {
                if((my_vec.get(i).get(j) == 100000000)||(my_vec.get(i).get(j) == -1))
                    addTableElement(my_jta, "N", l_loc);
                else
                    addTableElement(my_jta, my_vec.get(i).get(j).toString(), l_loc);
                    //my_jta.append(my_vec.get(i).get(j).toString() + " ");
            }
            my_jta.append("\n");
        }
    }
    public static void addTableUpdateElement(Vector<Vector<Integer>> my_vec, JTextArea my_jta, mxGraph my_g, int i_loc, int j_loc, int k_loc) {
        my_jta.setText(null);
        int l_loc = getMaxLengthElement(my_vec);
        Vector<String> str_vec = getVecNames(my_g);
        l_loc = getMaxLengthElementString(str_vec, l_loc);
        l_loc = getMaxLengthElementAlgorithm(my_vec, str_vec, l_loc, i_loc, j_loc, k_loc);
        addTableElement(my_jta, "", l_loc);
        for(int i = 0; i < my_vec.size(); i++) {
            addTableElement(my_jta, str_vec.get(i), l_loc);
        }
        my_jta.append("\n");
        for(int i = 0; i < my_vec.size(); i++) {
            addTableElement(my_jta, str_vec.get(i), l_loc);
            for(int j = 0; j < my_vec.size(); j++) {
                if((my_vec.get(i).get(j) == 100000000)||(my_vec.get(i).get(j) == -1))
                    addTableElement(my_jta, "N", l_loc);
                else {
                    if((i_loc!=i)||(j_loc!=j))
                        addTableElement(my_jta, my_vec.get(i).get(j).toString(), l_loc);
                    else {
                        addTableElement(my_jta, my_vec.get(i).get(j).toString()+"/"+str_vec.get(k_loc), l_loc);
                    }
                }
                //my_jta.append(my_vec.get(i).get(j).toString() + " ");
            }
            my_jta.append("\n");
        }
    }
}
