package pl.info.czerniak.weekop.dao;

import pl.info.czerniak.weekop.exception.NoSuchDbTypeException;

public abstract class DAOFactory {

    public static final int POSTGRESQL_DAO_FACTORY = 1;

    public abstract DiscoveryDAO getDiscoveryDAO();
    public abstract UserDAO getUserDAO();
    public abstract VoteDAO getVoteDAO();

    public static DAOFactory getDAOFactory(){
        DAOFactory factory = null;
        try{
            factory = getDAOFactory(POSTGRESQL_DAO_FACTORY);
        }catch (NoSuchDbTypeException e){
            e.printStackTrace();
        }
        return factory;
    }

    private static DAOFactory getDAOFactory(int type) throws NoSuchDbTypeException{
        switch (type){
            case (POSTGRESQL_DAO_FACTORY):
                return new PostgresqlDAOFactory();
            default: throw new NoSuchDbTypeException();
        }
    }
}
