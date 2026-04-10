package br.infnet.tp1guilda.repository.aventura;


import br.infnet.tp1guilda.domain.aventura.Companheiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanheiroRepository extends JpaRepository<Companheiro, Long> {
}