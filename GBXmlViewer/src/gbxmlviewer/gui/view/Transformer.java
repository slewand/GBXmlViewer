package gbxmlviewer.gui.view;

import java.awt.Point;

import gbxmlviewer.geom.Point3D;

/**
 * Klasa przeliczaj¹ca punkt 3D na wspó³rzêdne ekranowe
 */
public class Transformer
{
 /** Odlegloœc kamery od rzutni */
 private double d;
 /** K¹t widzenia kamery (stopnie) */
 private double fov;
 /** K¹ty obrotu */
 private double alpha = Math.PI, beta = 0.0;
 /** Macierz obrotu */
 private transient double[][] Mt = new double[3][3];
 /** Maksymalna rozpiêtoœæ uk³adu w dowolnym kierunku */
 private double maxBound = 2.0;

 public Transformer()
 {
  d = 1.0;
  fov = 45.0;
  updateMt();
 }
 
 /**
  * Transformacja punktu z przestrzeni 3D do 2D
  * @param szerokoœæ widoku (piksele)
  * @param wysokoœæ widoku (piksele)
  */
 public Point transform3DTo2D(Point3D point3D, Point3D centralPoint, int viewWidth, int viewHeight, int transX, int transY)
 {
  double pX = point3D.getX();
  double pY = point3D.getY();
  double pZ = point3D.getZ();
  double w = Math.min(viewWidth, viewHeight);
  // Po³owa szerokoœci (lub wysokoœci) rzutni
  double r = d*Math.tan(Math.toRadians(fov/2.0));
  // Przesuniêcie punktu obrotu do œrodka uk³adu
  double x = pX-centralPoint.getX();
  double y = pY-centralPoint.getY();
  double z = pZ-centralPoint.getZ();
  // Obrót uk³adu wokó³ œrodka
  double xx = Mt[0][0]*x+Mt[0][1]*y;
  double yy = Mt[1][0]*x+Mt[1][1]*y+Mt[1][2]*z;
  double zz = Mt[2][0]*x+Mt[2][1]*y+Mt[2][2]*z;
  // Przesuniêcie uk³adu za rzutnie
  zz = zz+d+2.0*maxBound;
  // Rzutowanie na p³aszczyznê 2D
  double xp = xx*d/zz;
  double yp = yy*d/zz;
  // Wspó³rzêdne punktu w uk³adzie ekranu
  int xs = (int)(0.5*w*xp/r)+viewWidth/2;
  int ys = (int)(-0.5*w*yp/r)+viewHeight/2;
  // Przesuniêcie w p³aszczyŸnie 2D (piksele)
  Point point2D = new Point(xs+transX, ys+transY);
  return point2D;
 }

 public double getAlpha()
 {
  return alpha;
 }
 
 public double getBeta()
 {
  return beta;
 }
 
 public void setAlpha(double alpha)
 {
  this.alpha = alpha;
  updateMt();
 }
 
 public void setBeta(double beta)
 {
  this.beta = beta;
  updateMt();
 }
 
 public void setAngles(double alpha, double beta)
 {
  this.alpha = alpha;
  this.beta = beta;
  updateMt();
 }
 
 public double getFov()
 {
  return fov;
 }
 
 public void setFov(double fov)
 {
  this.fov = fov;
 }
 
 /** Uaktualnia macierz obrotu */
 private void updateMt()
 {
  Mt[0][0] = Math.cos(-beta);
  Mt[1][0] = Math.sin(-beta)*Math.cos(alpha);
  Mt[2][0] = Math.sin(-beta)*Math.sin(alpha);
  Mt[0][1] = -Math.sin(-beta);
  Mt[1][1] = Math.cos(-beta)*Math.cos(alpha);
  Mt[2][1] = Math.cos(-beta)*Math.sin(alpha);
  Mt[0][2] = 0;
  Mt[1][2] = -Math.sin(alpha);
  Mt[2][2] = Math.cos(alpha);
 }

 
}
