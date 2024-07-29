package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Comparator<Resume> fullNameUuidComparator = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public final void update(Resume resume) {
        SK searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    public final void save(Resume resume) {
        SK searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    public final Resume get(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public final void delete(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);

        if (isExist(searchKey)) {
            return searchKey;
        }

        throw new NotExistStorageException(uuid);
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);

        if (!isExist(searchKey)) {
            return searchKey;
        }

        throw new ExistStorageException(uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = doCopyAll();
        resumes.sort(fullNameUuidComparator);
        return resumes;
    }

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract void doSave(SK searchKey, Resume resume);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> doCopyAll();
}
