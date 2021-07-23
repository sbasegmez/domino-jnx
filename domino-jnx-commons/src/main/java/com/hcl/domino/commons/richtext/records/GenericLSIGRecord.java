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
package com.hcl.domino.commons.richtext.records;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.hcl.domino.richtext.structures.LSIG;
import com.hcl.domino.richtext.structures.MemoryStructure;

public class GenericLSIGRecord extends AbstractCDRecord<LSIG> {
  public GenericLSIGRecord(final ByteBuffer data) {
    this(data, null);
  }

  public GenericLSIGRecord(final ByteBuffer data, final Class<? extends MemoryStructure> recordClass) {
    super(data, recordClass);
  }

  @Override
  protected void _updateHeaderLength(final long value) {
    this.getHeader().setLength(value);
  }

  @Override
  public LSIG getHeader() {
    final ByteBuffer buf = this.getData().slice();
    buf.limit(6);
    return MemoryStructureProxy.forStructure(LSIG.class, () -> buf.slice().order(ByteOrder.nativeOrder()));
  }
}
