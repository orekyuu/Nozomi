package org.orekyuu.nozomi.infrastructure.datasource;

import java.util.*;

public class AbstractInMemoryRepository<ID, T> {
    protected final Map<ID, T> store = new HashMap<>();

    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<T> find(ID id) {
        return Optional.ofNullable(store.get(id));
    }

    public void insert(ID id, T value) {
        store.put(id, value);
    }

    public void update(ID id, T value) {
        store.put(id, value);
    }

    public void remove(ID id) {
        store.remove(id);
    }
}
