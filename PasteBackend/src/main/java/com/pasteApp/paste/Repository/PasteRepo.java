package com.pasteApp.paste.Repository;

import com.pasteApp.paste.Entity.PasteEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasteRepo extends MongoRepository<PasteEntry, String> {
}
