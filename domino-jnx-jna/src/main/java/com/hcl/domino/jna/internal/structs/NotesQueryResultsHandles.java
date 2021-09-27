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
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class NotesQueryResultsHandles extends BaseStructure {
	/**
	 * RESULTS_INFO_LIST handle<br>
	 * C type : MEMHANDLE
	 */
	public int hInResults;
	/**
	 * SORT_COLUMN_LIST handle<br>
	 * C type : MEMHANDLE
	 */
	public int hOutFields;
	/**
	 * FIELD_FORMULA_LIST<br>
	 * C type : MEMHANDLE
	 */
	public int hFieldRules;
	/**
	 * COLUMN_COMBINE_LIST handle<br>
	 * C type : MEMHANDLE
	 */
	public int hCombineRules;
	
	public NotesQueryResultsHandles() {
		super();
	}
	
	public static NotesQueryResultsHandles newInstance() {
		return AccessController.doPrivileged((PrivilegedAction<NotesQueryResultsHandles>) () -> {
			return new NotesQueryResultsHandles();
		});
	}
	
	@SuppressWarnings("nls")
  @Override
  protected List<String> getFieldOrder() {
		return Arrays.asList("hInResults", "hOutFields", "hFieldRules", "hCombineRules");
	}
	
	/**
	 * @param hInResults RESULTS_INFO_LIST handle<br>
	 * C type : MEMHANDLE<br>
	 * @param hOutFields SORT_COLUMN_LIST handle<br>
	 * C type : MEMHANDLE<br>
	 * @param hFieldRules FIELD_FORMULA_LIST<br>
	 * C type : MEMHANDLE<br>
	 * @param hCombineRules COLUMN_COMBINE_LIST handle<br>
	 * C type : MEMHANDLE
	 */
	public NotesQueryResultsHandles(int hInResults, int hOutFields, int hFieldRules, int hCombineRules) {
		super();
		this.hInResults = hInResults;
		this.hOutFields = hOutFields;
		this.hFieldRules = hFieldRules;
		this.hCombineRules = hCombineRules;
	}
	
	public NotesQueryResultsHandles(Pointer peer) {
		super(peer);
	}
	
	public static class ByReference extends NotesQueryResultsHandles implements Structure.ByReference {
		
	};
	
	public static class ByValue extends NotesQueryResultsHandles implements Structure.ByValue {
		
	};
}
