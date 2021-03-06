package org.gudartem.aars.impl.repository;

import org.gudartem.aars.api.repository.HasDirectoryIdRepository;
import org.gudartem.aars.api.repository.TableDescriptor;
import org.gudartem.aars.db.model.entity.Directory;
import org.jooq.Field;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.gudartem.aars.api.repository.RepositoryName.DIRECTORY_REPOSITORY;
import static org.gudartem.aars.db.jooq.Tables.DIRECTORY;
import static org.gudartem.aars.model.PojoFieldNames.Directory.DIRECTORY_NAME;
import static org.gudartem.aars.model.PojoFieldNames.Directory.DIRECTORY_TYPE;
import static org.gudartem.aars.model.PojoFieldNames.Directory.PARENT_ID;
import static org.gudartem.aars.model.PojoFieldNames.HasId.ID;
import static org.gudartem.aars.model.PojoFieldNames.HasRevision.REVISION;
import static org.gudartem.aars.model.PojoFieldNames.HasThemeId.THEME_ID;

@Repository(DIRECTORY_REPOSITORY)
public class DirectoryRepository
        extends BaseRepository<Directory, UUID>
        implements HasDirectoryIdRepository<Directory, UUID> {

    private TableDescriptor tableDescriptor;

    @Override
    public TableDescriptor getTableDescriptor() {
        if (tableDescriptor != null) {
            return tableDescriptor;
        }

        Map<String, Field> mapping = new HashMap<>();
        mapping.put(ID, DIRECTORY.ID);
        mapping.put(REVISION, DIRECTORY.REVISION);
        mapping.put(THEME_ID, DIRECTORY.THEME_ID);
        mapping.put(DIRECTORY_NAME, DIRECTORY.DIRECTORY_NAME);
        mapping.put(DIRECTORY_TYPE, DIRECTORY.DIRECTORY_TYPE);
        mapping.put(PARENT_ID, DIRECTORY.PARENT_ID);

        tableDescriptor = tableDescriptorBuilder.table(DIRECTORY)
                .idField(DIRECTORY.ID)
                .entityType(Directory.class)
                .propertyFieldMapping(mapping)
                .build();

        return tableDescriptor;
    }

    @Override
    public List<Directory> getAllByThemeId(UUID themeId) {
        return findAll(DIRECTORY.DIRECTORY_NAME.asc(), DIRECTORY.THEME_ID.eq(themeId), DIRECTORY.PARENT_ID.isNull());
    }

    @Override
    public List<Directory> getAllByDirectoryId(UUID directoryId) {
        return findAll(DIRECTORY.DIRECTORY_NAME.asc(), DIRECTORY.PARENT_ID.eq(directoryId));
    }
}
