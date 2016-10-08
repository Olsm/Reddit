package org.olav.backend.ejb;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.olav.backend.entity.Address;
import org.olav.backend.entity.User;

import javax.ejb.EJB;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class UserBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(UserBean.class, User.class, Address.class)
                .addAsResource("META-INF/persistence.xml");
    }


    @EJB
    private UserBean user;

    @Test
    public void testRegisterNewUser() {

    }

    @Test
    public void testGetNumberOfUsers() {

    }
}