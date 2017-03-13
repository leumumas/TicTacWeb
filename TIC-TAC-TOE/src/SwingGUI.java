
import java.awt.*;          
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;       
import javax.swing.event.*;


public class SwingGUI extends JFrame {
	
	public Case [][] Cases; 
	public JTextArea txArea, rxArea; 	
 	public JPanel  GridZone;	
 	public JPanel  Title;
 	public JPanel  Turn;        
 	public GridLayout gl;
    private Image Background;
    private JLabel lblBackgroundImage = new JLabel();
    protected JLabel TitleText;
    private JLabel TurnText;
    	
    //Construction of the UI by slice
	public SwingGUI (String title)
	{
		setTitle(title);		
        setSize(640, 640);
        //Set the Title Zone at the top
        TitleText = new JLabel("Tic-Tac-Toe",
                JLabel.CENTER);
        TitleText.setFont(new Font("Courier New", Font.BOLD, 42));
        TitleText.setForeground(Color.WHITE);
        Title = new JPanel();
        Title.setLayout( new FlowLayout() );
        Title.setPreferredSize(new Dimension(640, 50));
        Title.add(TitleText);
        Title.setOpaque(false);
        
        //Prepare the grid
        GridZone = new JPanel();
        GridZone.setLayout( new FlowLayout() );
        GridZone.setOpaque(false);
        
        GridZone.add(Title);
        Cases = new Case[3][3];
        for (int i = 0; i < 3; i++ )
        { 
        	for (int j = 0; j < 3; j++ )
        	{
        		Case Current  = new Case (i, j);
        		Cases[i][j] = Current;
        		Cases[i][j].setOpaque(false);
        		Cases[i][j].setContentAreaFilled(false);
        		Cases[i][j].setBorderPainted(false);
        		GridZone.add (Cases[i][j]);
        	}
        }

        //Set the Text at the bottom of the UI
        /*TurnText = new JLabel("Player to play",
                JLabel.CENTER);
        TurnText.setFont(new Font("Courier New", Font.BOLD, 42));
        TurnText.setForeground(Color.WHITE);
        Turn = new JPanel();
        Turn.setLayout( new FlowLayout() );
        Turn.setPreferredSize(new Dimension(640, 50));
        Turn.add(TurnText);
        Turn.setOpaque(false);        
        Turn.add(GridZone);*/
        
        //Set the Background
        lblBackgroundImage.setLayout(new FlowLayout());
        try {
        	Background = ImageIO.read(getClass().getResource("res/forest.bmp"));
        	lblBackgroundImage.setIcon(new ImageIcon(Background));
          } catch (Exception ex) {
            System.out.println(ex);
          }
        lblBackgroundImage.setLayout(new BorderLayout());        
        
        lblBackgroundImage.add(GridZone);
        add(lblBackgroundImage);
	}
	
	public void ResetGrid() 
	{
		for (int i = 0; i < 3; i++ )
        { 
        	for (int j = 0; j < 3; j++ )
        	{
        		Cases[i][j].tileChange(0);
        		System.out.println("Reset");
        	}
        }
	}
	
	public static void main (String[] args)
	{
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
		JFrame f = new SwingGUI ("Tic-Tac-Chose");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack ();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
