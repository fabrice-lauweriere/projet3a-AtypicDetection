package projet3a.graphics;

import projet3a.generator.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

public class GraphicWindow extends JFrame{
    Listener listener = new Listener();
    public JPanel pan = new JPanel();
    
    int currentPage = 0;
    //JEditorPane message = new JEditorPane();
    //JScrollPane jsp = new JScrollPane(message);
    JFormattedTextField groupTestSize = new JFormattedTextField(NumberFormat.getIntegerInstance());
    JTextField groupTestName = new JTextField();
    JButton generateGroup = new JButton();
    JLabel enterGroupSizeLabel = new JLabel("Enter the size of the group test :");
    
    JButton nextPage = new JButton("Next >>");
    JButton previousPage = new JButton("<< Previous");
    
    public void init(int w, int h) {
        //window size (width and height)
        this.setSize(w, h);
        //title of the window
        this.setTitle("Web Search History Analyser");
        this.setResizable(false);
        //place the window in the middle of the screen
        this.setLocationRelativeTo(null);
        //end program when window is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //no automatic positioning
        pan.setLayout(null);
        //pan.setSize(400, 275);
        
        
        this.getContentPane().setLayout(new GridLayout(1,1));
        this.getContentPane().add(pan);

        //===== ??? =====
        enterGroupSizeLabel.setSize(210, 15);
        enterGroupSizeLabel.setLocation((int)((w-enterGroupSizeLabel.getWidth()-100)/2), 5);
        //enterGroupSizeLabel.setLocation(5, 5);
        enterGroupSizeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        enterGroupSizeLabel.setVerticalAlignment(SwingConstants.TOP);
        

        //===== ??? =====
        groupTestSize.setSize(100, 20);
        groupTestSize.setLocation(enterGroupSizeLabel.getSize().width+enterGroupSizeLabel.getLocation().x, enterGroupSizeLabel.getLocation().y);
        groupTestSize.addKeyListener(listener);
        
        


        //===== ??? =====
        generateGroup.setSize(enterGroupSizeLabel.getSize().width+groupTestSize.getSize().width, 30);
        generateGroup.setText("Generate GroupTest");
        generateGroup.setLocation(enterGroupSizeLabel.getLocation().x, enterGroupSizeLabel.getLocation().y+enterGroupSizeLabel.getSize().height+5);
        generateGroup.addActionListener(listener);
        generateGroup.addKeyListener(listener);
        
        
        
        
        
        //===== ??? =====
        groupTestName.setSize(300, 20);
        groupTestName.setLocation((int)((this.getWidth()-groupTestName.getWidth())/2), 100);
        groupTestName.addKeyListener(listener);
        
        
        //===== Next Page Button =====
        nextPage.setSize(120, 30);
        nextPage.setLocation((int)(this.getWidth()-nextPage.getWidth()-5), (int)(h-nextPage.getHeight()-5-20));
        nextPage.addActionListener(listener);
        nextPage.addKeyListener(listener);
        nextPage.setEnabled(false);
        
      //===== Previous Page Button =====
        previousPage.setSize(120, 30);
        previousPage.setLocation(5, (int)(h-nextPage.getHeight()-5-20));
        previousPage.addActionListener(listener);
        previousPage.addKeyListener(listener);
        

        
        goToPage(0);
        this.setVisible(true);
        
    }
    
    //===== Page switcher =====
    
    public void goToPage(int page){
    	pan.removeAll();
    	nextPage.setEnabled(false);
    	switch( page ){
    	case 0:
    		//pan.setBackground(Color.LIGHT_GRAY);
    		pan.add(enterGroupSizeLabel);
    		pan.add(groupTestSize);
    		pan.add(generateGroup);
    		pan.add(nextPage);
    		break;
    	case 1:
    		pan.setBackground(Color.RED);
    		pan.add(groupTestName);
    		pan.add(nextPage);
    		pan.add(previousPage);
    		break;
    	case 2:
    		pan.setBackground(Color.GREEN);
    		pan.add(groupTestName);
    		pan.add(previousPage);
    		break;
    	}
    	pan.repaint();
    	
    }
    
    
    
    
    
    
    //============================ Class Listener =============================
    
    class Listener implements ActionListener, KeyListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
           if(ae.getSource()==generateGroup && Integer.parseInt(groupTestSize.getText())>1){
                   //Main.theGroupTest = new GroupTest(Integer.parseInt(groupTestSize.getText()));
        	   //nextPage.setEnabled(true);
        	   //System.out.println(Main.getGroupTest().toString());
           }
           else if(ae.getSource()==nextPage){
        	   //currentPage++;
        	   //goToPage(currentPage);
           }
           else if(ae.getSource()==previousPage){
        	   //currentPage--;
        	   //goToPage(currentPage);
           }
        }

        @Override
        public void keyTyped(KeyEvent ke) {
           
        }

        @Override
        public void keyPressed(KeyEvent ke) {
        }

        @Override
        public void keyReleased(KeyEvent ke) {
        }
    }
    
    
}
