package com.satelite;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class JtreeGUI extends JFrame {


    DefaultTreeModel tree_model;
    JSplitPane splitter;
    JTree tree;
    JEditorPane display;
    JTextField searchtext;
    JButton searchbutton;
    JPanel panel;

    DefaultMutableTreeNode channel = new DefaultMutableTreeNode("Channels");
    SateliteDataBase db = new SateliteDataBase();
    List<TVChannel> tvChannels = new ArrayList<>();
    JFrame warningWindow = new JFrame("WARNING");

    public JtreeGUI(List<TVChannel> tvChannels) {

        super("Lyngsat-Parser");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        this.tvChannels = tvChannels;

        channel = new DefaultMutableTreeNode("Channels");
        tree_model = new DefaultTreeModel(channel);
        tree = new JTree(tree_model);

        createTree("");

        JScrollPane treescroll = new JScrollPane(tree);
        display = new JEditorPane();
        display.setEditable(false);
        JScrollPane displayscroll = new JScrollPane(display);
        splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitter.setLeftComponent(treescroll);
        splitter.setRightComponent(displayscroll);
        splitter.setDividerLocation(300);

        panel = new JPanel();

        searchtext = new JTextField(50);
        panel.add(searchtext);

        JButton searchbutton = new JButton("Search");
        searchbutton.addActionListener(new ButtonListener());
        panel.add(searchbutton);
        JButton dbUpdateButton = new JButton("Update");
        dbUpdateButton.addActionListener(new ButtonListener());
        panel.add(dbUpdateButton);
        getContentPane().add(panel, BorderLayout.NORTH);


        setVisible(true);
        add(splitter);
        tree.addTreeSelectionListener(new SelectionListener());

    }

    class ButtonListener implements ActionListener {
        ButtonListener() {
        }
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Search")) {
                createTree(searchtext.getText());
            }
            else if (e.getActionCommand().equals("Update")){

                int opcion = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to update whole data base?" +
                                " It can take up to 10 minutes",
                        "Warning",JOptionPane.YES_NO_OPTION);

                if (opcion==0){
                    SateliteParser satPar = new SateliteParser();
                    satPar.runParser();
                    List<TVChannel> chls = satPar.getTvChannels();
                    System.out.println("Parsinng Done >> inserting into database");
//                    db.insertAllChannels(chls);
                    System.out.println("Done");
                }

            }

        }
    }

    class SelectionListener implements TreeSelectionListener {
        public void valueChanged(TreeSelectionEvent se) {
            DefaultMutableTreeNode selected_node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            String selected_node_name = selected_node.toString();


            int nodeLevel = selected_node.getPath().length;
            if (3 == nodeLevel) {
                display.setText(db.getSateliteDescription(selected_node_name));

            } else if (2 == nodeLevel) {

                List<Satelite> sats = db.getSatsForChannelName(selected_node_name);


                for (Satelite s : sats) {
                    String satelite = s.getName();

                    DefaultMutableTreeNode satNode = new DefaultMutableTreeNode(satelite);
                    selected_node.add(satNode);
                }

                display.setText(db.getChannelDescription(selected_node_name));
            }
        }
    }


    void createTree(String search) {
        List<TVChannel> tvCh;

        if (search.equals("")) {
            tvCh = tvChannels;
            System.out.println(tvChannels.size());

        } else {
            tvCh = db.getChannelsLike(search);
        }

        channel.removeAllChildren();

        //create channel nodes
        for (TVChannel tvc : tvCh) {
            String chn = tvc.getName();
            DefaultMutableTreeNode chanNode = new DefaultMutableTreeNode(chn);
            channel.add(chanNode);
        }
        tree.updateUI();
        tree.expandPath(new TreePath(channel.getPath()));
    }
}
