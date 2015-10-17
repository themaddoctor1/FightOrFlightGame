/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays;

import gui.Interface3D;
import gui.displays.buttons.DisplayButton;
import gui.displays.buttons.ResumeButton;
import gui.displays.buttons.UpgradeCategoryButton;
import gui.displays.buttons.upgrades.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import main.Scoreboard;

/**
 *
 * @author Christopher
 */
public class UpgradeDisplay extends Display{
    
    private boolean canExit = false;
    
    private HashMap<String, DisplayButton[]> buttons = new HashMap<>();
    private DisplayButton[] defaultButtons;
    private String index;
    
    public UpgradeDisplay(){
        
        defaultButtons = new DisplayButton[]{
                new ResumeButton(Interface3D.getInterface3D().getCenterX()-90, Interface3D.getInterface3D().getHeight() - 120),
                new UpgradeCategoryButton(Interface3D.getInterface3D().getCenterX()-150, Interface3D.getInterface3D().getCenterY()-320, "Player"),
                new UpgradeCategoryButton(Interface3D.getInterface3D().getCenterX() -50, Interface3D.getInterface3D().getCenterY()-320, "Gun"),
                new UpgradeCategoryButton(Interface3D.getInterface3D().getCenterX() +50, Interface3D.getInterface3D().getCenterY()-320, "Fist")
        };
        
        buttons.put(
                "All",
                new DisplayButton[]{
                        new UpgradeHpButton(Interface3D.getInterface3D().getCenterX()-123, Interface3D.getInterface3D().getCenterY() - 273),
                        new UpgradeSpeedButton(Interface3D.getInterface3D().getCenterX()+27, Interface3D.getInterface3D().getCenterY() - 273),
                        new UpgradeFistDmgButton(Interface3D.getInterface3D().getCenterX()-123, Interface3D.getInterface3D().getCenterY() - 123),
                        new UpgradeFistSpdButton(Interface3D.getInterface3D().getCenterX()+27, Interface3D.getInterface3D().getCenterY() - 123),
                        new UpgradeGunRldButton(Interface3D.getInterface3D().getCenterX()-123, Interface3D.getInterface3D().getCenterY() + 27),
                        new UpgradeGunSpdButton(Interface3D.getInterface3D().getCenterX()+27, Interface3D.getInterface3D().getCenterY() + 27),
                        new UpgradeGunAmmoButton(Interface3D.getInterface3D().getCenterX()-123, Interface3D.getInterface3D().getCenterY() + 177)
            
        });
        
        buttons.put(
                "Player",
                new DisplayButton[]{
                        new UpgradeHpButton(Interface3D.getInterface3D().getCenterX()-123, Interface3D.getInterface3D().getCenterY() - 273),
                        new UpgradeSpeedButton(Interface3D.getInterface3D().getCenterX()+27, Interface3D.getInterface3D().getCenterY() - 273),
            
        });
        
        buttons.put(
                "Gun",
                new DisplayButton[]{
                        new UpgradeGunRldButton(Interface3D.getInterface3D().getCenterX()-123, Interface3D.getInterface3D().getCenterY() - 273),
                        new UpgradeGunSpdButton(Interface3D.getInterface3D().getCenterX()+27, Interface3D.getInterface3D().getCenterY() - 273),
                        new UpgradeGunAmmoButton(Interface3D.getInterface3D().getCenterX()-123, Interface3D.getInterface3D().getCenterY() - 123)
            
        });
        
        buttons.put(
                "Fist",
                new DisplayButton[]{
                        new UpgradeFistDmgButton(Interface3D.getInterface3D().getCenterX()-123, Interface3D.getInterface3D().getCenterY() - 273),
                        new UpgradeFistSpdButton(Interface3D.getInterface3D().getCenterX()+27, Interface3D.getInterface3D().getCenterY() - 273),
            
        });
        
        
        index = "All";
    }
    
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        Interface3D interf = Interface3D.getInterface3D();
        
        g2.setFont(new Font("Courier New", Font.BOLD, 16));
        g2.drawString("XP: " + (int)(Scoreboard.XP()), interf.getCenterX() - (int)(5*(4+Math.log10(1+Scoreboard.XP()))), interf.getCenterY()-310);
        
        g2.setColor(Color.BLACK);
        
        g2.drawRect(interf.getCenterX()-150, interf.getCenterY()-300, 300, 625);
        for(DisplayButton db : getCurrentButtons())
            db.draw(g2);
        
        drawCursor(g2);
    }

    @Override
    public void cycle() {
        
    }
    
    public void permitExit(){ canExit = true; }
    public boolean canExit(){ return canExit; }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(DisplayButton db : getCurrentButtons())
            if(db.isInButton(e.getX(), e.getY()))
                db.act();
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

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    private ArrayList<DisplayButton> getCurrentButtons() {
        
        ArrayList<DisplayButton> result = new ArrayList<>();
        for(DisplayButton b : buttons.get(index))
            result.add(b);
        
        for(DisplayButton b : defaultButtons)
            result.add(b);
        
        return result;
    }

    public void setButtonCategory(String menuName) {
        index = menuName;
    }
    
}
