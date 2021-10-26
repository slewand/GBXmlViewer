package gbxmlviewer.model;

import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import gbxmlviewer.gui.View;

/** 
 * This is an element from ifcXML that describes a collection of faces that make up a closed shell.
 */
public class ClosedShell extends ModelElement
{
 private List<PolyLoop> polyLoops = new ArrayList<>();
 
 public List<PolyLoop> getPolyLoops()
 {
  return polyLoops;
 }
 
 public void addPolyLoop(PolyLoop polyLoop)
 {
  polyLoops.add(polyLoop);
 }
 
 public List<GeneralPath> getAsScreenPaths(View view)
 {
  List<GeneralPath> paths = new ArrayList<>(polyLoops.size());
  for(PolyLoop polyLoop: polyLoops)
   paths.add(polyLoop.getAsScreenPath(view));
  return paths;
 }
}
