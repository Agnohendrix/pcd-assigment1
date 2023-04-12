package shared;

import model.SourceFile;

import java.util.*;

public class FileList {
    private final int filesToKeep;
    private final Queue<SourceFile> queue = new PriorityQueue<>(Comparator.comparingInt(SourceFile::getLength));

    public FileList(int filesToKeep) {
        this.filesToKeep = filesToKeep;
    }

    public synchronized void put(SourceFile fileInfo) throws InterruptedException {
        if (this.queue.size() < this.filesToKeep ||
                fileInfo.getLength() > Objects.requireNonNull(this.queue.peek()).getLength()) {
            this.queue.offer(fileInfo);
            if (this.queue.size() > this.filesToKeep) {
                this.queue.poll();
            }
        }
    }

    public Collection<SourceFile> get() {
        return Collections.unmodifiableCollection(new PriorityQueue<>(this.queue));
    }
}
