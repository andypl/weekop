package pl.info.czerniak.weekop.dao;

import pl.info.czerniak.weekop.model.Vote;

public interface VoteDAO extends GenericDAO<Vote, Long> {
    public Vote getVoteByUserIdDiscoveryId(long userId, long discoveryId);
}
