package com.kartikey.databases.music.model;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private Connection connection;

    private PreparedStatement querySongInfoView;
    private PreparedStatement insertArtists;
    private PreparedStatement insertAlbums;
    private PreparedStatement insertSongs;
    private PreparedStatement queryArtist;
    private PreparedStatement queryAlbum;


    public boolean open() {
        try {
            connection = DriverManager.getConnection(DataSourceConstants.CONNECTION_STRING);
            querySongInfoView = connection.prepareStatement(DataSourceConstants.QUERY_VIEW_SONG_INFO_PREP);
            insertArtists = connection.prepareStatement(DataSourceConstants.INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
            insertAlbums = connection.prepareStatement(DataSourceConstants.INSERT_ALBUMS, Statement.RETURN_GENERATED_KEYS);
            insertSongs = connection.prepareStatement(DataSourceConstants.INSERT_SONGS);
            queryArtist = connection.prepareStatement(DataSourceConstants.QUERY_ARTIST);
            queryAlbum = connection.prepareStatement(DataSourceConstants.QUERY_ALBUM);

            return true;
        } catch (SQLException e) {
            System.out.println("There's a problem :: Couldn't connect to the DB");
            return false;
        }
    }

    public void close() {
        try {
            if (querySongInfoView != null) querySongInfoView.close();
            if (insertArtists != null) insertArtists.close();
            if (insertAlbums != null) insertAlbums.close();
            if (insertSongs != null) insertSongs.close();
            if (queryArtist != null) queryArtist.close();
            if (queryAlbum != null) queryAlbum.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Couldn't close connection :: " + e.getMessage());
        }
    }

    public List<Artist> queryArtists(int sortOrder) {
        StringBuilder sb = new StringBuilder("select * from ");
        sb.append(DataSourceConstants.TABLE_ARTISTS);
        if (sortOrder != DataSourceConstants.ORDER_BY_NONE) {
            sb.append(" order by ");
            sb.append(DataSourceConstants.CL_ARTIST_NAME);
            sb.append(" collate nocase ");
            if (sortOrder == DataSourceConstants.ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sb.toString())) {
            List<Artist> artists = new ArrayList<>();

            while (resultSet.next()) {
                Artist artist = new Artist();
                artist.setId(resultSet.getInt(DataSourceConstants.CL_ARTIST_ID));
                artist.setName(resultSet.getString(DataSourceConstants.CL_ARTIST_NAME));
                artists.add(artist);
            }

            return artists;
        } catch (SQLException ex) {
            System.out.println("Query failed :: " + ex.getMessage());
            return null;
        }
    }

    public List<Album> queryAlbums() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from " + DataSourceConstants.TABLE_ALBUMS)) {
            List<Album> albums = new ArrayList<>();

            while (resultSet.next()) {
                Album album = new Album();
                album.setId(resultSet.getInt(DataSourceConstants.CL_ALBUM_ID));
                album.setName(resultSet.getString(DataSourceConstants.CL_ALBUM_NAME));
                album.setArtistId(resultSet.getInt(DataSourceConstants.CL_ALBUM_ARTIST));
                albums.add(album);
            }

            return albums;
        } catch (SQLException ex) {
            System.out.println("Query failed :: " + ex.getMessage());
            return null;
        }
    }

    public List<Song> querySongs() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from " + DataSourceConstants.TABLE_SONGS)) {
            List<Song> songs = new ArrayList<>();

            while (resultSet.next()) {
                Song song = new Song();
                song.setId(resultSet.getInt(DataSourceConstants.CL_SONG_ID));
                song.setName(resultSet.getString(DataSourceConstants.CL_SONG_TITLE));
                song.setTrack(resultSet.getInt(DataSourceConstants.CL_SONG_TRACK));
                song.setAlbumId(resultSet.getInt(DataSourceConstants.CL_SONG_ALBUM));
                songs.add(song);
            }

            return songs;
        } catch (SQLException ex) {
            System.out.println("Query failed :: " + ex.getMessage());
            return null;
        }
    }

    public List<String> queryAlbumsForArtist(String artistName, int sortOrder) {
        StringBuilder sb = new StringBuilder(DataSourceConstants.QUERY_ALBUM_BY_ARTIST_START);
        sb.append(artistName).append("\"");

        if (sortOrder != DataSourceConstants.ORDER_BY_NONE) {
            sb.append(DataSourceConstants.QUERY_ALBUMS_BY_ARTIST_SORT).append(sortOrder == DataSourceConstants.ORDER_BY_DESC ? DataSourceConstants.DESC : DataSourceConstants.ASC);
        }

        System.out.println("SQL Statement == " + sb.toString());

        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<String> albums = new ArrayList<>();

            while (results.next()) {
                albums.add(results.getString(1));
            }

            return albums;
        } catch (SQLException ex) {
            System.out.println("Query failed :: " + ex.getMessage());
            return null;
        }
    }

    public List<SongArtist> queryArtistBySong(String songTitle, int sortOrder) {
        StringBuilder sb = new StringBuilder(DataSourceConstants.QUERY_ARTIST_FOR_SONG_START);
        sb.append(songTitle).append("\"");

        if (sortOrder != DataSourceConstants.ORDER_BY_NONE) {
            sb.append(DataSourceConstants.QUERY_ARTIST_FOR_SONG_SORT);
            sb.append(sortOrder == DataSourceConstants.ORDER_BY_DESC ? DataSourceConstants.DESC : DataSourceConstants.ASC);
        }

        String sqlQuery = sb.toString();
        System.out.println("SQL Query == " + sqlQuery);

        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sqlQuery)) {
            return getSongArtists(results);
        } catch (SQLException ex) {
            System.out.println("Query failed :: " + ex.getMessage());
            return null;
        }
    }

    public int getCount(String table) {
        String sql = "select count(*) as count from " + table;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return resultSet.getInt("count");
        } catch (SQLException e) {
            System.out.println("Query failed :: " + e.getMessage());
            return -1;
        }
    }

    public boolean createViewForSongArtists() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DataSourceConstants.CREATE_ARTIST_FOR_SONG_VIEW);
            return true;
        } catch (SQLException e) {
            System.out.println("Create view failed :: " + e.getMessage());
            return false;
        }
    }

    public List<SongArtist> querySongInfoView(String title) {
        try {
            querySongInfoView.setString(1, title);
            ResultSet resultSet = querySongInfoView.executeQuery();

            return getSongArtists(resultSet);
        } catch (SQLException e) {
            System.out.println("Query on View failed :: " + e.getMessage());
            return null;
        }
    }

    @NotNull
    private List<SongArtist> getSongArtists(ResultSet resultSet) throws SQLException {
        List<SongArtist> songArtistList = new ArrayList<>();

        while (resultSet.next()) {
            SongArtist songArtist = new SongArtist();
            songArtist.setArtistName(resultSet.getString(1));
            songArtist.setAlbumName(resultSet.getString(2));
            songArtist.setTrack(resultSet.getInt(3));

            songArtistList.add(songArtist);
        }

        return songArtistList;
    }

    public void querySongsMetadata() {
        String sql = "select * from " + DataSourceConstants.TABLE_SONGS;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumns = metaData.getColumnCount();
            for (int i = 1; i <= numColumns; i++) {
                System.out.format("Column %d in the songs table is named %s\n", i, metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            System.out.println("Query failed :: " + e.getMessage());
        }
    }

    private int insertArtist(String name) throws SQLException {
        queryArtist.setString(1, name);
        ResultSet resultSet = queryArtist.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            insertArtists.setString(1, name);
            int affectedRows = insertArtists.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert artist");
            }

            ResultSet generatedKeys = insertArtists.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for the artist");
            }
        }
    }

    private int insertAlbum(String name, int artistId) throws SQLException {
        queryAlbum.setString(1, name);
        ResultSet resultSet = queryAlbum.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            insertAlbums.setString(1, name);
            insertAlbums.setInt(2, artistId);
            int affectedRows = insertAlbums.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert album");
            }

            ResultSet generatedKeys = insertAlbums.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for the album");
            }
        }
    }

    public void insertSong(String title, String artist, String album, int track) {
        try {
            connection.setAutoCommit(false);

            int artistId = insertArtist(artist);
            int albumId = insertAlbum(album, artistId);
            insertSongs.setInt(1, track);
            insertSongs.setString(2, title);
            insertSongs.setInt(3, albumId);

            int affectedRows = insertSongs.executeUpdate();

            if (affectedRows == 1) {
                connection.commit();
            } else {
                throw new SQLException("Song insertion failed");
            }
        } catch (Exception e) {
            System.out.println("Insert song exception :: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println("Oh boy, things are really bad! :: " + e1.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behaviour");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit :: " + e.getMessage());
            }
        }
    }
}
