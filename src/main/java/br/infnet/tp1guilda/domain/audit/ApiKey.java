package br.infnet.tp1guilda.domain.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "api_keys",
        schema = "audit",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_api_keys_nome_por_org", columnNames = {"organizacao_id", "nome"})
        }
)
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizacao_id", nullable = false, foreignKey = @ForeignKey(name = "fk_api_keys_org"))
    private Organization organizacao;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "key_hash", nullable = false, length = 255)
    private String keyHash;

    @Column(name = "ativo", nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "last_used_at")
    private OffsetDateTime lastUsedAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = OffsetDateTime.now();
        }
        if (this.ativo == null) {
            this.ativo = true;
        }
    }
}