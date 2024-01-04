package com.handwoong.rainbowletter.mock.pet;

import com.handwoong.rainbowletter.member.domain.Email;
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
        return findByEmailAndId(email, id)
                .orElseThrow(() -> new PetResourceNotFoundException(id));
    }

    @Override
    public Optional<Pet> findByEmailAndId(final Email email, final Long id) {
        final Pet pet = database.get(id);
        if (Objects.isNull(pet)) {
            return Optional.empty();
        }
        return Optional.ofNullable(pet.member().email().equals(email) ? pet : null);
    }

    @Override
    public List<Pet> findAllByEmail(final Email email) {
        return database.values()
                .stream()
                .filter(pet -> pet.member().email().equals(email))
                .toList();
    }

    @Override
    public Optional<Pet> findByEmailAndIdWithImage(final Email email, final Long id) {
        return database.values()
                .stream()
                .filter(pet -> pet.member().email().equals(email))
                .filter(pet -> Objects.nonNull(pet.image()) && pet.image().id().equals(id))
                .findAny();
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
