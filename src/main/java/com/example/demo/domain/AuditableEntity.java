
package com.example.demo.domain;

import java.util.Date;


public interface AuditableEntity {
	
	public Date getCreatedDate();
	public void setCreatedDate(Date date);
	
	public Date getUpdatedDate();
	public void setUpdatedDate(Date date);
}
