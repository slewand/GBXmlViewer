package gbxmlviewer.gui;

import java.awt.Color;

public enum ViewElement
{
 SURFACE_PLANAR_GEOMETRY("Surface-PlanarGeometry", Color.BLACK, Color.GREEN),
 SPACE_SHELL_GEOMETRY("Space-ShellGeometry", Color.BLACK, Color.YELLOW),
 SPACE_SPACE_BOUNDARY("Space-SpaceBoundary", Color.BLACK, Color.RED);
 
 private String description;
 private Color strokeColor;
 private Color fillColor;
 
 private ViewElement(String descrption, Color strokeColor, Color fillColor)
 {
  this.description = descrption;
  this.strokeColor = strokeColor;
  this.fillColor = fillColor;
 }
 
 public String getDescription()
 {
  return description;
 }

 public Color getStrokeColor()
 {
  return strokeColor;
 }

 public Color getFillColor()
 {
  return fillColor;
 }
}
