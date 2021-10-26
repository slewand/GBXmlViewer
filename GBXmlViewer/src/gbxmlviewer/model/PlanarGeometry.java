package gbxmlviewer.model;

import java.awt.geom.GeneralPath;

import gbxmlviewer.geom.Bounds3D;
import gbxmlviewer.gui.View;

/**
 * List of points defining a loop.
 * There are no repeated points in the list.
 * All data are global, with the assumption that positive Z is up,
 * and if CADModelAzimuth is undefined or zero,
 * positive X is East and positive Y is North.
 * If CADModelAzimuth is defined it is the angle of positive Y to North,
 * positive X is the vectorial product of Y and Z.
 * If geometry is to be precise, use Longitude, Latitude and Elevation
 * in the Location element to define the origin.
 * Otherwise the origin is an arbitrary point.
 * Use PlanarGeometry to define a three dimensional polygon that lies on a plane,
 * and has no self-intersection.
 */
public class PlanarGeometry extends ModelElement
{
 private PolyLoop polyLoop;
 
 
 public PolyLoop getPolyLoop()
 {
  return polyLoop;
 }

 public void setPolyLoop(PolyLoop polyLoop)
 {
  this.polyLoop = polyLoop;
 }

 public Bounds3D getBounds3D()
 {
  return polyLoop.getBounds3D();
 }
 
 public GeneralPath getAsScreenPath(View view)
 {
  return polyLoop.getAsScreenPath(view);
 }
}
