package shared;

import java.awt.*;

public class SourceFileList<SourceFile> extends List {

    public SourceFileList(){}

    //TO-DO
    public SourceFileList getFirstFiveLongestFiles(){
        return this;
    }

    public void synchronizedAdd(String file) throws InterruptedException {
        synchronized (this){
            this.add(file);
        }
    }
}
