package pl.info.czerniak.weekop.service;

import pl.info.czerniak.weekop.dao.DAOFactory;
import pl.info.czerniak.weekop.dao.DiscoveryDAO;
import pl.info.czerniak.weekop.model.Discovery;
import pl.info.czerniak.weekop.model.User;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DiscoveryService {
    public void addDiscovery(String name, String desc, String url, User user){
        Discovery discovery = createDiscoveryObject(name, desc, url, user);
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDAO = factory.getDiscoveryDAO();
        discoveryDAO.create(discovery);
    }

    private Discovery createDiscoveryObject(String name, String desc, String url, User user) {
        Discovery discovery = new Discovery();
        discovery.setName(name);
        discovery.setDescription(desc);
        discovery.setUrl(url);
        User userCopy = new User(user);
        discovery.setUser(userCopy);
        discovery.setTimestamp(new Timestamp(new Date().getTime()));
        return discovery;
    }

    public Discovery getDiscoveryById(long id){
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDAO = factory.getDiscoveryDAO();
        Discovery discovery = discoveryDAO.read(id);
        return discovery;
    }

    public List<Discovery> getAllDiscoveries(){
        return getAllDiscoveries(null);
    }

    public List<Discovery> getAllDiscoveries(Comparator<Discovery> comparator){
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDAO = factory.getDiscoveryDAO();
        List<Discovery> discoveryList = discoveryDAO.getAll();
        if(comparator != null && discoveryList != null){
            discoveryList.sort(comparator);
        }
        return discoveryList;
    }

    public boolean updateDiscovery(Discovery discovery){
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDAO = factory.getDiscoveryDAO();
        boolean result = discoveryDAO.update(discovery);
        return result
                ;
    }
}
