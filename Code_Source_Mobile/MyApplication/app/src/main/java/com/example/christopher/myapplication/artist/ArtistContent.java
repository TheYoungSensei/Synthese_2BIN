package com.example.christopher.myapplication.artist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ArtistContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ArtistItem> ITEMS = new ArrayList<ArtistItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ArtistItem> ITEM_MAP = new HashMap<String, ArtistItem>();

    private static final int COUNT = 25;


    public static void addItem(ArtistItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ArtistItem createArtistItem(int position) {
        return new ArtistItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ArtistItem {
        public final String id;
        public final String content;
        public final String details;

        public ArtistItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
