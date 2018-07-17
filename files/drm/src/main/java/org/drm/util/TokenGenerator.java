package org.drm.util;

import java.security.SecureRandom;

public class TokenGenerator {

        protected static SecureRandom random = new SecureRandom();
        
        
        public synchronized Token generateToken( String username ) {
                long longToken = Math.abs( random.nextLong() );
                String random = Long.toString( longToken, 16 );
                return (new Token(random, username));
        }
}
