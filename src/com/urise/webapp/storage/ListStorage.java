package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage<Integer> {

    protected final List<Resume> list = new ArrayList<>();

    public void clear() {
        list.clear();
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        list.set(searchKey, resume);
    }

    @Override
    protected void doSave(Integer searchKey, Resume resume) {
        list.add(resume);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return list.get(searchKey);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        list.remove(searchKey.intValue());
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(list);
    }

    public int size() {
        return list.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (Objects.equals(list.get(i).getUuid(), uuid)) {
                return i;
            }
        }

        return null;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }
}
