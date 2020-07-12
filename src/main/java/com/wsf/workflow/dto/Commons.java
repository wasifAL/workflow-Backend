package com.wsf.workflow.dto;

import java.util.TreeMap;

public class Commons {
    public static final TreeMap<String, String> roles = new TreeMap() {{
        put("USER", "User");
        put("EMPLOYEE", "Employee");
        put("ADMIN", "Administrator");
    }};

    public static final TreeMap<String, String> docTypes = new TreeMap() {{
        put("PP", "Passport");
        put("NID", "National ID");
        put("BC", "Birth Certificate");
        put("IS", "Income Statement");
    }};
}
