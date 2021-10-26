package gbxmlviewer.model;

import java.util.ArrayList;
import java.util.List;

import gbxmlviewer.geom.Point3D;

/** 
 * This is the x, y, and z distances from the origin.
 * This element must have three Coordinate elements when representing 3-d space,
 * which represent x, y and z in order.
 * This element must have two Coordinate elements when representing 2-d space.
 */
public class CartesianPoint extends ModelElement
{
 private List<Double> coordinates = new ArrayList<>(3);
 
 public List<Double> getCoordinates()
 {
  return coordinates;
 }
 
 public void addCoordinate(Double coordinate)
 {
  coordinates.add(coordinate);
 }
 
 public Point3D getAsPoint3D()
 {
  Point3D point = new Point3D();
  if(coordinates.size()>=1)
   point.setX(coordinates.get(0));
  if(coordinates.size()>=2)
   point.setY(coordinates.get(1));
  if(coordinates.size()>=3)
   point.setZ(coordinates.get(2));  
  return point;
 }
 
 @Override
 public String toString()
 {
  StringBuilder stringBuilder = new StringBuilder();
  stringBuilder.append("[");
  for(Double coordinate: coordinates)
  {
   stringBuilder.append(coordinate);
   stringBuilder.append(";");
  }
  stringBuilder.append("]");
  return stringBuilder.toString();
 }
}
