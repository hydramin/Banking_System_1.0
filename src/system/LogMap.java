package system;

import java.util.LinkedList;

public class LogMap<T1, T2> {

	private T1 SIN_TYPE;
	private T2 LOG_TYPE;

	private LinkedList<T1> key = new LinkedList<>();
	private LinkedList<T2> entry = new LinkedList<>();

	
	public LogMap() {
		key = new LinkedList<>();
		entry = new LinkedList<>();
	}
	/////\/\////////////////////////////////////////////  GENERIC OPERATIONS  //////////////////////////////////////////////////// 
    ////\/\/\///////////////////////////////////////////  GENERIC OPERATIONS  ////////////////////////////////////////////////////
    ///\/==\/\//////////////////////////////////////////  GENERIC OPERATIONS  ////////////////////////////////////////////////////
    //\/////\/\/////////////////////////////////////////  GENERIC OPERATIONS  ////////////////////////////////////////////////////
	
	public void put(T1 sin, T2 log){
		key.add(sin);
		entry.add(log);		
	}
	
	public int size() {		
		return key.size();
	}
	
	public LinkedList<T2> getAllLogs(T1 sin) {
		LinkedList<T2> sameSinLogs = new LinkedList<>();
		if (!key.isEmpty()) {
			for(int i=0; i<key.size();i++) {
				if (key.get(i).equals(sin)) {
					sameSinLogs.add(entry.get(i));
				}
			}
		}
		return sameSinLogs;
	}
	
	public LinkedList<T1> keySet() {
		return key;		
	}
	
	public LinkedList<T2> entrySet(){
		return entry;
	}
	
	
}
