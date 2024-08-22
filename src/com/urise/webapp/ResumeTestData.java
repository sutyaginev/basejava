package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = getTestResume("uuid1", "Григорий Кислин");
        System.out.println(resume.getFullName());

        for (Map.Entry<ContactType, String> contactEntry : resume.getContacts().entrySet()) {
            System.out.println(contactEntry.getKey().getTitle() + ": " + contactEntry.getValue());
        }

        for (Map.Entry<SectionType, Section> sectionEntry : resume.getSections().entrySet()) {
            System.out.println(sectionEntry.getKey().getTitle() + ": " + sectionEntry.getValue());
        }
    }

    public static Resume getTestResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        // fill contacts
        Map<ContactType, String> contacts = resume.getContacts();
        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "skype:grigory.kislin");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru/");

        // fill sections
        Map<SectionType, Section> sections = resume.getSections();
        sections.put(
                SectionType.OBJECTIVE,
                new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям")
        );

        sections.put(
                SectionType.PERSONAL,
                new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.")
        );

        sections.put(
                SectionType.ACHIEVEMENT,
                new ListSection(
                        "Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей " +
                                "спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot " +
                                "+ Vaadin проект для комплексных DIY смет",
                        "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                                "Более 3500 выпускников."
                )
        );

        sections.put(
                SectionType.QUALIFICATIONS,
                new ListSection(
                        "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                        "Version control: Subversion, Git, Mercury, ClearCase, Perforce"
                )
        );

        sections.put(
                SectionType.EXPERIENCE,
                new CompanySection(
                        new Company(
                                new Link("Java Online Projects", "https://javaops.ru/"),
                                new ArrayList<>(List.of(new Company.Position(
                                        LocalDate.of(2013, 10, 1),
                                        LocalDate.now(),
                                        "Автор проекта",
                                        "Создание, организация и проведение Java онлайн проектов и стажировок."
                                )))
                        ),
                        new Company(
                                new Link("Wrike", "https://www.wrike.com/"),
                                new ArrayList<>(List.of(new Company.Position(
                                        LocalDate.of(2014, 10, 1),
                                        LocalDate.of(2016, 1, 1),
                                        "Старший разработчик (backend)",
                                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."
                                )))
                        )
                )
        );

        sections.put(
                SectionType.EDUCATION,
                new CompanySection(
                        new Company(
                                new Link("Coursera", "https://www.coursera.org/course/progfun"),
                                new ArrayList<>(List.of(new Company.Position(
                                        LocalDate.of(2013, 3, 1),
                                        LocalDate.of(2013, 5, 1),
                                        "",
                                        "'Functional Programming Principles in Scala' by Martin Odersky"
                                )))
                        ),
                        new Company(
                                new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                                new ArrayList<>(List.of(new Company.Position(
                                        LocalDate.of(2011, 3, 1),
                                        LocalDate.of(2011, 4, 1),
                                        "",
                                        "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'"
                                )))
                        )
                )
        );

        return resume;
    }
}
