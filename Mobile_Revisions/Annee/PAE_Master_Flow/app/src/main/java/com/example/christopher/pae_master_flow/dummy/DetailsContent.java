package com.example.christopher.pae_master_flow.dummy;

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
public class DetailsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DetailsItem> ITEMS = new ArrayList<DetailsItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DetailsItem> ITEM_MAP = new HashMap<String, DetailsItem>();

    private static final int COUNT = 25;

    public static void addItem(DetailsItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DetailsItem createDummyItem(int position) {
        return new DetailsItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static void reinitialize() {
        ArrayList<DetailsItem> temp = new ArrayList(ITEMS);
        for(DetailsItem item : temp) {
            ITEMS.remove(item);
        }
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DetailsItem {
        public final String id;
        public final String content;
        public final String details;

        public DetailsItem(String id, String content, String details) {
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
