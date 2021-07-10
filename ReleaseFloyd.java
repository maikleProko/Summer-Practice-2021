
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
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

    public static void run_step(Vector <Vector<Integer>> my_vec, Vector<Vector<String>> my_vecSlashText, mxGraph graph) {
        int n = my_vec.get(0).size();
        fillMatrix(my_vec);
        RecolorVertexDefault(graph);
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        MatrixAdapterFabric.copyMatrix(tmp, my_vec);
        states.add(tmp);
        //System.out.println(states);
        if(k_step<n) {
            if(i_step<n) {
                MakeStep(my_vec, my_vecSlashText, graph, n);
                ++i_step;
            }else {
                i_step = 0;
                ++k_step;
                if(k_step < n) {
                    MakeStep(my_vec, my_vecSlashText, graph, n);
                }
                ++i_step;
            }
        }
        if(k_step == n){

            JOptionPane.showMessageDialog(null,  "Algorithm is finish, check output matrix", "Promt", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println("End of algo");
        }else {
            //System.out.println("srabotalo rs");
        }
    }

    public static void run_all(Vector <Vector<Integer>> my_vec, Vector<Vector<String>> my_vecSlashText, mxGraph graph) {
        int n = my_vec.get(0).size();
        fillMatrix(my_vec);
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        MatrixAdapterFabric.copyMatrix(tmp, my_vec);
        states.add(tmp);
        for (int k=0; k<n; ++k) {
            for (int i = 0; i < n; ++i)
                for (int j = 0; j < n; ++j)
                    MakeStepAll(my_vec,my_vecSlashText,graph,i,j,k);
        }
        TableAdapterFabric.addTableUpdateFloyd(my_vec, my_vecSlashText, Main.jFieldTable, graph);
    }

    private static void MakeStep(Vector<Vector<Integer>> my_vec, Vector<Vector<String>> my_vecSlashText, mxGraph graph, int n) {
        for(int j_step=0; j_step<n; ++j_step) {
            int tmp1 = my_vec.get(i_step).get(j_step);
            int tmp2 = Math.min(my_vec.get(i_step).get(j_step), my_vec.get(i_step).get(k_step) + my_vec.get(k_step).get(j_step));
            //System.out.println("collision: " + tmp1 + " and " + tmp2);
            if(tmp1!=tmp2) {
                my_vec.get(i_step).set(j_step, tmp2);
                my_vecSlashText.get(i_step).set(j_step, TableAdapterFabric.getVecNames(graph).get(k_step));
                TableAdapterFabric.addTableUpdateFloyd(my_vec, my_vecSlashText, Main.jFieldTable, graph);
                RecolorVertex(graph, i_step, j_step, k_step);
            }
        }
    }

    private static void MakeStepAll(Vector<Vector<Integer>> my_vec, Vector<Vector<String>> my_vecSlashText, mxGraph graph ,int i_step, int j_step, int k_step) {
        int tmp1 = my_vec.get(i_step).get(j_step);
        int tmp2 = Math.min(my_vec.get(i_step).get(j_step), my_vec.get(i_step).get(k_step) + my_vec.get(k_step).get(j_step));
        if(tmp1!=tmp2) {
            my_vec.get(i_step).set(j_step, tmp2);
            my_vecSlashText.get(i_step).set(j_step, TableAdapterFabric.getVecNames(graph).get(k_step));
        }
    }

    private static void RecolorVertexDefault(mxGraph graph) {
        for (Object c : graph.getChildVertices(graph.getDefaultParent())) {
            mxCell timeCell;
            timeCell = (mxCell) c;
            timeCell.setStyle("defaultStyle");
        }
        graph.refresh();
    }

    private static void RecolorVertex(mxGraph graph, int i_step, int j_step, int k_step){

        for (Object c : graph.getChildVertices(graph.getDefaultParent())) {
            mxCell timeCell;
            int timeCellId;
            timeCell = (mxCell) c;
            StringBuilder time_string = new StringBuilder( timeCell.getId());
            time_string.deleteCharAt(time_string.length()-1);
            timeCellId = Integer.parseInt(time_string.toString());
            //System.out.println(timeCellId + ": " + i_step+ ", " + i_step+ ", " + k_step+ ", ");
            if(i_step == timeCellId) {
                //System.out.println("+");
                timeCell.setStyle("fillColor=green");
            }
            if(j_step == timeCellId) {
                //System.out.println("++");
                timeCell.setStyle("fillColor=green");
            }
            if(k_step == timeCellId) {
                //System.out.println("+++");
                timeCell.setStyle("fillColor=red");
            }

        }
        graph.refresh();
    }

    public static void algo(Vector <Vector<Integer>> my_vec) {
        int n = my_vec.get(0).size();
        fillMatrix(my_vec);
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        MatrixAdapterFabric.copyMatrix(tmp, my_vec);
        for (int k=0; k<n; ++k) {
            for (int i = 0; i < n; ++i)
                for (int j = 0; j < n; ++j)
                    my_vec.get(i).set(j, Math.min(my_vec.get(i).get(j), my_vec.get(i).get(k) + my_vec.get(k).get(j)));
        }
    }

}