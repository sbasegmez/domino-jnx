/*
 * ==========================================================================
 * Copyright (C) 2019-2022 HCL America, Inc. ( http://www.hcl.com/ )
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
package com.hcl.domino.jna.server;

import java.lang.ref.ReferenceQueue;

import com.hcl.domino.commons.gc.APIObjectAllocations;
import com.hcl.domino.commons.gc.IAPIObject;
import com.hcl.domino.commons.gc.IGCDominoClient;
import com.hcl.domino.jna.BaseJNAAPIObject;
import com.hcl.domino.jna.internal.NotesStringUtils;
import com.hcl.domino.jna.internal.capi.NotesCAPI;
import com.hcl.domino.jna.internal.gc.allocations.JNAServerStatusLineAllocations;
import com.hcl.domino.server.ServerStatusLine;
import com.sun.jna.Memory;

public class JNAServerStatusLine extends BaseJNAAPIObject<JNAServerStatusLineAllocations> implements ServerStatusLine {

	public JNAServerStatusLine(IGCDominoClient<?> client, long hDesc) {
		super(client);
		
		getAllocations().setLineHandle(hDesc);
	}

	@Override
	public void close() {
		getAllocations().dispose();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected JNAServerStatusLineAllocations createAllocations(IGCDominoClient<?> parentDominoClient,
			APIObjectAllocations parentAllocations, ReferenceQueue<? super IAPIObject> queue) {
		return new JNAServerStatusLineAllocations(parentDominoClient, parentAllocations, this, queue);
	}
	
	@Override
	public void setLine(String line) {
		checkDisposed();
		long hDesc = getAllocations().getLineHandle();
		Memory string = NotesStringUtils.toLMBCS(line, true);
		NotesCAPI.get().AddInSetStatusLine(hDesc, string);
	}
	
    @SuppressWarnings("unchecked")
    @Override
    protected <T> T getAdapterLocal(Class<T> clazz) {
      if(long.class.equals(clazz) || Long.class.equals(clazz)) {
        return (T)Long.valueOf(getAllocations().getLineHandle());
      }
      return super.getAdapterLocal(clazz);
    }

}
