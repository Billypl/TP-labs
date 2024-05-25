package controller;

import org.example.contoller.MageController;
import org.example.contoller.MageControllerCode;
import org.example.contoller.MageControllerImpl;
import org.example.model.Mage;
import org.example.repository.MageRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MageControllerTest {

    private MageRepository mageRepoMock;
    private MageController mageController;


    @Before
    public void setup() {
        mageRepoMock = mock(MageRepository.class);
        mageController = new MageControllerImpl(mageRepoMock);
    }

    @Test
    public void find_whenMageExists_shouldReturnDoneString() {
        when(mageRepoMock.find("ExistingMage"))
            .thenReturn(Optional.of(new Mage("ExistingMage", 2137)));
        String result = mageController.find("ExistingMage");
        assertThat(result).isEqualTo((new Mage("ExistingMage", 2137).toString()));
    }

    @Test
    public void find_whenMageDoesNotExist_shouldReturnNotFoundString() {
        when(mageRepoMock.find("NonExistingMage"))
            .thenReturn(Optional.empty());
        String result = mageController.find("NonExistingMage");
        assertThat(result).isEqualTo(MageControllerCode.NOT_FOUND.toString());
    }

    @Test
    public void delete_whenMageExists_shouldReturnDoneStringAndCallDelete() {
        String result = mageController.delete("ExistingMage");
        verify(mageRepoMock).delete("ExistingMage");
        assertThat(result).isEqualTo(MageControllerCode.DONE.toString());
    }

    @Test
    public void delete_whenMageDoesNotExist_shouldReturnNotFoundString() {
        doThrow(IllegalArgumentException.class)
            .when(mageRepoMock).delete("NonExistingMage");
        String result = mageController.delete("NonExistingMage");
        assertThat(result).isEqualTo(MageControllerCode.NOT_FOUND.toString());
    }

    @Test
    public void save_whenMageExists_shouldReturnBadRequestString() {
        doThrow(IllegalArgumentException.class)
            .when(mageRepoMock).save(new Mage("ExistingMage", 2137));
        String result = mageController.save("ExistingMage", "2137");
        assertThat(result).isEqualTo(MageControllerCode.BAD_REQUEST.toString());
    }

    @Test
    public void save_whenMageDoesNotExist_shouldReturnDoneString() {
        String result = mageController.save("NonExistingMage", "2137");
        verify(mageRepoMock).save(new Mage("NonExistingMage", 2137));
        assertThat(result).isEqualTo(MageControllerCode.DONE.toString());
    }

    @Test
    public void save_badLevelArgument_shouldReturnBadArgumentString() {
        String result = mageController.save("NoMatterIfExistingMage", "BadLevelNumberFormat");
        assertThat(result).isEqualTo(MageControllerCode.BAD_ARGUMENT.toString());
    }
}
