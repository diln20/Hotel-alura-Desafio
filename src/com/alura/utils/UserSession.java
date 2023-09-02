
package com.alura.utils;

/**
 *
 * @author andre_hk4s7fk
 */
public class UserSession {
    
private static String loggedInUser;

    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(String user) {
        loggedInUser = user;
    }
    

    
}
