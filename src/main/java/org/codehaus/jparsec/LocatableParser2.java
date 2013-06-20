/*****************************************************************************
 * Copyright (C) Codehaus.org                                                *
 * ------------------------------------------------------------------------- *
 * Licensed under the Apache License, Version 2.0 (the "License");           *
 * you may not use this file except in compliance with the License.          *
 * You may obtain a copy of the License at                                   *
 *                                                                           *
 * http://www.apache.org/licenses/LICENSE-2.0                                *
 *                                                                           *
 * Unless required by applicable law or agreed to in writing, software       *
 * distributed under the License is distributed on an "AS IS" BASIS,         *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 * See the License for the specific language governing permissions and       *
 * limitations under the License.                                            *
 *****************************************************************************/

package org.codehaus.jparsec;

public class LocatableParser2<T, U> extends Parser<T> {
	  private final Parser<T> parser;
	  private LocatableHandler<U> handler;

	  public LocatableParser2(Parser<T> parser, LocatableHandler<U> handler) {
	    this.parser = parser;
	    this.handler = handler;
	  }

	  @SuppressWarnings("unchecked")
	  @Override boolean apply(ParseContext ctxt) {
	    int begin = ctxt.getIndex();
	    if (!parser.apply(ctxt)) {
	      return false;
	    }

	    handler.handle((U) ctxt.result, ctxt.source.subSequence(begin, ctxt.getIndex()).toString(), ctxt.module, begin, ctxt.getIndex());
	    
	    if(ctxt.result instanceof Locatable) {
	      Locatable locatable = (Locatable) ctxt.result;
	      locatable.setLocation(begin,ctxt.getIndex());
	      locatable.setSource(ctxt.source.subSequence(begin, ctxt.getIndex()).toString());
	    }

	    return true;
	  }

	  @Override public String toString() {
	    return "source";
	  }
}