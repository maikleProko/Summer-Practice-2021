import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

public class FileOpening {
    private File f = null;
    private boolean incorrect = false;
    private Vector<Vector<Integer>> matrixAdj = new Vector<Vector<Integer>>();

    private int vertexNum = 0;

    public FileOpening(File df){
        f = df;
    }

    public void ReadingData(){
        try(FileReader reader = new FileReader(f))
        {
            Scanner scanner = new Scanner(f);
            try {
                int n = scanner.nextInt();
                vertexNum = n;
                for (int i = 0; i < n; i++) {
                    matrixAdj.addElement(new Vector<Integer>());
                    for (int j = 0; j < n; j++) {
                        int scanTmp = scanner.nextInt();
                        if(scanTmp<0)
                            if(scanTmp != -1)
                                incorrect = true;
                            else
                                scanTmp = 100000000;
                        matrixAdj.get(i).addElement(scanTmp);
                    }
                }
            }
            catch(InputMismatchException e){
                incorrect = true;
            }
        }

        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public int getVertexNum(){
        return vertexNum;
    }

    public boolean getCheck() {
        return incorrect;
    }

    public Vector<Vector<Integer>> getMatrixAdj(){
        return matrixAdj;
    }

}