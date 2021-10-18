package gbxmlviewer.geom;

public class Bounds3D
{
 private double xMin, yMin, zMin;
 private double xMax, yMax, zMax;
 
 public Bounds3D(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax)
 {
  this.xMin = xMin;
  this.yMin = yMin;
  this.zMin = zMin;
  this.xMax = xMax;
  this.yMax = yMax;
  this.zMax = zMax;  
 }
  
 public double getxMin()
 {
  return xMin;
 }

 public double getyMin()
 {
  return yMin;
 }

 public double getzMin()
 {
  return zMin;
 }

 public double getxMax()
 {
  return xMax;
 }

 public double getyMax()
 {
  return yMax;
 }

 public double getzMax()
 {
  return zMax;
 }

 public double getDx()
 {
  return Math.abs(xMax-xMin);
 }
 
 public double getDy()
 {
  return Math.abs(yMax-yMin);
 }
 
 public double getDz()
 {
  return Math.abs(zMax-zMin);
 }

 public void union(Bounds3D otherBounds)
 {
  xMin = Double.min(xMin, otherBounds.xMin);
  yMin = Double.min(yMin, otherBounds.yMin);
  zMin = Double.min(zMin, otherBounds.zMin);
  xMax = Double.max(xMax, otherBounds.xMax);
  yMax = Double.max(yMax, otherBounds.yMax);
  zMax = Double.max(zMax, otherBounds.zMax);
 }
 
 @Override
 public String toString()
 {
  return String.format("[%f, %f, %f; %f, %f, %f]", xMin, yMin, zMin, xMax, yMax, zMax);
 }
}
