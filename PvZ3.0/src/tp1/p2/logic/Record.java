package tp1.p2.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.RecordException;
import tp1.p2.view.Messages;

public class Record {
	
	private int current_record;
	private int saved_record;
	private String level_name;
	
	public Record(String level_name) {	
		this.current_record = 0;
		this.level_name = level_name;			
	}
	
	public void loadRecord() throws GameException{
		Scanner read = null;
		String[] entry;
		boolean found = false;
		try {
			 read = new Scanner (new File(Messages.RECORD_FILENAME));				
			try {
				while(read.hasNext() && !found) {
					entry = read.next().split(":");
					if(entry[0].equals(this.level_name)) {
						this.current_record = Integer.parseInt(entry[1]);	
						this.saved_record = this.current_record;
						found = true;
					}
				}						
			}catch(NumberFormatException e) {
				throw new RecordException(Messages.RECORD_READ_ERROR, e);
			}catch(NullPointerException npe) {	
				throw new RecordException(Messages.RECORD_READ_ERROR, npe);
			}	
		}catch(FileNotFoundException fnfe) {			
			throw new RecordException(Messages.RECORD_READ_ERROR, fnfe);
		}finally {
			if(read != null)
				read.close();
		}		
	}
	public void saveRecord() throws GameException{
		if(this.current_record > this.saved_record) {
			PrintWriter out = null;		
			Scanner read = null;
			String[] entry;
			StringBuilder sb = new StringBuilder();
			try {					
				read = new Scanner (new File(Messages.RECORD_FILENAME));				
				while(read.hasNext()) {
					entry = read.nextLine().split(":");
					if(!entry[0].equals(this.level_name)) {
						sb.append(entry[0] + ":" + entry[1] + "\n");					
					}												
				}
				sb.append(this.level_name + ":" + this.current_record + "\n");
				out = new PrintWriter(Messages.RECORD_FILENAME);
				out.println(sb.toString());
			}catch(FileNotFoundException fnfe) {			
				throw new RecordException(Messages.RECORD_WRITE_ERROR);
			}finally {
				if(out != null)
					out.close();
				if(read != null)
					read.close();
			}	
		}				
	}
	public void update(int score, boolean thereIsAWinner) throws GameException{		
		if(score > this.current_record) {
			this.current_record = score;
		}
		if(thereIsAWinner) {
			saveRecord();
		}
	}		
	public boolean thereIsANewRecord() {
		return this.current_record > this.saved_record;
	}
	public String getLevelName() {
		return this.level_name;
	}
	public int getSavedRecord() {
		return this.saved_record;
	}
}
