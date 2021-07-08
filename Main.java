import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.view.mxGraph;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class Main extends JFrame
{

    static JFrame jFrame;
    private  final  String[][]  menuFile =
            {{"Граф"     ,  "Ф",  "", ""},
                    {"Новый граф"  ,  "О", "O", ""},
                    {"Открыть граф из файла",  "С", "S", ""}};
    private  final  String[][]  menuEdit =
            {{"Другое" , "Р",  "", ""},
                    {"Справка"  , "В", "X", ""},
                    {"О программе", "К", "C", ""}};
    private final double widthVertex = 80;
    private final double heightVertex = 30;
    public static JTextArea jFieldTable;
    private static final long serialVersionUID = -2707712944901661771L;

    private JMenu createMenuItems(final String[][] items)
    {
        // Создание выпадающего меню
        JMenu menu = new JMenu(items[0][0]);
        menu.setMnemonic(items[0][1].charAt(0));
        for (int i = 1; i < items.length; i++) {
            // пункт меню "Открыть"
            JMenuItem item = new JMenuItem(items[i][0]);
            item.setMnemonic(items[i][1].charAt(0)); // русская буква
            // установим клавишу быстрого доступа (латинская буква)
            item.setAccelerator(KeyStroke.getKeyStroke(items[i][2].charAt(0),
                    KeyEvent.CTRL_MASK));
            if (items[i][3].length() > 0)
                item.setIcon(new ImageIcon(items[i][3]));
            menu.add(item);
        }
        return menu;
    }

    private void setAllVertexDefault(mxGraph g){
        mxCell cell;
        for (Object c : g.getChildVertices(g.getDefaultParent())){
            cell = (mxCell) c;
            cell.setStyle("defaultStyle");
        }
        g.refresh();
    }

    public Main()
    {
        super("Graph vizualizator");


        JMenuBar menuBar = new JMenuBar();
        // Создание меню "Файл"
        menuBar.add(createMenuItems(menuFile));
        // Создание меню "Редактирование"
        menuBar.add(createMenuItems(menuEdit));
        setJMenuBar(menuBar);

        mxGraph graph = new mxGraph();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        mxGraphOutline mxGraphOutline = new mxGraphOutline(graphComponent);
        MouseAdapterFabric mouseAdapterFabric = new MouseAdapterFabric(graph, mxGraphOutline);
        graphComponent.setEnabled(false);

        MouseAdapter moveVertexMouseAdapter = mouseAdapterFabric.getMoveVertexMouseAdapter();
        graphComponent.getGraphControl().addMouseListener(moveVertexMouseAdapter);
        graphComponent.getGraphControl().addMouseMotionListener(moveVertexMouseAdapter);

        JPanel panelCenter = new JPanel();
        JPanel panelRightRight = new JPanel();
        JPanel panelRightRightDown = new JPanel();
        panelRightRight.setMinimumSize(new Dimension(700, 30));

        BoxLayout boxlayoutVerticalAll = new BoxLayout(panelCenter, BoxLayout.Y_AXIS);
        BoxLayout boxlayoutAll = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
        BoxLayout boxlayoutRightRight = new BoxLayout(panelRightRight, BoxLayout.X_AXIS);
        BoxLayout boxlayoutRightRightDown = new BoxLayout(panelRightRightDown, BoxLayout.X_AXIS);

        panelCenter.setLayout(boxlayoutVerticalAll);
        panelRightRight.setLayout(boxlayoutRightRight);
        panelRightRightDown.setLayout(boxlayoutRightRightDown);
        getContentPane().setLayout(boxlayoutAll);

        JButton jButtonNewGraph = new JButton("New_Graph");
        JToggleButton jButtonAddVertex = new JToggleButton("Add Vertex");
        JToggleButton jButtonAddEdge = new JToggleButton("Add Edge");
        JToggleButton jButtonRename = new JToggleButton("Rename");
        JToggleButton jButtonRemove = new JToggleButton("Remove");


        JButton jButtonStepUndo = new JButton("Play back");
        JButton jButtonStep = new JButton("Play one step");
        JButton jButtonAll = new JButton("Play all");
        JButton jButtonLoad = new JButton("Loading");


        JTextArea jTextMatrixSmeshznosti = new JTextArea("Adjacency matrix:");

        jTextMatrixSmeshznosti.setMaximumSize(new Dimension(130, 20));
        jFieldTable = new JTextArea();
        jFieldTable.setMaximumSize(new Dimension(300, 300));
        jFieldTable.setEditable(false);

        panelRightRight.add(jButtonAddVertex);
        panelRightRight.add(jButtonAddEdge);
        panelRightRight.add(jButtonRename);
        panelRightRight.add(jButtonRemove);

        panelRightRightDown.add(jButtonStepUndo);
        panelRightRightDown.add(jButtonStep);
        panelRightRightDown.add(jButtonAll);

        panelCenter.add(panelRightRight);
        panelCenter.add(jTextMatrixSmeshznosti);
        panelCenter.add(jFieldTable);
        panelCenter.add(panelRightRightDown);

        MouseAdapter addVertexMouseAdapter = mouseAdapterFabric.getAddVertexMouseAdapter();
        jButtonAddVertex.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(itemEvent.getStateChange()==ItemEvent.SELECTED){
                    jButtonRemove.setSelected(false);
                    jButtonAddEdge.setSelected(false);
                    jButtonRename.setSelected(false);
                    graphComponent.getGraphControl().addMouseListener(addVertexMouseAdapter);
                } else if(itemEvent.getStateChange()==ItemEvent.DESELECTED){
                    graphComponent.getGraphControl().removeMouseListener(addVertexMouseAdapter);
                }
            }
        });

        MouseAdapter deleteCellMouseAdapter = mouseAdapterFabric.getDeleteCellMouseAdapter();
        jButtonRemove.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==ItemEvent.SELECTED){
                    jButtonAddVertex.setSelected(false);
                    jButtonAddEdge.setSelected(false);
                    jButtonRename.setSelected(false);
                    graphComponent.getGraphControl().addMouseListener(deleteCellMouseAdapter);
                } else if(itemEvent.getStateChange()==ItemEvent.DESELECTED){
                    graphComponent.getGraphControl().removeMouseListener(deleteCellMouseAdapter);
                }
            }
        });

        jButtonAddEdge.addItemListener(new ItemListener() {
            MouseAdapter addEdgeMouseAdapter;
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==ItemEvent.SELECTED){
                    jButtonAddVertex.setSelected(false);
                    jButtonRemove.setSelected(false);
                    jButtonRename.setSelected(false);
                    addEdgeMouseAdapter = mouseAdapterFabric.getAddEdgeMouseAdapter();
                    graphComponent.getGraphControl().addMouseListener(addEdgeMouseAdapter);
                } else if(itemEvent.getStateChange()==ItemEvent.DESELECTED){
                    setAllVertexDefault(graph);
                    graphComponent.getGraphControl().removeMouseListener(addEdgeMouseAdapter);
                }
            }
        });

        MouseAdapter renameVertexMouseAdapter = mouseAdapterFabric.getRenameVertexMouseAdapter();
        jButtonRename.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==ItemEvent.SELECTED){
                    jButtonAddVertex.setSelected(false);
                    jButtonRemove.setSelected(false);
                    jButtonAddEdge.setSelected(false);
                    graphComponent.getGraphControl().addMouseListener(renameVertexMouseAdapter);
                } else if(itemEvent.getStateChange()==ItemEvent.DESELECTED){
                    graphComponent.getGraphControl().removeMouseListener(renameVertexMouseAdapter);
                }
            }
        });

        jButtonStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MouseAdapterFabric.getRunStep();
            }
        });

        jButtonAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MouseAdapterFabric.getRunAll();
            }
        });

        jButtonStepUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MouseAdapterFabric.getRunUndo();
            }
        });

        getContentPane().add(graphComponent);
        getContentPane().add(panelCenter);
    }

    public static void main(String[] args)
    {
        jFrame = getFrame();
    }

    static JFrame getFrame(){
        Main frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds(dimension.width / 2 - 375, dimension.height / 2 - 250, 750, 500);
        frame.setVisible(true);
        return frame;
    }

}