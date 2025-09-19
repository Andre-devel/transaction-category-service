package br.com.andredevel.transaction.category.service.domain.model.entity;

import br.com.andredevel.transaction.category.service.domain.model.id.TransactionCategoryId;
import br.com.andredevel.transaction.category.service.domain.model.id.UserId;
import br.com.andredevel.transaction.category.service.domain.model.valueobject.Name;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TransactionCategory {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "UUID"))
    private TransactionCategoryId id;
    
    @AttributeOverride(name ="value", column = @Column(name = "id_user", columnDefinition = "UUID"))
    private UserId idUser;

    @AttributeOverride(name = "value", column = @Column(name = "name", columnDefinition = "string"))
    private Name name;
    
    private String description;

    @CreatedDate
    private OffsetDateTime createdAt;

    @LastModifiedDate
    private OffsetDateTime updatedAt;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;
}
