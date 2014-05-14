import java.io.IOException;
import java.util.Scanner;


public class RadioRecommenderSystem{
	private static Parser n = null;
	private static Song[] s = null;
	private static Station[] ss = null;
			
	/**
	 * Initializes the Parser and the RadioRecommenderSystem. Asks for user input through the console afterwards.
	 * Should keep asking for input indefinitely. The user can input the following commands:
	 *   similarsong <song ID>          - Finds the most similar song to the chosen song.
	 *   stats <song ID>                - Prints statistics of the chosen song.
	 *   recommend <station ID>         - Recommends a song to the chosen station.
	 *   similarradio <station ID>      - Finds the most similar radio station to the chosen station.
	 *   exit                           - Exits the program.
	 * 
	 * @param args The first argument should contain the folder path for the three files. 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		n = new Parser("songs.txt", "stations.txt", "playlists.txt");
		s = n.getSongs();
		ss = n.getStations();
		RadioRecommenderSystem rad = new RadioRecommenderSystem(s, ss);
		String station = null;
		do{
			Scanner console = new Scanner(System.in);
			System.out.println("Input command (choices: similar song, stats, recommend, similar radio, and exit");
			station = console.next();
			if (station == "similar song"){
				System.out.println("What is the song id?");
				int id = console.nextInt();
				System.out.println(rad.closestSong(id));
			}
			if (station == "stats"){
				System.out.println("What is the song id?");
				int id1 = console.nextInt();
				int[] stats = s[id1].getStatistics();
				System.out.println("Average number of plays on each station that carries it:" + stats[0]);
				System.out.println("Total number of plays across all stations:" + stats[1]);
				System.out.println("Station that plays this specific song most often:" + ss[stats[2]]);
				System.out.println("Maximum number of plays on one station:" + stats[3]);
				System.out.println("Minimum number of plays on any one station:" + stats[5]);
			}
			if (station == "recommend"){
				System.out.println("What is the station id?");
				int id2 = console.nextInt();
				System.out.println(rad.bestRecommendation(id2));
			}
			if (station == "similar radio"){
				System.out.println("What is the station id?");
				int id3 = console.nextInt();
				System.out.println(rad.closestStation(id3));
			}
			if (station != "similar radio" || station != "stats" || station != "recommend" || station != "similar song"){
				System.out.println("Invalid input, try again");
			}
		} while (station != "exit");
	}


	/**
	 * Constructor. Should store a local copy of the arrays.
	 * @param songs
	 * @param stations
	 */
	public RadioRecommenderSystem(Song[] songs, Station[] stations) {
		s = songs;
		ss = stations;
	}

	/**
	 * Returns the song which is most similar to the song with the corresponding songID.
	 * In the case of a tie, return the song with the lowest ID.
	 * @param songID ID of the original song
	 * @return -1 if the given songID is invalid
	 *         -2 if there are not enough songs 
     *          otherwise the ID of the most similar song 
	 * @throws IOException 
	 */
	public int closestSong(int songID) throws IOException {
		if (songID >= s.length || songID < 0)
			return -1;
		Song testcase = s[songID];
		int songnum = 0;
		int i = 0;
		while (i < s.length){
			double standard = 0;
			double j = songSimilarity(testcase, s[i]);
			if (j > standard){
				standard = j;
				songnum = i;
			}
			if (i == songID-1)
				i = i + 2;
			else
				i++;
		}
		if (songnum == songID)
			return -2;
		return songnum;
	}

	/**
	 * Computes the similarity of two given songs
	 * @param s1 First song
	 * @param s2 Second song
	 * @return Double representing the similarity between the songs
	 * @throws IOException 
	 */
	public double songSimilarity(Song s1, Song s2) throws IOException {
		int[] songsim1 = s1.getStationPlays();
		int[] songsim2 = s2.getStationPlays();
		double numerator = 0;
		double denominator1 = 0;
		double denominator2 = 0;
		for (int i = 0; i < songsim1.length; i++){
			numerator = numerator + songsim1[i] * songsim2[i];
			denominator1 = denominator1 + Math.pow(songsim1[i], 2);
			denominator2 = denominator2 + Math.pow(songsim2[i], 2);
		}
		double denominator = Math.sqrt(denominator1) * Math.sqrt(denominator2);
		return numerator/denominator;
	}

	/**
	 * Returns the station which is most similar to the station with the corresponding radioID.
	 * In the case of a tie, return the station with the lowest ID.
	 * @param radioID ID of the original radio station
	 * @return -1 if the given radioID is invalid
	 *         -2 if there are not enough stations 
     *          otherwise the ID of the most similar station
	 * @throws IOException 
	 */
	public int closestStation(int radioID) throws IOException {
		if (radioID >= ss.length || radioID < 0){
			return -1;
		}
		Station testcase = ss[radioID];
		int radionum = 0;
		int i = 0;
		while (i < ss.length){
			double standard = 0;
			double j = stationSimilarity(testcase, ss[i]);
			if (j > standard){
				standard = j;
				radionum = i;
			}
			if (i == radioID-1)
				i = i + 2;
			else
				i++;
		}
		if (radionum == radioID)
			return -2;
		return radionum;
	}

	/**
	 * Computes the similarity of two given stations
	 * @param s1 First station
	 * @param s2 Second station
	 * @return Double representing the similarity between the stations
	 * @throws IOException 
	 */
	public double stationSimilarity(Station s1, Station s2) throws IOException {
		double numerator = 0;
		double denominator1 = 0;
		double denominator2 = 0;
		int[] test = null;
		for (int i = 0; i < s.length; i++){
			test = s[i].getStationPlays();
			numerator = numerator + test[s1.getID()] * test[s2.getID()];
			denominator1 = denominator1 + Math.pow(test[s1.getID()], 2);
			denominator2 = denominator2 + Math.pow(test[s2.getID()], 2);
		}
		double denominator = Math.sqrt(denominator1) * Math.sqrt(denominator2);
		return numerator / denominator;
	}

	/**
	 * Gets the song with the highest recommendation for the given station that isn't 
	 * already played by the station.
	 * @param radioID ID of the station for which we want a recommendation.
	 * @return -1 if the given radioID is invalid
	 *         -2 if there are not enough stations to make a recommendation
	 *         -3 if there are not enough songs to make a recommendation
     *          otherwise the ID of the most highly recommended song for this station
	 * @throws IOException 
	 */
	public int bestRecommendation(int radioID) throws IOException {
		if (radioID >= ss.length || radioID < 0){
			return -1;
		}
		int recommendation = 0;
		int i = 0;
		while (i < s.length){
			double standard = 0;
			double j = makeRecommendation(s[i].getID(), radioID);
			if (j > standard){
				standard = j;
				recommendation = i;
			}
			if (i == radioID-1)
				i = i + 2;
			else
				i++;
		}
		if (recommendation == radioID)
			return -2;
		return recommendation;
	}

	/**
	 * Computes the recommendation of a given song to a particular radio station
	 * @param recSongID Recommended song ID
	 * @param radioID ID of station being recommended to
	 * @return Value indicating how highly recommended is the song
	 * @return -1 if the given radioID is invalid
	 *         -2 if the given recSongID is invalid
     *          otherwise a value indicating how highly recommended is the song
	 * @throws IOException 
	 */
	public double makeRecommendation(int recSongID, int radioID) throws IOException {
		if (recSongID >= s.length || recSongID < 0)
			return -2;
		if (radioID >= ss.length || radioID < 0)
			return -1;
		double num = 0;
		double den = 0;
		double piece = 0;
		double totalsongs = s.length;
		double total = ss[radioID].getPlaylistLength() / totalsongs;
		int[] fly = s[recSongID].getStationPlays();
		for (int j = 0; j < ss.length; j++){
			if (j != radioID && fly[radioID] != 0)
				den = den + stationSimilarity(ss[j], ss[radioID]);
		}
		for (int i = 0; i < ss.length; i++){
			int[] r = s[recSongID].getStationPlays();
			if (i != radioID && fly[radioID] != 0)
				num = stationSimilarity(ss[i], ss[radioID]) + (r[radioID] - ss[i].getPlaylistLength() / totalsongs);
			piece = num / den;
			total = total + piece;
		}
		return total;
	}

}
