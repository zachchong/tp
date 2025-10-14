package presspal.contact.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import presspal.contact.commons.exceptions.DataLoadingException;
import presspal.contact.model.ReadOnlyContactBook;
import presspal.contact.model.ReadOnlyUserPrefs;
import presspal.contact.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ContactBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getContactBookFilePath();

    @Override
    Optional<ReadOnlyContactBook> readContactBook() throws DataLoadingException;

    @Override
    void saveContactBook(ReadOnlyContactBook contactBook) throws IOException;

}
