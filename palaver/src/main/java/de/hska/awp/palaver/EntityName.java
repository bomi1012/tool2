package de.hska.awp.palaver;

public class EntityName {
	public EntityName() {
		super();
	}
	
	protected Long id;
	protected String name;
	
	public Long getId() { return this.id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
}
