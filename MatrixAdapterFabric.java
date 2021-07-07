import java.util.Vector;

public class MatrixAdapterFabric {

    public static void addVertexMatrix(Vector<Vector<Integer>> my_vec) {
        if(my_vec.size() == 0) {
            my_vec.addElement(new Vector<Integer>());
            my_vec.get(0).addElement(new Integer(0));
        }
        else {
            my_vec.addElement(new Vector<Integer>());
            int i = 0;
            for(; i < my_vec.size()-1; i++) {
                my_vec.get(i).addElement(new Integer(-1));
            }
            for(int j = 0; j < my_vec.size(); j++) {
                if(i!=j)
                    my_vec.get(i).addElement(new Integer(-1));
                else
                    my_vec.get(i).addElement(new Integer(0));
            }

        }

    }

    public static void addEdgeMatrix(Vector<Vector<Integer>> my_vec, int i, int j, int value) {
        my_vec.get(i).set(j, value);
    }

    public static void deleteVertexMatrix(Vector<Vector<Integer>> my_vec, int number) {
        int i = 0;
        for(; i < number; i++) {
            my_vec.get(i).remove(number);
        }
        my_vec.remove(number);
        int loc_size = my_vec.size() - 1;
        for(; i < loc_size; i++) {
            my_vec.get(i).remove(number);
        }
    }

    public static void copyMatrix(Vector <Vector<Integer>> vec1, Vector <Vector<Integer>> vec2){
        for(int i = 0; i < vec2.size(); i++){
            vec1.addElement(new Vector<Integer>());
            for(int j = 0; j < vec2.size(); j++){
                //vec1.addElement(new Vector<Integer>());
                vec1.get(i).addElement(vec2.get(i).get(j));
                //vec1.get(i).set()
            }
        }
    }
}