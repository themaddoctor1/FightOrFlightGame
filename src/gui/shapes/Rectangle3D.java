/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.shapes;

import physics.Coordinate;
import physics.Vector;

/**
 *
 * @author Christopher
 */
public class Rectangle3D extends Polygon3D{
    
    public final Coordinate CENTER;
    public final double XZ, Y, WIDTH, HEIGHT;
    
    public Rectangle3D(Coordinate c, double xz, double y, double w, double h) {
        super(generateCorners(c, xz, y, w, h), 5);
        CENTER = c;
        XZ = xz;
        Y = y;
        WIDTH = w;
        HEIGHT = h;
    }
    
    public static Coordinate[] generateCorners(Rectangle3D shape){
        return generateCorners(shape.CENTER, shape.XZ, shape.Y, shape.WIDTH, shape.HEIGHT);
    }
    
    public static Coordinate[] generateCorners(Coordinate coord, double xz, double y, double w, double h) {
        
        Vector[] edges = new Vector[]{
            new Vector(w/2.0, xz+Math.PI/2.0, 0),
            new Vector(h/2.0, xz-Math.PI, y + Math.PI/2.0),
            new Vector(w/2.0, xz-Math.PI/2.0, 0),
            new Vector(h/2.0, xz+Math.PI, y - Math.PI/2.0)
            
        };
        
        Coordinate[] result = new Coordinate[4];
        
        for(int i = 0; i < 4; i++){
            Coordinate c = new Coordinate(coord.X(), coord.Y(), coord.Z());
            c.addVector(edges[i]);
            c.addVector(edges[(i+1)%4]);
            
            result[i] = c;
            
        }
        
        return result;
        
    }
    
}
