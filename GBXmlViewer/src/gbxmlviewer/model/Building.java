package gbxmlviewer.model;

import java.util.ArrayList;
import java.util.List;

public class Building
{
 private List<Space> spaces = new ArrayList<>();
 
 public List<Space> getSpaces()
 {
  return spaces;
 }
 
 public void addSpace(Space space)
 {
  spaces.add(space);
 }
}
