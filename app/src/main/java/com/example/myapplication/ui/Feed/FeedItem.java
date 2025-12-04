package com.example.myapplication.ui.Feed;

public class FeedItem {

    private static int latestId = 0;
    private final int postId = ++latestId;
    private int clubImage;
    private String clubName;
    private String description;
    private int postImage;
    //boolean isLiked = false;

    public FeedItem(int clubImage, String clubName, String description, int postImage) {
        this.clubImage = clubImage;
        this.clubName = clubName;
        this.description = description;
        this.postImage = postImage;
    }

    public int getClubImage() {
        return clubImage;
    }

    public void setClubImage(int clubImage) {
        this.clubImage = clubImage;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }

//    public boolean isLiked() {
//        return isLiked;
//    }
//
//    public void setLiked(boolean liked) {
//        isLiked = liked;
//    }

    public int getPostId(){
        return postId;
    }
}
