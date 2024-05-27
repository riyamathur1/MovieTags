public class TagFrequency {
    private String tag;
    private int count;

    public TagFrequency(String tag, int count) {
        this.tag = tag;
        this.count = count;
    }

    public String getTag() {
        return tag;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }
}
