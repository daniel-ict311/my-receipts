package au.edu.usc.dcd008.myreceipts.database;

public class ReceiptDbSchema {
    public static final class ReceiptTable {
        public static final String NAME = "receipts";

        public static final class cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SHOP_NAME = "shop_name";
            public static final String COMMENT = "comment";
            public static final String LOCATION = "location";
        }
    }
}
