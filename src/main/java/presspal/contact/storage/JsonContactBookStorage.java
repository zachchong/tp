package presspal.contact.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import presspal.contact.commons.core.LogsCenter;
import presspal.contact.commons.exceptions.DataLoadingException;
import presspal.contact.commons.exceptions.IllegalValueException;
import presspal.contact.commons.util.FileUtil;
import presspal.contact.commons.util.JsonUtil;
import presspal.contact.model.ReadOnlyContactBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonContactBookStorage implements ContactBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonContactBookStorage.class);

    private Path filePath;

    public JsonContactBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getContactBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyContactBook> readContactBook() throws DataLoadingException {
        return readContactBook(filePath);
    }

    /**
     * Similar to {@link #readContactBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyContactBook> readContactBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableContactBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableContactBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveContactBook(ReadOnlyContactBook contactBook) throws IOException {
        saveContactBook(contactBook, filePath);
    }

    /**
     * Similar to {@link #saveContactBook(ReadOnlyContactBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveContactBook(ReadOnlyContactBook contactBook, Path filePath) throws IOException {
        requireNonNull(contactBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableContactBook(contactBook), filePath);
    }

}
