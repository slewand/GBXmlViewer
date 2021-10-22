package gbxmlviewer.model;

/**
 * This element establishes the logical relation of a given part of the space ShellGeometry
 * such that its PlanarGeometry is part of an interior surface bounding the space.
 */
public class SpaceBoundary
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
