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
package com.hcl.domino.richtext.records;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.hcl.domino.richtext.annotation.StructureDefinition;
import com.hcl.domino.richtext.annotation.StructureGetter;
import com.hcl.domino.richtext.annotation.StructureMember;
import com.hcl.domino.richtext.annotation.StructureSetter;
import com.hcl.domino.richtext.structures.AssistFieldStruct;
import com.hcl.domino.richtext.structures.MemoryStructureWrapperService;
import com.hcl.domino.richtext.structures.WSIG;

/**
 * @author Jesse Gallagher
 * @since 1.0.15
 */
@StructureDefinition(name = "CDACTIONBYFORM", members = {
    @StructureMember(name = "Header", type = WSIG.class),
    @StructureMember(name = "dwFlags", type = int.class),
    @StructureMember(name = "wFieldCount", type = short.class, unsigned = true),
    @StructureMember(name = "wFormNameLen", type = short.class, unsigned = true)
})
public interface CDActionByForm extends RichTextRecord<WSIG> {
  /**
   * Retrieves the action data as a {@link ByteBuffer} view of the underlying
   * structure data. This data
   * consists of a series of {@link AssistFieldStruct} structures.
   *
   * @return a {@link ByteBuffer} constrained to the action data
   */
  default ByteBuffer getActionData() {
    final ByteBuffer buf = this.getVariableData();
    final int dataLen = buf.limit();
    final int nameLen = this.getFormNameLength();

    final ByteBuffer result = buf.duplicate();
    result.limit(dataLen - nameLen);
    return result.order(ByteOrder.nativeOrder());
  }

  /**
   * Retrieves the action data as a list of {@link AssistFieldStruct} objects.
   * These objects are views
   * onto the underlying data, though the association will be broken if they are
   * resized.
   *
   * @return a {@link List} of {@link AssistFieldStruct} objects
   */
  default List<AssistFieldStruct> getAssistFields() {
    ByteBuffer buf = this.getActionData();
    final int fieldCount = this.getFieldCount();
    final MemoryStructureWrapperService wrapper = MemoryStructureWrapperService.get();
    final List<AssistFieldStruct> result = new ArrayList<>(fieldCount);
    for (int i = 0; i < fieldCount; i++) {
      // The first two bytes are the length of the record
      final int structLen = Short.toUnsignedInt(buf.getShort(0));
      final ByteBuffer structBuf = buf.slice().order(ByteOrder.nativeOrder());
      structBuf.limit(structLen);
      result.add(wrapper.wrapStructure(AssistFieldStruct.class, structBuf));

      buf.position(structLen);
      buf = buf.slice().order(ByteOrder.nativeOrder());
    }
    return result;
  }

  @StructureGetter("wFieldCount")
  int getFieldCount();

  default String getFormName() {
    return new String(this.getFormNameData(), Charset.forName("LMBCS-native")); //$NON-NLS-1$
  }

  default byte[] getFormNameData() {
    final int formNameLen = this.getFormNameLength();
    final ByteBuffer buf = this.getVariableData();
    buf.position(buf.limit() - formNameLen);
    final byte[] lmbcs = new byte[formNameLen];
    buf.get(lmbcs);
    return lmbcs;
  }

  @StructureGetter("wFormNameLen")
  int getFormNameLength();

  @StructureGetter("Header")
  @Override
  WSIG getHeader();

  /**
   * Sets the action data for this record.
   * <p>
   * Note: when modifying this data manually, it is important to also call
   * {@link #setFieldCount(int)}
   * to reflect the number of {@link AssistFieldStruct} structures present.
   * </p>
   *
   * @param actionData the new action data to set
   * @return this record
   */
  default CDActionByForm setActionData(final byte[] actionData) {
    final byte[] data = actionData == null ? new byte[0] : actionData;
    final byte[] formNameData = this.getFormNameData();

    this.resizeVariableData(data.length + formNameData.length);
    final ByteBuffer buf = this.getVariableData();
    buf.put(data);
    buf.put(formNameData);

    return this;
  }

  /**
   * Sets the action data for this record.
   *
   * @param assistFieldStructs a {@link Collection} of {@link AssistFieldStruct}s
   *                           to set
   * @return this record
   */
  default CDActionByForm setAssistFields(final Collection<AssistFieldStruct> assistFieldStructs) {
    final Collection<AssistFieldStruct> structs = assistFieldStructs == null ? Collections.emptyList() : assistFieldStructs;
    this.setFieldCount(structs.size());
    final int totalSize = structs.stream().mapToInt(AssistFieldStruct::getTotalLength).sum();
    final byte[] newData = new byte[totalSize];
    final ByteBuffer buf = ByteBuffer.wrap(newData).order(ByteOrder.nativeOrder());
    for (final AssistFieldStruct struct : structs) {
      buf.put(struct.getData());
    }
    this.setActionData(newData);

    return this;
  }

  @StructureSetter("wFieldCount")
  CDActionByForm setFieldCount(int fieldCount);

  default CDActionByForm setFormName(final String formName) {
    final byte[] lmbcs = formName == null ? new byte[0] : formName.getBytes(Charset.forName("LMBCS-native")); //$NON-NLS-1$
    final int dataLen = this.getVariableData().limit();
    final int oldNameLen = this.getFormNameLength();
    final int newLen = dataLen - oldNameLen + lmbcs.length;
    this.resizeVariableData(newLen);
    this.setFormNameLength(lmbcs.length);

    final ByteBuffer buf = this.getVariableData();
    buf.position(buf.limit() - lmbcs.length);
    buf.put(lmbcs);

    return this;
  }

  @StructureSetter("wFormNameLen")
  CDActionByForm setFormNameLength(int formNameLength);
}
