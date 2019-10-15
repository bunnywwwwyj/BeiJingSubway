import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Dijkstra {
	
	private static HashMap<Station, Result> result = new HashMap<>(); // 结果集
	private static List<Station> visitedStations = new ArrayList<>();   // 记录分析过的站点
	

	public static Result calculate(Station startStation, Station endStation) {
		if (!visitedStations.contains(startStation)) { 
			visitedStations.add(startStation);
		}
		if (result.isEmpty()) {
			List<Station> adjacentStation = getLinkStations(startStation);
			for (Station s : adjacentStation) {
				Result r = new Result();
				r.setStartStation(startStation);
				r.setEndStation(s);
				r.setDistance(1); // 默认各站点间距离为1
				r.getPassStations().add(s);
				result.put(s, r);
			}
		}

		Station parent = getNextStation();
		if (parent == null) {
			Result r = new Result();
			r.setStartStation(startStation);
			r.setEndStation(endStation);
			r.setDistance(0);
			return result.put(endStation, r);
		}
		
		if (parent.equals(endStation)) {
			return result.get(parent);
		}
		
		List<Station> childLinkStations = getLinkStations(parent);
		for (Station s : childLinkStations) {
			if (visitedStations.contains(s))
				continue;
			
			int distance = 0;
			if (parent.getName().equals(s.getName()))
				distance = 0;
			
			int parentDistance = result.get(parent).getDistance();
			distance = parentDistance + 1;
			List<Station> parentPassStations = result.get(parent).getPassStations();
			Result childResult = result.get(s);
			if (childResult != null) {
				if (childResult.getDistance() > distance) {
					childResult.setDistance(distance);
					childResult.getPassStations().clear();
					childResult.getPassStations().addAll(parentPassStations);
					childResult.getPassStations().add(s);
				}
			} 
			else {
				childResult = new Result();
				childResult.setDistance(distance);
				childResult.setStartStation(startStation);
				childResult.setEndStation(s);
				childResult.getPassStations().addAll(parentPassStations);
				childResult.getPassStations().add(s);
			}
			result.put(s, childResult);
		}
		visitedStations.add(parent);
		return calculate(startStation, endStation);
	}
	
	// 获取所有相邻站点
	public static List<Station> getLinkStations(Station station) { 
		List<Station> linkedStaions = new ArrayList<>();

		for (SubwayLine line : Main.lines) {
			for (int i = 0; i < line.stations.size(); i++) {
				if (station.equals(line.stations.get(i))) {
					if (i == 0) {
						linkedStaions.add(line.stations.get(i + 1));
					}
					else if (i == (line.stations.size() - 1)) {
						linkedStaions.add(line.stations.get(i - 1));
					} 
					else {
						linkedStaions.add(line.stations.get(i + 1));
						linkedStaions.add(line.stations.get(i - 1));
					}
				}
			}				
		}
		return linkedStaions;
	}

	// 计算下一个需要分析的点
	public static Station getNextStation() { 
		int min = 1000000;
		Station station = null;
		Set<Station> stations = result.keySet();
		for (Station s : stations) {
			if (visitedStations.contains(s))
				continue;
			Result r = result.get(s); 
			if (r.getDistance() < min) { 
				min = r.getDistance();
				station = r.getEndStation();
			}
		}
		return station;
	}

}
