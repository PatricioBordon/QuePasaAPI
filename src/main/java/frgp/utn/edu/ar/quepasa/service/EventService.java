package frgp.utn.edu.ar.quepasa.service;

import frgp.utn.edu.ar.quepasa.data.request.event.EventPatchEditRequest;
import frgp.utn.edu.ar.quepasa.model.Event;
import frgp.utn.edu.ar.quepasa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EventService {
    Page<Event> getEvents(String query, Pageable pageable);

    Event findById(UUID id);

    Page<Event> findByOp(User owner, Pageable pageable);

    Page<Event> findByUsername(String username, Pageable pageable);

    Event create(EventPatchEditRequest event, User owner);

    Event update(UUID id, EventPatchEditRequest newEvent);

    void delete(UUID id);
}
