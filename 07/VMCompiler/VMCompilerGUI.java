package VMCompiler;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class VMCompilerGUI extends JFrame implements ActionListener
{
    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 200;
    
    private JButton loadButton;
    private JButton loadFolderButton;
    private JButton assembleButton;
    private JTextArea textArea;
    private int state = 0;
    private Vector<String> vmCode = new Vector<String>();
    private Font font = new Font("Monospaced", Font.PLAIN, 16);
    
    public VMCompilerGUI()
    {
        super();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Virtual Machine Compiler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1));
        
        // panel and elements
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
        this.add(mainPanel);
        
        JPanel subpanel = new JPanel();
        subpanel.setLayout(new GridLayout(1, 3));
        mainPanel.add(subpanel);
        
        loadButton = new JButton("Load .vm file");
        loadButton.setFont(font);
        loadButton.addActionListener(this);
        subpanel.add(loadButton);
        
        loadFolderButton = new JButton("Load folder");
        loadFolderButton.setFont(font);
        loadFolderButton.addActionListener(this);
        subpanel.add(loadFolderButton);
        
        assembleButton = new JButton("Assemble into .asm file");
        assembleButton.setFont(font);
        assembleButton.addActionListener(this);
        subpanel.add(assembleButton);
        
        textArea = new JTextArea("Text area.");
        textArea.setFont(font);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFocusable(false);
        mainPanel.add(textArea);
        
        reset();
        
        setVisible(true);
    }
    
    public void reset()
    {
        vmCode = new Vector<String>();
        updateState(0);
    }
    
    public void updateState(int newState)
    {
        state = newState;
        switch(state)
        {
            case 0: textArea.setText("Select a file or folder to load.");
                    break;
            case 1: textArea.setText(String.format("File %s loaded.", FileLoader.getFileName()));
                    break;
            case 2: textArea.setText(String.format("File %s failed to load.", FileLoader.getFileName()));
                    break;
            case 3: textArea.setText(String.format("File %s compiled and saved.", FileLoader.getFileName().replaceAll(".vm", ".asm")));
                    break;
            case 4: textArea.setText(String.format("File %s failed to load.", FileLoader.getFileName()));
                    break;
            case 5: textArea.setText("You must successfully load a file before you can save it.");
                    break;
            default: textArea.setText("I dunno what's going on, man.");
        }
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == loadButton)
        {
            this.reset();
            Vector<String> stringList = FileLoader.loadFile(this);
            if(stringList.size() > 0)
            {
                stringList = FileProcessor.compile(stringList);
                vmCode = stringList;
                updateState(1);
            }
            else
            {
                updateState(2);
            }
        }
        
        // loads all .vm files in a folder
        if(ae.getSource() == loadFolderButton)
        {
            this.reset();
            Vector<String> stringList = FileLoader.loadFolder(this);
            if(stringList.size() > 0)
            {
                stringList = FileProcessor.compile(stringList);
                vmCode = stringList;
                updateState(1);
            }
            else
            {
                updateState(2);
            }
        }
        
        if(ae.getSource() == assembleButton)
        {
            if(vmCode.size() > 0)
            {
                // successful save
                if(FileWriter.save(FileLoader.getFileName(), vmCode))
                {
                    updateState(3);
                }
                // exception thrown
                else
                {
                    updateState(4);
                }
            }
            // nothing to save
            else
            {
                updateState(5);
            }
            
        }
        
    }
    
    public static void main(String[] args)
    {
        VMCompilerGUI compiler = new VMCompilerGUI();
        compiler.repaint();
    }
}