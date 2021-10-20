package gbxmlviewer.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.JPanel;

import gbxmlviewer.geom.Bounds3D;
import gbxmlviewer.geom.Point3D;
import gbxmlviewer.model.Model;
import gbxmlviewer.model.Surface;

/**
 * Widok, na kt�rym odbywa si� rysowanie
 */
@SuppressWarnings("serial")
public class View extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener
{
 /** Odleglo�c kamery od rzutni */
 private double d = 1.0;
 /** K�t widzenia kamery (stopnie) */
 private double fov = 45.0;
 /** K�ty obrotu */
 private double alpha = Math.PI, beta = 0.0;
 /** Macierz obrotu */
 private transient double[][] Mt = new double[3][3];
 /** Ostatnia pozycji myszki */
 private transient int lastX = 0, lastY = 0;
 /** Przesuni�cie (w pikselach) */
 private int transX = 0, transY = 0; 

 /** Maksymalna rozpi�to�� modelu w dowolnym kierunku */
 private double maxBound = 2.0;
 /** �rodek modelu */
 private Point3D centralPoint = new Point3D(0.0, 0.0, 0.0);
 
 /** Czy pokazywa� powierzchnie (surfaces) */
 private boolean showSurfaces = true;
 /** Czy pokazywa� pomieszczenia (spaces) */
 private boolean showSpaces = true;
 
 /** Aktualny model lub null */
 private Model model;
 
 public View()
 {
  reset();
  updateMt();
  addMouseListener(this);
  addMouseMotionListener(this);
  addMouseWheelListener(this);
 }
 
 
 public void reset()
 {
  d = 1.0;
  fov = 45.0;
  alpha = Math.PI;
  beta = 0.0;
  lastX = 0;
  lastY = 0;
  transX = 0;
  transY = 0;
  maxBound = 2.0;
  centralPoint = new Point3D(0.0, 0.0, 0.0);
 }
 
 public void setModel(Model model)
 {
  reset();
  this.model = model;
  if(model!=null)
  {
   Bounds3D bounds = model.getBounds3D();
   centralPoint = new Point3D((bounds.getxMin()+bounds.getxMax())/2.0, (bounds.getyMin()+bounds.getyMax())/2.0, (bounds.getzMin()+bounds.getzMax())/2.0);
   maxBound = Double.max(Double.max(bounds.getDx(), bounds.getDy()), bounds.getDz());
  }
  repaint();
 }
 
 public void setShowSurfaces(boolean showSurfaces)
 {
  this.showSurfaces = showSurfaces;
  repaint();
 }
 
 public void setShowSpaces(boolean showSpaces)
 {
  this.showSpaces = showSpaces;    
  repaint();
 }
 
 @Override
 protected void paintComponent(Graphics g)
 {
  Graphics2D g2d = (Graphics2D)g;
  int viewWidth = getWidth();
  int viewHeight = getHeight();
  g.setColor(Color.LIGHT_GRAY);
  g.fillRect(0, 0, viewWidth, viewHeight);
  
  g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  if(model!=null)
  {
   // Powierzchnie (surfaces)
   if(showSurfaces)
   {
    List<Surface> surfaces = model.getCampus().getSurfaces();
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
    for(Surface surface: surfaces)
    {
     Appearance appearance = surface.getAppearanceForView(this);
     g2d.setPaint(appearance.getFillColor());
     g2d.fill(appearance.getScreenShape());
    }
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    for(Surface surface: surfaces)
    {
     Appearance appearance = surface.getAppearanceForView(this);
     g2d.setColor(appearance.getStrokeColor());
     g2d.draw(appearance.getScreenShape());    
    }    
   }   
  }
  
  // Uk�ad wsp�rz�dnych
  Point p1 = null, p2 = null;  
  p1 = transform3DTo2D(new Point3D(0.0, 0.0, 0.0));
  if(p1!=null)
  {
   g.setColor(Color.BLUE); // o� X
   p2 = transform3DTo2D(new Point3D(1.0, 0.0, 0.0));
   if(p2 != null)
   {
    g2d.draw(new Line2D.Double(p1, p2));
    g2d.drawString("X", p2.x+5, p2.y);
   }
   g.setColor(Color.GREEN); // o� Y
   p2 = transform3DTo2D(new Point3D(0.0, 1.0, 0.0));
   if(p2 != null)
   {
    g2d.draw(new Line2D.Double(p1, p2));
    g2d.drawString("Y", p2.x+5, p2.y);
   }
   g.setColor(Color.RED); // o� Z
   p2 = transform3DTo2D(new Point3D(0.0, 0.0, 1.0));
   if(p2 != null)
   {
    g2d.draw(new Line2D.Double(p1, p2));
    g2d.drawString("Z", p2.x+5, p2.y);
   }   
  }
 }
 
 public Point transform3DTo2D(Point3D point3D)
 {
  int viewWidth = getWidth();
  int viewHeight = getHeight();
  double pX = point3D.getX();
  double pY = point3D.getY();
  double pZ = point3D.getZ();
  double w = Math.min(viewWidth, viewHeight);
  // Po�owa szeroko�ci (lub wysoko�ci) rzutni
  double r = d*Math.tan(Math.toRadians(fov/2.0));
  // Przesuni�cie punktu obrotu do �rodka uk�adu
  double x = pX-centralPoint.getX();
  double y = pY-centralPoint.getY();
  double z = pZ-centralPoint.getZ();
  // Obr�t uk�adu wok� �rodka
  double xx = Mt[0][0]*x+Mt[0][1]*y;
  double yy = Mt[1][0]*x+Mt[1][1]*y+Mt[1][2]*z;
  double zz = Mt[2][0]*x+Mt[2][1]*y+Mt[2][2]*z;
  // Przesuni�cie uk�adu za rzutnie
  zz = zz+d+2.0*maxBound;
  // Rzutowanie na p�aszczyzn� 2D
  double xp = xx*d/zz;
  double yp = yy*d/zz;
  // Wsp�rz�dne punktu w uk�adzie ekranu
  int xs = (int)(0.5*w*xp/r)+viewWidth/2;
  int ys = (int)(-0.5*w*yp/r)+viewHeight/2;
  // Przesuni�cie w p�aszczy�nie 2D (piksele)
  Point point2D = new Point(xs+transX, ys+transY);
  return point2D;
 }

 public void mousePressed(MouseEvent e)
 {
  lastX = e.getX();
  lastY = e.getY();
 }

 public void mouseDragged(MouseEvent e)
 {
  if(e.getModifiersEx()==MouseEvent.BUTTON1_DOWN_MASK)
  {
   alpha -= (double)(e.getY()-lastY)/100.0; 
   beta += (double)(e.getX()-lastX)/60.0;
   updateMt();
  }
  if(e.getModifiersEx()==MouseEvent.BUTTON3_DOWN_MASK)
  {
   transX += (e.getX()-lastX);
   transY += (e.getY()-lastY);
  }
  lastX = e.getX();
  lastY = e.getY();
  repaint();
 }

 public void mouseWheelMoved(MouseWheelEvent e)
 { 
  fov = fov+(e.getWheelRotation()/1.0);
  if(fov<0.001)
   fov = 0.001;
  if(fov>180.0)
   fov = 180.0;
  repaint();
 }

 public void mouseMoved(MouseEvent e) { }
 public void mouseClicked(MouseEvent e) { }
 public void mouseEntered(MouseEvent e) { }
 public void mouseExited(MouseEvent e) { }
 public void mouseReleased(MouseEvent e) { }

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
