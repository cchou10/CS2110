
public class Station {
	private String Name;
	private int id;
	private int songs;
	
	/**Constructor. Makes a new radio station from the name and ID.
	 * @param name Name of this radio station.
	 * @param ID ID of this radio station.
	 */
	public Station(String name, int ID) {
		Name = name;
		id = ID;
	}
	/**
	 * @return Returns the name of this station.
	 * If you're lucky, you'll get their number too.
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name A name for this station.
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return Returns the station ID.
	 */
	public int getID() {
		return id;
	}
	/**
	 * @param iD The Id for this station. 
	 */
	public void setID(int iD) {
		id = iD;
	}
	/**
	 * @return The length of the playlist that is broadcast on 
	 * this station counted by the number of songs. 
	 */
	public int getPlaylistLength() {
		return songs;
	}
	/**
	 * @param playlistLength The length of this station's playlist.
	 */
	public void setPlaylistLength(int playlistLength) {
		songs = playlistLength;
	}
	
	@Override
	public String toString() {
		return id+"." +Name;
	}
}
