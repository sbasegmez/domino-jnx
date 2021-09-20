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
package com.hcl.domino.jna.internal.structs;

import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.List;
import com.hcl.domino.commons.structs.WrongArraySizeException;
import com.hcl.domino.jna.internal.structs.DirectoryAssistanceStruct;
import com.hcl.domino.data.IAdaptable;
import com.hcl.domino.misc.NotesConstants;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * JNA class for the CreateDAConfigStruct type
 * 
 * @author Raghu M R
 */
public class CreateDAConfigStruct extends BaseStructure implements Serializable, IAdaptable {

  private static final long serialVersionUID = 1L;

  public boolean bUpdateServerDoc;
  public short wSearchOrder;
  public byte[] szDNSearch = new byte[NotesConstants.MAXLDAPBASE];
  public boolean bAcceptExpiredCertificates;
  public boolean bVerifyRemoteSrvCert;
  public short wTimeout;
  public short wMaxEntriesReturned;
  public DirectoryAssistanceStruct daStruct;

  public CreateDAConfigStruct( boolean updateServerDoc, short searchOrder, byte dnSearch[], boolean acceptExpiredCertificates,
      boolean verifyRemoteSrvCert, short timeout, short maxEntriesReturned,
      DirectoryAssistanceStruct daStruct) {
    
    super();
    
    this.bUpdateServerDoc = updateServerDoc;
    
    this.wSearchOrder = searchOrder;
    
    if ((dnSearch.length != this.szDNSearch.length)) {
        throw new WrongArraySizeException("dnSearch");
    }
    this.szDNSearch = dnSearch;
    
    this.bAcceptExpiredCertificates = acceptExpiredCertificates;
    
    this.bVerifyRemoteSrvCert = verifyRemoteSrvCert;
    
    this.wTimeout = timeout;

    this.wMaxEntriesReturned = maxEntriesReturned;
    
    this.daStruct = daStruct;
  }

    public CreateDAConfigStruct() {
      super();
    }
  
	public static CreateDAConfigStruct newInstance() {
		return AccessController.doPrivileged((PrivilegedAction<CreateDAConfigStruct>) () -> new CreateDAConfigStruct());
	}

	public static CreateDAConfigStruct.ByValue newInstanceByVal() {
		return AccessController.doPrivileged((PrivilegedAction<ByValue>) () -> new CreateDAConfigStruct.ByValue());
	}	
	
	@Override
	protected List<String> getFieldOrder() {
		return Arrays.asList(  "szServerName",
		    "bUpdateServerDoc",
		    "wSearchOrder",
		    "szDNSearch",
		    "bAcceptExpiredCertificates",
		    "bVerifyRemoteSrvCert",
		    "wTimeout",
		    "wMaxEntriesReturned",
		    "daStruct"
		    );
	}
			
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAdapter(Class<T> clazz) {
		if (clazz == CreateDAConfigStruct.class) {
			return (T) this;
		}
		else if (clazz == Pointer.class) {
			return (T) getPointer();
		}
		return null;
	}
	
	public static class ByReference extends CreateDAConfigStruct implements Structure.ByReference {
		private static final long serialVersionUID = -2958581285484373942L;		
	};
	
	public static class ByValue extends CreateDAConfigStruct implements Structure.ByValue {
		private static final long serialVersionUID = -6538673668884547829L;		
	};
	
}
