package org.olav.backend.businesslayer;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup //initialize it immediately at startup, ie don't wait for its first access
public class StatisticsBot {

    public static int numberOfPosts = 0;
    public static int numberOfComments = 0;
    public static int numberOfUsers = 0;

    @EJB
    private PostBean postEJB;
    @EJB
    private CommentBean commentEJB;
    @EJB
    private UserBean userEJB;

    @Schedule(second = "*/10", minute="*", hour="*", persistent=false)
    public void commentator(){
        numberOfPosts = postEJB.getNumberOfPosts();
        numberOfComments = commentEJB.getNumberOfComments();
        numberOfUsers = userEJB.getNumberOfUsers();
    }



}
