package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());

            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());

            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                Section section = entry.getValue();

                dos.writeUTF(sectionType.name());

                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        TextSection textSection = (TextSection) section;
                        dos.writeUTF(textSection.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection listSection = (ListSection) section;
                        writeList(dos, listSection.getItems(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        CompanySection companySection = (CompanySection) section;
                        writeList(dos, companySection.getCompanies(), company -> {
                            dos.writeUTF(company.getHomePage().getName());
                            dos.writeUTF(company.getHomePage().getUrl());
                            writeList(dos, company.getPositions(), position -> {
                                dos.writeLong(position.getDateFrom().toEpochDay());
                                dos.writeLong(position.getDateTo().toEpochDay());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription());

                            });
                        });
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();

            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            size = dis.readInt();

            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            }

            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new CompanySection(readList(dis, () ->
                        new Company(new Link(dis.readUTF(), dis.readUTF()), readList(dis, () ->
                                new Company.Position(LocalDate.ofEpochDay(dis.readLong()), LocalDate.ofEpochDay(dis.readLong()),
                                        dis.readUTF(), dis.readUTF())
                        ))
                ));
            default:
                throw new IOException();
        }
    }

    private <T> void writeList(DataOutputStream dos, List<T> list, ItemWriter<T> iw) throws IOException {
        dos.writeInt(list.size());
        for (T item : list) {
            iw.write(item);
        }
    }

    private <T> List<T> readList(DataInputStream dis, ItemReader<T> ir) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();

        for (int j = 0; j < size; j++) {
            list.add(ir.read());
        }

        return list;
    }

    private interface ItemWriter<T> {
        void write(T t) throws IOException;
    }

    private interface ItemReader<T> {
        T read() throws IOException;
    }
}
