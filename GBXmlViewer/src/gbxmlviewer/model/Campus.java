package gbxmlviewer.model;

import java.util.ArrayList;
import java.util.List;

import gbxmlviewer.geom.Bounds3D;

public class Campus
{
 private List<Surface> surfaces = new ArrayList<>();
 
 public List<Surface> getSurfaces()
 {
  return surfaces;
 }
 
 public void addSurface(Surface surface)  
 {
  surfaces.add(surface);
 }
 
 public Bounds3D getBounds3D()
 {
  Bounds3D bounds = null;
  for(Surface surface: surfaces)
  {
   if(bounds==null)
    bounds = surface.getBounds3D();
   else
    bounds.union(surface.getBounds3D());
  }
  return bounds;
 }
}
