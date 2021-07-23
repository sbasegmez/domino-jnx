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
package com.hcl.domino.commons.design.action;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import com.hcl.domino.design.action.FolderBasedAction;
import com.hcl.domino.richtext.records.CDActionFolder;

/**
 * @author Jesse Gallagher
 * @since 1.0.24
 */
public class DefaultFolderBasedAction implements FolderBasedAction {
  private final String folderName;
  private final Set<CDActionFolder.Flag> flags;
  private final Type type;

  public DefaultFolderBasedAction(final String folderName, final Collection<CDActionFolder.Flag> flags, final Type type) {
    this.folderName = folderName;
    this.flags = EnumSet.copyOf(flags);
    this.type = type;
  }

  @Override
  public String getFolderName() {
    return this.folderName;
  }

  @Override
  public Type getType() {
    return this.type;
  }

  @Override
  public boolean isCreateNewFolder() {
    return this.flags.contains(CDActionFolder.Flag.NEWFOLDER);
  }

  @Override
  public boolean isFolderPrivate() {
    return this.flags.contains(CDActionFolder.Flag.PRIVATEFOLDER);
  }

}
