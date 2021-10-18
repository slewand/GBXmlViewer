package gbxmlviewer.model;

import gbxmlviewer.geom.Bounds3D;

public class Surface
{
 private PlanarGeometry planarGeometry;

 public PlanarGeometry getPlanarGeometry()
 {
  return planarGeometry;
 }

 public void setPlanarGeometry(PlanarGeometry planarGeometry)
 {
  this.planarGeometry = planarGeometry;
 }
 
 public Bounds3D getBounds3D()
 {
  return planarGeometry.getBounds3D();
 }
}
