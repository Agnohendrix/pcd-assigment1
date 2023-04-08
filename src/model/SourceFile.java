package model;

public class SourceFile {
    private String path;
    private int length;

    public SourceFile(String path, int length){
        this.path = path;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
