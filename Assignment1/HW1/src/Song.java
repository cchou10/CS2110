import java.io.IOException;


public class Song {
	private String Name;
	private String singer;
	private int id;
	private int[] station;
	private Parser n = null;

	/**
	 * @return The name of this song.
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name A name for this song.
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return The artist that poured their heart and soul 
	 * into making this work of art. Don't pirate music! 
	 * You wouldn't download a bear.
	 */
	public String getArtist() {
		return singer;
	}

	/**
	 * @param artist An artist for this song.
	 */
	public void setArtist(String artist) {
		singer = artist;
	}
	/**
	 * @return Returns the Song Id. 
	 */
	public int getID() {
		return id;
	}
	/**
	 * @param iD Set a Song Id
	 */
	public void setID(int iD) {
		id = iD;
	}
	/**
	 * @return  An array the length of the number of radio stations. 
	 * stationPlays[i] should be the number of times this song is played on 
	 * the station with station id equal to i. 
	 */
	public int[] getStationPlays() throws IOException{
		n = new Parser("songs.txt", "stations.txt", "playlists.txt");
		int[][] tester = n.getStationplays();
		for (int i = 0; i < n.getStationplays().length; i++){
			station[i] = tester[i][id];
		}
		return station;
	}
	/** Constructor. 
	 * @param name Name of this song
	 * @param artist Artist of this song
	 * @param ID Id of this song
	 * @param numStations The total number of radio stations.
	 */
	public Song(String name, String artist, int ID, int numStations) {
		int num = station.length;
		Name = name;
		singer = artist;
		id = ID;
		num = numStations;
	}
	
	/** Calculates basic statistics about this current song and returns it in an array of integers. 
	 * The order of the calculations should be: 
	 * <ol>
	 *  <li>Average number of plays on each station that carries it, 0 if song is never played</li>
	 *  <li>Total number of plays across all stations</li>
	 *  <li>Station that plays this specific song most often, 
	 *  -1 if the song is played the same number of times across multiple stations</li>
	 *  <li>Maximum number of plays on one station</li>
	 *  <li>Station that plays this specific song the least
	 *  -1 if the song is played the same number of times across all stations</li>
	 *  <li>Minimum number of plays on any one station</li>
	 * <ol>
	 * If the song is never played, the average in (0) should be set to 0. 
	 * For all calculations except for the average, we can count stations that do not carry this song.
	 * That is the maximum and minimum number of plays can be 0.
	 * If multiple stations play this song the same number of times, use the station with the lowest station id for (2) and (4). 
	 * If no stations play this song, (2) and (4) should be set to -1 since our station ids are zero-based. 
	 * @return An array of basic song statistics 
	 * @throws IOException 
	 */
	public int[] getStatistics() throws IOException{
		n = new Parser("songs.txt", "stations.txt", "playlists.txt");
		int[] stationplays = this.getStationPlays();
		int[] stats = new int[6];
		int total = 0;
		int counter = 0;
		int highest = 0;
		int lowest = 10000;
		for (int i = 0; i < stationplays.length; i++){
			if (stationplays[i] != 0){
				total = total + stationplays[i];
				counter++;
				stats[1] = stats[1] + stationplays[i];
				
				if (stationplays[i] > highest)
					highest = stationplays[i];
				if (stationplays[i] < lowest)
					lowest = stationplays[i];
			}
			stats[1] = stats[1] + station[i];
		}
		stats[0] = total / counter;
		stats[3] = highest;
		stats[4] = 0;
		stats[5] = lowest;
		for (int j = 0; j < stationplays.length; j++){
			int highid = -2;
			int lowid = -2;
			int count = 0;
			int countlow = 0;
			if (stationplays[j] == highest){
				highid = j;
				count = 1;
				if (count > 1)
					stats[2] = -1;
				else
					stats[2] = highid;
			}	
			if (stationplays[j] == lowest){
				lowid = j;
				countlow = 1;
				if (countlow > 1)
					stats[4] = -1;
				else
					stats[4] = lowid;
			}
					
		}
		return stats;
	}
	
	@Override
	public String toString() {
		return id+". " + singer + " - " + Name;
	}
	
}
