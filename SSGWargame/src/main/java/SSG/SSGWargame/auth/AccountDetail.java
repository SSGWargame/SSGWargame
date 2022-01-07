package SSG.SSGWargame.auth;

import SSG.SSGWargame.domain.Account.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * exService inherits UserDetails Interface.
 * Spring Security use user data as implementation of UserDetails Interface.
 * We can think UserDetails is User data VO(Value Object)
 * VO : Value Object, Immutable.
 */
public class AccountDetail implements UserDetails {

    private String id;
    private String password;
    private String auth;

    /**
     * Constructor
     * @param account : Account Entity.
     */
    public AccountDetail(Account account){
        this.id = account.getUsername();
        this.password = account.getPw();
        this.auth = "ROLE_" + account.getRole();
    }

    /**
     * Return authentication list of user
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.auth));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    /**
     * Whether the account expires or not.
     * false means expiration.
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Whether the account is locked
     * false means lock.
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Whether the account's password is expired.
     * false means expiration.
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Whether the account is Enable(active).
     * false means disable(inactive).
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
