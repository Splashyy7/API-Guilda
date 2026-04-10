package br.infnet.tp1guilda.repository.aventura;

import br.infnet.tp1guilda.domain.aventura.Aventureiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AventureiroRepository extends JpaRepository<Aventureiro, Long>, JpaSpecificationExecutor<Aventureiro> {
}