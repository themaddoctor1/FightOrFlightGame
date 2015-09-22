/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import java.util.ArrayList;
import physics.Coordinate;
import physics.Vector;
import physics.functions.Plane;
import world.entities.creatures.Creature;
import world.entities.Entity;
import world.entities.creatures.Player;

/**
 *
 * @author Christopher Hittner
 */
public class World {
    
    private final ArrayList<Entity> entities = new ArrayList<>();
    
    public void executePhysics(double time){
        
        //Runs an entity cycle
        for(int i = entities.size()-1; i >= 0; i--){
            try{
            entities.get(i).execute(time);
            } catch(NullPointerException npe){}
                    
        }
        
        //Removes null and dead entities
        for(int i = entities.size()-1; i >= 0; i--){
            Entity e = entities.get(i);
            if(e == null)
                entities.remove(e);
            else if(e instanceof Creature){
                Creature c = (Creature) e;
                if(c instanceof Player)
                    continue;
                if(c.getHealth() <= 0)
                    c.killSelf();
            }
        }
        
        //Gravity
        for(int i = entities.size()-1; i >= 0; i--){
            try{
                Entity e = entities.get(i);
                if(e.getPosition().Y() < e.getSize()){
                    e.getPosition().addVector(new Vector(e.getSize() - e.getPosition().Y(), 0, Math.PI/2.0));
                    e.getVelocity().addVectorToThis(new Vector(e.getVelocity().getMagnitudeY(), 0, -Math.PI/2.0));
                }
            } catch(Exception e){
                
            }
        }
        
        
        for(int i = 0; i < entities.size(); i++) {
            Entity a = entities.get(i);
            if(a == null)
                continue;
            for(int j = i+1; j < entities.size(); j++){
                Entity b = entities.get(j); 
                if(b == null)
                    continue;
                
                collisionPhysics(a,b,time);
                
            }
        }
        
    }
    
    public void collisionPhysics(Entity a, Entity b, double time){
        
        double dist = Coordinate.relativeDistance(a.getPosition(), b.getPosition())/0.5;
        if(dist < a.getSize()+b.getSize()){
            //A direction along which the entities should be moved
            Vector a2b = new Vector(a.getPosition(), b.getPosition()).unitVector();
            //How far the entities needs to move
            double distMin = a.getSize()+b.getSize();
            double distLeft = distMin-dist;
            double A = distLeft*a.getSize()/distMin;
            double B = distLeft*b.getSize()/distMin;
            a.getPosition().addVector(new Vector(a2b,-A));
            b.getPosition().addVector(new Vector(a2b,B));

            Vector momentumNet = new Vector(a.getVelocity(), Math.pow(a.getSize(),3));
            momentumNet.addVectorToThis(new Vector(b.getVelocity(), Math.pow(b.getSize(),3)));

            Vector newVel = new Vector(momentumNet, 1.0/(Math.pow(a.getSize(),3)+Math.pow(b.getSize(),3)));

            a.getVelocity().addVectorToThis(new Vector(a.getVelocity(),-1));
            a.getVelocity().addVectorToThis(newVel);
            b.getVelocity().addVectorToThis(new Vector(b.getVelocity(),-1));
            b.getVelocity().addVectorToThis(newVel);

        }
        
    }
    
    
    
    public int enemyCount(){
        int count = 0;
        for(int i = 0; i < entities.size(); i++)
            if(entities.get(i) instanceof Creature && !(entities.get(i) instanceof Player))
                count++;
        return count;
    }
    
    
    public ArrayList<Entity> getEntities(){ return entities; }
    
    
    
}
