package gbxmlviewer.model;

import java.util.ArrayList;
import java.util.List;

import gbxmlviewer.geom.Bounds3D;

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
}
