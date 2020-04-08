package pl.info.czerniak.weekop.comparator;

import pl.info.czerniak.weekop.model.Discovery;

import java.util.Comparator;

public class DiscoveryVoteUpComparator implements Comparator<Discovery> {
    @Override
    public int compare(Discovery d1, Discovery d2) {
        int d1Result = d1.getUpVote() - d1.getDownVote();
        int d2Result = d2.getUpVote() - d2.getDownVote();
        if(d1Result < d2Result){
            return 1;
        }else if(d1Result > d2Result){
            return -1;
        }
        return 0;
    }
}
