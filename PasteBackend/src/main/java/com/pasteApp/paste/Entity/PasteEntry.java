package com.pasteApp.paste.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Paste_Data")
@Data
@NoArgsConstructor
public class PasteEntry {

    @Id
    @Indexed(unique = true)
    String _id;
    @NonNull
    @Indexed(unique = true)
    String title;
    String content;
    String createdAt;
}
