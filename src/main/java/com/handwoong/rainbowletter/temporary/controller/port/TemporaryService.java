package com.handwoong.rainbowletter.temporary.controller.port;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.temporary.domain.Temporary;
import com.handwoong.rainbowletter.temporary.domain.dto.TemporaryCreate;
import com.handwoong.rainbowletter.temporary.domain.dto.TemporaryUpdate;

public interface TemporaryService {

    String create(Email email, TemporaryCreate temporaryCreate);

    void delete(Email email, Long id);

    String changeSession(Email email, Long id);

    void update(Email email, TemporaryUpdate temporaryUpdate);

    boolean exists(Email email, Long petId);

    Temporary findByPetId(Email email, Long petId);
}
