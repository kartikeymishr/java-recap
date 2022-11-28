package com.kartikey.databases.music.model;

public class DataSourceConstants {
    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:E:\\Workspaces\\IdeaProjects\\java-recap\\" + DB_NAME;
    public static final String TABLE_ALBUMS = "albums";
    public static final String CL_ALBUM_ID = "_id";
    public static final String CL_ALBUM_NAME = "name";
    public static final String QUERY_ALBUMS_BY_ARTIST_SORT = " order by " + TABLE_ALBUMS + "." + CL_ALBUM_NAME + " collate nocase ";
    public static final String CL_ALBUM_ARTIST = "artist";
    public static final String TABLE_ARTISTS = "artists";
    public static final String CL_ARTIST_ID = "_id";
    public static final String CL_ARTIST_NAME = "name";
    public static final String TABLE_SONGS = "songs";
    public static final String CL_SONG_ID = "_id";
    public static final String CL_SONG_TRACK = "track";
    public static final String CL_SONG_TITLE = "title";
    public static final String CL_SONG_ALBUM = "album";

    public static final String QUERY_ARTIST_FOR_SONG_SORT = " order by " + TABLE_ARTISTS + "." + CL_ARTIST_NAME + ", "
            + TABLE_ALBUMS + "." + CL_ALBUM_NAME + " collate nocase ";
    public static final String QUERY_ALBUM_BY_ARTIST_START = "select " + TABLE_ALBUMS + "." + CL_ALBUM_NAME
            + " from " + TABLE_ALBUMS + " inner join " + TABLE_ARTISTS + " on " + TABLE_ALBUMS + "." + CL_ALBUM_ARTIST
            + " = " + TABLE_ARTISTS + "." + CL_ARTIST_ID + " where " + TABLE_ARTISTS + "." + CL_ARTIST_NAME + " = \"";

    public static final String QUERY_ARTIST_FOR_SONG_START = "select " + TABLE_ARTISTS + "." + CL_ARTIST_NAME + ", "
            + TABLE_ALBUMS + "." + CL_ALBUM_NAME + ", " + TABLE_SONGS + "." + CL_SONG_TRACK + " from " + TABLE_SONGS
            + " inner join " + TABLE_ALBUMS + " on " + TABLE_SONGS + "." + CL_SONG_ALBUM + " = " + TABLE_ALBUMS + "." + CL_ALBUM_ID
            + " inner join " + TABLE_ARTISTS + " on " + TABLE_ALBUMS + "." + CL_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + CL_ARTIST_ID
            + " where " + TABLE_SONGS + "." + CL_SONG_TITLE + " = \"";

    public static final String TABLE_ARTIST_SONG_VIEW = "artist_list";
    public static final String CREATE_ARTIST_FOR_SONG_VIEW = "create view if not exists " + TABLE_ARTIST_SONG_VIEW
            + " as select " + TABLE_ARTISTS + "." + CL_ARTIST_NAME + ", " + TABLE_ALBUMS + "." + CL_ALBUM_NAME
            + " as " + CL_SONG_ALBUM + ", " + TABLE_SONGS + "." + CL_SONG_TRACK + ", " + TABLE_SONGS + "." + CL_SONG_TITLE
            + " from " + TABLE_SONGS + " inner join " + TABLE_ALBUMS + " on " + TABLE_SONGS + "." + CL_SONG_ALBUM
            + " = " + TABLE_ALBUMS + "." + CL_ALBUM_ID + " inner join " + TABLE_ARTISTS + " on " + TABLE_ALBUMS
            + "." + CL_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + CL_ARTIST_ID + " order by "
            + TABLE_ARTISTS + "." + CL_ARTIST_NAME + ", " + TABLE_ALBUMS + "." + CL_ALBUM_NAME + ", "
            + TABLE_SONGS + "." + CL_SONG_TRACK;

    public static final String QUERY_VIEW_SONG_INFO = "select " + CL_ARTIST_NAME + ", " + CL_SONG_ALBUM + ", " + CL_SONG_TRACK
            + " from " + TABLE_ARTIST_SONG_VIEW + " where " + CL_SONG_TITLE + " = \"";

    public static final String QUERY_VIEW_SONG_INFO_PREP = "select " + CL_ARTIST_NAME + ", " + CL_SONG_ALBUM
            + ", " + CL_SONG_TRACK + " from " + TABLE_ARTIST_SONG_VIEW + " where " + CL_SONG_TITLE + " = ?";

    public static final String INSERT_ARTIST = "insert into " + TABLE_ARTISTS + "(" + CL_ARTIST_NAME + ") values(?)";
    public static final String INSERT_ALBUMS = "insert into " + TABLE_ALBUMS + "(" + CL_ALBUM_NAME + ", " + CL_ALBUM_ARTIST + ") values(?, ?)";
    public static final String INSERT_SONGS = "insert into " + TABLE_SONGS + "(" + CL_SONG_TRACK + ", " + CL_SONG_TITLE + ", " + CL_SONG_ALBUM + ") values (?, ?, ?)";

    public static final String QUERY_ARTIST = "select " + CL_ARTIST_ID + " from " + TABLE_ARTISTS + " where " + CL_ARTIST_NAME + " = ?";
    public static final String QUERY_ALBUM = "select " + CL_ALBUM_ID + " from " + TABLE_ALBUMS + " where " + CL_ALBUM_NAME + " = ?";
    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;
    public static final String DESC = "desc ";
    public static final String ASC = "asc ";
}
