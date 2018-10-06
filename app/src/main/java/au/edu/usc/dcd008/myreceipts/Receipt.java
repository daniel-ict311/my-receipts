package au.edu.usc.dcd008.myreceipts;

import java.util.Date;
import java.util.UUID;

public class Receipt {
        private UUID mId;
        private String mTitle;
        private String mShopName;
        private String mComment;
        private Date mDate;
        private Double mLatitude;
        private Double mLongitude;

        public Receipt(){
            this(UUID.randomUUID());
        }

        public Receipt(UUID uuid){
            mId = uuid;
            mDate = new Date();
        }

        public String getPhotoFilename(){
            return "IMG_" + getId().toString() + ".jpg";
        }

        public UUID getId() {
            return mId;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public Date getDate() {
            return mDate;
        }

        public void setDate(Date date) {
            mDate = date;
        }

        public String getShopName() {
            return mShopName;
        }

        public void setShopName(String shopName) {
            mShopName = shopName;
        }

        public String getComment() {
            return mComment;
        }

        public void setComment(String comment) {
            mComment = comment;
        }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        mLongitude = longitude;
    }
}