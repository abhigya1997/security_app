package com.example.security;

public class consts {
    public static String help_db = "help_requests";
}

class UserHelp {
    String userId;
    Boolean isHelpNeeded;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getHelpNeeded() {
        return isHelpNeeded;
    }

    public void setHelpNeeded(Boolean helpNeeded) {
        isHelpNeeded = helpNeeded;
    }


    UserHelp(String uid, Boolean help){
        this.userId = uid;
        this.isHelpNeeded = help;
    }
}
