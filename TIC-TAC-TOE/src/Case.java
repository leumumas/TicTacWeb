/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.imageio.ImageIO;
import java.io.*;

/**
 *
 * @author Samuel Laliberté
 */
public class Case extends JButton implements MouseListener
{
    private int x, y;
    private int valeur;
    private boolean selected;
    private Image EmptyTile;
    private Image XTile;
    private Image OTile;
    
    public static boolean start = false;
    
    public Case(int row, int column) {
        valeur = 0;
        selected = false;
        x = row;
        y = column;
        
        this.setText("");
        this.addMouseListener(this); 
        this.setMinimumSize(new Dimension(160, 160));
        
        //Prépare les images
        try {
            EmptyTile = ImageIO.read(getClass().getResource("res/EmptyTile.png"));
            this.setIcon(new ImageIcon(EmptyTile));
          } catch (Exception ex) {
            System.out.println(ex);
          }
        try {
        	XTile = ImageIO.read(getClass().getResource("res/XTile.png"));
          } catch (Exception ex) {
            System.out.println(ex);
          }
        try {
        	OTile = ImageIO.read(getClass().getResource("res/OTile.png"));
            this.setIcon(new ImageIcon(XTile));
          } catch (Exception ex) {
            System.out.println(ex);
          }
    
    }
    
    public int getPosX() { return x; }
    public int getPosY() { return y; }
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getValeur() { return valeur; }
    public void setValeur(int v) {
        valeur = v;
    }	
    
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    // EVENT HANDLER CALLED WHEN MOUVE HOVER A CASE
    public void mouseEntered(MouseEvent e) {
        if (selected) {
        	
        }
    }
    
    public void mouseExited(MouseEvent e) {}

    // EVENT HANDLER CALLED WHEN A CASE IS CLICKED
    public void mouseClicked(MouseEvent e) {
       if (!start) {
    	   
       }
    }
}
