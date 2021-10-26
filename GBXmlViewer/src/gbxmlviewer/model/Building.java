package gbxmlviewer.model;

import java.util.ArrayList;
import java.util.List;

public class Building extends ModelElement
{
 private BuildingStorey buildingStorey;
 public BuildingStorey getBuildingStorey()
 {
  return buildingStorey;
 }

 public void setBuildingStorey(BuildingStorey buildingStorey)
 {
  this.buildingStorey = buildingStorey;
 }

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
