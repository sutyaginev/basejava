package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private final File directory;
    private final Serializer serializer;

    protected FileStorage(File directory, Serializer serializer) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(serializer, "serializer must not be null");

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }

        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }

        this.directory = directory;
        this.serializer = serializer;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();

        if (files == null) {
            throw new StorageException("Directory read error", null);
        }

        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(resume, file);
    }


    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files == null) {
            throw new StorageException("Directory read error", null);
        }

        for (File file : files) {
            list.add(doGet(file));
        }

        return list;
    }

    @Override
    public int size() {
        String[] list = directory.list();

        if (list == null) {
            throw new StorageException("Directory read error", null);
        }

        return list.length;
    }

    protected void doWrite(Resume resume, OutputStream os) throws IOException {
        serializer.doWrite(resume, os);
    }

    protected Resume doRead(InputStream is) throws IOException {
        return serializer.doRead(is);
    }
}
