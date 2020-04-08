package pl.info.czerniak.weekop.controller;

import pl.info.czerniak.weekop.model.Discovery;
import pl.info.czerniak.weekop.model.User;
import pl.info.czerniak.weekop.model.Vote;
import pl.info.czerniak.weekop.model.VoteType;
import pl.info.czerniak.weekop.service.DiscoveryService;
import pl.info.czerniak.weekop.service.VoteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (urlPatterns = "/vote")
public class VoteController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedUser = (User) req.getSession().getAttribute("user");
        if(loggedUser != null){
            VoteType voteType = VoteType.valueOf(req.getParameter("vote"));
            long userId = loggedUser.getId();
            long discoveryId = Long.parseLong(req.getParameter("discovery_id"));
            updateVote(userId, discoveryId, voteType);
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }

    private void updateVote(long userId, long discoveryId, VoteType voteType) {
        VoteService voteService = new VoteService();
        Vote existingVote = voteService.getVoteByDiscoveryIdUserId(userId, discoveryId);
        Vote newVote = voteService.addOrUpdateVote(discoveryId, userId, voteType);
        if(!newVote.equals(existingVote)){
            updateDiscovery(discoveryId, existingVote, newVote);
        }

    }

    private void updateDiscovery(long discoveryId, Vote existingVote, Vote newVote) {
        DiscoveryService discoveryService = new DiscoveryService();
        Discovery discoveryById = discoveryService.getDiscoveryById(discoveryId);
        Discovery discoveryUpdate = null;
        if(existingVote == null && newVote != null){
            discoveryUpdate = addDiscoveryVote(discoveryById, newVote.getVoteType());
        }else if(existingVote != null && newVote != null){
            discoveryUpdate = removeDiscoveryVote(discoveryById, existingVote.getVoteType());
            discoveryUpdate = addDiscoveryVote(discoveryUpdate, newVote.getVoteType());
        }
        discoveryService.updateDiscovery(discoveryUpdate);
    }



    private Discovery addDiscoveryVote(Discovery discovery, VoteType voteType) {
        Discovery discoveryCopy = new Discovery(discovery);
        if(voteType == VoteType.VOTE_UP){
            discoveryCopy.setUpVote(discoveryCopy.getUpVote() + 1);
        }else if(voteType == VoteType.VOTE_DOWN){
            discoveryCopy.setDownVote(discoveryCopy.getDownVote() + 1);
        }
        return discoveryCopy;
    }

    private Discovery removeDiscoveryVote(Discovery discovery, VoteType voteType) {
        Discovery discoveryCopy = new Discovery(discovery);
        if(voteType == VoteType.VOTE_UP){
            discoveryCopy.setUpVote(discoveryCopy.getUpVote() - 1);
        }else if(voteType == VoteType.VOTE_DOWN){
            discoveryCopy.setUpVote(discoveryCopy.getDownVote() - 1);
        }
        return discoveryCopy;
    }

}
