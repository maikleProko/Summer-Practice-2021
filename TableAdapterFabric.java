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
        // System.out.println(out_vec);
        return out_vec;
    }



//Для названий вершин

    public static int getMaxLengthElementString(Vector<String> my_str_vec, int bool_int) {
        int out_int = bool_int;
        for(int i = 0; i < my_str_vec.size(); i++) {
            if(my_str_vec.get(i).length()>bool_int) {
                out_int = my_str_vec.get(i).length();
            }
        }
        return out_int;
    }

//Для элементов

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

    public static int getMaxLengthElementFloyd(Vector<Vector<Integer>> my_vec, Vector<Vector<String>> my_vecSlashText) {
        Integer max = 0;
        for(int i = 0; i < my_vec.size(); i++) {
            for (int j = 0; j < my_vec.size(); j++) {
                int my_vec_l = my_vec.get(i).get(j).toString().length();
                int my_vecSlashText_l = my_vecSlashText.get(i).get(j).length();
                int tmp = my_vec_l + my_vecSlashText_l + 1;
                if(tmp > max)
                    max = tmp;
            }
        }
        String time_string = max.toString();
        return time_string.length();
    }

//Для обновления таблицы

    public static void addTableUpdate(Vector<Vector<Integer>> my_vec, JTextArea my_jta, mxGraph my_g) {
        my_jta.setText(null);
        Vector<String> str_vec = getVecNames(my_g);
        int l_loc;

        l_loc = getMaxLengthElement(my_vec);
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

    public static void addTableUpdateFloyd(Vector<Vector<Integer>> my_vec, Vector<Vector<String>> my_vecSlashText, JTextArea my_jta, mxGraph my_g) {
        my_jta.setText(null);
        Vector<String> str_vec = getVecNames(my_g);
        int l_loc;

        l_loc = getMaxLengthElementFloyd(my_vec, my_vecSlashText);
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
                else {
                    if (my_vecSlashText.get(i).get(j).length() > 0) {
                        addTableElement(my_jta, my_vec.get(i).get(j).toString() + "/" + my_vecSlashText.get(i).get(j), l_loc);
                    }
                    else {
                        addTableElement(my_jta, my_vec.get(i).get(j).toString(), l_loc);
                    }
                }
            }
            my_jta.append("\n");
        }
    }

//Для печати

    public static void addTableElement(JTextArea my_jta, String my_str, int l) {
        int insert_l = my_str.length();
        my_jta.append(my_str);
        for(; insert_l<l; insert_l++) {
            my_jta.append("  ");
        }
        my_jta.append("  ");
    }

//Для Флойда

    public static void resetElementTableUpdateFloyd(Vector<Vector<String>> my_vec, String vertexName, int i, int j) {
        my_vec.get(i).set(j, vertexName);
    }

}