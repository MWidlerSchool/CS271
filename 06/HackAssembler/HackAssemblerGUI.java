package HackAssembler;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class HackAssemblerGUI extends JFrame implements ActionListener
{
    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 400;
    
    private JButton loadButton;
    private JButton assembleButton;
    private JTextArea textArea;
    private int state = 0;
    private Vector<String> cleanCode = null;
    
    public HackAssemblerGUI()
    {
        super();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Hack Assembler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1));
        
        // panel and elements
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
        this.add(mainPanel);
        
        JPanel subpanel = new JPanel();
        subpanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(subpanel);
        
        loadButton = new JButton("Load .asm file");
        loadButton.addActionListener(this);
        subpanel.add(loadButton);
        
        assembleButton = new JButton("Assemble into .hack file");
        assembleButton.addActionListener(this);
        subpanel.add(assembleButton);
        
        textArea = new JTextArea("Text area.");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFocusable(false);
        mainPanel.add(textArea);
        
        HackConstants.init();
        
        setVisible(true);
    }
    
    public void updateState(int newState)
    {
        state = newState;
        switch(state)
        {
            case 0: textArea.setText("No file loaded.");
                    break;
            case 1: textArea.setText(String.format("Processing file %s.", FileLoader.getFileName()));
                    break;
            case 2: textArea.setText(String.format("File %s loaded and compiled.", FileLoader.getFileName()));
                    break;
            case 3: textArea.setText(String.format("File %s failed to compile.", FileLoader.getFileName()));
                    break;
            default: textArea.setText("I dunno what's going on, man.");
        }
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == loadButton)
        {
            Vector<String> stringList = FileLoader.loadFile(this);
            updateState(1);
            stringList = FileProcessor.compile(stringList);
            /*
            for(String str : stringList)
            {
                textArea.append(str + "\n");
            }
            HackConstants.test1();*/
            cleanCode = stringList;
            updateState(2);
        }
        
        if(ae.getSource() == assembleButton)
        {
          ////////////////////////////
         /*   JFileChooser c = new JFileChooser();
            int rVal = c.showSaveDialog(this);
            if (rVal == JFileChooser.APPROVE_OPTION) 
            {
                
            }
            if (rVal == JFileChooser.CANCEL_OPTION) 
            {
            
            }*/
          ////////////////////////////
            FileWriter.save(FileLoader.getFileName(), cleanCode);
        }
        
    }
    
    public static void main(String[] args)
    {
        HackAssemblerGUI assembler = new HackAssemblerGUI();
        assembler.repaint();
    }
}