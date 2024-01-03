package com.handwoong.rainbowletter.mock.favorite;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.favorite.service.port.FavoriteRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FakeFavoriteRepository implements FavoriteRepository {
    private final Map<Long, Favorite> database = new HashMap<>();

    private Long sequence = 1L;
    private LocalDateTime lastIncreaseAt;

    public FakeFavoriteRepository() {
        this(LocalDate.now().atStartOfDay());
    }

    public FakeFavoriteRepository(final LocalDateTime lastIncreaseAt) {
        this.lastIncreaseAt = lastIncreaseAt;
    }

    @Override
    public Favorite save(final Favorite favorite) {
        Long id = Objects.nonNull(favorite.id()) ? favorite.id() : sequence++;
        final Favorite saveFavorite = createFavorite(id, favorite);
        database.put(id, saveFavorite);
        return saveFavorite;
    }

    private Favorite createFavorite(final Long id, final Favorite favorite) {
        return Favorite.builder()
                .id(id)
                .total(favorite.total())
                .dayIncreaseCount(favorite.dayIncreaseCount())
                .canIncrease(favorite.canIncrease())
                .lastIncreaseAt(lastIncreaseAt)
                .build();
    }

    @Override
    public Optional<Favorite> findById(final Long id) {
        return database.values()
                .stream()
                .filter(favorite -> favorite.id().equals(id))
                .findAny();
    }
}
