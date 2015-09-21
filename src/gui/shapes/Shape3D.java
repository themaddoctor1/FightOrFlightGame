/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.shapes;

import gui.Camera;
import java.awt.Graphics;

/**
 *
 * @author Christopher
 */
public interface Shape3D {
    
    public void drawShape(Graphics g, Camera c);
    public void fillShape(Graphics g, Camera c);
    
    public boolean contains(Camera c, int x, int y);
    
}
