/*
 * CurvedPolygon is a class that allows one to draw polygons with curved sides.
 */

package gui.shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import physics.Coordinate;
import gui.Camera;

/**
 *
 * @author Christopher Hittner
 */
public class Polygon3D implements Shape3D{
    
    private Coordinate[] coords;
    private int divisions;
    
    public Polygon3D(Coordinate[] c, int divides){
        coords = c;
        divisions = divides;
    }
    
    
    public boolean contains(Camera c, int x, int y){
        return convert(c).contains(x, y);
    }
    
    public Polygon convert(Camera cam){
        
        int[] x = new int[coords.length * divisions], y = new int[coords.length * divisions];
        
        for(int i = 0; i < coords.length * divisions; i++){
            int c = i/divisions;
            
            int d = i%divisions;
            
            Coordinate start = coords[c];
            Coordinate finish = coords[(c+1)%coords.length];
            
            Coordinate a = new Coordinate(
                      start.X() + (finish.X() - start.X())*d/(divisions)
                    , start.Y() + (finish.Y() - start.Y())*d/(divisions)
                    , start.Z() + (finish.Z() - start.Z())*d/(divisions)
            );
            
            int[] pos = cam.getPlanarCoordinate(a);
            
            x[i] = pos[0];
            y[i] = pos[1];
            
        }
        
        Polygon result = new Polygon(x, y, coords.length*divisions);
        
        return result;
    }
    
    @Override
    public void drawShape(Graphics g, Camera c){
        
        ((Graphics2D) g).drawPolygon(convert(c));
        
    }
    
    @Override
    public void fillShape(Graphics g, Camera c){
        Graphics2D g2 = (Graphics2D) g;
        
        ((Graphics2D) g).fillPolygon(convert(c));
        
    }
    
    public ArrayList<Coordinate> getDrawnCoordinates(){
        
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        
        for(int i = 0; i < coords.length; i++){
            
            Coordinate start = coords[i];
            Coordinate finish = coords[(i+1)%coords.length];
            
            for(int j = 0; j < divisions; j++){
                coordinates.add(new Coordinate(
                          start.X() + (finish.X() - start.X())*j/(divisions)
                        , start.Y() + (finish.Y() - start.Y())*j/(divisions)
                        , start.Z() + (finish.Z() - start.Z())*j/(divisions)
                ));

            }
        }
        
        return coordinates;
        
    }
    
    public static void drawCurvedLine(Graphics g, Camera c, double divisions, Coordinate start, Coordinate finish){
        for(int i = 0; i < divisions; i++){
            Coordinate a = new Coordinate(
                      start.X() + (finish.X() - start.X())*i/(divisions)
                    , start.Y() + (finish.Y() - start.Y())*i/(divisions)
                    , start.Z() + (finish.Z() - start.Z())*i/(divisions)
            );
            
            Coordinate b = new Coordinate(
                      start.X() + (finish.X() - start.X())*(i+1)/(divisions)
                    , start.Y() + (finish.Y() - start.Y())*(i+1)/(divisions)
                    , start.Z() + (finish.Z() - start.Z())*(i+1)/(divisions)
            );
            
            int[] A = c.getPlanarCoordinate(a);
            int[] B = c.getPlanarCoordinate(b);
            
            //camera.drawCoordinate(g, a);
            ((Graphics2D)g).drawLine(A[0], A[1], B[0], B[1]);
            //camera.drawCoordinate(g, b);
            
            
        }
    }
    
}
