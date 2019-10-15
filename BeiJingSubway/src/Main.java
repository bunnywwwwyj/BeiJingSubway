import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<SubwayLine> lines = new ArrayList<>();
    public static List<Station> stations = new ArrayList<>();
    
	// 解析 subway.txt 获取地铁线路图信息
	public static void subwayMap() {		
		String f = "subway.txt"; 
        try (FileReader r = new FileReader(f);
             BufferedReader br = new BufferedReader(r)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
            	String[] tokens = line.split(": ", 2); // 分离线路及站点
            	SubwayLine sl = new SubwayLine();
            	sl.name = tokens[0];
            	String[] tokens2 = tokens[1].split(" "); // 分离各个站点
            	for(String s: tokens2) {
            		Boolean isTransfer = s.contains("#");
            		Station station = new Station();
            		if(isTransfer) {
            			station.isTransferStation = true;
            			String[] tokens3 = s.split("#"); // 分离换乘站信息
            			station.name = tokens3[0];
            			for(int i = 1; i < tokens3.length; i++) {
            				if(tokens3[i].equals("1"))
            					station.lines.add("1号线");
            				else if(tokens3[i].equals("2"))
            					station.lines.add("2号线");
            				else if(tokens3[i].equals("4"))
            					station.lines.add("4号线");
            				else if(tokens3[i].equals("5"))
            					station.lines.add("5号线");
            				else if(tokens3[i].equals("6"))
            					station.lines.add("6号线");
            				else if(tokens3[i].equals("7"))
            					station.lines.add("7号线");
            				else if(tokens3[i].equals("8北"))
            					station.lines.add("8号线北");
            				else if(tokens3[i].equals("8南"))
            					station.lines.add("8号线南");
            				else if(tokens3[i].equals("9"))
            					station.lines.add("9号线");
            				else if(tokens3[i].equals("10"))
            					station.lines.add("10号线");
            				else if(tokens3[i].equals("13"))
            					station.lines.add("13号线");
            				else if(tokens3[i].equals("14西"))
            					station.lines.add("14号线西");
            				else if(tokens3[i].equals("14东"))
            					station.lines.add("14号线东");
            				else if(tokens3[i].equals("15"))
            					station.lines.add("15号线");
            				else if(tokens3[i].equals("16"))
            					station.lines.add("16号线");
            				else if(tokens3[i].equals("八通"))
            					station.lines.add("八通线");
            				else if(tokens3[i].equals("房山"))
            					station.lines.add("房山线");
            				else if(tokens3[i].equals("昌平"))
            					station.lines.add("昌平线");
            				else if(tokens3[i].equals("亦庄"))
            					station.lines.add("亦庄线");
            				else if(tokens3[i].equals("燕房"))
            					station.lines.add("燕房线");
            				else if(tokens3[i].equals("S1"))
            					station.lines.add("S1线");
            				else if(tokens3[i].equals("西郊"))
            					station.lines.add("西郊线");
            				else if(tokens3[i].equals("首都机场"))
            					station.lines.add("首都机场线");                   		
            			}
            			station.line = tokens[0];
                		stations.add(station);
                		sl.stations.add(station); 
            		}
            		else {
            			if(s.contains("!"))
            				continue;
            			station.isTransferStation = false;
            			station.name = s;
            			station.line = tokens[0];
                		stations.add(station);
                		sl.stations.add(station); 
            		}          		
            	}
            	lines.add(sl);
            }       
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static String getLineName(Station station) {
		for (SubwayLine s : lines) {
			for(Station st : s.stations) {
				if(st.name.equals(station.name))
					return st.line;
			}
		}
		return "";
	}
	
	public static void main(String[] args) throws IOException {
		switch (args[0]) {
	        case "-map":
	            if (args.length == 2) {
	            	subwayMap();
	                System.out.println("数据读取成功 ~");
	            } else {
	                System.out.println("输入格式有误,请重新输入。");
	                break;
	            }
	            break;
	
	        case "-a":
	        	subwayMap();
	        	int flag1 = 0; //判断该线路是否在所有线路中
	        	for(SubwayLine sl : lines) {
	        		if(sl.name.equals(args[1])) {
	        			flag1 = 1;
	        			break;
	        		}        			
	        	}
	        	if(flag1 == 0) {
	        		System.out.println("该线路不存在,请重新输入。");
	                break;
	        	}
	            if (args.length == 6 && args[4].equals("-o")) {
	            	for(int i = 0; i < lines.size(); i++) {
	            		if(args[1].equals(lines.get(i).name)) {
	            			String str = lines.get(i).name+"所经过的站点为: ";
		            		for(Station s : lines.get(i).stations) {
		            			if(s.isTransferStation) {
		            				str += s.name+"(可换乘";
		            				for(String st: s.lines) {
		            					str += " "+st;
		            				}
		            				str +=") ";
		            			}
		            			else {
		            				str += s.name+" ";
		            			}
		            		}
		            		byte[] text = str.getBytes();
	    	    			File file = new File("/Users/bunnywwwwyj/Desktop/BeiJingSubway", "station.txt");
	    	    			if(file.exists() == true)
	    	    				file.delete();
	    	    			file.createNewFile();
	    	    			
	    	    			FileOutputStream out = new FileOutputStream(file);
	    	    			out.write(text);
	    	    			out.close();
	    	    			break;
		            	}
	            	}
	            	String f2 = "/Users/bunnywwwwyj/Desktop/BeiJingSubway/station.txt"; 
	            	try (FileReader r2 = new FileReader(f2);
	                        BufferedReader br2 = new BufferedReader(r2)
	                   ) {
	                       String line2;
	                       while ((line2 = br2.readLine()) != null) {
	                    	   System.out.println(line2);
	                       }       
	                   } catch (IOException e) {
	                       e.printStackTrace();
	                 }
	            } 
	            else {	
	                System.out.println("输入格式有误，请重新输入。");
	                break;
	            }
	            break;
	
	        case "-b":
	        	subwayMap();
	        	if(args[1].equals(args[2])) {
	        		System.out.println("起点站与终点站不能相同。");
	                break;
	        	}
	        	int flag2 = 0; //判断起点站是否在所有站点中
	        	int flag3 = 0; //判断终点站是否在所有站点中
	        	for(Station s : stations) {
	        		if(s.name.equals(args[1]))
	        			flag2 = 1;
	        		if(s.name.equals(args[2]))
	        			flag3 = 1;
	        		if(flag2 == 1 && flag3 == 1)
	        			break;
	        	}
	        	if(flag2 == 0) {
	        		System.out.println("该起点站不存在,请重新输入。");
	                break;
	        	}
	        	if(flag3 == 0) {
	        		System.out.println("该终点站不存在,请重新输入。");
	                break;
	        	}
	            if (args.length == 7 && args[3].equals("-map") && args[5].equals("-o")) {     
	                Result r = Dijkstra.calculate(new Station(args[1]), new Station(args[2]));	
	                int total = r.getPassStations().size()+1;
	                String str = "共经过"+total+"站\n";
	                str += r.getStartStation().name+"\n";
	                String line = getLineName(r.getStartStation());
	                for (int i = 0; i < r.getPassStations().size(); i++) {
	        			if(r.getPassStations().get(i).isTransferStation) {
	        				str += r.getPassStations().get(i).name+"\n";
	        				if (i != r.getPassStations().size()-1 && !line.equals(getLineName(r.getPassStations().get(i+1)))) {
	        					str += "换乘"+getLineName(r.getPassStations().get(i+1))+"\n";
	        					line = getLineName(r.getPassStations().get(i+1));
		        			}
	        			}
	        			else
	        				str += r.getPassStations().get(i).name+"\n";
	        		}
	                
	                byte[] text = str.getBytes();
	        		File file = new File("/Users/bunnywwwwyj/Desktop/BeiJingSubway", "routine.txt");
	        		if(file.exists() == true)
	        			file.delete();
	        		file.createNewFile();
	        		
	        		FileOutputStream out = new FileOutputStream(file);
	        		out.write(text);
	        		out.close();
	        		
	        		String f3 = "/Users/bunnywwwwyj/Desktop/BeiJingSubway/routine.txt"; 
	            	try (FileReader r3 = new FileReader(f3);
	                        BufferedReader br3 = new BufferedReader(r3)
	                   ) {
	                       String line3;
	                       while ((line3 = br3.readLine()) != null) {
	                    	   System.out.println(line3);
	                       }       
	                   } catch (IOException e) {
	                       e.printStackTrace();
	                 }
	            } 
	            else {
	                System.out.println("输入格式有误，请重新输入。");
	                break;
	            }
	            break;
	
	        default:
	            System.out.println("输入格式有误，请重新输入。");
	        }
	}

}
