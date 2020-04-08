package pl.info.czerniak.weekop.service;

import pl.info.czerniak.weekop.dao.DAOFactory;
import pl.info.czerniak.weekop.dao.VoteDAO;
import pl.info.czerniak.weekop.model.Vote;
import pl.info.czerniak.weekop.model.VoteType;

import java.sql.Timestamp;
import java.util.Date;

public class VoteService {
    public Vote addVote(long discoveryId, long userId, VoteType voteType){
        Vote vote = new Vote();
        vote.setDiscoveryId(discoveryId);
        vote.setUserId(userId);
        vote.setDate(new Timestamp(new Date().getTime()));
        vote.setVoteType(voteType);
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDAO = factory.getVoteDAO();
        vote = voteDAO.create(vote);
        return vote;
    }

    public Vote updateVote(long discoveryId, long userId, VoteType voteType){
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDAO = factory.getVoteDAO();
        Vote voteToUpdate = voteDAO.getVoteByUserIdDiscoveryId(userId, discoveryId);
        if(voteToUpdate != null){
            voteToUpdate.setVoteType(voteType);
            voteDAO.update(voteToUpdate);
        }
        return voteToUpdate;
    }

    public Vote addOrUpdateVote(long discoveryId, long userId, VoteType voteType){
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDAO = factory.getVoteDAO();
        Vote vote = voteDAO.getVoteByUserIdDiscoveryId(userId, discoveryId);
        Vote resultVote = null;
        if(vote == null){
            resultVote = addVote(discoveryId, userId, voteType);
        }else{
            resultVote = updateVote(discoveryId, userId, voteType);
        }
        return resultVote;
    }

    public Vote getVoteByDiscoveryIdUserId(long userId, long discoveryId){
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDAO = factory.getVoteDAO();
        Vote vote = voteDAO.getVoteByUserIdDiscoveryId(userId, discoveryId);
        return vote;
    }
}
