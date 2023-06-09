package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame
        implements ActionListener {

    private final JButton start;
    private final JButton stop;
    private final JButton reset;

    private final List<JTextField> nLongestFilesDisplay;

    private final List<JTextField> nIntervalsDisplay;

    public GUI(String path, int nFiles, int nIntervals, int lMax){
        setTitle("Assignment 1 gui.GUI");
        setSize(700,300);

        JTextField display = new JTextField(10);
        JLabel displayLabel = new JLabel("Directory:");
        display.setEditable(true);
        display.setText(""+ path);
        JLabel nLongestFilesLabel = new JLabel("N° File più lunghi");
        JTextField nLongestFilesTF = new JTextField(2);
        nLongestFilesTF.setText(String.valueOf(nFiles));
        nLongestFilesTF.setEditable(true);
        JLabel nIntervalsLabel = new JLabel("N° Intervalli");
        JTextField nIntervalsTF = new JTextField(2);
        nIntervalsTF.setText(String.valueOf(nIntervals));
        nIntervalsTF.setEditable(true);
        JLabel lMaxLabel = new JLabel("Massima lunghezza");
        JTextField lMaxTF = new JTextField(2);
        lMaxTF.setText(String.valueOf(lMax));
        lMaxTF.setEditable(true);
        start = new JButton("start");
        stop  = new JButton("stop");
        reset = new JButton("reset");
        stop.setEnabled(false);

        Container cp = getContentPane();
        JPanel panel = new JPanel();

        Box p0 = new Box(BoxLayout.X_AXIS);
        p0.add(displayLabel);

        p0.add(display);
        p0.add(nLongestFilesLabel);
        p0.add(nLongestFilesTF);
        p0.add(nIntervalsLabel);
        p0.add(nIntervalsTF);
        p0.add(lMaxLabel);
        p0.add(lMaxTF);
        Box p1 = new Box(BoxLayout.X_AXIS);
        p1.add(start);
        p1.add(stop);
        p1.add(reset);

        Box p2 = new Box(BoxLayout.Y_AXIS);
        p2.add(p0);
        p2.add(Box.createVerticalStrut(10));
        p2.add(p1);

        JLabel jl1 = new JLabel(nFiles + " longest files:");
        p2.add(jl1);
        nLongestFilesDisplay = new ArrayList<>(nFiles);
        for(int i = 0; i< nFiles; i++){
            JTextField jtf = new JTextField("0");
            jtf.setName("longest-"+i);
            jtf.setEditable(false);
            nLongestFilesDisplay.add(jtf);
            p2.add(nLongestFilesDisplay.get(i));
        }

        int intervalSize = lMax / (nIntervals - 1);
        nIntervalsDisplay = new ArrayList<>(nIntervals);
        for(int i=0; i < nIntervals; i++){

            String s ="[ " + i * intervalSize + " , " + (i + 1) * intervalSize + " ]";
            if (i+1 == nIntervals ){
                s = "[ " + i * intervalSize + " , " + "- ]";
            }
            panel.add(new JLabel(s));
            JTextField jtf = new JTextField(3);
            jtf.setName("jtf-" + i);
            jtf.setEditable(false);
            nIntervalsDisplay.add(jtf);
            panel.add(nIntervalsDisplay.get(i));
        }

        panel.add(p2);

        cp.add(panel);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent ev){
                System.exit(-1);
            }
            public void windowClosed(WindowEvent ev){
                System.exit(-1);
            }
        });

        start.addActionListener(this);
        stop.addActionListener(this);
        reset.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ev){
        Object src = ev.getSource();
        if (src==start){
            //controller.notifyStarted();
            start.setEnabled(false);
            stop.setEnabled(true);
            reset.setEnabled(false);
        } else if (src == stop){
            //controller.notifyStopped();
            start.setEnabled(true);
            stop.setEnabled(false);
            reset.setEnabled(true);
        }
    }

    public void updateSubdivideCountValue(int[] values) {
        SwingUtilities.invokeLater(()-> {
             for(int i=0; i<values.length; i++){
                 nIntervalsDisplay.get(i).setText(String.valueOf(values[i]));
             }
        });
    }

    public void updateLongestFilesValue(String[] values){
        SwingUtilities.invokeLater(()-> {
            for(int i=0; i< values.length; i++){
                nLongestFilesDisplay.get(i).setText(values[i]);
            }
        });
    }

    public void display() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
    }


}
