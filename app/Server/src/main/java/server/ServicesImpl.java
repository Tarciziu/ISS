package server;

import model.Product;
import model.User;
import persistence.IProductRepository;
import persistence.IUserRepository;
import services.IObserver;
import services.IService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServicesImpl implements IService {
    private IUserRepository userRepository;
    private IProductRepository productRepository;
    private Map<String, IObserver> loggedClients;

    private final int defaultThreadsNo=5;

    public ServicesImpl(IUserRepository uRepo, IProductRepository pRepo) {

        userRepository = uRepo;
        productRepository = pRepo;
        loggedClients=new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(User user, IObserver client) throws Exception {
        User userR=userRepository.findByUsername(user.getUsername());
        if (userR!=null)
            if(userR.getPassword().equals(user.getPassword())){
                if(loggedClients.get(user.getUsername())!=null)
                    throw new Exception("User already logged in.");
                System.out.println(user);
                try{
                loggedClients.put(userR.getUsername(), client);}
                catch(Exception ex){
                    System.out.println(ex);
                }
                System.out.println("server");
            }
            else
                throw new Exception("Username or password incorrect!");
        else
            throw new Exception("Authentication failed.");

    }

    @Override
    public synchronized void logout(User user, IObserver client) throws Exception {
        IObserver localClient = loggedClients.remove(user.getUsername());
        if (localClient==null)
            throw new Exception("User " + user.getUsername() + " is not logged in.");
    }

    @Override
    public Iterable<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
