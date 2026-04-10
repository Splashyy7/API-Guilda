package br.infnet.tp1guilda.repository.audit;

import br.infnet.tp1guilda.domain.audit.AuditEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEntryRepository extends JpaRepository<AuditEntry, Long> {
}
