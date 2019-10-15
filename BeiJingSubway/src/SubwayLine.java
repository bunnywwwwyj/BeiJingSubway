import java.util.ArrayList;
import java.util.List;

public class SubwayLine {
	
	public String name; 
    public List<Station> stations = new ArrayList<>(); 
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Station> getStations() {
		return stations;
	}
	public void setStations(List<Station> stations) {
		this.stations = stations;
	} 

}