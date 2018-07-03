
package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

import java.util.Date;

@Data
public abstract class AuditableNodeEntity implements AuditableEntity{

	   @Id @GeneratedValue
	   @lombok.Setter(AccessLevel.NONE)
	   private Long nodeId;
	   
	   private Date createdDate;
		  
	   private Date updatedDate;

	   @Override
	   public abstract boolean equals(Object o);

	   @Override
	   public abstract int hashCode();
}
