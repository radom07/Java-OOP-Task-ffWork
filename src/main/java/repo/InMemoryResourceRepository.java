package repo;

import domain.resource.Resource;

import java.util.*;

public class InMemoryResourceRepository implements ResourceRepository {
    // Kluczem jest unikalny String name
    private final Map<String, Resource> resources = new HashMap<>();

    @Override
    public void add(Resource r) {
        resources.put(r.getName(), r);
    }

    @Override
    public Optional<Resource> findByName(String name) {
        return Optional.ofNullable(resources.get(name));
    }

    @Override
    public List<Resource> findAll() {
        return new ArrayList<>(resources.values());
    }
}
