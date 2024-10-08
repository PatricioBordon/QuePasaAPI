package frgp.utn.edu.ar.quepasa.service.impl;

import frgp.utn.edu.ar.quepasa.data.request.event.EventPatchEditRequest;
import frgp.utn.edu.ar.quepasa.model.Event;
import frgp.utn.edu.ar.quepasa.model.User;
import frgp.utn.edu.ar.quepasa.repository.EventRepository;
import frgp.utn.edu.ar.quepasa.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service("eventService")
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    @Override
    public Page<Event> getEvents(String query, Pageable pageable) {
        return eventRepository.search(query, pageable, true)
                .orElseThrow(() -> new RuntimeException("No Events found"));
    }

    @Override
    public Event findById(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public Page<Event> findByOp(User owner, Pageable pageable) {
        return eventRepository.findByOwner(owner, pageable)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public Page<Event> findByUsername(String username, Pageable pageable) {
        return eventRepository.findByOwnerUsername(username, pageable)
                .orElseThrow(() -> new RuntimeException("No Events found"));
    }

    @Override
    public Event create(EventPatchEditRequest event, User owner) {
        /*TODO
        *  -Validacion info*/
        Event newEvent = new Event();
        newEvent.setTitle(event.getTitle());
        newEvent.setDescription(event.getDescription());
        newEvent.setStart(event.getStartDate());
        newEvent.setEnd(event.getEndDate());
        newEvent.setCategory(event.getCategory());
        newEvent.setAudience(event.getAudience());
        newEvent.setAddress(event.getAddress());
        newEvent.setActive(true);
        newEvent.setCreatedAt(Timestamp.from(Instant.now()));
        newEvent.setOwner(owner);
        eventRepository.save(newEvent);
        return newEvent;
    }

    @Override
    public Event update(UUID id, EventPatchEditRequest newEvent) {
        return eventRepository.findById(id).map(
                event -> {
                    if (newEvent.getTitle() != null) event.setTitle(newEvent.getTitle());
                    if (newEvent.getDescription() != null) event.setDescription(newEvent.getDescription());
                    if (newEvent.getStartDate() != null) event.setStart(newEvent.getStartDate());
                    if (newEvent.getEndDate() != null) event.setEnd(newEvent.getEndDate());
                    if (newEvent.getCategory() != null) event.setCategory(newEvent.getCategory());
                    if (newEvent.getAudience() != null) event.setAudience(newEvent.getAudience());
                    if (newEvent.getAddress() != null) event.setAddress(newEvent.getAddress());
                    return eventRepository.save(event);
                }
                )
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public void delete(UUID id) {
        eventRepository.deleteById(id);
    }
}
