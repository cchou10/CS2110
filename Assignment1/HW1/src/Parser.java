import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Parser {	
	private Song[] songs;
	private Station[] stations = new Station[144];
	private String[] playlists;
	private int[][] stationplays;
	
	/**Parses the song, station and playlist files and builds the songs and stations array
	 * with information provided in the files
	 * @param songFile The name of the songs dataset file
	 * @param stationFile The name of the station dataset file
	 * @param playlistsFile The name of the playlist dataset file
	 */
	public Parser(String songFile, String stationFile, String playlistsFile) throws IOException{
		int i = 0;
		String nxt;
		FileReader stationin = new FileReader(stationFile);
		Scanner src1 = new Scanner(stationin);
		nxt = src1.next();
		while (src1.hasNext()){
			nxt = src1.next();
			Station sta = parseOneStation(nxt);
			stations[i] = sta;
			i++;
		}
		i = 0;
		FileReader songin = new FileReader(songFile);
		Scanner src = new Scanner(songin);
		nxt = src.next();
		while (src.hasNext()){
			nxt = src.next();
			Song sng = parseOneSong(nxt, stations.length - 1);
			songs[i] = sng;
			i++;
		}
		i = 0;
		FileReader pin = new FileReader(playlistsFile);
		Scanner src2 = new Scanner(pin);
		while (src2.hasNext()){
			nxt = src2.next();
			playlists[i] = nxt;
			i++;
			parseOnePlaylist(nxt);
		}
	}
	/**Each line of the dataset represents a distinct radio station. 
	 * This method should parse and construct a station from a line of the file.
	 * 
	 * @param line A line of your dataset file.
	 * @return A station that is described by line.
	 */
	public Station parseOneStation(String line){
		int i = line.indexOf(";");
		String id = line.substring(0,i);
		int ID = Integer.parseInt(id);
		String NAME = line.substring(i);
		Station S = new Station(NAME, ID);
		return S;
	}
	/**Each line of the dataset represents a distinct song.
	 * This method should parse and construct a station from a line of the file.
	 * @param line  A line of your dataset file.
	 * @param numberOfStations The total number of radio stations.
	 * @return  A song that is described by line.
	 */
	public Song parseOneSong(String line, int numberOfStations){
		int i = line.indexOf(";");
		String id = line.substring(0,i);
		int ID = Integer.parseInt(id);
		int j = line.indexOf(";", i);
		String NAME = line.substring(i,j);
		String ARTIST = line.substring(j);
		Song SONG = new Song(NAME, ARTIST, ID, numberOfStations);
		return SONG;
	}
	/**
	 * 
	 * @param line
	 * @return
	 */
	public void parseOnePlaylist(String line){
		int i = 0;
		while (i < playlists.length){
			int station = line.indexOf(";");
			String test = line.substring(station);
			int j = 0;
			int k = 0;
			int counter = 0;
			while (j < test.length()){
				if (test.charAt(j) == ';'){
					String num = test.substring(k, j);
					k = j;
					int number = Integer.parseInt(num);
					stationplays[i][number]++;
					counter++;
				}
			}
			String last = test.substring(k);
			int lastnum = Integer.parseInt(last);
			stationplays[i][lastnum]++;
			stations[i].setPlaylistLength(counter + 1);
		}
	}
	/**
	 * @return An array of song objects made from the songs dataset file. 
	 * Elements are indexed by song id. For example, a song with 
	 * song id 14853 belongs in Song[14853]. This works because the Song Ids
	 * will be zero-based indexed
	 */
	public Song[] getSongs() {
		return songs;
	}
	/**
	 * @return An array of station objects made from the stations dataset file. 
	 * Elements are indexed by station id. For example, a station with 
	 * station id 562 belongs in Song[562]. This works because the Song Ids
	 * will be zero-based indexed
	 */
	public Station[] getStations() {
		return stations;
	}
	/**
	 * @return An array of Strings for the Playlist
	 */
	public String[] getPlaylist() {
		return playlists;
	}
	/**
	 * @return An array of ints for the number of times each song is played
	 */
	public int[][] getStationplays() {
		return stationplays;
	}
}
