package org.olav.backend.ejb;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.olav.backend.entity.Address;
import org.olav.backend.entity.User;

import javax.ejb.EJB;

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
        assertEquals(user, userBean.getUser(username));
        assertEquals(username.toLowerCase(), user.getUsername());
        assertEquals("City", user.getAddress().getCity());
        assertEquals("Country", user.getAddress().getCountry());
        assertEquals("Very Name", user.getName());
        assertEquals("shiba@inu.wow", user.getEmail());
    }

    @Test
    public void testGetNumberOfUsers() {
        assertEquals(0, userBean.getNumberOfUsers());
        userBean.registerNewUser("suchUser", "shiba@inu.wow", "Very Name", new Address("City", "Country"));
        assertEquals(1, userBean.getNumberOfUsers());
    }
}