/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author lucas
 */
@Named(value = "user")
@RequestScoped
public class User {

    /**
     * Creates a new instance of User
     */
    public User() {
    }
    
}
