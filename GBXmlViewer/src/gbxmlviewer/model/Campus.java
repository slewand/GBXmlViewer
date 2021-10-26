package gbxmlviewer.model;

import java.util.ArrayList;
import java.util.List;

import gbxmlviewer.geom.Bounds3D;

/**
 * The Campus element should be used as the base for all physical objects.
 * On a campus, place one or more buildings.
 */
public class Campus extends ModelElement
{
 private List<Building> buildings = new ArrayList<>();
 private List<Surface> surfaces = new ArrayList<>();
 
 public List<Building> getBuildings()
 {
  return buildings;
 }
 
 public void addBuilding(Building building)
 {
  buildings.add(building);
 }
 
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
