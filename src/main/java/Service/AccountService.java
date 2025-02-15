package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;

public class AccountService {

    public AccountDAO accountDAO;

    /**
     * No Args constructor creating AccountService which create AccountDAO.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Method used for user account registration.
     * @param account account being registered.
     * @return account if register successful, else return null.
     */
    public Account register(Account account){
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4){
            return null;
        }
        if (this.accountDAO.findUser(account.getUsername()) == null){
            return this.accountDAO.register(account.getUsername(), account.getPassword());
        }
        return null;
    }

    /**
     * Method used for user login.
     * @param account account logging in.
     * @return account logging in if account exists, else return null.
     */
    public Account login(Account account){
        return this.accountDAO.login(account.getUsername(), account.getPassword());
    }
    

}
