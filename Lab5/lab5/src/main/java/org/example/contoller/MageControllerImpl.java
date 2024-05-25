package org.example.contoller;

import org.example.model.Mage;
import org.example.repository.MageRepository;

import java.util.Optional;

public class MageControllerImpl implements MageController {

    private final MageRepository repository;

    public MageControllerImpl(MageRepository repository) {
        this.repository = repository;
    }

    @Override
    public String find(String name) {
        Optional<Mage> mage = repository.find(name);
        return mage.isPresent() ? mage.get().toString() : MageControllerCode.NOT_FOUND.toString();
    }

    @Override
    public String delete(String name) {
        try {
            repository.delete(name);
            return MageControllerCode.DONE.toString();
        } catch (IllegalArgumentException e) {
            return MageControllerCode.NOT_FOUND.toString();
        }
    }

    @Override
    public String save(String name, String level) {
        try {
            repository.save(new Mage(name, Integer.parseInt(level)));
            return MageControllerCode.DONE.toString();
        } catch (NumberFormatException e) {
            return MageControllerCode.BAD_ARGUMENT.toString();
        } catch (IllegalArgumentException e) {
            return MageControllerCode.BAD_REQUEST.toString();
        }
    }

}
