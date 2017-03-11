
import java.awt.*;          
import java.awt.event.*;    
import javax.swing.*;       
import javax.swing.event.*;
import javax.swing.BorderFactory; 
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JPanel; 
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class SwingGUI extends JFrame {
	
	public JButton sendButton; 

	public JTextArea txArea, rxArea;
 	
 	public Container container;
     	
    	
	public SwingGUI (String title)
	{
		super (title);
		
        container = getContentPane();             
        container.setLayout( new FlowLayout() );
        
        Border blackline, raisedetched, loweredetched,
        raisedbevel, loweredbevel, empty;
        
        Border paneEdge = BorderFactory.createEmptyBorder(0,10,10,10);
        JPanel simpleBorders = new JPanel();
        simpleBorders.setBorder(paneEdge);
        simpleBorders.setLayout(new BoxLayout(simpleBorders,
                                              BoxLayout.Y_AXIS));
        
		txArea = new JTextArea (6, 40);
		
		rxArea = new JTextArea (6, 40);
	
		sendButton = new JButton ("envoyer");	
			
		container.add (rxArea);
		container.add (txArea);
		container.add (sendButton);
	}
	
	public static void main (String[] args)
	{
		Frame f = new SwingGUI ("Tic-Tac-Chose");
		f.pack ();
		f.setVisible(true);
	}

}
