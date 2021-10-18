package gbxmlviewer.gui.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import gbxmlviewer.geom.Point3D;
import gbxmlviewer.model.Model;

/**
 * Widok, na którym odbywa siê rysowanie
 */
@SuppressWarnings("serial")
public class View extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener
{
 private Model model;
 private Transformer transformer;
 
 /** Ostatnia pozycji myszki */
 private transient int lastX = 0, lastY = 0;
 /** Przesuniêcie (w pikselach) */
 private int transX = 0, transY = 0; 

 public View(Transformer transformer)
 {
  this.transformer = transformer;
  
  addMouseListener(this);
  addMouseMotionListener(this);
  addMouseWheelListener(this);
 }
 
 public void setModel(Model model)
 {
  this.model = model;
  repaint();
 }
 
 @Override
 protected void paintComponent(Graphics g)
 {
  int viewWidth = getWidth();
  int viewHeight = getHeight();
  g.setColor(Color.WHITE);
  g.fillRect(0, 0, viewWidth, viewHeight);

  Point3D centralPoint;
  if(model==null)
   centralPoint = new Point3D(0.0, 0.0, 0.0);
  else
   centralPoint = model.getCentralPoint();
  Graphics2D g2d = (Graphics2D)g;
  Point p1 = null, p2 = null;
  // uk³ad wspó³rzêdnych
  p1 = transformer.transform3DTo2D(new Point3D(0.0, 0.0, 0.0), centralPoint, viewWidth, viewHeight, transX, transY);
  if(p1!=null)
  {
   g.setColor(Color.BLUE); // oœ X
   p2 = transformer.transform3DTo2D(new Point3D(1.0, 0.0, 0.0), centralPoint, viewWidth, viewHeight, transX, transY);
   if(p2 != null)
   {
    g2d.draw(new Line2D.Double(p1, p2));
    g2d.drawString("X", p2.x+5, p2.y);
   }
   g.setColor(Color.GREEN); // oœ Y
   p2 = transformer.transform3DTo2D(new Point3D(0.0, 1.0, 0.0), centralPoint, viewWidth, viewHeight, transX, transY);
   if(p2 != null)
   {
    g2d.draw(new Line2D.Double(p1, p2));
    g2d.drawString("Y", p2.x+5, p2.y);
   }
   g.setColor(Color.RED); // oœ Z
   p2 = transformer.transform3DTo2D(new Point3D(0.0, 0.0, 1.0), centralPoint, viewWidth, viewHeight, transX, transY);
   if(p2 != null)
   {
    g2d.draw(new Line2D.Double(p1, p2));
    g2d.drawString("Z", p2.x+5, p2.y);
   }   
  }
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
   double alpha = transformer.getAlpha();
   double beta = transformer.getBeta();
   alpha -= (double)(e.getY()-lastY)/100.0; 
   beta += (double)(e.getX()-lastX)/60.0;
   transformer.setAngles(alpha, beta);
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
  double fov = transformer.getFov();
  fov = fov+(e.getWheelRotation()/1.0);
  if(fov<0.001)
   fov = 0.001;
  if(fov>180.0)
   fov = 180.0;
  transformer.setFov(fov);
  repaint();
 }

 public void mouseMoved(MouseEvent e) { }
 public void mouseClicked(MouseEvent e) { }
 public void mouseEntered(MouseEvent e) { }
 public void mouseExited(MouseEvent e) { }
 public void mouseReleased(MouseEvent e) { }

}
