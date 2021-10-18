package gbxmlviewer.model;

import gbxmlviewer.geom.Bounds3D;
import gbxmlviewer.geom.Point3D;

/**
 * Model (odpowiada sekcji gbXML)
 */
public class Model
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
 
 public Point3D getCentralPoint()
 {
  Bounds3D bounds = getBounds3D();
  return new Point3D((bounds.getxMin()+bounds.getxMax())/2.0, (bounds.getyMin()+bounds.getyMax())/2.0, (bounds.getzMin()+bounds.getzMax())/2.0);
 }
}
