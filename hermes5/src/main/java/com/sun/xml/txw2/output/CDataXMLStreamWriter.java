/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package com.sun.xml.txw2.output;


import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Implementation which is able to decide to use a CDATA section for a string.
 */
public class CDataXMLStreamWriter extends  DelegatingXMLStreamWriter
{
   private static final Pattern XML_CHARS = Pattern.compile( "[&<>]" );

   public CDataXMLStreamWriter( XMLStreamWriter del )
   {
      super( del );
   }

   @Override
   public void writeCharacters( String text ) throws XMLStreamException
   {
      boolean useCData = XML_CHARS.matcher( text ).find();
      if( useCData )
      {
         super.writeCData( text );
      }
      else
      {
         super.writeCharacters( text );
      }
   }
}