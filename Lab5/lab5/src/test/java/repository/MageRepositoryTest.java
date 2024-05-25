package repository;

import org.example.model.Mage;
import org.example.repository.MageRepository;
import org.example.repository.MageRepositoryImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class MageRepositoryTest {

    MageRepository magRepo;

    @Before
    public void setup() {
        magRepo = new MageRepositoryImpl(
            List.of(
                new Mage("ExistingMage", 2137)
            )
        );
    }

    @Test
    public void find_whenMageExists_shouldReturnMage() {
        assertThat(magRepo.find("ExistingMage"))
            .isEqualTo(Optional.of(new Mage("ExistingMage", 2137)));
    }

    @Test
    public void find_whenMageDoesNotExist_shouldReturnEmpty() {
        assertThat(magRepo.find("NotAMage"))
            .isEqualTo(Optional.empty());
    }

    @Test
    public void delete_whenMageExists_shouldNotFind() {
        magRepo.delete("ExistingMage");
        assertThat(magRepo.find("ExistingMage"))
            .isEqualTo(Optional.empty());
    }

    @Test
    public void delete_whenMageDoesNotExist_shouldThrow() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> magRepo.delete("NotAMage"));
    }

    @Test
    public void save_whenMageExitInRepo_shouldThrow() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> magRepo.save(new Mage("ExistingMage", 2137)));
    }

    @Test
    public void save_whenMageDoesNotExistInRepo_shouldFind() {
        magRepo.save(new Mage("NewMage", 2137));
        assertThat(magRepo.find("NewMage"))
            .isEqualTo(Optional.of(new Mage("NewMage", 2137)));
    }

}
