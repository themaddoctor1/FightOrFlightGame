/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays.buttons;

import gui.Interface3D;
import gui.displays.MainMenuDisplay;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import main.Scoreboard;

/**
 *
 * @author Christopher
 */
public class QuitGameButton extends DisplayButton{

    public QuitGameButton(int X, int Y) {
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
        
        g.drawString("QUIT", x+44, y+28);
    }

    @Override
    public void act() {
        Scoreboard.endGame();
        Interface3D.getInterface3D().setDisplay(new MainMenuDisplay());
    }

    
}
