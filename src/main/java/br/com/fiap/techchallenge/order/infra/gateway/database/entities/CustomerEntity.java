package br.com.fiap.techchallenge.order.infra.gateway.database.entities;

import br.com.fiap.techchallenge.order.domain.models.Customer;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class CustomerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;

	private String name;

	private String document;

	private String email;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public CustomerEntity() {
	}

	public CustomerEntity(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.document = customer.getDocument().replace(".", "").replace("-", "");
		this.email = customer.getEmail();
	}

	public Customer toCustomer() {
		return new Customer(id, name, document, email);
	}

}
