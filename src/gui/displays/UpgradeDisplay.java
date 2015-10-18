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
import java.util.Iterator;
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
        
        Interface3D interf = Interface3D.getInterface3D();
        
        
        /*
        buttons.put(
                "All",
                new DisplayButton[]{
                        new UpgradeHpButton(interf.getCenterX()-123, interf.getCenterY() - 273),
                        new UpgradeSpeedButton(interf.getCenterX()+27, interf.getCenterY() - 273),
                        new UpgradeFistDmgButton(interf.getCenterX()-123, interf.getCenterY() - 123),
                        new UpgradeFistSpdButton(interf.getCenterX()+27, interf.getCenterY() - 123),
                        new UpgradeGunRldButton(interf.getCenterX()-123, interf.getCenterY() + 27),
                        new UpgradeGunSpdButton(interf.getCenterX()+27, interf.getCenterY() + 27),
                        new UpgradeGunAmmoButton(interf.getCenterX()-123, interf.getCenterY() + 177)
            
        });
        */
        buttons.put(
                "Player",
                new DisplayButton[]{
                        new UpgradeHpButton(interf.getCenterX()-123, interf.getCenterY() - 273),
                        new UpgradeSpeedButton(interf.getCenterX()+27, interf.getCenterY() - 273),
                        new UpgradeSpeedCapacityButton(interf.getCenterX()-123, interf.getCenterY() - 123),
                        new UpgradeVelocityCompassButton(interf.getCenterX()+27, interf.getCenterY() - 123)
            
        });
        
        buttons.put(
                "Gun",
                new DisplayButton[]{
                        new UpgradeGunRldButton(interf.getCenterX()-123, interf.getCenterY() - 273),
                        new UpgradeGunSpdButton(interf.getCenterX()+27, interf.getCenterY() - 273),
                        new UpgradeGunAmmoButton(interf.getCenterX()-123, interf.getCenterY() - 123),
                        new UpgradeRecoilButton(interf.getCenterX()+27, interf.getCenterY() - 123),
                        new UpgradeMovementAimCompensationButton(interf.getCenterX()-123, interf.getCenterY() + 27)
            
        });
        
        buttons.put(
                "Fist",
                new DisplayButton[]{
                        new UpgradeFistDmgButton(interf.getCenterX()-123, interf.getCenterY() - 273),
                        new UpgradeFistSpdButton(interf.getCenterX()+27, interf.getCenterY() - 273),
            
        });
        
        DisplayButton[] pre = new DisplayButton[]{
                new ResumeButton(interf.getCenterX()-90, interf.getHeight() - 120)
        };
        
        Iterator iter = buttons.keySet().iterator();
        ArrayList<DisplayButton> btns = new ArrayList<>();
        for(DisplayButton db : pre)
            btns.add(db);
        for(int i = 0; i < buttons.size(); i++){
            String nm = (String)iter.next();
            btns.add(new UpgradeCategoryButton(interf.getCenterX()-150+(100*i), interf.getCenterY()-320-20*(i/3), nm));
        }
            
        
        defaultButtons = new DisplayButton[btns.size()];
        for(int i = 0; i < btns.size(); i++)
            defaultButtons[i] = btns.get(i);
        
        index = "Player";
    }
    
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        Interface3D interf = Interface3D.getInterface3D();
        
        g2.setFont(new Font("Courier New", Font.BOLD, 16));
        g2.drawString("XP: " + (int)(Scoreboard.XP()), interf.getCenterX() - (int)(5*(4+Math.log10(1+Scoreboard.XP()))), interf.getCenterY()-310-20*(buttons.size()/3));
        
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

    public String getSubmenuName() {
        return index;
    }
    
}
