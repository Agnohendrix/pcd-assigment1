package shared;

public class SubdividedCounter extends Counter {
    private int nIntervals;
    private int nLines;
    private int[] counters;
    public SubdividedCounter(int nIntervals, int nLines){
        this.nIntervals = nIntervals;
        this.nLines = nLines;
        this.counters = new int[nIntervals];
    }

    public synchronized void inc(int value){
        if (value >= nLines){
            counters[nIntervals - 1]++;
        } else {
            int intervalLength = nLines / (nIntervals - 1);
            for(int i = 0; i < nIntervals - 1; i++){
                if(value > intervalLength*i && value <= intervalLength*(i+1) ){
                    counters[i]++;
                }
            }
        }
    }

    public synchronized int[] getAllCounters(){
        return counters;
    }

}
