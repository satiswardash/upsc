package com.kortain.upsc.models;

import java.util.List;

/**
 * Created by satiswardash on 20/12/17.
 */

public class ApplicationData {

    private List<Post> postList;
    private List<UserInformation> userInformationList;
    private List<Question> objectiveList;
    private List<Question> subjectiveList;
    private List<PracticeSet> practiceSetList;

    public ApplicationData() {
    }

    public ApplicationData(List<Post> postList, List<UserInformation> userInformationList, List<Question> objectiveList, List<Question> subjectiveList, List<PracticeSet> practiceSetList) {
        this.postList = postList;
        this.userInformationList = userInformationList;
        this.objectiveList = objectiveList;
        this.subjectiveList = subjectiveList;
        this.practiceSetList = practiceSetList;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public List<UserInformation> getUserInformationList() {
        return userInformationList;
    }

    public void setUserInformationList(List<UserInformation> userInformationList) {
        this.userInformationList = userInformationList;
    }

    public List<Question> getObjectiveList() {
        return objectiveList;
    }

    public void setObjectiveList(List<Question> objectiveList) {
        this.objectiveList = objectiveList;
    }

    public List<Question> getSubjectiveList() {
        return subjectiveList;
    }

    public void setSubjectiveList(List<Question> subjectiveList) {
        this.subjectiveList = subjectiveList;
    }

    public List<PracticeSet> getPracticeSetList() {
        return practiceSetList;
    }

    public void setPracticeSetList(List<PracticeSet> practiceSetList) {
        this.practiceSetList = practiceSetList;
    }
}
