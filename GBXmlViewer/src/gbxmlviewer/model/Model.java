package gbxmlviewer.model;

import gbxmlviewer.geom.Bounds3D;

/**
 * Model (odpowiada sekcji gbXML)
 */
public class Model extends ModelElement
{
 private Campus campus;

 public Campus getCampus()
 {
  return campus;
 }

 public void setCampus(Campus campus)
 {
  this.campus = campus;
 }
 
 public Bounds3D getBounds3D()
 {
  return campus.getBounds3D();
 } 
}
