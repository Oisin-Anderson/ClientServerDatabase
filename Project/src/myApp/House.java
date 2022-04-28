package myApp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="house")
@XmlType(propOrder = {"id", "hName", "seat", "words"})
public class House {
	private int id;
	private String hName;
	private String seat;
	private String words;
	
	public House(int id, String hName, String seat, String words) {
		this.id = id;
		this.hName = hName;
		this.seat = seat;
		this.words = words;
	}
	
	public House() {
		
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	
	public void sethName(String hName) {
		this.hName = hName;
	}
	
	public String gethName() {
		return hName;
	}
	
	
	public void setSeat(String seat) {
		this.seat = seat;
	}
	
	public String getSeat() {
		return seat;
	}
	
	
	public void setWords(String words) {
		this.words = words;
	}
	
	public String getWords() {
		return words;
	}

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", hName=" + hName + ", seat=" + seat + ", words=" + words + '}';
    }
}
