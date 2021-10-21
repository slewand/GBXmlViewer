package gbxmlviewer.model;

import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import gbxmlviewer.geom.Bounds3D;
import gbxmlviewer.gui.View;

public class PlanarGeometry
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
 
 public Bounds3D getBounds3D()
 {
  Bounds3D bounds = null;
  for(PolyLoop polyLoop: polyLoops)
  {
   if(bounds==null)
    bounds = polyLoop.getBounds3D();
   else
    bounds.union(polyLoop.getBounds3D());
  }
  return bounds;
 }
 
 public List<GeneralPath> getAsScreenPaths(View view)
 {
  List<GeneralPath> paths = new ArrayList<>(polyLoops.size());
  for(PolyLoop polyLoop: polyLoops)
   paths.add(polyLoop.getAsScreenPath(view));
  return paths;
 }
}
