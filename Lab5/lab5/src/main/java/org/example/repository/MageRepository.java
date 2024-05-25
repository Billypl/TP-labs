package org.example.repository;
import org.example.model.Mage;
import java.util.Optional;

public interface MageRepository {
    public Optional<Mage> find(String name);
    public void delete(String name) throws IllegalArgumentException;
    public void save(Mage mage) throws IllegalArgumentException;
}
