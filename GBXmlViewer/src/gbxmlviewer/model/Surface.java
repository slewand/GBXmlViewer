package gbxmlviewer.model;

import gbxmlviewer.geom.Bounds3D;
import gbxmlviewer.gui.Appearance;
import gbxmlviewer.gui.Drawable;
import gbxmlviewer.gui.View;

public class Surface implements Drawable
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
 
 public Bounds3D getBounds3D()
 {
  return planarGeometry.getBounds3D();
 }

 @Override
 public Appearance getAppearanceForView(View view)
 {
  return null;
 }
}
