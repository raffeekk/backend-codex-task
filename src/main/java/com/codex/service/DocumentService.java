package com.codex.service;

import com.codex.model.Document;
import com.codex.model.ElasticDoc;
import com.codex.utils.JsonReader;
import com.codex.dto.SearchResult;
import com.codex.repository.DocumentRepository;
import com.codex.repository.ElasticDocumentRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepo;

    @Autowired
    private ElasticDocumentRepo elasticDocumentRepo;

    @Autowired
    private JsonReader jsonReader;

    @PostConstruct
    public void loadContentFromJson() {
        if (documentRepo.count() == 0) {
            String filePath = "src/main/resources/static/mocks.json";
            List<Document> contents = jsonReader.readContentFromJson(filePath);
            documentRepo.saveAll(contents);
            contents.forEach(document -> {
                ElasticDoc elasticDoc = new ElasticDoc(document.getId(), document.getTitle(), document.getContent());
                elasticDocumentRepo.save(elasticDoc);
            });
        }
    }

    public Document saveDocumentToDatabase(Document document) {
        Document savedDocument = documentRepo.save(document);
        ElasticDoc elasticDoc = new ElasticDoc(savedDocument.getId(), savedDocument.getTitle(), savedDocument.getContent());
        elasticDocumentRepo.save(elasticDoc);
        return savedDocument;
    }

    public Document updateDocument(Long documentId, Document updatedDocument) {
        Document existingDocument = documentRepo.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        existingDocument.setTitle(updatedDocument.getTitle());
        existingDocument.setContent(updatedDocument.getContent());
        Document savedDocument = documentRepo.save(existingDocument);
        ElasticDoc elasticDoc = new ElasticDoc(savedDocument.getId(), savedDocument.getTitle(), savedDocument.getContent());
        elasticDocumentRepo.save(elasticDoc);
        return savedDocument;
    }

    public List<SearchResult> searchDocuments(String query) {
        List<ElasticDoc> foundDocuments = elasticDocumentRepo.searchBySubstring(query);
        return foundDocuments.stream()
                .flatMap(doc -> {
                    List<SearchResult> results = List.of(
                            new SearchResult(doc.getId(), doc.getTitle(), doc.getContent())
                    );
                    return results.stream();
                })
                .collect(Collectors.toList());
    }
}
