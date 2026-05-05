## Descrição

<!-- Resuma a mudança em 1–3 linhas. Foque no "porquê", não no "o quê". -->

## Tipo de mudança

- [ ] Bug fix (não quebra contrato existente)
- [ ] Nova feature (não quebra contrato existente)
- [ ] Breaking change (quebra contrato — exige bump de versão)
- [ ] Refatoração / melhoria interna
- [ ] Documentação
- [ ] Build / CI / chore

## Issue relacionada

Closes #

## Checklist

- [ ] Código compila localmente (`./mvnw clean verify`)
- [ ] Testes existentes continuam passando
- [ ] Cobri o novo comportamento com testes (quando aplicável)
- [ ] Atualizei a documentação no README / OpenAPI quando relevante
- [ ] Atualizei a coleção Postman quando endpoints mudaram
- [ ] Não introduzi credenciais ou dados sensíveis no diff

## Como testar

<!-- Passos concretos: subir o ambiente, requests Postman/curl, payloads de exemplo. -->

```bash
# exemplo
curl -X GET http://localhost:8080/api/v1/aventureiros
```

## Notas adicionais

<!-- Trade-offs, decisões arquiteturais, follow-ups planejados. -->