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

import com.hcl.domino.design.action.ReplyAction;
import com.hcl.domino.richtext.records.CDActionReply.Flag;

public class DefaultReplyAction implements ReplyAction {
  private final Set<Flag> flags;
  private final String body;

  public DefaultReplyAction(final Collection<Flag> flags, final String body) {
    this.flags = EnumSet.copyOf(flags);
    this.body = body;
  }

  @Override
  public String getBody() {
    return this.body;
  }

  @Override
  public boolean isIncludeDocument() {
    return this.flags.contains(Flag.INCLUDEDOC);
  }

  @Override
  public boolean isNoAgentReply() {
    return this.flags.contains(Flag.NOAGENTREPLY);
  }

  @Override
  public boolean isReplyOnce() {
    return this.flags.contains(Flag.REPLYONCE);
  }

  @Override
  public boolean isReplyToAll() {
    return this.flags.contains(Flag.REPLYTOALL);
  }

  @Override
  public boolean isSaveMailDocument() {
    return this.flags.contains(Flag.SAVEMAIL);
  }

}
