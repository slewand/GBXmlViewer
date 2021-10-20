package gbxmlviewer.model;

import java.util.ArrayList;
import java.util.List;

public class Space
{
 private List<SpaceBoundary> spaceBoundaries = new ArrayList<>();
 
 public List<SpaceBoundary> getSpaceBoundaries()
 {
  return spaceBoundaries;
 }
 
 public void add(SpaceBoundary spaceBoundary)
 {
  spaceBoundaries.add(spaceBoundary);
 }
}
