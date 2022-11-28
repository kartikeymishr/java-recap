package com.kartikey.databases.music;

import com.kartikey.databases.music.model.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        // if (printArtists(dataSource)) return;
        // if (printAlbums(dataSource)) return;
        // if (printSongs(dataSource)) return;

        /*List<String> albumsForArtist = dataSource.queryAlbumsForArtist("Pink Floyd", DataSourceConstants.ORDER_BY_DESC);
        albumsForArtist.forEach(System.out::println);

        List<SongArtist> songArtists = dataSource.queryArtistBySong("Go Your Own Way", DataSourceConstants.ORDER_BY_ASC);
        songArtists.forEach(System.out::println);*/

        // dataSource.querySongsMetadata();

        // int count = dataSource.getCount(DataSourceConstants.TABLE_SONGS);
        // System.out.println(count);

        /*dataSource.createViewForSongArtists();
        List<SongArtist> songArtistList = dataSource.querySongInfoView("Go Your Own Way");
        songArtistList.forEach(System.out::println);*/

//        dataSource.insertSong();

        dataSource.close();
    }

    private static boolean printSongs(DataSource dataSource) {
        List<Song> songs = dataSource.querySongs();
        if (songs == null) {
            System.out.println("No artists found");
            return true;
        }
        for (Song song : songs) {
            System.out.println(song.toString());
        }
        return false;
    }

    private static boolean printAlbums(DataSource dataSource) {
        List<Album> albums = dataSource.queryAlbums();
        if (albums == null) {
            System.out.println("No artists found");
            return true;
        }
        for (Album album : albums) {
            System.out.println(album.toString());
        }
        return false;
    }

    private static boolean printArtists(DataSource dataSource) {
        List<Artist> artists = dataSource.queryArtists(DataSourceConstants.ORDER_BY_ASC);
        if (artists == null) {
            System.out.println("No artists found");
            return true;
        }
        for (Artist artist : artists) {
            System.out.println(artist.toString());
        }
        return false;
    }
}
