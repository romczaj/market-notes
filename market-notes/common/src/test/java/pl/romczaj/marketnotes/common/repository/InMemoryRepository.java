package pl.romczaj.marketnotes.common.repository;

import pl.romczaj.marketnotes.common.domain.DomainModel;

import java.util.Map;
import java.util.function.BiFunction;

public interface InMemoryRepository {

    default <T extends DomainModel> Long generateId(Map<Long, T> databaseMap) {
        return (long) databaseMap.size() + 1;
    }

    default <T extends DomainModel> T saveDomainModel(
            T domainModel,
            Map<Long, T> databaseMap,
            BiFunction<T, Long, T> withIdMapper) {
        if (domainModel.id() != null) {
            databaseMap.put(domainModel.id(), domainModel);
            return domainModel;
        }
        T domainWithId = withIdMapper.apply(domainModel, generateId(databaseMap));
        databaseMap.put(domainWithId.id(), domainWithId);
        return domainWithId;
    }
}
