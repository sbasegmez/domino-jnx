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
package com.hcl.domino.design.simplesearch;

import java.util.List;

/**
 * Represents a basic or multi-value text term within a simple search.
 * 
 * @author Jesse Gallagher
 * @since 1.0.38
 */
public interface TextTerm extends SimpleSearchTerm {
  enum Type {
    PLAIN, AND, ACCRUE, NEAR
  }
  
  /**
   * Retrieves the type of operation for multi-value terms.
   * 
   * @return a {@link Type} instance
   */
  Type getType();
  
  /**
   * Retrieves the text values of the term.
   * 
   * @return the text values of the term
   */
  List<String> getValues();
}
