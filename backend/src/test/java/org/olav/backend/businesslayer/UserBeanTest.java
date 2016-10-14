package org.olav.backend.businesslayer;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.spi.ArquillianProxyException;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.olav.backend.datalayer.Address;
import org.olav.backend.datalayer.User;

import javax.ejb.EJB;
import javax.ejb.EJBException;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class UserBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(UserBean.class, User.class, Address.class)
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    private UserBean userBean;

    @Test
    public void testRegisterNewUser() {
        String username = "SuchUserName";
        User user = userBean.registerNewUser(username, "shiba@inu.wow", "Very Name", new Address("City", "Country"));
        assertEquals(username.toLowerCase(), user.getUsername());
        assertEquals("City", user.getAddress().getCity());
        assertEquals("Country", user.getAddress().getCountry());
        assertEquals("Very Name", user.getName());
        assertEquals("shiba@inu.wow", user.getEmail());
    }

    @Test
    public void testGetNumberOfUsers() {
        Long users = userBean.getNumberOfUsers();
        userBean.registerNewUser("suchUser", "shiba@inu.wow", "Very Name", new Address("City", "Country"));
        assertEquals(users+1, userBean.getNumberOfUsers());
    }

    @Test(expected = ArquillianProxyException.class)
    public void testUserMustHaveUsername() {
        userBean.registerNewUser(null, "shiba@inu.wow", "Very Name", new Address("City", "Country"));
    }

    @Test(expected = EJBException.class)
    public void testUserCannotBeEmpty() {
        userBean.registerNewUser("   ", "shiba@inu.wow", "Very Name", new Address("City", "Country"));
    }

    @Test(expected = EJBException.class)
    public void testUserMustBeMin3Chars() {
        userBean.registerNewUser("12", "shiba@inu.wow", "Very Name", new Address("City", "Country"));
    }

    public void testUserWithMinChars() {
        userBean.registerNewUser("123", "shiba@inu.wow", "Very Name", new Address("City", "Country"));
    }

    @Test(expected = EJBException.class)
    public void testUserMustBeMax15Chars() {
        userBean.registerNewUser("1234567890123456", "shiba@inu.wow", "Very Name", new Address("City", "Country"));
    }

    public void testUserWithMaxChars() {
        userBean.registerNewUser("123456789012345", "shiba@inu.wow", "Very Name", new Address("City", "Country"));
    }

    @Test(expected = EJBException.class)
    public void testUserWithInvalidSpecialChar() {
        userBean.registerNewUser("123*abc", "shiba@inu.wow", "Very Name", new Address("City", "Country"));
    }

    public void testUserWithValidSpecialChars() {
        userBean.registerNewUser("1_2-3", "shiba@inu.wow", "Very Name", new Address("City", "Country"));
    }

    @Test(expected = EJBException.class)
    public void testEmailWithoutDomain() {
        userBean.registerNewUser("invalidEmail1", "invalid@email", "Very Name", new Address("City", "Country"));
    }

    @Test(expected = EJBException.class)
    public void testEmailWithoutAt() {
        userBean.registerNewUser("invalidEmail2", "invalidemail.wow", "Very Name", new Address("City", "Country"));
    }

    @Test(expected = EJBException.class)
    public void testEmailWithoutAtAndDomain() {
        userBean.registerNewUser("invalidemail3", "invalidemail", "Very Name", new Address("City", "Country"));
    }
}