import com.mxgraph.view.mxGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.Vector;

public class ReleaseFloydTester {

    @Test
    public void fillMatrixTest1(){
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        for(int i = 0; i < 5; i++){
            tmp.addElement(new Vector<Integer>());
            for(int j = 0; j < 5; j++){
                tmp.get(i).addElement(new Integer(2));
            }
        }
        tmp.get(0).set(0, -1);
        tmp.get(1).set(1, -1);
        tmp.get(2).set(2, -1);
        tmp.get(3).set(3, -1);
        tmp.get(4).set(4, -1);

        ReleaseFloyd.fillMatrix(tmp);

        int num =0;

        for(int i = 0; i < 5; i++){
            num += tmp.get(i).get(i);
        }

        Assert.assertEquals(500000000, num);
    }

    @Test
    public void runAllTest1(){
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        for(int i = 0; i < 3; i++){
            tmp.addElement(new Vector<Integer>());
            for(int j = 0; j < 3; j++){
                tmp.get(i).addElement(new Integer(0));
            }
        }
        tmp.get(0).set(1, 2);
        tmp.get(0).set(2, 10);
        tmp.get(1).set(0, -1);
        tmp.get(1).set(2, 3);
        tmp.get(2).set(0, -1);
        tmp.get(2).set(1, -1);

        ReleaseFloyd.algo(tmp);

        int num = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                num += tmp.get(i).get(j);
            }
        }
        Assert.assertEquals(300000010, num);
    }

    @Test
    public void runAllTest2(){
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        for(int i = 0; i < 4; i++){
            tmp.addElement(new Vector<Integer>());
            for(int j = 0; j < 4; j++){
                tmp.get(i).addElement(new Integer(0));
            }
        }
        tmp.get(0).set(1, 2);
        tmp.get(0).set(2, 3);
        tmp.get(0).set(3, 5);
        tmp.get(1).set(0, 1);
        tmp.get(1).set(2, 10);
        tmp.get(1).set(3, 1);
        tmp.get(2).set(0, -1);
        tmp.get(2).set(1, -1);
        tmp.get(2).set(3, 2);
        tmp.get(3).set(0, -1);
        tmp.get(3).set(1, -1);
        tmp.get(3).set(2, 3);
        ReleaseFloyd.algo(tmp);
        int num = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                num += tmp.get(i).get(j);
            }
        }
        Assert.assertEquals(400000019, num);
    }

    @Test
    public void runAllTest3(){
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        for(int i = 0; i < 5; i++){
            tmp.addElement(new Vector<Integer>());
            for(int j = 0; j < 5; j++){
                tmp.get(i).addElement(new Integer(0));
            }
        }
        tmp.get(0).set(1, 3);
        tmp.get(0).set(2, 5);
        tmp.get(0).set(3, -1);
        tmp.get(0).set(4, 1);
        tmp.get(1).set(0, -1);
        tmp.get(1).set(2, -1);
        tmp.get(1).set(3, 3);
        tmp.get(1).set(4, -1);
        tmp.get(2).set(0, -1);
        tmp.get(2).set(1, -1);
        tmp.get(2).set(3, 1);
        tmp.get(2).set(4, -1);
        tmp.get(3).set(0, -1);
        tmp.get(3).set(1, -1);
        tmp.get(3).set(2, -1);
        tmp.get(3).set(4, -1);
        tmp.get(4).set(0, -1);
        tmp.get(4).set(1, 1);
        tmp.get(4).set(2, 1);
        tmp.get(4).set(3, -1);
        ReleaseFloyd.algo(tmp);
        int num = 0;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                num += tmp.get(i).get(j);
            }
        }
        Assert.assertEquals(1100000016, num);
    }

    @Test
    public void runAllTest4(){
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        for(int i = 0; i < 4; i++){
            tmp.addElement(new Vector<Integer>());
            for(int j = 0; j < 4; j++){
                tmp.get(i).addElement(new Integer(0));
            }
        }
        tmp.get(0).set(1, 2);
        tmp.get(0).set(2, 5);
        tmp.get(0).set(3, 10);
        tmp.get(1).set(0, -1);
        tmp.get(1).set(2, -1);
        tmp.get(1).set(3, 3);
        tmp.get(2).set(0, -1);
        tmp.get(2).set(1, 1);
        tmp.get(2).set(3, 2);
        tmp.get(3).set(0, -1);
        tmp.get(3).set(1, -1);
        tmp.get(3).set(2, -1);
        ReleaseFloyd.algo(tmp);
        int num = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                num += tmp.get(i).get(j);
            }
        }
        Assert.assertEquals(600000018, num);
    }

    @Test
    public void addVertexMatrixTest1(){
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        for(int i = 0; i < 4; i++){
            tmp.addElement(new Vector<Integer>());
            for(int j = 0; j < 4; j++){
                tmp.get(i).addElement(new Integer(0));
            }
        }
        tmp.get(0).set(1, 2);
        tmp.get(0).set(2, 5);
        tmp.get(0).set(3, 10);
        tmp.get(1).set(0, -1);
        tmp.get(1).set(2, -1);
        tmp.get(1).set(3, 3);
        tmp.get(2).set(0, -1);
        tmp.get(2).set(1, 1);
        tmp.get(2).set(3, 2);
        tmp.get(3).set(0, -1);
        tmp.get(3).set(1, -1);
        tmp.get(3).set(2, -1);
        MatrixAdapterFabric.addVertexMatrix(tmp);
        int num = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                num += tmp.get(i).get(j);
            }
        }
        Assert.assertEquals(17, num);
    }

    @Test
    public void addVertexMatrixTest2(){
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        for(int i = 0; i < 4; i++){
            tmp.addElement(new Vector<Integer>());
            for(int j = 0; j < 4; j++){
                tmp.get(i).addElement(new Integer(0));
            }
        }
        tmp.get(0).set(1, 2);
        tmp.get(0).set(2, 5);
        tmp.get(0).set(3, 14);
        tmp.get(1).set(0, -1);
        tmp.get(1).set(2, -1);
        tmp.get(1).set(3, 3);
        tmp.get(2).set(0, -1);
        tmp.get(2).set(1, 4);
        tmp.get(2).set(3, 5);
        tmp.get(3).set(0, -1);
        tmp.get(3).set(1, -1);
        tmp.get(3).set(2, -1);
        MatrixAdapterFabric.addVertexMatrix(tmp);
        int num = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                num += tmp.get(i).get(j);
            }
        }
        Assert.assertEquals(27, num);
    }

    @Test
    public void addEdgeMatrixTest(){
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        for(int i = 0; i < 4; i++){
            tmp.addElement(new Vector<Integer>());
            for(int j = 0; j < 4; j++){
                tmp.get(i).addElement(new Integer(0));
            }
        }
        tmp.get(0).set(1, 2);
        tmp.get(0).set(2, 5);
        tmp.get(0).set(3, 14);
        tmp.get(1).set(0, -1);
        tmp.get(1).set(2, -1);
        tmp.get(1).set(3, 3);
        tmp.get(2).set(0, -1);
        tmp.get(2).set(1, 4);
        tmp.get(2).set(3, 5);
        tmp.get(3).set(0, -1);
        tmp.get(3).set(1, -1);
        tmp.get(3).set(2, -1);
        MatrixAdapterFabric.addEdgeMatrix(tmp, 1, 1, 5);
        int res = tmp.get(1).get(1);
        Assert.assertEquals(5, res);
    }

    @Test
    public void deleteEdgeMatrixTest(){
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        for(int i = 0; i < 4; i++){
            tmp.addElement(new Vector<Integer>());
            for(int j = 0; j < 4; j++){
                tmp.get(i).addElement(new Integer(0));
            }
        }
        tmp.get(0).set(1, 2);
        tmp.get(0).set(2, 5);
        tmp.get(0).set(3, 14);
        tmp.get(1).set(0, -1);
        tmp.get(1).set(2, -1);
        tmp.get(1).set(3, 3);
        tmp.get(2).set(0, -1);
        tmp.get(2).set(1, 4);
        tmp.get(2).set(3, 5);
        tmp.get(3).set(0, -1);
        tmp.get(3).set(1, -1);
        tmp.get(3).set(2, -1);
        MatrixAdapterFabric.deleteEdgeMatrix(tmp, 1, 1);
        int res = tmp.get(1).get(1);
        Assert.assertEquals(100000000, res);
    }

    @Test
    public void copyMatrixTest(){
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        for(int i = 0; i < 4; i++){
            tmp.addElement(new Vector<Integer>());
            for(int j = 0; j < 4; j++){
                tmp.get(i).addElement(new Integer(0));
            }
        }
        tmp.get(0).set(1, 2);
        tmp.get(0).set(2, 5);
        tmp.get(0).set(3, 14);
        tmp.get(1).set(0, -1);
        tmp.get(1).set(2, -1);
        tmp.get(1).set(3, 3);
        tmp.get(2).set(0, -1);
        tmp.get(2).set(1, 4);
        tmp.get(2).set(3, 5);
        tmp.get(3).set(0, -1);
        tmp.get(3).set(1, -1);
        tmp.get(3).set(2, -1);
        Vector <Vector<Integer>> vec = new Vector <Vector<Integer>>();
        MatrixAdapterFabric.copyMatrix(vec, tmp);
        int res = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                res += vec.get(i).get(j);
            }
        }
        Assert.assertEquals(27, res);
    }

    @Test
    public void changeMatrixTest(){
        Vector <Vector<Integer>> tmp = new Vector <Vector<Integer>>();
        for(int i = 0; i < 4; i++){
            tmp.addElement(new Vector<Integer>());
            for(int j = 0; j < 4; j++){
                tmp.get(i).addElement(new Integer(0));
            }
        }
        tmp.get(0).set(1, 2);
        tmp.get(0).set(2, 5);
        tmp.get(0).set(3, 14);
        tmp.get(1).set(0, -1);
        tmp.get(1).set(2, -1);
        tmp.get(1).set(3, 3);
        tmp.get(2).set(0, -1);
        tmp.get(2).set(1, 4);
        tmp.get(2).set(3, 5);
        tmp.get(3).set(0, -1);
        tmp.get(3).set(1, -1);
        tmp.get(3).set(2, -1);
        Vector <Vector<Integer>> vec = new Vector <Vector<Integer>>();
        for(int i = 0; i < 4; i++){
            vec.addElement(new Vector<Integer>());
            for(int j = 0; j < 4; j++){
                vec.get(i).addElement(new Integer(0));
            }
        }
        MatrixAdapterFabric.copyMatrix(vec, tmp);
        int res = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                res += vec.get(i).get(j);
            }
        }
        Assert.assertEquals(0, res);
    }
}
