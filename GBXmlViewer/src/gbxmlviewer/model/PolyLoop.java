package gbxmlviewer.model;

import java.util.ArrayList;
import java.util.List;

import gbxmlviewer.geom.Bounds3D;

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
}
