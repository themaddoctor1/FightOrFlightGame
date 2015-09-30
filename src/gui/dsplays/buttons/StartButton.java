/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.dsplays.buttons;

import gui.Controller;
import gui.Interface;
import gui.Interface3D;
import gui.dsplays.GameDisplay;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Scoreboard;
import world.WorldManager;

/**
 *
 * @author Christopher
 */
public class StartButton extends DisplayButton{

    public StartButton(int X, int Y) {
        super(X, Y, 160, 40);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setFont(new Font("Courier New", Font.PLAIN, 30));
        
        boolean select = isInButton(Interface3D.getInterface3D().mouseX(), Interface3D.getInterface3D().mouseY());
        
        if(select)
            g.setColor(Color.WHITE);
        else
            g.setColor(Color.BLACK);
        
        g.drawString("START", x+32, y+28);
    }

    @Override
    public void act() {
        Interface3D.getInterface3D().setDisplay(new GameDisplay());
        Scoreboard.startGame();
        
        try {
            (new Robot()).mouseMove(
                    Toolkit.getDefaultToolkit().getScreenSize().width/2 + Interface3D.getInterface3D().getFrame().getInsets().left,
                    Toolkit.getDefaultToolkit().getScreenSize().height/2 + Interface3D.getInterface3D().getFrame().getInsets().top
            );
        } catch (AWTException ex) {
            Logger.getLogger(StartButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
