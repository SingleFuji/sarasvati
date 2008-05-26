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
/**
 * Created on Apr 25, 2008
 */
package org.codemonk.wf.hib;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codemonk.wf.Arc;
import org.codemonk.wf.Engine;
import org.codemonk.wf.GuardResponse;
import org.codemonk.wf.Node;
import org.codemonk.wf.NodeToken;
import org.codemonk.wf.guardlang.GuardLang;
import org.codemonk.wf.guardlang.PredicateRepository;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.Type;

@Entity
@Table (name="wf_node")
@Inheritance (strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula( "(select t.behaviour from wf_node_type t where t.id = type)" )
@DiscriminatorValue( "node" )
public class HibNode implements Node
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  protected Long   id;

  @ManyToOne (fetch=FetchType.EAGER)
  @JoinColumn( name="graph_id")
  protected HibGraph graph;

  protected String name;
  protected String type;

  @Column (name="is_join")
  @Type (type="yes_no")
  protected boolean join;

  protected String guard;

  public Long getId ()
  {
    return id;
  }

  public void setId (Long id)
  {
    this.id = id;
  }

  @Override
  public String getName ()
  {
    return name;
  }

  public void setName (String name)
  {
    this.name = name;
  }

  @Override
  public String getType ()
  {
    return type;
  }

  public void setType (String type)
  {
    this.type = type;
  }

  public HibGraph getGraph ()
  {
    return graph;
  }

  public void setGraph (HibGraph graph)
  {
    this.graph = graph;
  }

  @Override
  public boolean isJoin ()
  {
    return join;
  }

  public void setJoin (boolean join)
  {
    this.join = join;
  }

  @Override
  public String getLabel ()
  {
    return "";
  }

  public String getGuard()
  {
    return guard;
  }

  public void setGuard( String guard )
  {
    this.guard = guard;
  }

  @Override
  public GuardResponse guard (Engine engine, NodeToken token)
  {
    if ( guard == null || guard.trim().length() == 0 )
    {
      return GuardResponse.ACCEPT_TOKEN_RESPONSE;
    }

    return GuardLang.eval( guard, PredicateRepository.newGuardEnv( engine, token ) );
  }

  @Override
  public void execute (Engine engine, NodeToken token)
  {
    engine.completeExecuteNode( token, Arc.DEFAULT_ARC );
  }

  @Override
  public int hashCode ()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ( ( id == null )
        ? 0 : id.hashCode() );
    return result;
  }

  @Override
  public boolean equals (Object obj)
  {
    if ( this == obj ) return true;
    if ( obj == null ) return false;
    if ( !( obj instanceof HibNode ) ) return false;
    final HibNode other = (HibNode)obj;
    if ( id == null )
    {
      if ( other.id != null ) return false;
    }
    else if ( !id.equals( other.id ) ) return false;
    return true;
  }
}