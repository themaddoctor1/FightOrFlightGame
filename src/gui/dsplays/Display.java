/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.dsplays;

import java.awt.Graphics;
import java.awt.event.*;

/**
 *
 * @author Christopher Hittner
 */
public abstract class Display implements KeyListener, MouseListener, MouseMotionListener{
    
    public Display(){}
    public Display(int W, int H){}
    
    public abstract void draw(Graphics g);
    public abstract void cycle();
}
