
import com.mxgraph.view.mxGraph;

import java.util.*;
public class ReleaseFloyd {

    private static int k_step = 0;
    private static int i_step = 0;

    private static ArrayList<Vector<Vector<Integer>>> states = new ArrayList <Vector<Vector<Integer>>>();

    public static Vector<Vector<Integer>> getState(){
        if(states.size() != 0){
            Vector<Vector<Integer>> st = states.get(states.size() - 1);
            states.remove(states.size() - 1);
            if(k_step > 0){
                if(i_step > 0){
                    i_step--;
                }else{
                    i_step = st.size() - 1;
                    k_step--;
                }
            }
            return st;
        }
        return null;
    }

    public static void fillMatrix(Vector <Vector<Integer>> my_vec){
        int n = my_vec.size();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if(my_vec.get(i).get(j) == -1){
                    my_vec.get(i).set(j, 100000000);
                }
            }
        }
    }

    public static void run_step(Vector <Vector<Integer>> my_vec, mxGraph graph) {
        int n = my_vec.get(0).size();
        fillMatrix(my_vec);
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        MatrixAdapterFabric.copyMatrix(tmp, my_vec);
        states.add(tmp);
        //System.out.println(states);
        if(k_step<n) {
            if(i_step<n) {
                for(int j_step=0; j_step<n; ++j_step) {
                    System.out.println("collision: " + my_vec.get(i_step).get(j_step) + " and " + Math.min(my_vec.get(i_step).get(j_step), my_vec.get(i_step).get(k_step) + my_vec.get(k_step).get(j_step)));
                    if(my_vec.get(i_step).get(j_step)!=Math.min(my_vec.get(i_step).get(j_step), my_vec.get(i_step).get(k_step) + my_vec.get(k_step).get(j_step))) {
                        my_vec.get(i_step).set(j_step, Math.min(my_vec.get(i_step).get(j_step), my_vec.get(i_step).get(k_step) + my_vec.get(k_step).get(j_step)));
                        TableAdapterFabric.addTableUpdateElement(my_vec, Main.jFieldTable, graph, i_step, j_step, k_step);
                    }
                }
                ++i_step;
            }else {
                i_step = 0;
                ++k_step;
                if(k_step < n) {
                    for (int j_step = 0; j_step < n; ++j_step) {
                        System.out.println("collision: " + my_vec.get(i_step).get(j_step) + " and " + Math.min(my_vec.get(i_step).get(j_step), my_vec.get(i_step).get(k_step) + my_vec.get(k_step).get(j_step)));
                        if(my_vec.get(i_step).get(j_step)!=Math.min(my_vec.get(i_step).get(j_step), my_vec.get(i_step).get(k_step) + my_vec.get(k_step).get(j_step))) {
                            my_vec.get(i_step).set(j_step, Math.min(my_vec.get(i_step).get(j_step), my_vec.get(i_step).get(k_step) + my_vec.get(k_step).get(j_step)));
                            TableAdapterFabric.addTableUpdateElement(my_vec, Main.jFieldTable, graph, i_step, j_step, k_step);
                        }
                    }
                }
                ++i_step;
            }
        }
        if(k_step == n){
            System.out.println("End of algo");
        }else {
            System.out.println("srabotalo rs");
        }
    }

    public static void run_all(Vector <Vector<Integer>> my_vec) {
        int n = my_vec.get(0).size();
        fillMatrix(my_vec);
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        MatrixAdapterFabric.copyMatrix(tmp, my_vec);
        states.add(tmp);
        for (int k=0; k<n; ++k) {
            for (int i = 0; i < n; ++i)
                for (int j = 0; j < n; ++j)
                    my_vec.get(i).set(j, Math.min(my_vec.get(i).get(j), my_vec.get(i).get(k) + my_vec.get(k).get(j)));
        }
        System.out.println("srabotalo");
    }

}