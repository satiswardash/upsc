package com.kortain.upsc;

import com.kortain.upsc.models.ApplicationData;
import com.kortain.upsc.models.Comment;
import com.kortain.upsc.models.Objective;
import com.kortain.upsc.models.Post;
import com.kortain.upsc.models.PracticeQuestion;
import com.kortain.upsc.models.PracticeSet;
import com.kortain.upsc.models.Question;
import com.kortain.upsc.models.Scoreboard;
import com.kortain.upsc.models.Subjective;
import com.kortain.upsc.models.UserInformation;
import com.kortain.upsc.models.UserInteraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by satiswardash on 20/12/17.
 */

public class SampleDataProvider {

    private SampleDataProvider() {
    }

    public static SampleDataProvider getInstance(){
        return new SampleDataProvider();
    }

    public ApplicationData generateSampleData(){

        ApplicationData data = new ApplicationData();

        Comment comment1 = new Comment("c101", "Nice explanation", null);
        Comment comment2 = new Comment("c102", "Awesome", new UserInteraction("cui001", 2, 0, null));

        List<Comment> cl0 = new ArrayList<>();
        cl0.add(new Comment("0092", "Hmm, great!", null));
        cl0.add(new Comment("0092", "Hmm, great!", new UserInteraction("9993", 12, 0, null)));

        Comment comment3 = new Comment("c103", "Didn't understand", new UserInteraction("cui001", 2, 0, cl0));

        List<Comment> cl1 = new ArrayList<>();
        cl1.add(comment1);
        cl1.add(comment2);
        cl1.add(comment3);

        UserInteraction userInteraction = new UserInteraction("ui001", 20, 2, cl1);

        String[] uris = {"url1", "url2", "url3", "url4", "url5"};

        Post p1 = new Post("p001", "987uuys6y77shmbxvgs54", Calendar.getInstance().getTime().toString(), "Sample post title 1", "sample post description 01", null, userInteraction);
        Post p2 = new Post("p002", "987uuys6y77shmbxvgs54", Calendar.getInstance().getTime().toString(), "Sample post title 2", "sample post description 02", Arrays.asList(uris), userInteraction);
        Post p3 = new Post("p003", "987uuys6y77shmbxvgs54", Calendar.getInstance().getTime().toString(), "Sample post title 3", "sample post description 03", Arrays.asList(uris), userInteraction);
        Post p4 = new Post("p004", "987uuys6y77shmbxvgs54", Calendar.getInstance().getTime().toString(), "Sample post title 4", "sample post description 04", null, userInteraction);


        List<Post> postList = new ArrayList<>();

        postList.add(p1);
        postList.add(p2);
        postList.add(p3);
        postList.add(p4);


        Scoreboard scoreboard = new Scoreboard("sc009", "12/22", "sample score information");

        String[] options = {"Option 01", "Option 02", "Option 03", "Option 04"};
        Question q1 = new Objective("obj001", Arrays.asList(uris), "this is a sample question 01!", "this is sample information about question given 01", Arrays.asList(options), 3);
        Question q2 = new Objective("obj002", Arrays.asList(uris), "this is a sample question 02!", "this is sample information about question given 02", Arrays.asList(options), 2);


        Question q3 = new Subjective("sub001", Arrays.asList(uris), "this is sample subjective question 01", "this is sample information about subjective question 01", "this is answer for sample objective question 01");
        Question q4 = new Subjective("sub002", Arrays.asList(uris), "this is sample subjective question 02", "this is sample information about subjective question 02", "this is answer for sample objective question 02");

        Question pq1 = new PracticeQuestion("pq101", Arrays.asList(uris), "this is practice question 01", "this is sample information about practice question 01", Arrays.asList(options), 2, 1, 4);
        Question pq2 = new PracticeQuestion("pq102", Arrays.asList(uris), "this is practice question 02", "this is sample information about practice question 02", Arrays.asList(options), 3, 1, 4);
        Question pq3 = new PracticeQuestion("pq103", Arrays.asList(uris), "this is practice question 03", "this is sample information about practice question 03", Arrays.asList(options), 2, 1, 4);
        Question pq4 = new PracticeQuestion("pq104", Arrays.asList(uris), "this is practice question 04", "this is sample information about practice question 04", Arrays.asList(options), 1, 1, 4);

        List<Question> objQl = new ArrayList<>();
        List<Question> subQl = new ArrayList<>();

        List<PracticeQuestion> ps1 = new ArrayList<>();
        List<PracticeQuestion> ps2 = new ArrayList<>();

        PracticeSet practiceSet1 = new PracticeSet("prs001", ps1, "this is sample information about mock exam 01", 10, 10, 4);
        PracticeSet practiceSet2 = new PracticeSet("prs002", ps2, "this is sample information about mock exam 02", 10, 10, 4);

        List<PracticeSet> practiceSetList = new ArrayList<>();
        practiceSetList.add(practiceSet1);
        practiceSetList.add(practiceSet2);


        objQl.add(q1);
        objQl.add(q2);
        subQl.add(q3);
        subQl.add(q4);

        List<Question> bookmark01 = new ArrayList<>();
        bookmark01.add(q1);
        bookmark01.add(q2);
        bookmark01.add(q3);

        List<Question> bookmark02 = new ArrayList<>();
        bookmark01.add(q1);
        bookmark01.add(q3);
        bookmark01.add(q4);

        UserInformation u1 = new UserInformation("jz7XMOHiyBVA0OExpIutrqOLT2b2", "Satiswar Dash", "satish.dash93@gmail.com", "8297034650", "profile_picture_url_01.com", scoreboard, postList, bookmark01);
        UserInformation u2 = new UserInformation("Z5n5o5QufNOSXtPJO9DSViijhHT2", "Alex Jose", "alex.jose@gmail.com", "9040350030", "profile_picture_url_01.com", scoreboard, postList, bookmark02);


        List<UserInformation> userInformationList = new ArrayList<>();
        userInformationList.add(u1);
        userInformationList.add(u2);


        data.setUserInformationList(userInformationList);
        data.setObjectiveList(objQl);
        data.setSubjectiveList(subQl);
        data.setPracticeSetList(practiceSetList);
        data.setPostList(postList);

        return data;
    }
}
