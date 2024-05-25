package org.example.repository;
import org.example.model.Mage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class MageRepositoryImpl implements MageRepository {
    private final Collection<Mage> collection;

    public MageRepositoryImpl(Collection<Mage> collection) {
        this.collection = new ArrayList<>();
        this.collection.addAll(collection);
    }

    @Override
    public Optional<Mage> find(String name) {
        return collection.stream()
                .filter(mage -> mage.getName().equals(name))
                .findFirst();
    }

    @Override
    public void delete(String name) throws IllegalArgumentException {
        Optional<Mage> mage = this.find(name);
        if(mage.isEmpty()) {
            throw new IllegalArgumentException();
        }
        collection.remove(mage.get());
    }

    @Override
    public void save(Mage mage) throws IllegalArgumentException {
        if(this.find(mage.getName()).isPresent()) {
            throw new IllegalArgumentException();
        }
        collection.add(mage);
    }

}
