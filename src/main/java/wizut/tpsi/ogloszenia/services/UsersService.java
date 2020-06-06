package wizut.tpsi.ogloszenia.services;

import org.springframework.stereotype.Service;
import wizut.tpsi.ogloszenia.jpa.Offer;
import wizut.tpsi.ogloszenia.jpa.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UsersService {
    @PersistenceContext
    private EntityManager em;
    public byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    public String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
    public User loginUser(User user)
    {
        String passwd=user.getPassword();
        try {
            byte[] encodedPasswd=getSHA(passwd);
            passwd=toHexString(encodedPasswd);
            String jpql = "select usr from User usr where usr.username = :username and usr.password LIKE CONCAT('%',:password,'%')";    //LIKE poniewaz w przypadku user z jakiegos powodu zakodowane haslo ma jedna cyfre wiecej - zawsze 0
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("username", user.getUsername());
            query.setParameter("password", passwd);
            if(query.getResultList().size()==1)
            {
                return query.getResultList().get(0);
            }
        } catch (Exception e) {
            System.out.println("Algorytm nie dziala");
        }
        return null;
    }
    public User getUserById(int id)
    {
        return em.find(User.class, id);
    }
}
