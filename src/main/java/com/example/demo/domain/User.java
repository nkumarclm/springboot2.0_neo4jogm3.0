package com.example.demo.domain;

import lombok.*;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=false, of = {"userRefId"})
@NodeEntity
public class User extends AuditableNodeEntity {

    String userRefId;
}
