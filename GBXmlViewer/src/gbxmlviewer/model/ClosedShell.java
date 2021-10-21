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
 
 public List<GeneralPath> getAsScreenPaths(View view)
 {
  List<GeneralPath> paths = new ArrayList<>(polyLoops.size());
  for(PolyLoop polyLoop: polyLoops)
   paths.add(polyLoop.getAsScreenPath(view));
  return paths;
 }
}
