/*
 * ==========================================================================
 * Copyright (C) 2019-2021 HCL America, Inc. ( http://www.hcl.com/ )
 *                            All rights reserved.
 * ==========================================================================
 * Licensed under the  Apache License, Version 2.0  (the "License").  You may
 * not use this file except in compliance with the License.  You may obtain a
 * copy of the License at <http://www.apache.org/licenses/LICENSE-2.0>.
 *
 * Unless  required  by applicable  law or  agreed  to  in writing,  software
 * distributed under the License is distributed on an  "AS IS" BASIS, WITHOUT
 * WARRANTIES OR  CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the  specific language  governing permissions  and limitations
 * under the License.
 * ==========================================================================
 */
package it.com.hcl.domino.test.queries;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.hcl.domino.data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.com.hcl.domino.test.AbstractNotesRuntimeTest;

@SuppressWarnings("nls")
public class TestDocumentSelection extends AbstractNotesRuntimeTest {

  @Test
  public void testDocumentSelection() throws Exception {
    this.withResourceDxl("/dxl/testHtmlRendering", db -> {
      final IDTable designElementNoteIds = db
          .createDocumentSelection()
          .selectAllDesignElements()
          .build();

      Assertions.assertFalse(designElementNoteIds.isEmpty());

      boolean hasForm = false;
      boolean hasView = false;

      for (final Integer currNoteId : designElementNoteIds) {
        // System.out.println("Opening doc with noteid "+currNoteId);

        final Document currDoc = db.getDocumentById(currNoteId).get();
        final Set<DocumentClass> docClass = currDoc.getDocumentClass();
        if (docClass.contains(DocumentClass.FORM)) {
          hasForm = true;
        } else if (docClass.contains(DocumentClass.VIEW)) {
          hasView = true;
        }

        // System.out.println("Class="+currDoc.getDocumentClass()+"\t$TITLE="+currDoc.get("$TITLE",
        // String.class, ""));
      }

      Assertions.assertTrue(hasForm);
      Assertions.assertTrue(hasView);
    });
  }

  @Test
  public void testAllDataDocumentsSelection() throws Exception {
    this.withResourceDxl("/dxl/testDocumentSelection", db -> {

      final IDTable allDocumentNoteIds = db
              .createDocumentSelection()
              .selectAllDataDocuments()
              .build();

      Assertions.assertFalse(allDocumentNoteIds.isEmpty());
      Assertions.assertEquals(1, allDocumentNoteIds.size());

      boolean hasDataDocument = false;

      for (Integer docId : allDocumentNoteIds) {
        Optional<Document> documentById = db.getDocumentById(docId);
        Assertions.assertTrue(documentById.isPresent());
        final Document currentDoc = documentById.get();

        Set<DocumentClass> documentClass = currentDoc.getDocumentClass();
        if (documentClass.contains(DocumentClass.DOCUMENT)) {
          hasDataDocument = true;
        }
      }

      Assertions.assertTrue(hasDataDocument);
    });
  }

  @Test
  public void testAllAdminDocumentsSelection() throws Exception {
    this.withTempDb(db -> {

      final Document replFormulaDocument = db.createDocument();
      replFormulaDocument.setDocumentClass(DocumentClass.REPLFORMULA);
      replFormulaDocument.save();

      final IDTable allAdminDocumentNoteIds = db
              .createDocumentSelection()
              .selectAllAdminDocuments()
              .build();

      boolean hasACL = false;
      boolean hasReplicationFormula = false;

      for (Integer docId : allAdminDocumentNoteIds) {
        Optional<Document> documentById = db.getDocumentById(docId);
        Assertions.assertTrue(documentById.isPresent());
        final Document currentDoc = documentById.get();

        Set<DocumentClass> documentClass = currentDoc.getDocumentClass();
        if (documentClass.contains(DocumentClass.ACL)) {
          hasACL = true;
        } else if (documentClass.contains(DocumentClass.REPLFORMULA)) {
          hasReplicationFormula = true;
        }
      }

      Assertions.assertTrue(hasACL);
      Assertions.assertTrue(hasReplicationFormula);
      Assertions.assertEquals(2, allAdminDocumentNoteIds.size());
    });
  }

  @Test
  public void testAllDocumentsSelection() throws Exception {
    this.withResourceDxl("/dxl/testDocumentSelection", db -> {

      final IDTable allDocumentNoteIds = db
              .createDocumentSelection()
              .selectAllDocuments()
              .build();

      int adminDocumentSize = db
              .createDocumentSelection()
              .selectAllAdminDocuments()
              .build()
              .size();

      int dataDocumentSize = db
              .createDocumentSelection()
              .selectAllDataDocuments()
              .build()
              .size();

      int designElementsSize = db
              .createDocumentSelection()
              .selectAllDesignElements()
              .build()
              .size();

      Assertions.assertFalse(allDocumentNoteIds.isEmpty());
      Assertions.assertEquals(adminDocumentSize + dataDocumentSize + designElementsSize, allDocumentNoteIds.size());

      boolean hasAdminDocs = false;
      boolean hasDataDocument = false;
      boolean hasDesignElements = false;

      for (Integer docId : allDocumentNoteIds) {
        Optional<Document> documentById = db.getDocumentById(docId);
        Assertions.assertTrue(documentById.isPresent());
        final Document currentDoc = documentById.get();

        Set<DocumentClass> documentClass = currentDoc.getDocumentClass();
        if (documentClass.contains(DocumentClass.ACL) || documentClass.contains(DocumentClass.REPLFORMULA)) {
          hasAdminDocs = true;
        } else if (documentClass.contains(DocumentClass.DATA)) {
          hasDataDocument = true;
        } else if (DocumentClass.isDesignElement(documentClass)) {
          hasDesignElements = true;
        }
      }

      Assertions.assertTrue(hasAdminDocs);
      Assertions.assertTrue(hasDataDocument);
      Assertions.assertTrue(hasDesignElements);
    });
  }

  @Test
  public void testDeselectOneContentType() throws Exception {
    this.withTempDb(db -> {
      final IDTable allDocumentNoteIds = db
              .createDocumentSelection()
              .selectAllDocuments()
              .build();

      Assertions.assertFalse(allDocumentNoteIds.isEmpty());

      boolean hasACLDoc = false;

      for (Integer docId : allDocumentNoteIds) {
        Optional<Document> documentById = db.getDocumentById(docId);
        Assertions.assertTrue(documentById.isPresent());
        final Document currentDoc = documentById.get();

        Set<DocumentClass> documentClass = currentDoc.getDocumentClass();
        if (documentClass.contains(DocumentClass.ACL)) {
          hasACLDoc = true;
        }
      }

      Assertions.assertTrue(hasACLDoc);

      final IDTable allDocumentNoteIdsWithoutACL = db
              .createDocumentSelection()
              .selectAllDocuments()
              .deselect(DocumentSelection.SelectionType.ACL)
              .build();

      hasACLDoc = false;

      for (Integer docId : allDocumentNoteIdsWithoutACL) {
        Optional<Document> documentById = db.getDocumentById(docId);
        Assertions.assertTrue(documentById.isPresent());
        final Document currentDoc = documentById.get();

        Set<DocumentClass> documentClass = currentDoc.getDocumentClass();
        if (documentClass.contains(DocumentClass.ACL)) {
          hasACLDoc = true;
        }
      }

      Assertions.assertFalse(hasACLDoc);
    });
  }

  @Test
  public void testDeselectMultipleContentType() throws Exception {
    List<DocumentSelection.SelectionType> selectionTypes = Arrays.asList(DocumentSelection.SelectionType.ACL,
            DocumentSelection.SelectionType.DOCUMENTS);
    this.withResourceDxl("/dxl/testDocumentSelection", db -> {
      final IDTable allDocumentNoteIds = db
              .createDocumentSelection()
              .selectAllDocuments()
              .build();

      Assertions.assertFalse(allDocumentNoteIds.isEmpty());

      boolean hasACLDoc = false;
      boolean hasDataDoc = false;

      for (Integer docId : allDocumentNoteIds) {
        Optional<Document> documentById = db.getDocumentById(docId);
        Assertions.assertTrue(documentById.isPresent());
        final Document currentDoc = documentById.get();

        Set<DocumentClass> documentClass = currentDoc.getDocumentClass();
        if (documentClass.contains(DocumentClass.ACL)) {
          hasACLDoc = true;
        } else if (documentClass.contains(DocumentClass.DOCUMENT)) {
          hasDataDoc = true;
        }
      }

      Assertions.assertTrue(hasACLDoc);
      Assertions.assertTrue(hasDataDoc);

      final IDTable allDocumentNoteIdsWithoutACL = db
              .createDocumentSelection()
              .selectAllDocuments()
              .deselect(selectionTypes)
              .build();

      hasACLDoc = false;
      hasDataDoc = false;

      for (Integer docId : allDocumentNoteIdsWithoutACL) {
        Optional<Document> documentById = db.getDocumentById(docId);
        Assertions.assertTrue(documentById.isPresent());
        final Document currentDoc = documentById.get();

        Set<DocumentClass> documentClass = currentDoc.getDocumentClass();
        if (documentClass.contains(DocumentClass.ACL)) {
          hasACLDoc = true;
        } else if (documentClass.contains(DocumentClass.DOCUMENT)) {
          hasDataDoc = true;
        }
      }

      Assertions.assertFalse(hasACLDoc);
      Assertions.assertFalse(hasDataDoc);
    });
  }

  @Test
  public void testGetSelection() throws Exception {
    this.withTempDb(db -> {
      Set<DocumentSelection.SelectionType> selection = db
              .createDocumentSelection()
              .selectAllDocuments()
              .getSelection();

      Assertions.assertNotNull(selection);
      Assertions.assertFalse(selection.isEmpty());
    });
  }

  @Test
  public void testSelectOneContentType() throws Exception {
    this.withTempDb(db -> {
      final IDTable documentNoteIds = db
              .createDocumentSelection()
              .select(DocumentSelection.SelectionType.ACL)
              .build();

      Assertions.assertNotNull(documentNoteIds);
      Assertions.assertFalse(documentNoteIds.isEmpty());
      Assertions.assertEquals(1, documentNoteIds.size());
    });
  }

  @Test
  public void testSelectMultipleContentType() throws Exception {
    List<DocumentSelection.SelectionType> selectionTypes = Arrays.asList(DocumentSelection.SelectionType.ACL,
            DocumentSelection.SelectionType.DOCUMENTS);
    this.withResourceDxl("/dxl/testDocumentSelection", db -> {
      final IDTable ACLDocumentNoteIds = db
              .createDocumentSelection()
              .select(selectionTypes)
              .build();

      Assertions.assertNotNull(ACLDocumentNoteIds);
      Assertions.assertFalse(ACLDocumentNoteIds.isEmpty());
    });
  }

  @Test
  public void testSelectionFormula() throws Exception {
    this.withResourceDxl("/dxl/testDocumentSelection", db -> {

      final Document doc = db.createDocument();
      doc.replaceItemValue("Form", "Test");
      doc.save();

      DocumentSelection docSelection = db
              .createDocumentSelection();
      final IDTable documentNoteIds = docSelection
              .selectAllDocuments()
              .withSelectionFormula("Form=\"Test\"")
              .build();

      String selectionFormula = docSelection.getSelectionFormula();

      Assertions.assertFalse(selectionFormula.isEmpty());
      Assertions.assertEquals("Form=\"Test\"", selectionFormula);

      Assertions.assertFalse(documentNoteIds.isEmpty());
      Assertions.assertEquals(1, documentNoteIds.size());
    });
  }

  @Test
  public void testPreselection() throws Exception {
    this.withResourceDxl("/dxl/testDocumentSelection", db -> {
      final IDTable preselectionNoteIds = db
              .createDocumentSelection()
              .select(DocumentSelection.SelectionType.FORMS, DocumentSelection.SelectionType.VIEWS, DocumentSelection.SelectionType.ACL)
              .build();
      DocumentSelection docSelection = db
              .createDocumentSelection()
              .withPreselection(preselectionNoteIds);
      final IDTable ids = docSelection
              .select(DocumentSelection.SelectionType.FORMS)
              .build();
      Assertions.assertTrue(docSelection.getPreselection().isPresent());

      Assertions.assertFalse(ids.isEmpty());
      Assertions.assertEquals(1, ids.size());
    });
  }

  @Test
  public void testGetParentDatabase() throws Exception {
    this.withTempDb(db -> {
      Database parentDb = db
              .createDocumentSelection()
              .getParentDatabase();
      Assertions.assertNotNull(parentDb);
    });
  }

  @Test
  public void testGetLastBuildTime() throws Exception {
    this.withTempDb(db -> {
      DocumentSelection docSelection = db.createDocumentSelection();
      docSelection
              .selectAllDocuments()
              .build();

      Optional<DominoDateTime> lastBuildTime = docSelection.getLastBuildTime();
      Assertions.assertTrue(lastBuildTime.isPresent());
      Assertions.assertNotNull(lastBuildTime.get());
    });
  }

  @Test
  public void testGetUntilTime() throws Exception {
    this.withTempDb(db -> {
      DocumentSelection docSelection = db.createDocumentSelection();
      docSelection
              .selectAllDocuments()
              .build();
      Optional<DominoDateTime> untilTime = docSelection.getUntilTime();
      Assertions.assertTrue(untilTime.isPresent());
      Assertions.assertTrue(untilTime.get().isValid());
    });
  }
}
