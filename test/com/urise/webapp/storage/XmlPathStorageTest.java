    package com.urise.webapp.storage;

    import com.urise.webapp.storage.serializer.XmlStreamSerializer;

    class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new XmlStreamSerializer()));
    }
}