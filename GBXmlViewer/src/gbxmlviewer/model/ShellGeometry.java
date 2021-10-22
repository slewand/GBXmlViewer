package gbxmlviewer.model;

/** 
 * All data are global, with the assumption that positive Z is up,
 * and if CADModelAzimuth is undefined or zero,
 * positive X is East and positive Y is North.
 * If CADModelAzimuth is defined it is the angle of positive Y to North,
 * positive X is the vectorial product of Y and Z.
 * If geometry is to be precise, use Longitude Latitude,
 * and Elevation in the Location element to define the origin.
 * Otherwise the origin is an arbitrary point.
 * ShellGeometry is used to define a union of closed shells,
 * where there is no intersection of any two of the given shells.
 */
public class ShellGeometry
{
 private ClosedShell closedShell;

 public ClosedShell getClosedShell()
 {
  return closedShell;
 }

 public void setClosedShell(ClosedShell closedShell)
 {
  this.closedShell = closedShell;
 }
}
