package gbxmlviewer.model;

import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import gbxmlviewer.geom.Bounds3D;
import gbxmlviewer.geom.Point3D;
import gbxmlviewer.gui.View;

/** 
 * This is a list of coordinates that make up a polygon in three-dimensional space.
 * All coordinates must lie on the same plane.
 * The right-hand rule applies for defining the outward normal of a surface:
 * For every surface, points must be defined in order, such that the direction of
 * (the average cross-product between (any point, the centroid of the surface, and the next point))
 * points in the direction of the outward normal,
 * which is a vector pointing away from the first AdjacentSpaceID listed.
 */
public class PolyLoop
{
 private List<CartesianPoint> cartesianPoints = new ArrayList<>();
 
 public List<CartesianPoint> getCartesianPoints()
 {
  return cartesianPoints;
 }
 
 public void addCartesianPoint(CartesianPoint cartesianPoint)
 {  
  cartesianPoints.add(cartesianPoint);
 }
  
 public Bounds3D getBounds3D()
 {
  double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE, zMin = Double.MAX_VALUE;
  double xMax = -Double.MAX_VALUE, yMax = -Double.MAX_VALUE, zMax = -Double.MAX_VALUE;
  for(CartesianPoint cartesianPoint: cartesianPoints)
  {
   if(cartesianPoint.getCoordinates().size()>=1)
   {
    xMin = Double.min(xMin, cartesianPoint.getCoordinates().get(0));
    xMax = Double.max(xMax, cartesianPoint.getCoordinates().get(0));    
   }
   if(cartesianPoint.getCoordinates().size()>=2)
   {
    yMin = Double.min(yMin, cartesianPoint.getCoordinates().get(1));
    yMax = Double.max(yMax, cartesianPoint.getCoordinates().get(1));    
   }
   if(cartesianPoint.getCoordinates().size()>=3)
   {
    zMin = Double.min(zMin, cartesianPoint.getCoordinates().get(2));
    zMax = Double.max(zMax, cartesianPoint.getCoordinates().get(2));    
   }
  }
  return new Bounds3D(xMin, yMin, zMin, xMax, yMax, zMax);
 }
 
 public GeneralPath getAsScreenPath(View view)
 {
  GeneralPath generalPath = new GeneralPath();
  List<CartesianPoint> points = cartesianPoints;
  for(int i=0; i<points.size(); i++)
  {
   Point3D point3D = points.get(i).getAsPoint3D();
   Point point2D = view.transform3DTo2D(point3D);
   if(i==0)
    generalPath.moveTo(point2D.x, point2D.y);
   else
    generalPath.lineTo(point2D.x, point2D.y);
  }
  generalPath.closePath();
  return generalPath;
 }
}
