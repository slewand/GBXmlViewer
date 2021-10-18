package gbxmlviewer.model;

import java.util.ArrayList;
import java.util.List;

public class CartesianPoint
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
