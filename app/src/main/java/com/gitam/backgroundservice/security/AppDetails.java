package com.gitam.backgroundservice.security;

public class AppDetails {
    String applabel, permissions;

    public AppDetails(String applabel) {
        this.applabel = applabel;
     }

    public String getApplabel() {
        return applabel;
    }

    public void setApplabel(String applabel) {
        this.applabel = applabel;
    }


}
