public class Tag {
    private int userID;
    private int movieID;
    private String tag;
    private long timestamp;

    public Tag(int userID, int movieID, String tag, long timestamp) {
        this.userID = userID;
        this.movieID = movieID;
        this.tag = tag;
        this.timestamp = timestamp;
    }

    public int getUserID() {
        return userID;
    }

    public int getMovieID() {
        return movieID;
    }

    public String getTag() {
        return tag;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
