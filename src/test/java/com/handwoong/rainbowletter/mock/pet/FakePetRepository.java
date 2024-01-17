package com.handwoong.rainbowletter.mock.pet;

import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.controller.response.DashboardResponse;
import com.handwoong.rainbowletter.pet.controller.response.PetResponse;
import com.handwoong.rainbowletter.pet.controller.response.PetResponseDto;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.exception.PetResourceNotFoundException;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FakePetRepository implements PetRepository {
    private final Map<Long, Pet> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public Pet findByIdOrElseThrow(final Long id) {
        final Pet pet = database.get(id);
        if (Objects.isNull(pet)) {
            throw new PetResourceNotFoundException(id);
        }
        return pet;
    }

    @Override
    public Pet findByEmailAndIdOrElseThrow(final Email email, final Long id) {
        return Optional.ofNullable(database.get(id))
                .filter(pet -> pet.member().email().equals(email))
                .orElseThrow(() -> new PetResourceNotFoundException(id));
    }

    @Override
    public List<PetResponse> findAllByEmail(final Email email) {
        return database.values()
                .stream()
                .filter(pet -> pet.member().email().equals(email))
                .map(PetResponseDto::from)
                .map(PetResponse::from)
                .toList();
    }

    @Override
    public List<DashboardResponse> findDashboardItems(final Email email) {
        return database.values()
                .stream()
                .filter(pet -> pet.member().email().equals(email))
                .map(pet ->
                        new DashboardResponse(
                                pet.id(), pet.name(), 0L, pet.favorite().total(), ImageResponse.from(pet.image()),
                                pet.deathAnniversary()))
                .toList();
    }

    @Override
    public Pet findByEmailAndIdWithImageOrElseThrow(final Email email, final Long id) {
        return database.values()
                .stream()
                .filter(pet -> pet.member().email().equals(email))
                .filter(pet -> Objects.nonNull(pet.image()) && pet.image().id().equals(id))
                .findAny()
                .orElseThrow(() -> new PetResourceNotFoundException(id));
    }

    @Override
    public Pet save(final Pet pet) {
        Long id = Objects.nonNull(pet.id()) ? pet.id() : sequence++;
        final Pet savePet = createPet(id, pet);
        database.put(id, savePet);
        return savePet;
    }

    @Override
    public void delete(final Pet pet) {
        database.remove(pet.id());
    }

    private Pet createPet(final Long id, final Pet pet) {
        return Pet.builder()
                .id(id)
                .name(pet.name())
                .species(pet.species())
                .owner(pet.owner())
                .personalities(pet.personalities())
                .deathAnniversary(pet.deathAnniversary())
                .favorite(pet.favorite())
                .member(pet.member())
                .image(pet.image())
                .build();
    }
}
