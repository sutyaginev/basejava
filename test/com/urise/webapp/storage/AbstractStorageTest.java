package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected Storage storage;

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static final Resume R1;
    private static final Resume R2;
    private static final Resume R3;
    private static final Resume R4;

    static {
        R1 = ResumeTestData.getTestResume(UUID_1, "Name1");
        R2 = ResumeTestData.getTestResume(UUID_2, "Name2");
        R3 = ResumeTestData.getTestResume(UUID_3, "Name3");
        R4 = ResumeTestData.getTestResume(UUID_4, "Name4");
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        Assertions.assertArrayEquals(storage.getAllSorted().toArray(), new Resume[]{});
    }

    @Test
    void update() {
        Resume newResume1 = new Resume(UUID_1, "New Name");
        R1.addContact(ContactType.EMAIL, "gkislin@google.com");
        R1.addContact(ContactType.SKYPE, "NewSkype");
        R1.addContact(ContactType.PHONE, "+7 921 222-22-22");
        storage.update(newResume1);
        Assertions.assertEquals(newResume1, storage.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    void save() {
        storage.save(R4);
        assertGet(R4);
        assertSize(4);
    }

    @Test
    void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(R1);
        });
    }

    @Test
    void get() {
        assertGet(storage.get(UUID_1));
        assertGet(storage.get(UUID_2));
        assertGet(storage.get(UUID_3));
    }

    void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get(UUID_1);
        });
    }

    @Test
    void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete("dummy");
        });
    }

    @Test
    void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(list, Arrays.asList(R1, R2, R3));
    }

    @Test
    void size() {
        assertSize(3);
    }

    void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

}