package com.urise.webapp.util;

import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;

import java.util.ArrayList;
import java.util.Arrays;

public class ResumeUtil {

    public static void addSection(Resume resume, SectionType type, String value) {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                resume.addSection(type, new TextSection(value));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                resume.addSection(type, new ListSection(new ArrayList<>(Arrays.asList(value.split("\n")))));
                break;
        }
    }
}
