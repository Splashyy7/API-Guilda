package br.infnet.tp1guilda.repository.audit;

import br.infnet.tp1guilda.domain.audit.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
}
