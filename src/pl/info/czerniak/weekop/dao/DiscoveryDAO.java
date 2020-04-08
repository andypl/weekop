package pl.info.czerniak.weekop.dao;

import pl.info.czerniak.weekop.model.Discovery;

import java.util.List;

public interface DiscoveryDAO extends GenericDAO<Discovery, Long> {
    List<Discovery> getAll();
}
