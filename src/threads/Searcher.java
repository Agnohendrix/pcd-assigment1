package threads;

import model.SourceFile;
import shared.IBoundedBuffer;

import java.io.File;

public class Searcher extends Thread {

    private IBoundedBuffer buffer;
    private File path;

    public Searcher(File path,  IBoundedBuffer buffer){
        this.path = path;
        this.buffer = buffer;
    }

    public void run(){
            System.out.println("Thread " + path + " spawned");
            seeFolderContent(path);
    }

    public void seeFolderContent(File path){
        for(final File fileEntry: path.listFiles()){
            if(isJavaFileExtension(fileEntry)){
                //Add file to sourcefilelist
                SourceFile sf = new SourceFile(path.getPath() + '\\' + fileEntry.getName(), 1);
                try {
                    buffer.put(sf);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else if (fileEntry.isDirectory()){
                //Recursively check nested folders
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
