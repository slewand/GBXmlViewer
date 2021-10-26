package gbxmlviewer.model;

/** 
 * Captures Building Storey Structure
 */
public class BuildingStorey extends ModelElement
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
}
