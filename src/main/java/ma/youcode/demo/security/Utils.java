package ma.youcode.demo.security;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

//permet de préciser que le bean est un composant
@Component
public class Utils {
	
	   private final Random RANDOM = new SecureRandom();
	    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		
	    
	    public String generateStringId(int length) {
	        StringBuilder returnValue = new StringBuilder(length);

	        for (int i = 0; i < length; i++) {
	            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
	        }

	        return new String(returnValue);
	    }

}
