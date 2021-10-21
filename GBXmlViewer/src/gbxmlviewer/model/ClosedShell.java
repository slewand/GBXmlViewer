package gbxmlviewer.model;

import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import gbxmlviewer.geom.Point3D;
import gbxmlviewer.gui.View;

public class ClosedShell
{
 private List<PolyLoop> polyLoops = new ArrayList<>();
 
 public List<PolyLoop> getPolyLoops()
 {
  return polyLoops;
 }
 
 public void addPolyLoop(PolyLoop polyLoop)
 {
  polyLoops.add(polyLoop);
 }
 
 public GeneralPath getScreenPath(View view)
 {
  GeneralPath generalPath = new GeneralPath();
  PolyLoop polyLoop = polyLoops.get(0);
//  for(PolyLoop polyLoop: polyLoops)
//  {
   List<CartesianPoint> points = polyLoop.getCartesianPoints();
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
//  }
  return generalPath;
 }
}
