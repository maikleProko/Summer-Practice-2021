import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;
import java.util.Vector;

public class MouseAdapterFabric {
    private Integer indexVertex;
    private Integer indexEdge;
    private final double widthVertex = 80;
    private final double heightVertex = 30;
    private mxGraph graph;
    private mxGraphOutline graphOutline;
    private static Vector<Vector<Integer>> adj;

    MouseAdapterFabric(mxGraph g, mxGraphOutline gOutline){
        graph = g;
        graphOutline = gOutline;
        adj = new Vector<Vector<Integer>>();
        indexVertex = -1;
        indexEdge = -1;
    }

    private boolean connectionVertex(mxCell cell1, mxCell cell2){
        boolean check = false;
        for (int i = 0; i < cell1.getEdgeCount(); i++) {
            mxICell target = ((mxCell) cell1.getEdgeAt(i)).getTarget();
            if (target == cell2){
                check = true;
            }
        }
        return check;
    }

    public MouseAdapter getAddEdgeMouseAdapter(){
        MouseAdapter addEdgeMouseAdapter = new MouseAdapter() {
            mxCell firstCell = null;
            mxCell secondCell = null;
            int firstCellId = 0;
            int secondCellId = 0;


            private void createEdge(JTextField textWeight, int weight){
                graph.getModel().beginUpdate(); // начали обновлять
                try
                {
                    firstCell.setStyle("defaultStyle;");
                    indexEdge++;
                    MatrixAdapterFabric.addEdgeMatrix(adj, firstCellId, secondCellId, weight);
                    TableAdapterFabric.addTableUpdate(adj,Main.jFieldTable);
                    graph.insertEdge(graph.getDefaultParent(), indexEdge.toString()+"e", textWeight.getText(), firstCell, secondCell);
                    new mxParallelEdgeLayout(graph).execute(graph.getDefaultParent());
                    firstCell = null;
                    secondCell = null;
                    graph.refresh();
                }
                finally
                {
                    graph.getModel().endUpdate(); // закончили
                }
            }
            private void chooseFirstVertex(MouseEvent e){
                firstCell = (mxCell) graphOutline.getGraphComponent().getCellAt(e.getX(), e.getY());

                if (firstCell != null){

                    if (firstCell.isVertex()){
                        StringBuilder time_string = new StringBuilder( firstCell.getId());
                        time_string.deleteCharAt(time_string.length()-1);
                        firstCellId = Integer.parseInt(time_string.toString());
                        graph.getModel().beginUpdate(); // начали обновлять
                        try
                        {
                            firstCell.setStyle("fillColor=green");
                            graph.refresh();
                        }
                        finally
                        {
                            graph.getModel().endUpdate(); // закончили
                        }
                    } else {
                        firstCell = null;
                    }
                }
            }
            public void chooseSecondVertex(MouseEvent e){
                secondCell = (mxCell) graphOutline.getGraphComponent().getCellAt(e.getX(), e.getY());

                if (secondCell == null){
                    resetSelection();
                } else {
                    if (secondCell.isVertex()){
                        StringBuilder time_string = new StringBuilder( secondCell.getId());
                        time_string.deleteCharAt(time_string.length()-1);
                        secondCellId = Integer.parseInt(time_string.toString());
                        if (connectionVertex(firstCell, secondCell)){
                            resetSelection();
                            JOptionPane.showMessageDialog(null,  "These vertices are already connected!", "Invalid selection of vertices", JOptionPane.ERROR_MESSAGE);
                        } else {
                            tryCreateEdge();

                        }
                    }
                }
            }
            private void resetSelection(){
                graph.getModel().beginUpdate(); // начали обновлять
                try
                {
                    firstCell.setStyle("defaultStyle;");
                    firstCell = null;
                    graph.refresh();
                }
                finally
                {
                    graph.getModel().endUpdate(); // закончили
                }
            }

            public void tryCreateEdge(){
                JTextField edgeWeight = new JTextField();
                final JComponent[] inputs = new JComponent[] {
                        new JLabel("Enter weight (Positive integer)"),
                        edgeWeight
                };
                int result = JOptionPane.showConfirmDialog(null, inputs, "Add edge", JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    Scanner sc = new Scanner(edgeWeight.getText().trim());
                    if (!sc.hasNextInt()){
                        resetSelection();
                        JOptionPane.showMessageDialog(null,  "You entered not an integer!", "Invalid weight input", JOptionPane.ERROR_MESSAGE);

                    } else {
                        int weight = sc.nextInt();
                        if (sc.hasNext()){
                            resetSelection();
                            JOptionPane.showMessageDialog(null,  "Wrong number of arguments!", "Invalid weight input", JOptionPane.ERROR_MESSAGE);
                        } else if (weight < 0){
                            resetSelection();
                            JOptionPane.showMessageDialog(null,  "The number must be positive!", "Invalid weight input", JOptionPane.ERROR_MESSAGE);
                        } else {
                            createEdge(edgeWeight, weight);
                        }
                    }
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (firstCell == null){
                    chooseFirstVertex(e);
                } else {
                    chooseSecondVertex(e);
                }
            }
        };
        return addEdgeMouseAdapter;
    }

    public MouseAdapter getMoveVertexMouseAdapter(){
        MouseAdapter moveVertexMouseAdapter = new MouseAdapter() {
            int distanceX, distanceY;
            boolean moves = false;
            double width, height;
            mxCell cell;

            @Override
            public void mouseDragged(MouseEvent e) {
                if (moves){
                    mxGeometry geometry = cell.getGeometry();
                    if (cell.isVertex()){
                        graph.getModel().beginUpdate();
                        graph.getModel().setGeometry(cell, new mxGeometry(geometry.getX() + (e.getX() - distanceX), geometry.getY() + (e.getY() - distanceY), width, height));
                        distanceX = e.getX();
                        distanceY = e.getY();
                        graph.getModel().endUpdate();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                cell = (mxCell) graphOutline.getGraphComponent().getCellAt(e.getX(), e.getY());
                if (cell != null){
                    mxGeometry geometry = cell.getGeometry();
                    width = geometry.getWidth();
                    height = geometry.getHeight();
                    distanceX = e.getX();
                    distanceY = e.getY();
                    moves = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                distanceX = 0;
                distanceY = 0;
                moves = false;
            }
        };
        return moveVertexMouseAdapter;
    }

    public MouseAdapter getAddVertexMouseAdapter(){
        VertexEnumerator vertexEnumerator = new VertexEnumerator();
        MouseAdapter addVertexMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                graph.getModel().beginUpdate(); // начали обновлять
                try
                {
                    indexVertex++;
                    // System.out.println((indexVertex-1)+ " + 1 = " + indexVertex + "; " + indexVertex + " + v =" + indexVertex + "v");
                    Object v2 = graph.insertVertex(graph.getDefaultParent(), null, vertexEnumerator.getNextValue(), e.getX() - widthVertex / 2, e.getY() - heightVertex / 2, widthVertex, heightVertex);
                    mxCell test = (mxCell) v2;
                    test.setId(indexVertex.toString()+"v");
                    //System.out.println(test.getId()+ " create vert");
                    MatrixAdapterFabric.addVertexMatrix(adj);
                    TableAdapterFabric.addTableUpdate(adj,Main.jFieldTable);
                }
                finally
                {
                    graph.getModel().endUpdate(); // закончили
                }
            }
        };
        return addVertexMouseAdapter;
    }

    public MouseAdapter getDeleteCellMouseAdapter(){
        MouseAdapter deleteCellMouseAdapter = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int cellId = 0;
                mxCell cell = (mxCell) graphOutline.getGraphComponent().getCellAt(e.getX(), e.getY());
                StringBuilder time_string = new StringBuilder( cell.getId());
                time_string.deleteCharAt(time_string.length()-1);
                cellId = Integer.parseInt(time_string.toString());
                if (cell != null){
                    graph.getModel().beginUpdate(); // начали обновлять
                    try
                    {
                        MatrixAdapterFabric.deleteVertexMatrix(adj,cellId);
                        TableAdapterFabric.addTableUpdate(adj,Main.jFieldTable);
                        cell.setId("222");
                        cell.removeFromParent();
                        mxCell timeCell;
                        //System.out.println(cellId + "::contr");
                        for (Object c : graph.getChildVertices(graph.getDefaultParent())){
                            timeCell = (mxCell) c;
                            int timeCellId = 1;
                            StringBuilder time_stringTimeCellId = new StringBuilder( timeCell.getId());
                            time_stringTimeCellId.deleteCharAt(time_stringTimeCellId.length()-1);
                            timeCellId = Integer.parseInt(time_stringTimeCellId.toString());
                            if(timeCellId>cellId) {
                                //System.out.println(timeCellId + ">" + cellId);
                                Integer renameCellId = timeCellId - 1;
                                timeCell.setId(renameCellId.toString() + "v");
                            }
                            //System.out.println(timeCell.getId());
                        }
                        indexVertex--;
                        //System.out.println("delete vert: " + cell.getId() +" indexVertex stal: " + indexVertex);
                        graph.refresh();
                    }
                    finally
                    {
                        graph.getModel().endUpdate(); // закончили
                    }
                }
            }
        };
        return deleteCellMouseAdapter;
    }

    public MouseAdapter getRenameVertexMouseAdapter(){
        MouseAdapter renameVertexMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mxCell cell = (mxCell) graphOutline.getGraphComponent().getCellAt(e.getX(), e.getY());
                if (cell != null){
                    if (cell.isVertex()){
                        JTextField changeName = new JTextField(cell.getValue().toString());
                        final JComponent[] inputs = new JComponent[] {
                                new JLabel("Enter new name"),
                                changeName
                        };
                        int result = JOptionPane.showConfirmDialog(null, inputs, "Change name", JOptionPane.PLAIN_MESSAGE);
                        if (result == JOptionPane.OK_OPTION) {
                            graph.getModel().beginUpdate(); // начали обновлять
                            try
                            {
                                graph.getModel().setValue(cell, changeName.getText());
                                graph.refresh();
                            }
                            finally
                            {
                                graph.getModel().endUpdate(); // закончили
                            }
                        }
                    }
                }
            }
        };
        return renameVertexMouseAdapter;
    }

    public static void getRunStep() {
        ReleaseFloyd.run_step(adj);
        TableAdapterFabric.addTableUpdate(adj,Main.jFieldTable);
    }

    public static void getRunUndo() {
        //пофиксить выход за границу
        Vector<Vector<Integer>> tmp = ReleaseFloyd.getState();
        if(tmp != null){
            adj = tmp;
            //System.out.println(ReleaseFloyd.getState());
            TableAdapterFabric.addTableUpdate(adj,Main.jFieldTable);
        }else{
            System.out.println("It is first step of algo");
        }
    }

    public static void getRunAll() {
        ReleaseFloyd.run_all(adj);
        TableAdapterFabric.addTableUpdate(adj,Main.jFieldTable);
    }
}