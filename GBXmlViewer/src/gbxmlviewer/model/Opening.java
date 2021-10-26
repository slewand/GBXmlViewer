package gbxmlviewer.model;

/**
 * Otwór 
 */
public class Opening extends ModelElement
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
