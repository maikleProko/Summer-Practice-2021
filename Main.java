import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.view.mxGraph;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class Main extends JFrame
{

    private JToggleButton jButtonAddVertex = new JToggleButton("Add Vertex");
    private JToggleButton jButtonAddEdge = new JToggleButton("Add Edge");
    private JToggleButton jButtonRename = new JToggleButton("Rename");
    private JToggleButton jButtonRemove = new JToggleButton("Remove");
    private JToggleButton jButtonAlgoMode = new JToggleButton("Algo Mode");
    private JButton jButtonStepUndo = new JButton("Play back");
    private JButton jButtonStep = new JButton("Play one step");
    private JButton jButtonAll = new JButton("Play all");
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
    /*=======================================*/
    private  JButton  btnFileFilter = null;
    private  JFileChooser fileChooser = null;
    private final String[][] FILTERS = {{"docx", "Файлы Word (*.docx)"},
            {"pdf" , "Adobe Reader(*.pdf)"}};
    /*=======================================*/
    private JMenu createMenuItems(final String[][] items, mxGraph g, MouseAdapterFabric mouseAdapterFabric)
    {
        // Создание выпадающего меню
        JMenu menu = new JMenu(items[0][0]);
        menu.setMnemonic(items[0][1].charAt(0));
        for (int i = 1; i < items.length; i++) {
            // пункт меню "Открыть"
            JMenuItem item = new JMenuItem(items[i][0]);
            if (item.getText() == "Новый граф"){
                item.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        MouseAdapterFabric.getDeleteAll();
                        MouseAdapterFabric.getDeleteAllSlashText();
                        deleteGraph(g);
                    }
                });
            } else

            if (item.getText() == "Открыть граф из файла"){
                item.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Выбор директории");
                        // Определение режима - только каталог
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        int result = fileChooser.showOpenDialog(Main.this);
                        // Если директория выбрана, покажем ее в сообщении
                        if (result == JFileChooser.APPROVE_OPTION) {
                            jButtonAlgoMode.setSelected(false);
                            jButtonAddVertex.setEnabled(true);
                            jButtonRename.setEnabled(true);
                            jButtonRemove.setEnabled(true);
                            jButtonAddEdge.setEnabled(true);

                            while (MouseAdapterFabric.getRunUndo());
                            jButtonStepUndo.setEnabled(false);
                            jButtonStep.setEnabled(false);
                            jButtonAll.setEnabled(false);


                            FileOpening fopening = new FileOpening(fileChooser.getSelectedFile());
                            fopening.ReadingData();
                            if(!fopening.getCheck()) {

                                MouseAdapterFabric.getDeleteAll();
                                MouseAdapterFabric.getDeleteAllSlashText();
                                deleteGraph(g);

                                int a = fopening.getVertexNum();
                                VertexEnumerator vertexEnumerator = new VertexEnumerator();
                                for (int i = 0; i < a; i++) {
                                    mouseAdapterFabric.getAddVertexMouseAdapterFromFile(vertexEnumerator);
                                }
                                mouseAdapterFabric.getAddEdgeMouseAdapterFromFile(fopening.getMatrixAdj());
                            }
                            else {
                                JOptionPane.showMessageDialog(null,  "File is incorrecct", "Promt", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                });
            }

            if (item.getText() == "Справка"){
                item.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        JOptionPane.showMessageDialog(null,  "Данная программа представляет собой визуализатор\n" +
                                "алгоритма Флойда-Уоршелла. Граф можно ввести двумя путями:" +
                                "\nвручную добавив вершины и рёбра на рабочей области,\n" +
                                "с учетом того, что веса ребер представляют собой целые неотрицательные числа." +
                                "\nВторой вариант — записать матрицу смежности в файл и загрузить его." +
                                "\nВ данном случае чтобы показать отсутствие ребра между вершиной i и j,\nследует ввести число -1.", "Reference", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
            }

            if (item.getText() == "О программе"){
                item.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        JOptionPane.showMessageDialog(null,  "Разработчики: Михаил Прокофьев, Борисовский Виктор, Ковалёв Павел", "Authors", JOptionPane.INFORMATION_MESSAGE);

                    }
                });
            }
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

    private void deleteGraph(mxGraph g){
        mxCell cell;
        for (Object c : g.getChildCells(g.getDefaultParent())){
            cell = (mxCell) c;
            cell.removeFromParent();
            cell.removeFromTerminal(true);
            g.refresh();
        }
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
        mxGraph graph = new mxGraph();

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        mxGraphOutline mxGraphOutline = new mxGraphOutline(graphComponent);
        MouseAdapterFabric mouseAdapterFabric = new MouseAdapterFabric(graph, mxGraphOutline);

        JMenuBar menuBar = new JMenuBar();
        // Создание меню "Файл"
        menuBar.add(createMenuItems(menuFile, graph, mouseAdapterFabric));
        // Создание меню "Редактирование"
        menuBar.add(createMenuItems(menuEdit, graph, mouseAdapterFabric));
        setJMenuBar(menuBar);

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

        // JButton jButtonNewGraph = new JButton("New_Graph");



        jButtonStepUndo.setEnabled(false);
        jButtonStep.setEnabled(false);
        jButtonAll.setEnabled(false);



        JTextArea jTextMatrixSmeshznosti = new JTextArea("Adjacency matrix:");

        jTextMatrixSmeshznosti.setMaximumSize(new Dimension(130, 20));
        jFieldTable = new JTextArea();
        jFieldTable.setMaximumSize(new Dimension(300, 300));
        JScrollPane jFieldScrollPane = new JScrollPane(jFieldTable);
        jFieldScrollPane.setMaximumSize(new Dimension(300, 300));

        jFieldTable.setEditable(false);

        panelRightRight.add(jButtonAddVertex);
        panelRightRight.add(jButtonAddEdge);
        panelRightRight.add(jButtonRename);
        panelRightRight.add(jButtonRemove);
        panelRightRight.add(jButtonAlgoMode);

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
                JOptionPane.showMessageDialog(null,  "Algorithm is finish, check output matrix", "Promt", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        jButtonStepUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*-----------------------------------------------*/
                boolean state = MouseAdapterFabric.getRunUndo();
                if (!state){
                    JOptionPane.showMessageDialog(null,  "You have reached the first step of the algorith", "Promt", JOptionPane.INFORMATION_MESSAGE);
                }
                /*------------------------------------------------*/
            }
        });
        /*------------------------------------------------------*/
        jButtonAlgoMode.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==ItemEvent.SELECTED){
                    jButtonAddVertex.setEnabled(false);
                    jButtonRename.setEnabled(false);
                    jButtonRemove.setEnabled(false);
                    jButtonAddEdge.setEnabled(false);

                    jButtonStepUndo.setEnabled(true);
                    jButtonStep.setEnabled(true);
                    jButtonAll.setEnabled(true);
                } else if (itemEvent.getStateChange()==ItemEvent.DESELECTED){
                    jButtonAddVertex.setEnabled(true);
                    jButtonRename.setEnabled(true);
                    jButtonRemove.setEnabled(true);
                    jButtonAddEdge.setEnabled(true);

                    while (MouseAdapterFabric.getRunUndo());
                    jButtonStepUndo.setEnabled(false);
                    jButtonStep.setEnabled(false);
                    jButtonAll.setEnabled(false);
                }
            }
        });
        /*---------------------------------------------------------------------*/
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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        return frame;
    }

}