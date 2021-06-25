package song;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SongDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public SongDAO() {

		try {
			String dbURL = "jdbc:mysql://localhost:3306/BANDUS";
			String dbID = "root";
			String dbPassword = "1234";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch (Exception e) {
			// TODO: handle exception
		}	
	}

	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public int getNext() {
		String SQL = "SELECT song_no FROM SONG ORDER BY song_no DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1;
			}
			return 1;	// 첫번째 게시물인 경우

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int post(String song_name, String artist_name,String song_url, String album_name, String song_comment,String song_imgurl) {
		String SQL = "INSERT INTO SONG VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, song_name);
			pstmt.setString(3, getDate());
			pstmt.setString(4, artist_name);
			pstmt.setString(5, song_url);
			pstmt.setString(6, album_name);
			pstmt.setString(7, song_comment);
			pstmt.setInt(8, 0);
			pstmt.setDouble(9, 0.0);
			pstmt.setString(10, song_imgurl);
			rs = pstmt.executeQuery();
			return pstmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
		
	}


}
