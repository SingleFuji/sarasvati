/*
    This file is part of Sarasvati.

    Sarasvati is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    Sarasvati is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with Sarasvati.  If not, see <http://www.gnu.org/licenses/>.

    Copyright 2008 Paul Lorenz
*/
package com.googlecode.sarasvati.example.db;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.googlecode.sarasvati.Arc;
import com.googlecode.sarasvati.Engine;
import com.googlecode.sarasvati.NodeToken;
import com.googlecode.sarasvati.hib.HibNode;

@Entity
@DiscriminatorValue( "dump" )
public class DumpNode extends HibNode
{
  @Override
  public void execute (Engine engine, NodeToken token)
  {
    System.out.println( "Accepted into: " + getName() );
    engine.completeExecution( token, Arc.DEFAULT_ARC );
  }
}