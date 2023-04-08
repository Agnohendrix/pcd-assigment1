package threads;

import model.SourceFile;
import shared.SourceFileList;

import java.io.File;

public class Searcher extends Thread {
    private int fileCount;

    private File path;

    private SourceFileList sfl;
    public Searcher(File path, SourceFileList sfl){
        this.path = path;
        this.sfl = sfl;
    }

    public void run(){
            System.out.println("Thread " + path + " spawned");
            seeFolderContent(path);
    }

    public void seeFolderContent(File path){
        for(final File fileEntry: path.listFiles()){
            if(isJavaFileExtension(fileEntry)){
                System.out.println("Is " + fileEntry + " java? " + isJavaFileExtension(fileEntry));
                //Spawn SourceFileAnalyzer
                //...
                SourceFile sf = new SourceFile(fileEntry.getName(), 1);
                sfl.add(fileEntry.getName());
            } else if (fileEntry.isDirectory()){
                seeFolderContent(fileEntry);
            }

        }
    }

    private boolean isJavaFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return false; // empty extension
        }
        return name.substring(lastIndexOf).equals(".java");
    }
}
