package be.ipl.csacre15.lastfragment.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtistContent {


    public static List<ArtistItem> items = new ArrayList<ArtistItem>();
    public static Map<String, ArtistItem> items_map = new HashMap<String, ArtistItem>();

    public static void addItem(ArtistItem item) {
        items.add(item);
        items_map.put(item.id, item);
    }

    public static void clearAll() {
        items = new ArrayList<>();
        items_map = new HashMap<>();
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
