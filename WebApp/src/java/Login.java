

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import JPAcontrollers.*;

@ManagedBean
@RequestScoped
public class Login {
  
    private String username;
	private String password;
	    
	    
	 public void login() {
	        
	        FacesContext context = FacesContext.getCurrentInstance();
                loginService service= new loginService();
                if(service.login(this.username, this.password)){
	            context.getExternalContext().getSessionMap().put("user", username);
	            try {
					context.getExternalContext().redirect("welcomePrimefaces.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	        else  {
	            context.addMessage(null, new FacesMessage("Authentication Failed. Check username or password."));

	        } 
	    }

	    public void logout() {
	    	FacesContext context = FacesContext.getCurrentInstance();
	    	context.getExternalContext().invalidateSession();
	        try {
				context.getExternalContext().redirect("Login.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
            public void signUp(){
                
                
            }
	    
	    
		 public String getUsername() {
				return username;
			}

			public void setUsername(String username) {
				this.username = username;
			}

			public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
			}
}
