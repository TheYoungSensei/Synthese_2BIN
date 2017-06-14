package be.ipl.csacre15.jemanager.businessDays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessDayContent {


    public static List<BusinessDayItem> ITEMS = new ArrayList<BusinessDayItem>();

    public static Map<String, BusinessDayItem> ITEM_MAP = new HashMap<String, BusinessDayItem>();

    public static void addItem(BusinessDayItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void clearAll() {
        ITEMS = new ArrayList<>();
        ITEM_MAP = new HashMap<>();
    }

    public static class BusinessDayItem {
        public final String id;
        public final String content;
        public final int details;

        public BusinessDayItem(String id, String content, int details) {
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
