/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import gui.shapes.*;
import java.awt.*;
import javax.swing.JFrame;

/**
 *
 * @author WHS-D4B1W8
 */
public class Interface3D extends Interface {
    
    //Pixels per radian
    protected double PPR = 600;
    
    protected Interface3D(String name){
        this(name,1200,900);
    }
    
    protected Interface3D(String name, int width, int height){
        super(name,width,height);
    }
    
    @Override
    public void paint(Graphics g){
        
        try {
            display.draw(g);
        } catch(Exception e){
            
        }
        /*
        //Draws a cursor
        g.drawLine(mouseX, mouseY, mouseX+9, mouseY+12);
        g.drawLine(mouseX, mouseY, mouseX, mouseY+15);
        g.drawLine(mouseX, mouseY+15, mouseX+9, mouseY+12);
        */
    }
    
    
    public static void initialize(String name){
        gui = new Interface3D(name, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
    }
    
    public static void initialize(String name, int width, int height){
        gui = new Interface3D(name, width, height);
    }
    
    public static Interface3D getInterface3D(){
        return (Interface3D) gui;
    }
    
    public void setPixelsPerRadian(double ppr){ PPR = ppr; }
    
    public void setDegreesPerRadian(double dpr){
        setPixelsPerRadian(dpr*180.0/Math.PI);
    }
    
    public double getPixelsPerDegree(){
        return PPR * Math.PI/180.0;
    }
    
    public double getPixelsPerRadian() { return PPR; }

    public JFrame getFrame() {
        return frame;
    }
    
    
}
